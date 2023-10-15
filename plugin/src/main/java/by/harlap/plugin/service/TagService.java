package by.harlap.plugin.service;

import by.harlap.plugin.model.Version;

public interface TagService {
    Version getUpdatedVersion(String branchName, Version version);

    void createTag(Version version);

    String findLastTag();

    boolean anyTagExists();
}
