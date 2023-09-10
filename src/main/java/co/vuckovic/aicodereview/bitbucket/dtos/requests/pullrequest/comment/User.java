package co.vuckovic.aicodereview.bitbucket.dtos.requests.pullrequest.comment;

import lombok.Data;

@Data
public class User {
    private String display_name;
    private Links links;
    private String type;

    private String created_on;

    private String uuid;
    private String account_id;
    private String nickname;

    private String account_status;
    private String kind;
}