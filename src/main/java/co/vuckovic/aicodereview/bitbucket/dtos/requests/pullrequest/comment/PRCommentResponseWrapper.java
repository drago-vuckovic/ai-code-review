package co.vuckovic.aicodereview.bitbucket.dtos.requests.pullrequest.comment;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PRCommentResponseWrapper {
    private List<ResponseItem> values ;
    private int pagelen;
    private int size;
    private int page;
}
