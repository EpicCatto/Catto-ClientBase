package catto.uwu.module.file;

import com.google.gson.Gson;
import catto.uwu.Client;

import java.io.*;

public interface IFile {

    void save(Gson gson);

    void load(Gson gson);

    void setFile(File root);

    default void writeFile(String content, File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
        } catch (IOException e) {
            Client.clientData.logError("Error while writing file", e);
        }
    }

    default String readFile(File file) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            Client.clientData.logError("Error while reading file", e);
        }
        return builder.toString();
    }

}