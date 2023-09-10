package co.vuckovic.aicodereview.bitbucket.dtos.responses.branches.links;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonProperty("display_name")
    private String displayName;
    private Links links;
    private String type;
    private String uuid;
    @JsonProperty("account_id")
    private String accountId;
    private String nickname;
}
