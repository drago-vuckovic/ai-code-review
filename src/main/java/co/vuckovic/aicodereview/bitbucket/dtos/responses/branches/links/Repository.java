package co.vuckovic.aicodereview.bitbucket.dtos.responses.branches.links;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Repository {
    private String type;
    @JsonProperty("full_name")
    private String fullName;
    private Links links;
    private String name;
    private String uuid;
}