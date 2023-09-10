package co.vuckovic.aicodereview.bitbucket.dtos.responses.root;

import co.vuckovic.aicodereview.bitbucket.dtos.responses.branches.Value;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchesResponse {
    private Value[] values;
}
