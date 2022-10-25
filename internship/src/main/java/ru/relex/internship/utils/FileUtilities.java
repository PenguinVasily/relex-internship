package ru.relex.internship.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtilities {
    public static String getName(String path) {
        String name = Path.of(path).toFile().getName();
        return name.substring(0, name.lastIndexOf("."));
    }
    public static long countLines(String path) throws IOException {
        return Files.lines(Path.of(path)).count();
    }
}
