package co.vuckovic.aicodereview.bitbucket.dtos.responses.root;


import lombok.Data;

@Data
public class RepositoryResponse {
    private String type;
    private String full_name;
    private Links links;
    private String name;
    private String slug;
    private String description;
    private String scm;
    private String website;
    private Owner owner;
    private Workspace workspace;
    private boolean is_private;
    private Project project;
    private String fork_policy;
    private String created_on;
    private String updated_on;
    private int size;
    private String language;
    private boolean has_issues;
    private boolean has_wiki;
    private String uuid;
    private MainBranch mainbranch;
    private OverrideSettings override_settings;
}
