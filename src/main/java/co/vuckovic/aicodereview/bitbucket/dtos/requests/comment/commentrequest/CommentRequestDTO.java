package co.vuckovic.aicodereview.bitbucket.dtos.requests.comment.commentrequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRequestDTO {
    private Content content;
    private Inline inline;
}
