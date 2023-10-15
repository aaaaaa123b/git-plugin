package by.harlap.plugin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static by.harlap.plugin.constant.AppConstants.Command.GET_BRANCH_COMMAND;

public class BranchUtil {

    private BranchUtil() {
    }

    public static String getCurrentBranch() {
        ProcessBuilder processBuilder = new ProcessBuilder(GET_BRANCH_COMMAND.split(" "));

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            if ((line = reader.readLine()) != null) {
                return line;
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException("There are no branches");
    }
}
