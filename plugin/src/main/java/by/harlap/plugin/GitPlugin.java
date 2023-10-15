package by.harlap.plugin;

import by.harlap.plugin.context.GitPluginExtension;
import by.harlap.plugin.task.impl.*;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GitPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("myPluginExtension", GitPluginExtension.class);

        project.getTasks().register("findLast", FindLastTagTask.class).get();
        CheckTagExistenceTask checkTagExistenceTask = project.getTasks().register("checkTagExistence", CheckTagExistenceTask.class).get();

        CheckUncommittedTask checkUncommittedTask = project.getTasks().register("checkUncommitted", CheckUncommittedTask.class).get();
        checkUncommittedTask.dependsOn("findLast");

        PushWithTagsTask pushWithTagsTask = project.getTasks().register("publishNewVersion", PushWithTagsTask.class).get();
        pushWithTagsTask.setOnlyIf(it -> (boolean) checkTagExistenceTask.getExtensions().getByName("result") && (boolean) checkUncommittedTask.getExtensions().getByName("result"));
        pushWithTagsTask.dependsOn("checkTagExistence", "checkUncommitted");
    }
}
