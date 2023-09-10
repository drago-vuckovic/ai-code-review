package co.vuckovic.aicodereview.bitbucket.dtos.responses.root;

import lombok.Data;

@Data
public class OverrideSettings {
    private boolean default_merge_strategy;
    private boolean branching_model;
}

