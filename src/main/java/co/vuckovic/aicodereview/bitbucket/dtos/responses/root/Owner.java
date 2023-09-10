package co.vuckovic.aicodereview.bitbucket.dtos.responses.root;

import lombok.Data;

@Data
public class Owner {
    private String display_name;
    private Links links;
    private String type;
    private String uuid;
    private String username;
}

