package co.vuckovic.aicodereview.bitbucket.dtos.responses.root;


import co.vuckovic.aicodereview.bitbucket.dtos.responses.root.shared.Link;
import lombok.Data;
@Data
public class Links {
    private Link self;
    private Link html;
    private Link avatar;
    private Link pullrequests;
    private Link commits;
    private Link forks;
    private Link watchers;
    private Link branches;
    private Link tags;
    private Link downloads;
    private Link source;
    private Clone[] clone;
    private Link hooks;
}