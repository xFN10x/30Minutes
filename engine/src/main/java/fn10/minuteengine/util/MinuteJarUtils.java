package fn10.minuteengine.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

public class MinuteJarUtils {
    public static URL getFileFromJar(Path jar, String file) throws MalformedURLException {
        String pathString = "jar:file://" + jar.toString().replace("\\", "/") + "!/" + file;
        return URI.create(pathString).toURL();
    }
}
