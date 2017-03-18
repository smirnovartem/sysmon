package com.iu5.sysmon.timertask;

import com.iu5.sysmon.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Calendar;

public class FileLogTask implements Runnable {

    private Path filepath;

    public FileLogTask(Path filepath) {
        this.filepath = filepath;
    }

    @Override
    public void run() {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            String file = filepath.toString() + System.getProperty("file.separator") + "file.log";
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(Calendar.getInstance().getTime().toString());
            bw.newLine();
            bw.write(Main.printUsage());
            bw.newLine();
            bw.newLine();
            System.out.println(Calendar.getInstance().getTime() + " Log written to: " + file);
        } catch (IOException e) {
            System.err.println(Calendar.getInstance().getTime() + " " + e.getMessage());
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                System.err.println(Calendar.getInstance().getTime() + " " + e.getMessage());
            }
        }
    }
}
