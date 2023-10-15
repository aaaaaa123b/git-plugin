package by.harlap.plugin.util;

import by.harlap.plugin.model.Version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionUtil {

    public static final Pattern VERSION_PATTERN = Pattern.compile("v(\\d+)\\.(\\d+)(?:-[a-zA-Z]+)?");

    private VersionUtil() {
    }

    public static Version parse(String value) {
        if (value == null) {
            return new Version(0, 0, null);
        }

        Matcher matcher = VERSION_PATTERN.matcher(value);

        if (matcher.find()) {
            int major = Integer.parseInt(matcher.group(1));
            int minor = Integer.parseInt(matcher.group(2));

            return new Version(major, minor, null);
        }

        throw new IllegalArgumentException("Version is not valid (pattern is v_._-_): " + value);
    }
}
