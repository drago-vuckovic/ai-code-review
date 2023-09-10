package co.vuckovic.aicodereview.bitbucket.dtos.requests.pullrequest.comment;

import co.vuckovic.aicodereview.bitbucket.dtos.requests.comment.commentrequest.Inline;
import lombok.Data;

@Data
public class ResponseItem {
    private long id;
    private String created_on;
    private String updated_on;
    private Content content;
    private User user;
    private boolean deleted;
    private Inline inline;
    private String type;
    private Links links;
    private PullRequest pullrequest;
}
