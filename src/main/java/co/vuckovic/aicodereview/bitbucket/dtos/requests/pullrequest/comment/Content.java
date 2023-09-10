package co.vuckovic.aicodereview.bitbucket.dtos.requests.pullrequest.comment;

import lombok.Data;

@Data
public class Content {
    private String type;
    private String raw;
    private String markup;
    private String html;
}
