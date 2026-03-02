package com.amdose.base.devportal.utils;

import lombok.SneakyThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;

/**
 * @author Alaa Jawhar
 */
public class JarFileUtils {

    @SneakyThrows
    public static void overrideHtmlInJar(String jarFilePath, String htmlFilePath, String newHtmlContent) {
        if (jarFilePath.startsWith("/")) { // for windows OS
            jarFilePath = jarFilePath.substring(1);
        }

        Path jarPath = Paths.get(jarFilePath);
        URI uri = URI.create("jar:file:" + jarPath.toUri().getPath().replaceAll(" ", "%20"));

        try (FileSystem zipfs = FileSystems.newFileSystem(uri, Collections.singletonMap("create", "true"))) {
            Path htmlPath = zipfs.getPath(htmlFilePath);
            if (Files.exists(htmlPath)) {
                Files.delete(htmlPath);
            }
            Files.createDirectories(htmlPath.getParent());
            Files.write(htmlPath, newHtmlContent.getBytes());
        }
    }

    public static void main(String[] args) throws URISyntaxException {
        JarFileUtils.overrideHtmlInJar(JarFileUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(),
                "/dev-portal-static/index.html", "<p>it works</p>");
    }



}