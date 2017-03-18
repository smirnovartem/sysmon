package com.iu5.sysmon.timertask;

import com.iu5.sysmon.Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;

public class UdpLogTask extends NetLogTask {

    public UdpLogTask(String host, int port) {
        super(host, port);
    }

    @Override
    public void run() {
        DatagramSocket soc = null;
        try {
            String sb = Calendar.getInstance().getTime().toString() + System.getProperty("line.separator") + Main.printUsage();
            byte[] buf = sb.getBytes();
            InetAddress address = InetAddress.getByName(hostname);
            soc = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
            soc.send(packet);
            System.out.println(Calendar.getInstance().getTime() + " Packet sent to " + hostname + ":" + port);
        } catch (IOException e) {
            System.err.println(Calendar.getInstance().getTime() + " " + e.getMessage());
        } finally {
            if (soc != null)
                soc.close();
        }
    }

}
