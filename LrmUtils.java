/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lrm_file_handling;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Yash
 */
public class LrmUtils {

    public void createNewFile(String filePath) {
        //creates Path instance  
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);     //delete if file exists
            path = Files.createFile(path);     //creates new file at specified location  
            System.out.println("File Created at Path: " + path);
        } catch (IOException e) {
            System.out.println("Error in createNewFile");
            e.printStackTrace();
        }
    }

    public void readContentFromFile(String filePathForReading) {
        try {

            // Creates a FileInputStream
            File f = new File(filePathForReading);
            if (f.exists()) {
                FileInputStream file = new FileInputStream(f);

                // Creates a BufferedInputStream
                BufferedInputStream input = new BufferedInputStream(file);

                // Reads first byte from file
                int i = input.read();

                while (i != -1) {
                    System.out.print((char) i);

                    // Reads next byte from the file
                    i = input.read();
                }
                input.close();
            } else {
                System.out.println("Could not found file with given path...");
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void appendDataToFile(String filePath, String data) {

        try {
            File f = new File(filePath);
            if (f.exists()) {
                FileWriter writerObj = new FileWriter(f, true);
                writerObj.write(data); //appends data in the file
                writerObj.close();
            } else {
                System.out.println("Could not found file with given path...");
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void OverwriteFileData(String filePath, String data) {
        try {
            try (FileWriter writerObj = new FileWriter(filePath, false)) {
                writerObj.write(data); //overwrite data in the file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchInFile(String filePath, String word) {
        int totalCountOfWord = countWordInFile(filePath, word);
        if (totalCountOfWord > 0) {
            System.out.println("The word " + word + " found in a given file");
        } else {
            System.out.println("The word " + word + " not found in a given file");
        }
    }

    public int countWordInFile(String filePath, String word) {
        int totalWordCount = 0;
        File file = new File(filePath);
        try {
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String st;
                while ((st = br.readLine()) != null) {
                    totalWordCount += countOccurencesInLine(word, st);
                }
                br.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("The word " + word + " is found " + totalWordCount + " times in a given file");
        return totalWordCount;
    }

    int countOccurencesInLine(String word, String Line) {
        int wordcount = 0;
        String str[] = Line.split(" ");
        for (String wordToBeMatched : str) {
            if (wordToBeMatched.toLowerCase().contains(word.toLowerCase())) {
                wordcount++;
            }
        }
        return wordcount;
    }

    public void replaceWordsInFile(String filePath, String oldWords, String newWords) {

        String fileContents = new String();
        File file = new File(filePath);
        try {
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String st;
                while ((st = br.readLine()) != null) {
                    fileContents += st;
                }
                br.close();
            }
            //Replacing the oldWords with newWords
            fileContents = fileContents.replaceAll(oldWords, newWords);
            //instantiating the FileWriter class
            System.out.println(fileContents);
            FileWriter writer = new FileWriter(filePath);
            writer.append(fileContents);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
