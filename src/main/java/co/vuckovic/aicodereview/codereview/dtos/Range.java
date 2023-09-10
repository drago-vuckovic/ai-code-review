package co.vuckovic.aicodereview.codereview.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Range {
    private int from;
    private int to;
}
