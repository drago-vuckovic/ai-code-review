package co.vuckovic.aicodereview.bitbucket.dtos.responses.pullrequests;

import lombok.Data;
@Data
public class SummaryDTO {
    private String type;
    private String raw;
    private String markup;
    private String html;
}
