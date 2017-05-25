package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyApp implements DataConnection {
    private static final String STATISTICS_FILE_NAME = "statistics.txt";
    private static final String INPUT_DATA_FILE_NAME = "input_data.txt";

    private static int count = 0;
    private static int savedTimes = 0;

    private String someData;

    public MyApp(String someData) {
        this.someData = someData;
    }

    public double loadData() throws Exception {
        File file = new File(INPUT_DATA_FILE_NAME);
        FileInputStream fileInputStream = new FileInputStream(file);
        String inputString = "";
        int i = fileInputStream.read();
        do {
            inputString = inputString + new String(new byte[]{(byte) i});
            i = fileInputStream.read();
        } while (i != -1);

        int sum = 0;
        int startIndex = 0;
        while (true) {
            int readingInteger = inputString.indexOf("\n", startIndex + 1);
            if (readingInteger == -1) {
                break;
            }

            String substring = inputString.substring(startIndex, readingInteger);
            String[] splitString = substring.split("	");
            if (splitString[2].contains(someData) || splitString[2].contains(someData)) {
                sum = sum + Integer.parseInt(splitString[3]);
            }

            count++;
            startIndex = readingInteger;
        }

        return sum > 0 ? (double) sum / count : 0;
    }

    public void saveData(int year, int data) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(STATISTICS_FILE_NAME), true);
        String statisticsLine = savedTimes + "	" + year + "	" + data + "\n";
        fileOutputStream.write(statisticsLine.getBytes());
        savedTimes++;
    }
}