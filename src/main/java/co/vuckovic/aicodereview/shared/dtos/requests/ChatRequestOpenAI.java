package co.vuckovic.aicodereview.shared.dtos.requests;

import co.vuckovic.aicodereview.shared.dtos.responses.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRequestOpenAI {
    private String model;
    private List<Message> messages;
    private int max_tokens;
}
