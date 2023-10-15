package by.harlap.plugin.task.impl;


import by.harlap.plugin.context.GitPluginExtension;
import by.harlap.plugin.model.Version;
import by.harlap.plugin.service.GitService;
import by.harlap.plugin.service.impl.GitServiceImpl;
import by.harlap.plugin.task.GitPluginTask;
import by.harlap.plugin.util.GitUtil;
import org.gradle.api.tasks.TaskAction;

import static by.harlap.plugin.constant.AppConstants.BranchPostfix.UNCOMMITTED_POSTFIX;

public class CheckUncommittedTask extends GitPluginTask {

    public static final String REPO_NOT_INITIALIZED_MSG = "Git repository is not initialized.";
    public static final String UNCOMMITTED_DETECTED_MSG = "There are uncommitted changes. Build version: '{}{}'.";
    public static final String COMMITTED_DETECTED_MSG = "All the changes are committed.";

    private final GitService gitService = GitServiceImpl.getInstance();

    @TaskAction
    public void checkUncommittedChanges() {
        if (!GitUtil.repositoryExists()) {
            throw new IllegalStateException(REPO_NOT_INITIALIZED_MSG);
        }

        final boolean uncommittedChanges = gitService.checkUncommittedChanges();

        if (uncommittedChanges) {
            final GitPluginExtension extension = getProject().getExtensions().getByType(GitPluginExtension.class);
            final Version version = extension.getVersion();
            getLogger().lifecycle(UNCOMMITTED_DETECTED_MSG, version, UNCOMMITTED_POSTFIX);
        } else {
            getLogger().lifecycle(COMMITTED_DETECTED_MSG);
        }

        this.getExtensions().add("result", !uncommittedChanges);
    }
}

