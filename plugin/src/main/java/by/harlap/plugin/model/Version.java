package by.harlap.plugin.model;

public record Version(int major, int minor, String postfix) {

    public static final String SIMPLE_VERSION = "v%d.%d";
    public static final String EXTENDED_VERSION = "v%d.%d-%s";

    @Override
    public String toString() {
        if (postfix == null || postfix.isBlank()) {
            return SIMPLE_VERSION.formatted(major, minor);
        }

        return EXTENDED_VERSION.formatted(major, minor, postfix);
    }
}
