package co.vuckovic.aicodereview.bitbucket.dtos.responses.branches.links;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Target {
    private String type;
    private String hash;
    private String date;
    private Author author;
    private String message;
    private Links links;
    private Parent[] parents;
    private Repository repository;
}