package com.iu5.sysmon.timertask;

import com.iu5.sysmon.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

public class TcpLogTask extends NetLogTask {

    public TcpLogTask(String host, int port) {
        super(host, port);
    }

    @Override
    public void run() {
        Socket soc = null;
        PrintWriter pw = null;
        try {
            soc = new Socket(hostname, port);
            pw = new PrintWriter(soc.getOutputStream(), true);
            pw.println(Main.printUsage());
            System.out.println(Calendar.getInstance().getTime() + " Message sent to " + hostname + ":" + port);
        } catch (IOException e) {
            System.err.println(Calendar.getInstance().getTime() + " " + e.getMessage());
        } finally {
            try {
                if (pw != null)
                    pw.close();
                if (soc != null)
                    soc.close();
            } catch (IOException e) {
                System.err.println(Calendar.getInstance().getTime() + " " + e.getMessage());
            }
        }
    }
}
