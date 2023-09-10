package co.vuckovic.aicodereview.bitbucket.services;

import co.vuckovic.aicodereview.bitbucket.clients.BitbucketPullRequestsClient;
import co.vuckovic.aicodereview.bitbucket.dtos.requests.comment.commentrequest.CommentRequestDTO;
import co.vuckovic.aicodereview.bitbucket.dtos.requests.comment.commentrequest.Content;
import co.vuckovic.aicodereview.bitbucket.dtos.requests.comment.commentrequest.Inline;
import co.vuckovic.aicodereview.bitbucket.dtos.requests.pullrequest.comment.PRCommentResponseWrapper;
import co.vuckovic.aicodereview.codereview.dtos.CodeReviewResponse;
import co.vuckovic.aicodereview.codereview.dtos.PrCodeReviewResponse;
import co.vuckovic.aicodereview.shared.dtos.Range;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BitbucketPullRequestService {
    private final BitbucketPullRequestsClient bitbucketPullRequestsClient;
    public String getListOfModifiedFilesAtThePullRequest(
            String id,
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketPullRequestsClient
                .getListOfModifiedFilesAtThePullRequest(
                        id,
                        username,
                        repo,
                        "Bearer " + repositoryAccessToken);
    }

    public String postACommentOnAFileAtThePullRequest(
            String id,
            String username,
            String repo,
            CommentRequestDTO commentRequestDTO,
            String authorizationRepositoryAccessToken){
        return bitbucketPullRequestsClient
                .postACommentOnAFileAtThePullRequest(
                        id,
                        username,
                        repo,
                        commentRequestDTO,
                        authorizationRepositoryAccessToken
                );
    }

    public String prCodeReviewResponseList(
            String username,
            String repo,
            String repositoryAccessToken,
            List<PrCodeReviewResponse> prCodeReviewResponseList) {

        AtomicReference<String> comment = new AtomicReference<>("");

        prCodeReviewResponseList.stream()
                .forEach(prCodeReview -> {
                    Integer prId = prCodeReview.getPrId();

                    List<CodeReviewResponse> codeReviews = prCodeReview.getCodeReviews();

                    PRCommentResponseWrapper prCRW =
                            getCommentsFromThePullRequestDTO(
                                    String.valueOf(prId), 
                                    username, 
                                    repo, 
                                    "Bearer " + repositoryAccessToken);

                    JsonNode jsonNodePrCRW = toJsonNode(prCRW);
                    boolean areCommentsResolved = areCommentsResolved(jsonNodePrCRW);

                    if((codeReviews.size()==0) && areCommentsResolved) {
                        bitbucketPullRequestsClient.approvePullRequestById(
                                String.valueOf(prId), username, repo, "Bearer " + repositoryAccessToken);
                    }

                    codeReviews.forEach(codeReview -> {

                        String filePath = codeReview.getFilePath();
                        Map<String, String> codeReviewCommentsMap = codeReview.getComments();

                        codeReviewCommentsMap.entrySet().stream()
                                .forEach(entry -> {
                                    String lineKey = entry.getKey();
                                    String commentComment = entry.getValue();

                                    Range lineRange = extractRange(lineKey);

                                    CommentRequestDTO commentRequestDTO = CommentRequestDTO.builder()
                                            .content(Content.builder()
                                                    .raw(commentComment)
                                                    .build())
                                            .inline(Inline.builder()
                                                    .path(filePath)
                                                    .from(lineRange.getFrom())
                                                    .to(lineRange.getTo())
                                                    .build())
                                            .build();

                                    comment.set(
                                            bitbucketPullRequestsClient
                                                    .postACommentOnAFileAtThePullRequest(
                                                            String.valueOf(prId),
                                                            username,
                                                            repo,
                                                            commentRequestDTO,
                                                            "Bearer " + repositoryAccessToken));

                                });
                    });
                });

        return comment.get();
    }

    public String approvePullRequestById(
            String id,
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketPullRequestsClient
                .approvePullRequestById(
                        id,
                        username,
                        repo,
                        "Bearer " + repositoryAccessToken);
    }

    public String getCommentsFromThePullRequest(
            String id,
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketPullRequestsClient
                .getCommentFromThePullRequest(
                        id,
                        username,
                        repo,
                        "Bearer " + repositoryAccessToken);
    }

    public PRCommentResponseWrapper getCommentsFromThePullRequestDTO(
            String id,
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketPullRequestsClient
                .getCommentFromThePullRequestDTO(
                        id,
                        username,
                        repo,
                        repositoryAccessToken);
    }

    public String getPullRequest(
            String id,
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketPullRequestsClient
                .getPullRequest(
                        id,
                        username,
                        repo,
                        "Bearer " + repositoryAccessToken);
    }

    public String getDiff(
            String id,
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketPullRequestsClient
                .getDiff(
                        id,
                        username,
                        repo,
                        "Bearer " + repositoryAccessToken);
    }


    private JsonNode toJsonNode(PRCommentResponseWrapper prCRW){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(prCRW);
    }

    private boolean areCommentsResolved(JsonNode jsonNode) {
        // Check if the "values" array is empty
        JsonNode valuesNode = jsonNode.get("values");
        if (valuesNode != null && valuesNode.isArray() && valuesNode.size() == 0) {
            // Empty "values" array indicates resolved comments
            return true;
        }

        // Check if the "values" array is not null and contains elements
        if (valuesNode != null && valuesNode.isArray() && valuesNode.size() > 0) {
            // Iterate over each comment in the "values" array
            for (JsonNode commentNode : valuesNode) {
                // Check if the "inline" field is null
                JsonNode inlineNode = commentNode.get("inline");
                if (inlineNode != null && inlineNode.isNull()) {
                    // Check if both "from" and "to" fields are null
                    JsonNode fromNode = inlineNode.get("from");
                    JsonNode toNode = inlineNode.get("to");
                    if (fromNode == null && toNode == null) {
                        // Comment is not resolved, return false
                        return false;
                    }
                }
            }
        }

        // All comments are resolved, return true
        return true;
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


}
