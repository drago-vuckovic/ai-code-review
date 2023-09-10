package co.vuckovic.aicodereview.bitbucket.dtos.responses.branches;

import co.vuckovic.aicodereview.bitbucket.dtos.responses.branches.links.Links;
import co.vuckovic.aicodereview.bitbucket.dtos.responses.branches.links.Target;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {
    private String name;
    private Target target;
    private Links links;
    @JsonProperty("merge_strategies")
    private String[] mergeStrategies;
    @JsonProperty("default_merge_strategy")
    private String defaultMergeStrategy;

}
