package co.vuckovic.aicodereview.bitbucket.dtos.requests.pullrequest.comment;

import lombok.Data;

@Data
public class Links {
    private Self self;
    private Html html;
    private Code code;
    private Avatar avatar;

}

