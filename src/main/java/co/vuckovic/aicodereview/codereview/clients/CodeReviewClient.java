package co.vuckovic.aicodereview.codereview.clients;

import co.vuckovic.aicodereview.codereview.dtos.CodeReviewResponse;
import co.vuckovic.aicodereview.codereview.dtos.PrCodeReviewResponse;
import org.springframework.web.bind.annotation.*;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "code-review", url = "${bitbucket.content.url}")
public interface CodeReviewClient {

    @GetMapping("/v1/bitbucket/{username}/{repositoryAccessToken}/commits")
    @Headers({"Content-Type: application/json"})
    String commits(
            @PathVariable("username") String username,
            @PathVariable("repositoryAccessToken") String repositoryAccessToken
    );

    @GetMapping("/v1/bitbucket/{username}/{repo}/{repositoryAccessToken}/pull-requests")
    @Headers({"Content-Type: application/json"})
    String pullRequests(
            @PathVariable String username,
            @PathVariable String repo,
            @PathVariable String repositoryAccessToken
    );

    @GetMapping("/{username}/{repo}/v1/bitbucket/{repositoryAccessToken}/pull-request/{id}")
    @Headers({"Content-Type: application/json"})
    String getListOfModifiedFilesAtThePullRequest(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @PathVariable("repositoryAccessToken") String repositoryAccessToken
    );

    @PostMapping("/{username}/{repo}/v1/bitbucket/{repositoryAccessToken}/pull-request/code-review-response")
    @Headers({"Content-Type: application/json"})
    String getCodeReviewResponse(
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @PathVariable("repositoryAccessToken") String repositoryAccessToken,
            @RequestBody CodeReviewResponse codeReviewResponse);

    @CrossOrigin
    @GetMapping("/{username}/{repo}/v1/bitbucket/{repositoryAccessToken}/pull-request/{id}/comment")
    @Headers({"Content-Type: application/json"})
    String getCommentsFromThePullRequest(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @PathVariable("repositoryAccessToken") String repositoryAccessToken);

    @GetMapping("/{username}/{repo}/v1/bitbucket/{repositoryAccessToken}/pull-request/{id}/get")
    @Headers({"Content-Type: application/json"})
    String getPullRequest(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @PathVariable("repositoryAccessToken") String repositoryAccessToken);


    @PostMapping("/{username}/{repo}/v1/bitbucket/{repositoryAccessToken}/pull-request/pr-code-review-response-list")
    @Headers({"Content-Type: application/json"})
    String prCodeReviewResponseList(
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @PathVariable("repositoryAccessToken") String repositoryAccessToken,
            List<PrCodeReviewResponse> prCodeReviewResponseList);


    @GetMapping("/{username}/{repo}/v1/bitbucket/{repositoryAccessToken}/pull-request/{id}/getdiff")
    @Headers({"Content-Type: application/json"})
    String getDiff(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @PathVariable("repositoryAccessToken") String repositoryAccessToken);


}
