package co.vuckovic.aicodereview.bitbucket.dtos.requests.pullrequest.comment;

import lombok.Data;

@Data
public class PullRequest {
    private String type;
    private int id;
    private Links links;
    private String title;
}

