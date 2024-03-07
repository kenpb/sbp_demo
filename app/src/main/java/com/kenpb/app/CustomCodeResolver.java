package com.kenpb.app;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import gg.jte.CodeResolver;
import gg.jte.TemplateNotFoundException;

public class CustomCodeResolver implements CodeResolver {

    private Path[] roots;

    public CustomCodeResolver(Path[] paths) {
        this.roots = paths;
    }

    @Override
    public String resolve(String name) {
        try {
            return resolveRequired(name);
        } catch (TemplateNotFoundException e) {
            return null;
        }
    }

    @Override
    public String resolveRequired(String name) throws TemplateNotFoundException {
        Path file = null;

        for (Path path : this.roots) {
            Path currentPath = path.resolve(name);
            if (Files.exists(currentPath)) {
                file = currentPath;
                break;
            }
        }

        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (NoSuchFileException e) {
            throw new TemplateNotFoundException(
                    name + " not found (tried to load file at " + file.toAbsolutePath() + ")");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public boolean exists(String name) {
        for (Path path : this.roots) {
            if (Files.exists(path.resolve(name))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public long getLastModified(String name) {
        for (Path path : this.roots) {
            Path currentPath = path.resolve(name);
            if (Files.exists(currentPath)) {
                return getLastModified(currentPath);
            }
        }

        return 0L;
    }

    private long getLastModified(Path file) {
        return file.toFile().lastModified();
    }

}
