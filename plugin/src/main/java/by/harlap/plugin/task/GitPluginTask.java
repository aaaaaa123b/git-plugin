package by.harlap.plugin.task;

import org.gradle.api.DefaultTask;

public abstract class GitPluginTask extends DefaultTask {

    public GitPluginTask() {
        super();
        setGroup("git plugin");
    }
}
