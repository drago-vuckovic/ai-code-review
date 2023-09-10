package co.vuckovic.aicodereview.bitbucket.dtos.responses.branches.links;

import lombok.Data;
@Data
public class Parent {
    private String type;
    private String hash;
    private Links links;
}
