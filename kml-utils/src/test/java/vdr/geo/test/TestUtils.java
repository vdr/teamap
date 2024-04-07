package vdr.geo.test;

import java.nio.file.Path;

public class TestUtils {
    public static Path getFile(String path) {
        ClassLoader classLoader = TestUtils.class.getClassLoader();

        return Path.of(classLoader.getResource(path).getFile());
    }
}
