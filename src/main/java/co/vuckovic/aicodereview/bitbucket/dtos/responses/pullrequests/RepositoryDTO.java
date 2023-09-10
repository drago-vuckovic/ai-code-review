package co.vuckovic.aicodereview.bitbucket.dtos.responses.pullrequests;

import lombok.Data;

@Data
public class RepositoryDTO {
    private String type;
    private String full_name;
    private LinksDTO links;
    private String name;
    private String uuid;
}