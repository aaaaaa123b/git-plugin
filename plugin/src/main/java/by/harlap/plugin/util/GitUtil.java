package by.harlap.plugin.util;

import java.io.IOException;

import static by.harlap.plugin.constant.AppConstants.Command.*;


public class GitUtil {

    private GitUtil() {
    }

    public static boolean repositoryExists() {
        final String[] commandParts = CHECK_GIT_EXISTS.split(" ");
        final ProcessBuilder checkGitExistBuilder = new ProcessBuilder(commandParts);

        final Process checkTagProcess;
        boolean result = true;
        try {
            checkTagProcess = checkGitExistBuilder.start();
            final int exitCode = checkTagProcess.waitFor();

            if (exitCode != 0)
                result = false;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

