package com.example.astromedics.helpers;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHandler {

    private static FileHandler instance;
    private Context context;

    private FileHandler(Context context) {
        this.context = context;
    }

    public static FileHandler getInstance(Context context) {
        if (instance == null) {
            instance = new FileHandler(context);
        }

        return instance;
    }

    public boolean writeFile(String fileName, String json) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName,
                                                                       context.MODE_PRIVATE);
            if (json != null) {
                fileOutputStream.write(json.getBytes());
            }
            fileOutputStream.close();
            return true;
        } catch (IOException ioException) {
            return false;
        }
    }

    public String readFile(String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }
}
