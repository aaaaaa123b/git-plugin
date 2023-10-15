package by.harlap.plugin.constant;

public class AppConstants {

    private AppConstants() {
    }

    public static class Branch {

        private Branch() {
        }

        public static final String DEV = "dev";
        public static final String QA = "qa";
        public static final String STAGE = "stage";
        public static final String MASTER = "master";
    }

    public static class BranchPostfix {

        private BranchPostfix() {
        }

        public static final String STAGE_POSTFIX = "rc";
        public static final String DEFAULT_POSTFIX = "SNAPSHOT";
        public static final String UNCOMMITTED_POSTFIX = ".uncommitted";
    }

    public static class Command {
        public static final String GET_BRANCH_COMMAND = "git rev-parse --abbrev-ref HEAD";
        public static final String CREATE_TAG_COMMAND = "git tag %s";
        public static final String CHECK_TAG_EXISTENCE = "git describe --exact-match --tags";
        public static final String FIND_LAST_TAG_FOR_PROJECT = "git describe --tags --abbrev=0";
        public static final String PUSH_COMMAND = "git push --tags origin %s";
        public static final String CHECK_UNCOMMITTED_CHANGES_COMMAND = "git status --porcelain";
        public static final String CHECK_GIT_EXISTS = "git status";

        public static final String DELIMITER = " ";
    }
}
