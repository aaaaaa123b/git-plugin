package by.harlap.plugin.task.impl;

import by.harlap.plugin.context.GitPluginExtension;
import by.harlap.plugin.model.Version;
import by.harlap.plugin.service.TagService;
import by.harlap.plugin.service.impl.TagServiceImpl;
import by.harlap.plugin.task.GitPluginTask;
import by.harlap.plugin.util.GitUtil;
import by.harlap.plugin.util.VersionUtil;
import org.gradle.api.tasks.TaskAction;

public class FindLastTagTask extends GitPluginTask {

    public static final String REPO_NOT_INITIALIZED_MSG = "Git repository is not initialized.";
    public static final String NO_TAGS_FOUND_MSG = "No tags were found for current project";
    public static final String TAGS_FOUND_MSG = "Last found tag is '{}'";

    private final TagService tagService = TagServiceImpl.getInstance();

    @TaskAction
    public void findLastTag() {
        if (!GitUtil.repositoryExists()) {
            throw new IllegalStateException(REPO_NOT_INITIALIZED_MSG);
        }

        final String lastTag = tagService.findLastTag();
        if (lastTag == null) {
            getLogger().warn(NO_TAGS_FOUND_MSG);
            return;
        }

        final GitPluginExtension extension = getProject().getExtensions().getByType(GitPluginExtension.class);
        final Version version = VersionUtil.parse(lastTag);

        extension.setVersion(version);
        getLogger().lifecycle(TAGS_FOUND_MSG, lastTag);
    }
}

