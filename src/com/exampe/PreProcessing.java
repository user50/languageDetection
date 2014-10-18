package com.exampe;

import java.io.*;

/**
 * Created by user50 on 18.10.2014.
 */
public class PreProcessing {

    PrintWriter writer;

    public static void main(String[] args) throws IOException {
        new PreProcessing().process();
    }

    public  void process() throws IOException {
        String dir = "data";
        writer = new PrintWriter(new BufferedWriter(new FileWriter("data/data.txt")));

         File[] directories = new File(dir).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        for (File directory : directories)
            processData(directory);

        writer.close();
    }

    private  void processData(File directory) throws IOException {
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().matches(".*\\.txt");
            }
        });

        for (File file : files)
            collectData(file, directory.getName());

    }

    private  void collectData(File file, String label) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = null;

        while ( (line = reader.readLine()) != null)
        {
            String text = line.split("\t")[1];
            if (text.length() < 5)
                continue;

            writer.println(label + " " + label + " " + text);
        }

        reader.close();
    }
}
