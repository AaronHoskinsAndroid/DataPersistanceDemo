package examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.fileaccess;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class InternalFileReadWriteUtility {

    public static String readTextFromFile(Context context) throws IOException {
        String returnString = "";
        FileInputStream fileInputStream = context.openFileInput("sampleText.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        int currentRead = inputStreamReader.read();

        while(currentRead > -1) {
            char currentChar = (char)currentRead;
            returnString = returnString + currentChar;
            currentRead = inputStreamReader.read();
        }

        inputStreamReader.close();
        return returnString;
    }

    public static void writeStringToFile(String stringToWrite, Context context) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput("sampleText.txt", Context.MODE_PRIVATE);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

        outputStreamWriter.write(stringToWrite);
        outputStreamWriter.close();
    }
}
