package co.vuckovic.aicodereview.codereview.controllers;

import co.vuckovic.aicodereview.codereview.dtos.BitbucketDTO;
import co.vuckovic.aicodereview.codereview.services.CodeReviewService;
import co.vuckovic.aicodereview.shared.dtos.requests.ChatRequestOpenAI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/code-review")
@RequiredArgsConstructor
@Tag(name = "CodeReview")
public class CodeReviewController {

    private final CodeReviewService codeReviewService;

    @Operation(
            summary = "is-prompt-plain-code",
            description = "post is-prompt-plain-code",
            tags = { "codeReview", "post" , "is-prompt-plain-code"})
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(
                            schema = @Schema(implementation = Boolean.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @RequestBody(
            description = "isPromptPlainCde",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = ChatRequestOpenAI.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "isPromptPlainCde",
                                    value = "code",
                                    summary = "Sending in an object which would be evaluated it is a code") }))
    @CrossOrigin
    @PostMapping("/is-prompt-plain-code")
    public ResponseEntity<Boolean> isPromptPlainCode(
            @RequestBody ChatRequestOpenAI chatRequest) {
        return  codeReviewService.isPromptPlainCode(chatRequest);
    }

    @Operation(
            summary = "all-checks",
            description = "post all-checks",
            tags = { "codeReview", "post" , "all-checks"})
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @RequestBody(
            description = "codeReview",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = ChatRequestOpenAI.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "codeReview",
                                    value = "code",
                                    summary = "Perform a code review") }))
    @CrossOrigin
    @PostMapping("/all-checks")
    public ResponseEntity<Map<String, String>> codeReview(
            @RequestBody ChatRequestOpenAI chatRequest) {
        Map<String, String> mapOutput = codeReviewService
                .codeReview(chatRequest.getMessages().get(0).getContent());
        return ResponseEntity.ok(mapOutput);
    }

    @Operation(
            summary = "all-checks-from-bitbucket",
            description = "post all-checks-from-bitbucket",
            tags = { "codeReview", "post" , "all-checks-from-bitbucket"})
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @CrossOrigin
    @PostMapping("/all-checks-from-bitbucket")
    public ResponseEntity<Map<String, String>> codeReviewFromBitbucket(
            @RequestBody String code) {
        Map<String, String> mapOutput = codeReviewService.codeReview(code);
        return ResponseEntity.ok(mapOutput);
    }


    @Operation(
            summary = "commits",
            description = "get commits",
            tags = { "codeReview", "get", "commits"})
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/commits")
    public ResponseEntity<String> getCommits(
            BitbucketDTO bitbucketDTO) {
        return ResponseEntity.ok(
                codeReviewService
                        .getCommits(bitbucketDTO.getUsername(),
                                bitbucketDTO.getRepositoryAccessToken()));
    }

    @Operation(
            summary = "pull-request",
            description = "get pull-request",
            tags = { "codeReview", "get", "pull-request" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/pull-request")
    public ResponseEntity<String> getPullRequest(
            BitbucketDTO bitbucketDTO) {
        return ResponseEntity.ok(
                codeReviewService
                        .getPullRequests(bitbucketDTO.getUsername(),
                                bitbucketDTO.getRepo(),
                                bitbucketDTO.getRepositoryAccessToken()));
    }

    @Operation(
            summary = "send-code-review-to-bitbucket",
            description = "get send-code-review-to-bitbucket",
            tags = { "codeReview", "get", "send-code-review-to-bitbucket" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/send-code-review-to-bitbucket")
    public String sendCodeReviewToBitbucket(@RequestBody BitbucketDTO bitbucketDTO) {

        return codeReviewService.sendCodeReviewToBitbucket(
                bitbucketDTO.getUsername(),
                bitbucketDTO.getRepo(),
                bitbucketDTO.getBranch(),
                bitbucketDTO.getRepositoryAccessToken());
    }


    @Operation(
            summary = "get-comments-from-pr",
            description = "get get-comments-from-pr",
            tags = { "codeReview", "get", "get-comments-from-pr" })
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/get-comments-from-pr")
    public String getCommentsFromPr(BitbucketDTO bitbucketDTO) {
        return codeReviewService
                .getCommentsFromPR(
                        bitbucketDTO.getUsername(),
                        bitbucketDTO.getRepo(),
                        bitbucketDTO.getRepositoryAccessToken());
    }
}
