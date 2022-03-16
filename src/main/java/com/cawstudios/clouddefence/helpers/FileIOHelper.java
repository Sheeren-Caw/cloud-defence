package com.cawstudios.clouddefence.helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileIOHelper {
    private FileIOHelper() {
    }

    public static List<String> readFileByPath(String filePath) {
        List<String> lines = new ArrayList<>();
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                lines.add(curLine);
            }
        } catch (IOException e) {
            return lines;
        }
        return lines;
    }

    public static boolean createFile(String filePath) {
        try {
            // Creating File Object
            File file = new File(filePath);
            return file.createNewFile();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }
    }

    public static boolean writeToFile(String filePath, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(text);
            return true;
        } catch (IOException e) {
            System.out.print(e.getMessage());
            return false;
        }
    }
}
