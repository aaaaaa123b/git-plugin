package by.harlap.plugin.task.impl;

import by.harlap.plugin.service.TagService;
import by.harlap.plugin.service.impl.TagServiceImpl;
import by.harlap.plugin.task.GitPluginTask;
import by.harlap.plugin.util.GitUtil;
import org.gradle.api.tasks.TaskAction;

public class CheckTagExistenceTask extends GitPluginTask {

    public static final String REPO_NOT_INITIALIZED_MSG = "Git repository is not initialized.";
    public static final String TAG_EXISTS_MSG = "Current project state already have a tag.";
    public static final String TAG_NOT_EXIST_MSG = "No tags found for current state of project.";

    private final TagService tagService = TagServiceImpl.getInstance();

    @TaskAction
    public void checkTag() {
        if (!GitUtil.repositoryExists()) {
            throw new IllegalStateException(REPO_NOT_INITIALIZED_MSG);
        }

        final boolean exists = tagService.anyTagExists();
        this.getExtensions().add("result", !exists);

        final String message = exists ? TAG_EXISTS_MSG : TAG_NOT_EXIST_MSG;
        getLogger().lifecycle(message);
    }
}
