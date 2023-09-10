package co.vuckovic.aicodereview.bitbucket.clients;

import co.vuckovic.aicodereview.bitbucket.dtos.responses.pullrequests.PullRequestDTO;
import co.vuckovic.aicodereview.bitbucket.dtos.responses.root.BranchesResponse;
import co.vuckovic.aicodereview.bitbucket.dtos.responses.root.RepositoryResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "request-client", url = "#{@bitbucketRepoConfig.apiRootUrl}")
public interface BitbucketGeneralRequestClient {

    @GetMapping("/{username}/{repo}")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<RepositoryResponse> root(
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping("/{username}/{repo}/refs/branches")
    @Headers({"Content-Type: application/json"})
    BranchesResponse branches(
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping("/{username}/{repo}/pullrequests")
    @Headers({"Content-Type: application/json"})
    String pullRequests(
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping("/{username}/{repo}/pullrequests")
    @Headers({"Content-Type: application/json"})
    PullRequestDTO getPullRequests(
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping("/{username}/v1/bitbucket/commits")
    @Headers({"Content-Type: application/json"})
    String commits(
            @PathVariable("username") String username,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping("/{username}/{repo}/refs/tags")
    @Headers({"Content-Type: application/json"})
    String tags(
            @PathVariable("username") String username,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping("/{username}/{repo}/downloads")
    @Headers({"Content-Type: application/json"})
    String downloads(
            @PathVariable("username") String username,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @PostMapping("/{username}/{repo}/create-file-in-repository")
    @Headers({"Content-Type: application/json"})
    void createFileInRepository(
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken);

    @GetMapping("/{username}/{repo}/refs/branches/{branch}")
    @Headers({"Content-Type: application/json"})
    String checkExistenceUsernameRepo(
            @PathVariable("username") String username,
            @PathVariable("repo") String repo,
            @PathVariable("branch") String branch,
            @RequestHeader("Authorization") String authorizationRepositoryAccessToken
    );
}

