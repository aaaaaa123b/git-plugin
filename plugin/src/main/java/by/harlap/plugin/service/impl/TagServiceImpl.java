package by.harlap.plugin.service.impl;

import by.harlap.plugin.model.Version;
import by.harlap.plugin.service.TagService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static by.harlap.plugin.constant.AppConstants.Branch.*;
import static by.harlap.plugin.constant.AppConstants.BranchPostfix.DEFAULT_POSTFIX;
import static by.harlap.plugin.constant.AppConstants.BranchPostfix.STAGE_POSTFIX;
import static by.harlap.plugin.constant.AppConstants.Command.*;

public class TagServiceImpl implements TagService {

    private static TagService instance;

    private TagServiceImpl() {
    }

    public static synchronized TagService getInstance() {
        if (instance == null) {
            instance = new TagServiceImpl();
        }

        return instance;
    }

    public Version getUpdatedVersion(String branch, Version previous) {
        int major = 0;
        int minor = 0;
        String postfix = null;

        if (previous != null) {
            major = previous.major();
            minor = previous.minor();
        }

        switch (branch) {
            case DEV, QA -> {
                minor++;
            }
            case STAGE -> {
                minor++;
                postfix = STAGE_POSTFIX;
            }
            case MASTER -> {
                major++;
                minor = 0;
            }
            default -> {
                postfix = DEFAULT_POSTFIX;
            }
        }

        return new Version(major, minor, postfix);
    }

    @Override
    public void createTag(Version version) {
        try {
            String[] createCommandParts = CREATE_TAG_COMMAND.formatted(version).split(DELIMITER);
            Process process = Runtime.getRuntime().exec(createCommandParts);
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("Tag creation failed!");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findLastTag() {
        final String[] commandParts = FIND_LAST_TAG_FOR_PROJECT.split(DELIMITER);
        final ProcessBuilder processBuilder = new ProcessBuilder(commandParts);

        String lastTag = null;
        String line;
        try {
            final Process process = processBuilder.start();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                lastTag = line;
            }

            process.waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        return lastTag;
    }

    @Override
    public boolean anyTagExists() {
        final String[] commandParts = CHECK_TAG_EXISTENCE.split(DELIMITER);
        final ProcessBuilder checkTagProcessBuilder = new ProcessBuilder(commandParts);
        try {
            final Process checkTagProcess = checkTagProcessBuilder.start();
            final int exitCode = checkTagProcess.waitFor();

            return exitCode == 0;
//            return exitCode == 0;
//            boolean result = true;
//            if (exitCode == 0) {
//                result = false;
//            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
