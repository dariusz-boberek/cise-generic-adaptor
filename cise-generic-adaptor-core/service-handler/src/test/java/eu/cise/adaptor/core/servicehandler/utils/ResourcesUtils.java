package eu.cise.adaptor.core.servicehandler.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ResourcesUtils {

    private ResourcesUtils() {
    }

    public static String readResource(String resourceName, Class clazz) {
        try {
            return Files.readString(Paths.get(getResourceURI(resourceName, clazz)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static URI getResourceURI(String resourceName, Class clazz) {
        try {
            return Objects.requireNonNull(clazz.getClassLoader().getResource(resourceName)).toURI();
        } catch (URISyntaxException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}
