package by.harlap.plugin.service;

public interface GitService {

    void push(String remoteBranch);

    boolean checkUncommittedChanges();
}
