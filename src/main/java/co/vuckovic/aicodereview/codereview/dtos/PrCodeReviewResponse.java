package co.vuckovic.aicodereview.codereview.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrCodeReviewResponse {

    Integer prId;
    List<CodeReviewResponse> codeReviews;
}
