package ru.relex.internship.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.relex.internship.models.*;
import ru.relex.internship.utils.FileUtilities;
import ru.relex.internship.utils.SortUtilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


@Component
public class NumberService {
    private File getFile(String path) throws FileNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            return file;
        } else {
            throw new FileNotFoundException("There is no file on the path: " + path);
        }
    }
    private Stream<Integer> fileToStream(File file) throws IOException {
        return Files.lines(file.toPath()).map(Integer::parseInt);
    }

    @Cacheable("result_#path_#operation")
    public Result executeOperation(String operation, String path) throws Exception {
        return switch (operation) {
            case "max" -> new IntegerResult(this.getMax(path));
            case "min" -> new IntegerResult(this.getMin(path));
            case "median" -> new FloatResult(this.getMedian(path));
            case "arithmetic mean" -> new FloatResult(this.getArithmeticMean(path));
            case "increasing sequence" -> new ListResult(this.getIncreasingSequence(path));
            case "decreasing sequence" -> new ListResult(this.getDecreasingSequence(path));
            default -> throw new Exception("Unknown command: " + operation);
        };
    }

    @Cacheable("result_#path_max")
    public Integer getMax(String path) throws IOException {
        File file = this.getFile(path);
        Integer result = null;

        try (Stream<Integer> stream = this.fileToStream(file)) {
            Iterator<Integer> numbers = stream.iterator();

            Integer max = numbers.next();
            while (numbers.hasNext()) {
                Integer current = numbers.next();
                if (current > max) max = current;
            }
            result = max;
        }
        return result;
    }

    @Cacheable("result_#path_min")
    public Integer getMin(String path) throws IOException {
        File file = this.getFile(path);
        Integer result = null;

        try (Stream<Integer> stream = this.fileToStream(file)) {
            Iterator<Integer> numbers = stream.iterator();

            Integer min = numbers.next();
            while (numbers.hasNext()) {
                Integer current = numbers.next();
                if (current < min) min = current;
            }
            result = min;
        }
        return result;
    }

    @Cacheable("result_#path_median")
    public Float getMedian(String path) throws IOException {
        String sortedFilePath = "D:\\Стажировка\\" + FileUtilities.getName(path) + "_sorted.txt";

        File file = this.getFile(path);
        File sortedFile = new File(sortedFilePath);

        // Отсортируем файл для нахождения медианы
        SortUtilities.fileMergeSort(file, sortedFile);

        Float result = null;
        long count = 0;

        // Найдём кол-во чисел в файле
        try (Stream<Integer> stream = this.fileToStream(sortedFile)) {
            count = stream.count();
        }

        // Найдём центральный элемент
        try (Stream<Integer> stream = this.fileToStream(sortedFile)) {
            if (count % 2 == 1) {
                Iterator<Integer> numbers = stream.skip(count / 2).iterator();
                result = numbers.next().floatValue();
            } else {
                Iterator<Integer> numbers = stream.skip((count / 2) - 1).iterator();
                result = (numbers.next().floatValue() + numbers.next()) / 2;
            }
        }
        return result;
    }

    @Cacheable("result_#path_arithmetic_mean")
    public Float getArithmeticMean(String path) throws IOException {
        File file = this.getFile(path);
        Float result = null;

        try (Stream<Integer> stream = this.fileToStream(file)) {
            long count = 0;
            Long sum = (long) 0;

            Iterator<Integer> numbers = stream.iterator();
            while (numbers.hasNext()) {
                sum += numbers.next();
                count += 1;
            }

            result = sum.floatValue() / count;
        }

        return result;
    }

    @Cacheable("result_#path_max")
    public List getIncreasingSequence(String path) throws IOException {
        File file = this.getFile(path);
        List result = new ArrayList<>();

        try (Stream<Integer> stream = this.fileToStream(file)) {
            Iterator<Integer> numbers = stream.iterator();

            List<Integer> temporarySequence = new ArrayList<>();
            int maxLength = 2;

            if (numbers.hasNext()) {
                int previous = numbers.next();
                temporarySequence.add(previous);
                while (numbers.hasNext()) {
                    int current = numbers.next();
                    if (current > previous) {
                        temporarySequence.add(current);
                    } else {
                        if (temporarySequence.size() == maxLength) {
                            result.add(new ArrayList<>(temporarySequence));
                        } else if (temporarySequence.size() > maxLength) {
                            maxLength = temporarySequence.size();
                            result.clear();
                            result.add(new ArrayList<>(temporarySequence));
                        }

                        temporarySequence.clear();
                        temporarySequence.add(current);
                    }
                    previous = current;
                }

                if (temporarySequence.size() == maxLength) {
                    result.add(temporarySequence);
                } else if (temporarySequence.size() > maxLength) {
                    result.clear();
                    result.add(temporarySequence);
                }
            }
        }

        return result;
    }

    @Cacheable(value = "result_#path_decr_sequence")
    public List getDecreasingSequence(String path) throws IOException {
        File file = this.getFile(path);
        List result = new ArrayList<>();

        try (Stream<Integer> stream = this.fileToStream(file)) {
            Iterator<Integer> numbers = stream.iterator();

            List<Integer> temporarySequence = new ArrayList<>();
            int maxLength = 2;

            if (numbers.hasNext()) {
                int previous = numbers.next();
                temporarySequence.add(previous);
                while (numbers.hasNext()) {
                    int current = numbers.next();
                    if (current < previous) {
                        temporarySequence.add(current);
                    } else {
                        if (temporarySequence.size() == maxLength) {
                            result.add(new ArrayList<>(temporarySequence));
                        } else if (temporarySequence.size() > maxLength) {
                            maxLength = temporarySequence.size();
                            result.clear();
                            result.add(new ArrayList<>(temporarySequence));
                        }

                        temporarySequence.clear();
                        temporarySequence.add(current);
                    }
                    previous = current;
                }

                if (temporarySequence.size() == maxLength) {
                    result.add(temporarySequence);
                } else if (temporarySequence.size() > maxLength) {
                    result.clear();
                    result.add(temporarySequence);
                }
            }
        }

        return result;
    }
}
