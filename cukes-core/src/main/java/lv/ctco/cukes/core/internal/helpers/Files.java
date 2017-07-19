package lv.ctco.cukes.core.internal.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class Files {

    public static boolean isRelative(String path) {
        return Files.getPrefixLength(path) == 0;
    }

    protected static int getPrefixLength(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int len = filename.length();
            if (len == 0) {
                return 0;
            } else {
                char ch0 = filename.charAt(0);
                if (ch0 == 58) {
                    return -1;
                } else if (len == 1) {
                    return ch0 == 126 ? 2 : (isSeparator(ch0) ? 1 : 0);
                } else {
                    int posUnix;
                    if (ch0 == 126) {
                        int ch11 = filename.indexOf(47, 1);
                        posUnix = filename.indexOf(92, 1);
                        if (ch11 == -1 && posUnix == -1) {
                            return len + 1;
                        } else {
                            ch11 = ch11 == -1 ? posUnix : ch11;
                            posUnix = posUnix == -1 ? ch11 : posUnix;
                            return Math.min(ch11, posUnix) + 1;
                        }
                    } else {
                        char ch1 = filename.charAt(1);
                        if (ch1 == 58) {
                            ch0 = Character.toUpperCase(ch0);
                            return ch0 >= 65 && ch0 <= 90 ? (len != 2 && isSeparator(filename.charAt(2)) ? 3 : 2) : -1;
                        } else if (isSeparator(ch0) && isSeparator(ch1)) {
                            posUnix = filename.indexOf(47, 2);
                            int posWin = filename.indexOf(92, 2);
                            if ((posUnix != -1 || posWin != -1) && posUnix != 2 && posWin != 2) {
                                posUnix = posUnix == -1 ? posWin : posUnix;
                                posWin = posWin == -1 ? posUnix : posWin;
                                return Math.min(posUnix, posWin) + 1;
                            } else {
                                return -1;
                            }
                        } else {
                            return isSeparator(ch0) ? 1 : 0;
                        }
                    }
                }
            }
        }
    }

    public static List<String> readLines(File file) throws IOException {
        return readLines(file, Charset.defaultCharset());
    }

    public static List<String> readLines(File file, Charset encoding) throws IOException {
        FileInputStream in = null;

        List<String> var3;
        try {
            in = openInputStream(file);
            var3 = IO.readLines(in, encoding == null ? Charset.defaultCharset() : encoding);
        } finally {
            IO.closeQuietly(in);
        }

        return var3;
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File \'" + file + "\' exists but is a directory");
            } else if (!file.canRead()) {
                throw new IOException("File \'" + file + "\' cannot be read");
            } else {
                return new FileInputStream(file);
            }
        } else {
            throw new FileNotFoundException("File \'" + file + "\' does not exist");
        }
    }

    private static boolean isSeparator(char ch) {
        return ch == 47 || ch == 92;
    }

    public static URL createCukesPropertyFileUrl(final ClassLoader classLoader) {
        String cukesProfile = System.getProperty("cukes.profile");
        String propertiesFileName = cukesProfile == null || cukesProfile.isEmpty()
            ? "cukes.properties"
            : "cukes-" + cukesProfile + ".properties";
        return classLoader.getResource(propertiesFileName);
    }
}
