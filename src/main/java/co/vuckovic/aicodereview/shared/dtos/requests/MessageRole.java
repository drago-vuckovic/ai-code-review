package co.vuckovic.aicodereview.shared.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageRole {
    USER("user"),
    ASSISTANT("assistant");

    private final String value;
}
