package co.vuckovic.aicodereview.bitbucket.clients;

import co.vuckovic.aicodereview.bitbucket.dtos.requests.comment.commentrequest.CommentRequestDTO;
import co.vuckovic.aicodereview.bitbucket.dtos.requests.pullrequest.comment.PRCommentResponseWrapper;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "pull-requests-client", url = "${bitbucket.repo.api-root-url}")
public interface BitbucketPullRequestsClient {
    String PULL_REQUESTS = "/{username}/{repo}/pullrequests";

    @GetMapping( PULL_REQUESTS + "/{id}/diffstat")
    @Headers({"Content-Type: application/json"})
    String getListOfModifiedFilesAtThePullRequest(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @PostMapping(PULL_REQUESTS + "/{id}/comments")
    String postACommentOnAFileAtThePullRequest(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestBody CommentRequestDTO commentRequestDTO,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @PostMapping(PULL_REQUESTS + "/{id}/approve")
    String approvePullRequestById(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping(PULL_REQUESTS + "/{id}/comments")
    String getCommentFromThePullRequest(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping(PULL_REQUESTS + "/{id}/comments")
    PRCommentResponseWrapper getCommentFromThePullRequestDTO(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping(PULL_REQUESTS + "/{id}")
    String getPullRequest(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping( PULL_REQUESTS + "/{id}/diff")
    @Headers({"Content-Type: application/json"})
    String getDiff(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);


}