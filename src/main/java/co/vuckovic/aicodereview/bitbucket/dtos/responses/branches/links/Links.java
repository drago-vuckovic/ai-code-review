package co.vuckovic.aicodereview.bitbucket.dtos.responses.branches.links;

import lombok.Data;

@Data
public class Links {
    private Self self;
    private Commits commits;
    private Html html;
    private Diff diff;
    private Approve approve;
    private Comments comments;
    private Statuses statuses;
    private Patch patch;
}
