package co.vuckovic.aicodereview.bitbucket.dtos.responses.pullrequests;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequestDTO {
    private int comment_count;
    private int task_count;
    private String type;
    private int id;
    private String title;
    private String description;
    private String state;
    private String merge_commit;
    private boolean close_source_branch;
    private Object closed_by;
    private AuthorDTO author;
    private String reason;
    private String created_on;
    private String updated_on;
    private DestinationDTO destination;
    private SourceDTO source;
    private LinksDTO links;
    private SummaryDTO summary;

    private int pagelen;
    private int size;
    private int page;
}

