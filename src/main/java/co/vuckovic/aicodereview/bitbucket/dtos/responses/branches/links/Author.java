package co.vuckovic.aicodereview.bitbucket.dtos.responses.branches.links;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    private String type;
    private String raw;
    private User user;
}

