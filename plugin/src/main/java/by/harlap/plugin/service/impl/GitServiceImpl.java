package by.harlap.plugin.service.impl;

import by.harlap.plugin.service.GitService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static by.harlap.plugin.constant.AppConstants.Command.*;

public class GitServiceImpl implements GitService {

    public static final String PUSH_FAILED_MSG = "Push failed with exit code: %s";

    private static GitService instance;

    private GitServiceImpl() {
    }

    public static synchronized GitService getInstance() {
        if (instance == null) {
            instance = new GitServiceImpl();
        }

        return instance;
    }

    @Override
    public void push(String remoteBranch) {
        String[] pushCommandParts = PUSH_COMMAND.formatted(remoteBranch).split(DELIMITER);
        final ProcessBuilder processBuilder = new ProcessBuilder(pushCommandParts);

        try {
            final Process process = processBuilder.start();
            final int exitCode = process.waitFor();

            if (exitCode == 0) return;

            final String message = PUSH_FAILED_MSG.formatted(exitCode);
            throw new RuntimeException(message);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkUncommittedChanges() {
        boolean uncommitedChanges = false;
        final String[] commandParts = CHECK_UNCOMMITTED_CHANGES_COMMAND.split(DELIMITER);

        try {
            final Process process = Runtime.getRuntime().exec(commandParts);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uncommitedChanges;
    }
}
