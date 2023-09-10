package co.vuckovic.aicodereview.bitbucket.dtos.responses.root;

import lombok.Data;

@Data
public class Workspace {
    private String type;
    private String uuid;
    private String name;
    private String slug;
    private Links links;
}

