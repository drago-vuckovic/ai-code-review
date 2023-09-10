package co.vuckovic.aicodereview.bitbucket.services;

import co.vuckovic.aicodereview.bitbucket.clients.BitbucketGeneralRequestClient;
import co.vuckovic.aicodereview.bitbucket.dtos.responses.pullrequests.PullRequestDTO;
import co.vuckovic.aicodereview.bitbucket.dtos.responses.root.BranchesResponse;
import co.vuckovic.aicodereview.bitbucket.dtos.responses.root.RepositoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BitbucketGeneralRequestService {
    private final BitbucketGeneralRequestClient bitbucketGeneralRequestClient;
    public ResponseEntity<RepositoryResponse> root(
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketGeneralRequestClient.root(
                username,
                repo,
                "Bearer " + repositoryAccessToken);
    }

    public BranchesResponse branches(
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketGeneralRequestClient.branches(
                username,
                repo,
                "Bearer " + repositoryAccessToken);
    }

    public String pullRequests(
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketGeneralRequestClient.pullRequests(
                username,
                repo,
                "Bearer " + repositoryAccessToken);
    }

    public PullRequestDTO getPullRequests(
            String username,
            String repo,
            String repositoryAccessToken){
        return bitbucketGeneralRequestClient.getPullRequests(
                username,
                repo,
                "Bearer " + repositoryAccessToken);
    }

    public String commits(
            String username,
            String repositoryAccessToken){
        return bitbucketGeneralRequestClient.commits(
                username,
                "Bearer " + repositoryAccessToken);
    }

    public String tags(
            String username,
            String repositoryAccessToken){
        return bitbucketGeneralRequestClient.tags(
                username,
                "Bearer " + repositoryAccessToken);
    }

    public String downloads(
            String username,
            String repositoryAccessToken){
        return bitbucketGeneralRequestClient.downloads(
                username,
                "Bearer " + repositoryAccessToken);
    }

    public String checkBitbucketRepo(
            String username,
            String repo,
            String branch,
            String  repositoryAccessToken) {
        return bitbucketGeneralRequestClient.checkExistenceUsernameRepo(
                username,
                repo,
                branch,
                "Bearer " + repositoryAccessToken);
    }

}
