package me.contrapost.vingtun;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTestUtil {

    public static String createFile(final String content) throws IOException {
        Path tempFile = Files.createTempFile("testDeckFile", ".tmp");
        tempFile.toFile().deleteOnExit();

        Files.write(tempFile, content.getBytes());

        return tempFile.toString();
    }
}
