package co.vuckovic.aicodereview.bitbucket.dtos.responses.pullrequests;

import lombok.Data;

@Data
public class DestinationDTO {
    private BranchDTO branch;
    private CommitDTO commit;
    private RepositoryDTO repository;
}
