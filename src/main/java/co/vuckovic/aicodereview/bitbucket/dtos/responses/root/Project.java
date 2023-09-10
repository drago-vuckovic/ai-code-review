package co.vuckovic.aicodereview.bitbucket.dtos.responses.root;

import lombok.Data;
@Data
public class Project {
    private String type;
    private String key;
    private String uuid;
    private String name;
    private Links links;
}