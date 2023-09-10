package co.vuckovic.aicodereview.bitbucket.dtos.responses.pullrequests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinksDTO {
    private SelfDTO self;
    private HtmlDTO html;
    private CommitsDTO commits;
    private ApproveDTO approve;
    private RequestChangesDTO request_changes;
    private DiffDTO diff;
    private DiffStatDTO diffstat;
    private CommentsDTO comments;
    private ActivityDTO activity;
    private MergeDTO merge;
    private DeclineDTO decline;
    private StatusesDTO statuses;
}