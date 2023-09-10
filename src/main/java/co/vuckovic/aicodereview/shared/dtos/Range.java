package co.vuckovic.aicodereview.shared.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Range {
    private int from;
    private int to;
}