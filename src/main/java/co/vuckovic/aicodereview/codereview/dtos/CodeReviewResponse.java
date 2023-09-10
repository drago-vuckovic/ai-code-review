package co.vuckovic.aicodereview.codereview.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeReviewResponse {

    String filePath;
    Map<String, String> comments;
}

