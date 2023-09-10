package co.vuckovic.aicodereview.bitbucket.dtos.responses.pullrequests;

import lombok.Data;

@Data
public class CommitDTO {
    private String type;
    private String hash;
    private LinksDTO links;
}
