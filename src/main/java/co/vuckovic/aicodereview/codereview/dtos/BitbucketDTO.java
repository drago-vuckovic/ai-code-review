package co.vuckovic.aicodereview.codereview.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BitbucketDTO {

    @JsonProperty(required = false)
    String id;
    String username;
    String branch;
    String repo;
    String repositoryAccessToken;
}
