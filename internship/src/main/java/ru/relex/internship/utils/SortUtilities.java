package ru.relex.internship.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SortUtilities {
    public static void fileMergeSort(File input, File output) throws IOException {
        output.delete();   // Очищаем выходной файл, чтобы записать туда новые числа

        int chunkSize = 1000000;
        int chunkCount = 0;

        // Разделяем основной файл на подфайлы
        try (LineIterator iterator = FileUtils.lineIterator(input)) {
            List<Integer> chunk = new ArrayList<>();

            while (iterator.hasNext()) {
                chunk.clear();
                chunkCount++;
                while (iterator.hasNext() && chunk.size() < chunkSize) {
                    chunk.add(Integer.parseInt(iterator.next()));
                }

                // Сортируем данные и записываем их во временный файл
                Collections.sort(chunk);
                FileUtils.writeLines(new File("D:\\Стажировка\\tmp\\chunk_" + chunkCount + ".txt"), chunk);
            }
        }

        // Список чанков, по которым будем делать обход
        List<LineIterator> chunks = new ArrayList<>();
        for (int i = 1; i <= chunkCount; i++) {
            chunks.add(FileUtils.lineIterator(new File("D:\\Стажировка\\tmp\\chunk_" + i + ".txt")));
        }

        List<Integer> values = new ArrayList<>();
        for (LineIterator chunk : chunks) {
            if (chunk.hasNext()) {
                values.add(Integer.parseInt(chunk.next()));
            } else {
                chunk.close();
                chunks.remove(chunk);
            }
        }

        List<Integer> buffer = new ArrayList<>();

        // Пока есть чанки сортируем числа и записываем их в основной файл
        while (!chunks.isEmpty()) {
            int indexMinNumber = SortUtilities.indexMin(values);

            buffer.add(values.get(indexMinNumber));
            if (chunks.get(indexMinNumber).hasNext()) {
                values.set(indexMinNumber, Integer.parseInt(chunks.get(indexMinNumber).next()));
            } else {
                chunks.get(indexMinNumber).close();
                chunks.remove(chunks.get(indexMinNumber));
                values.remove(indexMinNumber);
            }

            // Если буффер наполнился - записываем в файл
            if (buffer.size() >= chunkSize) {
                FileUtils.writeLines(output, buffer, true);
                buffer.clear();
            }
        }

        // Записываем оставшиеся числа
        if (!buffer.isEmpty() || !values.isEmpty()) {
            Collections.sort(values);
            buffer.addAll(values);
            FileUtils.writeLines(output, buffer, true);
        }

        // Очищаем папку с временными чанками
        for (int i = 1; i <= chunkCount; i++) {
            File file = new File("D:\\Стажировка\\tmp\\chunk_" + i + ".txt");
            file.delete();
        }
    }
    private static int indexMin(List<Integer> list) {
        int minIndex = 0;
        int min = list.get(minIndex);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < min) {
                minIndex = i;
                min = list.get(minIndex);
            }
        }

        return minIndex;
    }
}
