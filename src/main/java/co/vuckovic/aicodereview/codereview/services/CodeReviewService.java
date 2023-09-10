package co.vuckovic.aicodereview.codereview.services;

import co.vuckovic.aicodereview.bitbucket.services.BitbucketFileContentService;
import co.vuckovic.aicodereview.bitbucket.services.BitbucketGeneralRequestService;
import co.vuckovic.aicodereview.codereview.config.BitbucketConfig;
import co.vuckovic.aicodereview.codereview.config.GeneralCodeReviewConfig;
import co.vuckovic.aicodereview.codereview.dtos.CodeReviewResponse;
import co.vuckovic.aicodereview.codereview.dtos.Pr;
import co.vuckovic.aicodereview.codereview.dtos.PrCodeReviewResponse;
import co.vuckovic.aicodereview.shared.dtos.Range;
import co.vuckovic.aicodereview.shared.dtos.requests.ChatRequestOpenAI;
import co.vuckovic.aicodereview.shared.dtos.requests.MessageRole;
import co.vuckovic.aicodereview.shared.dtos.responses.ChatCompletionResponse;
import co.vuckovic.aicodereview.shared.services.ChatService;
import co.vuckovic.aicodereview.shared.util.MapUtil;
import co.vuckovic.aicodereview.shared.util.StringTemplates;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import co.vuckovic.aicodereview.bitbucket.services.BitbucketPullRequestService;

@Service
@RequiredArgsConstructor
public class CodeReviewService {

    private final ChatService chatService;
    private final BitbucketFileContentService bitbucketFileContentService;
    private final BitbucketGeneralRequestService bitbucketGeneralRequestService;
    private final BitbucketPullRequestService bitbucketPullRequestService;
    private final StringTemplates stringTemplates;
    private final BitbucketConfig bitbucketConfig;
    private final GeneralCodeReviewConfig generalCodeReviewConfig;


    public ResponseEntity<Boolean> isPromptPlainCode(ChatRequestOpenAI chatRequest) {
        Boolean isPromptPlainCode = false;
        String prompt = chatRequest.getMessages().get(0).getContent();
        String stripTextFromCodePrompt = stringTemplates.stripTextFromCodeTemplate(prompt);
        String stripTextFromCode = chatService.chatOpenAI(
                        MessageRole.USER.getValue(),
                        stripTextFromCodePrompt)
                .getChoices().get(0).getMessage().getContent();

        if (prompt.equals(stripTextFromCode)) {
            isPromptPlainCode = true;
        }
        return ResponseEntity.ok(isPromptPlainCode);
    }

    public Map<String, String> codeReview(String code) {
        String securityCheckCodeReview = stringTemplates.securityCheck();
        String codeStyleCodeReview = stringTemplates.codeStyleCheck();
        String generalCodeReview = stringTemplates.generalCodeReview();
        ChatCompletionResponse securityCheckCodeReviewResponse =
                chatService.chatOpenAI(MessageRole.USER.getValue(), securityCheckCodeReview + code);
        ChatCompletionResponse codeStyleResponse =
                chatService.chatOpenAI(MessageRole.USER.getValue(), codeStyleCodeReview + code);
        ChatCompletionResponse generalCodeReviewResponse =
                chatService.chatOpenAI(MessageRole.USER.getValue(), generalCodeReview + code);

        Map<String, String> mapSecurityCheckCodeReviewResponseOutput =
                mapCodeReviewOutput(securityCheckCodeReviewResponse.getChoices().get(0).getMessage().getContent());
        Map<String, String> mapCodeStyleResponseOutput =
                mapCodeReviewOutput(codeStyleResponse.getChoices().get(0).getMessage().getContent());
        Map<String, String> mapGeneralCodeReviewResponseOutput =
                mapCodeReviewOutput(generalCodeReviewResponse.getChoices().get(0).getMessage().getContent());

        return concatenateMaps(
                mapSecurityCheckCodeReviewResponseOutput,
                mapCodeStyleResponseOutput,
                mapGeneralCodeReviewResponseOutput);
    }

    public Map<String, String> mapCodeReviewOutput(String codeReviewOutput) {
        Map<String, String> codeReviewResult = new HashMap<>();

        String[] lines = codeReviewOutput.split("\n");
        for (String line : lines) {
            String[] parts = line.split(": ");
            if (parts.length == 3) {
                  if(Integer.parseInt(parts[1]) >= generalCodeReviewConfig.getImportanceRateThreshold()) {
                    String lineNumber = parts[0].substring(parts[0].indexOf("Line ") + 5);
                    String description = parts[2];
                    codeReviewResult.put(lineNumber, description);
                }
            }
        }

        return codeReviewResult;
    }

    private Map<String, String> concatenateMaps(
            Map<String, String> map1, Map<String, String> map2, Map<String, String> map3) {
        Map<String, String> concatenatedMap = new HashMap<>();

        // Add all entries from map1
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            concatenatedMap.put(entry.getKey(), entry.getValue());
        }

        // Merge entries from map2
        for (Map.Entry<String, String> entry : map2.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (concatenatedMap.containsKey(key)) {
                String existingValue = concatenatedMap.get(key);
                concatenatedMap.put(key, existingValue + value);
            } else {
                concatenatedMap.put(key, value);
            }
        }

