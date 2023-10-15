package by.harlap.plugin.task.impl;

import by.harlap.plugin.context.GitPluginExtension;
import by.harlap.plugin.model.Version;
import by.harlap.plugin.service.GitService;
import by.harlap.plugin.service.TagService;
import by.harlap.plugin.service.impl.GitServiceImpl;
import by.harlap.plugin.service.impl.TagServiceImpl;
import by.harlap.plugin.task.GitPluginTask;
import by.harlap.plugin.util.BranchUtil;
import org.gradle.api.tasks.TaskAction;

public class PushWithTagsTask extends GitPluginTask {

    public static final String PUSH_COMPLETED_MSG = "Push completed successfully.";
    public static final String TAG_CREATED_MSG = "New tag created for current branch ({}): '{}'";

    private final TagService tagService = TagServiceImpl.getInstance();
    private final GitService gitService = GitServiceImpl.getInstance();

    @TaskAction
    public void push() {
        final String branch = BranchUtil.getCurrentBranch();
        final GitPluginExtension extension = getProject().getExtensions().getByType(GitPluginExtension.class);
        final Version version = extension.getVersion();
        final Version newVersion = tagService.getUpdatedVersion(branch, version);

        tagService.createTag(newVersion);
        getLogger().lifecycle(TAG_CREATED_MSG, branch, newVersion);

        gitService.push(branch);
        getLogger().lifecycle(PUSH_COMPLETED_MSG);
    }
}

