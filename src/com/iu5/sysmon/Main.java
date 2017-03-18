package com.iu5.sysmon;

import com.iu5.sysmon.commandline.CommandLineParameters;
import com.iu5.sysmon.logtasks.LogTasks;
import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Locale;

public class Main {

    private static final String APPNAME = "<path to jar>" + System.getProperty("file.separator") +
            (new File(Main.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName());

    public static String printUsage() {
        String sep = System.lineSeparator();
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalPhysicalMemorySize = operatingSystemMXBean.getTotalPhysicalMemorySize();
        long freePhysicalMemorySize = operatingSystemMXBean.getFreePhysicalMemorySize();

        return "CPU Load %: " + String.format(Locale.US, "%.2f", operatingSystemMXBean.getSystemCpuLoad() * 100) + sep +
                "Memory usage %: "
                + String.format(Locale.US, "%.2f", (double) (totalPhysicalMemorySize - freePhysicalMemorySize) / totalPhysicalMemorySize * 100) + sep +
                "Memory size in use (MB): " + Long.toString((totalPhysicalMemorySize - freePhysicalMemorySize) >> 20) + sep +
                "Free memory size (MB): " + Long.toString(freePhysicalMemorySize >> 20) + sep +
                "Total memory size (MB): " + Long.toString(totalPhysicalMemorySize >> 20);
    }

    public static void main(String[] args) {
        try {
            CommandLineParameters params = new CommandLineParameters(args);
            if (params.getLogType() == CommandLineParameters.LogType.HELP) {
                System.out.println(getHelpMessage());
                System.exit(0);
            } else {
                LogTasks.runTask(params);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.err.println(getHelpMessage());
            System.exit(1);
        }
    }

    private static String getHelpMessage() {
        String sep = System.lineSeparator();
        return "Manual: " + sep
                + "Log to file:" + sep
                + "\tjava -jar " + APPNAME + " file <directory> [<log period>]. Any further parameters are ignored" + sep
                + "Send log over TCP:" + sep
                + "\tjava -jar " + APPNAME + " tcp <hostname> <portnumber> [<log period>]. Any further parameters are ignored" + sep
                + "Send log over UDP:" + sep
                + "\tjava -jar " + APPNAME + " udp <hostname> <portnumber> [<log period>]. Any further parameters are ignored" + sep
                + "Get help:" + sep
                + "\tjava -jar " + APPNAME + " ?" + sep
                + "\tor" + sep
                + "\tjava -jar " + APPNAME + " help";
    }
}