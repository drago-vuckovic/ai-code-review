package co.vuckovic.aicodereview.shared.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String role;
    private String content;
}