        // Merge entries from map3
        for (Map.Entry<String, String> entry : map3.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (concatenatedMap.containsKey(key)) {
                String existingValue = concatenatedMap.get(key);
                concatenatedMap.put(key, existingValue + value);
            } else {
                concatenatedMap.put(key, value);
            }
        }

        return MapUtil.getSortedMapByKey(concatenatedMap);
    }

    public String getCommits(String username, String repositoryAccessToken) {
        return bitbucketGeneralRequestService.commits(username, repositoryAccessToken);

    }

    public String getPullRequests(String username, String repo, String repositoryAccessToken) {
        return bitbucketGeneralRequestService.pullRequests(username, repo, repositoryAccessToken);
    }

    public String sendCodeReviewToBitbucket(String username, String repo, String branch, String repositoryAccessToken) {

        List<PrCodeReviewResponse> prCodeReviewResponseList = new ArrayList<>();
        String pullRequests = bitbucketGeneralRequestService.pullRequests(username, repo, repositoryAccessToken);

        List<Pr> prs = extractIds(pullRequests, username, repo, repositoryAccessToken);

        for(Pr pr: prs) {
            Integer id = pr.getId();
            PrCodeReviewResponse prCodeReviewResponse = new PrCodeReviewResponse();
            prCodeReviewResponse.setPrId(id);
            List<CodeReviewResponse> codeReviewResponseList = new ArrayList<>();

            try {
                String listOfFiles = bitbucketPullRequestService
                        .getListOfModifiedFilesAtThePullRequest(
                                String.valueOf(id),
                                username,
                                repo,
                                repositoryAccessToken);

                List<String> paths = extractPaths(listOfFiles);

                for (String path: paths) {
                    CodeReviewResponse codeReviewResponse = new CodeReviewResponse();

                    String commit = extractCommit(
                            bitbucketPullRequestService
                                    .getListOfModifiedFilesAtThePullRequest(
                                            String.valueOf(id),
                                            username,
                                            repo,
                                            repositoryAccessToken)
                            , path);


                    String code = bitbucketFileContentService
                            .getFileContentFromBitbucket(
                                    username,
                                    repo,
                                    branch,
                                    repositoryAccessToken,
                                    path);



                    Map<String, String> comments = codeReview(code);

                    if (!bitbucketConfig.isWholeFlag()) {
                        Map<String, String> filteredComments = new HashMap<>();
                        Map<String, List<Integer>> commentsMap = parseDiffString(
                                code,
                                bitbucketPullRequestService
                                        .getDiff(
                                                String.valueOf(id),
                                                username,
                                                repo,
                                                repositoryAccessToken));

                        List<Integer> commentsNumberList = new ArrayList<>();

                        for (String key : commentsMap.keySet()) {
                            if(key.equals(path)) {
                                commentsNumberList = commentsMap.get(key);
                            }
                        }

                        for (Map.Entry<String, String> entry : comments.entrySet()) {
                            String lineKey = entry.getKey();
                            Range lineRange = extractRange(lineKey);
                            if (checkLineIfModified(commentsNumberList, lineRange)) {
                                filteredComments.put(lineKey, entry.getValue());
                            }
                        }
                        codeReviewResponse.setComments(filteredComments);

                    } else {
                        codeReviewResponse.setComments(comments);
                    }

                    codeReviewResponse.setFilePath(path);
                    codeReviewResponseList.add(codeReviewResponse);
                }

                prCodeReviewResponse.setCodeReviews(codeReviewResponseList);
                prCodeReviewResponseList.add(prCodeReviewResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bitbucketPullRequestService.prCodeReviewResponseList(
                username,
                repo,
                repositoryAccessToken,
                prCodeReviewResponseList);

    }

    public  List<Pr> extractIds(String json, String username, String repo, String repositoryAccessToken) {
        List<Pr> prList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            Pr pr;
            JsonNode valuesNode = rootNode.get("values");
            if (valuesNode != null && valuesNode.isArray()) {
                for (JsonNode valueNode : valuesNode) {

                    JsonNode stateNode = valueNode.get("state");
                    if (stateNode != null && stateNode.asText().equals("OPEN")) {

                        JsonNode idNode = valueNode.get("id");
                        JsonNode sourceNode = valueNode.get("source");
                        JsonNode branchNode = sourceNode.get("branch");
                        JsonNode nameNode = branchNode.get("name");
                        String branchName = nameNode.asText();
                        if (idNode != null && idNode.isNumber()) {
                            pr = Pr.builder()
                                    .id(idNode.asInt())
                                    .name(branchName).build();
                            if (checkIfCommentsAreResolved(idNode.asInt(), username, repo, repositoryAccessToken ) &&
                                    !checkIfPrIsApproved(idNode.asInt(), username, repo, repositoryAccessToken))
                                prList.add(pr);
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prList;
    }

    private List<String> extractPaths(String json) {
        List<String> pathList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode valuesNode = rootNode.path("values");

            for (JsonNode valueNode : valuesNode) {
                JsonNode newPathNode = valueNode.path("new").path("path");
                String path = newPathNode.asText();
                pathList.add(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathList;
    }

//    public String getCodeReviewResponse(String username, String repo, String repositoryAccessToken, CodeReviewResponse codeReviewResponse){
//        return codeReviewClient.getCodeReviewResponse(username, repo, repositoryAccessToken, codeReviewResponse);
//        return codeReviewClient.getCodeReviewResponse(username, repo, repositoryAccessToken, codeReviewResponse);
//    }

    public String getCommentsFromPR(String username, String repo, String repositoryAccessToken) {
        String pr = bitbucketGeneralRequestService.pullRequests(username, repo, repositoryAccessToken);
        return "";
    }

    private boolean checkIfCommentsAreResolved(Integer id, String username, String repo, String repositoryAccessToken) {
//        String comments = codeReviewClient.getCommentsFromThePullRequest(String.valueOf(id), username, repo, repositoryAccessToken);
        String comments = bitbucketPullRequestService.getCommentsFromThePullRequest(String.valueOf(id), username, repo, repositoryAccessToken);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(comments);
            JsonNode valuesNode = rootNode.get("values");

            if (valuesNode != null && valuesNode.isArray()) {
                for (JsonNode valueNode : valuesNode) {
                    JsonNode inlineNode = valueNode.get("inline");

                    if (inlineNode != null && !inlineNode.isNull()) {
                        JsonNode fromNode = inlineNode.get("from");
                        JsonNode toNode = inlineNode.get("to");

                        if (fromNode != null && !fromNode.isNull()) {
                            return false;
                        }

                        if (toNode != null && !toNode.isNull()) {
                            return false;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean checkIfPrIsApproved(Integer id, String username, String repo, String repositoryAccessToken) throws IOException {
        String pr = bitbucketPullRequestService.getPullRequest(String.valueOf(id), username, repo, repositoryAccessToken);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(pr);

        // Get the "approved" field from the participants array
        JsonNode participants = root.get("participants");
        boolean isApproved = false;
        if (participants.isArray()) {
            for (JsonNode participant : participants) {
                JsonNode approved = participant.get("approved");
                if (approved != null && approved.asBoolean()) {
                    isApproved = true;
                    break;
                }
            }
        }

        return isApproved;
    }

    public Map<String, List<Integer>> parseDiffString(String fileContent, String diffString) {
        Map<String, List<Integer>> modifiedLinesMap = new HashMap<>();

        String[] diffBlocks = diffString.split("diff --git ");
        for (String diffBlock : diffBlocks) {
            String[] lines = diffBlock.split("\\r?\\n");
            if (lines.length > 2) {
                String filePathLine = lines[0].trim();
                String[] filePathParts = filePathLine.split(" ");
                if (filePathParts.length >= 2) {
                    String fileName = filePathParts[1].substring(2);

                    List<Integer> modifiedLines = new ArrayList<>();

                    for (int i = 0; i < lines.length; i++) {
                        String line = lines[i].trim();
                        if (line.startsWith("+") && !line.startsWith("++")) {

                            modifiedLines.add(findLineNumber(fileContent, line.substring(1).trim()));
                        }
                    }
                    modifiedLinesMap.put(fileName, modifiedLines);
                }
            }
        }

        return modifiedLinesMap;
    }

    private Range extractRange(String input) {
        int from = 0;
        int to = 0;
        boolean fromFound = false;
        Pattern pattern = Pattern.compile("\\b\\d+\\b");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();
            try {
                int number = Integer.parseInt(match);
                if (!fromFound) {
                    from = Math.max(number, 1);
                    fromFound = true;
                } else {
                    to = Math.max(number, from);
                    break;
                }
            } catch (NumberFormatException ignored) {
                // Ignore non-integer matches
            }
        }

        return new Range(Math.max(from, 1), Math.max(to, from));
    }

    private Boolean checkLineIfModified(List<Integer> numberList, Range range) {
        for (int number : numberList) {
            if (number >= range.getFrom() && number <= range.getTo()) {
                return true;
            }
        }
        return false;
    }

    public int findLineNumber(String input, String lineToSearch) {
        if (StringUtils.hasText(input) && StringUtils.hasText(lineToSearch)) {
            String[] lines = input.split("\\r?\\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i].trim();
                if (line.equals(lineToSearch)) {
                    return i + 1; // Line numbers are 1-based
                }
            }
        }
        return -1; // Indicates line not found
    }

    private String extractUrl(String stringDiff, String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse the JSON string
            JsonNode jsonNode = objectMapper.readTree(stringDiff);

            // Iterate over the values array
            for (JsonNode valueNode : jsonNode.get("values")) {
                JsonNode newPathNode = valueNode.get("new").get("path");
                if (newPathNode != null && newPathNode.asText().equals(path)) {
                    JsonNode hrefNode = valueNode.get("new").get("links").get("self").get("href");
                    if (hrefNode != null) {
                        return hrefNode.asText();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String extractCommit(String stringDiff, String path) {

        String url = extractUrl(stringDiff, path);

        String pattern = "/src/([^/]+)/";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

}