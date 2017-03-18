package com.iu5.sysmon.commandline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandLineParameters {

    private static final String FILELOGSTRING = "file";
    private static final String TCPLOGSTRING = "tcp";
    private static final String UDPLOGSTRING = "udp";
    private static final String HELP1 = "?";
    private static final String HELP2 = "help";

    private final String[] args;

    public enum LogType {
        FILELOG, TCPLOG, UDPLOG, HELP
    }

    private LogType logType;
    private Path fileLogPath;
    private String hostname;
    private int port;
    private int periodSeconds = 30;

    public CommandLineParameters(String[] args) throws IllegalArgumentException {
        this.args = args;
        if (this.args.length > 0) {
            String firstParameter = this.args[0].toLowerCase();
            switch (firstParameter) {
                case FILELOGSTRING:
                    parseFileLogParameters();
                    break;
                case TCPLOGSTRING:
                case UDPLOGSTRING:
                    parseNetLogParameters();
                    break;
                case HELP1:
                case HELP2:
                    logType = LogType.HELP;
                    fileLogPath = null;
                    hostname = null;
                    port = 0;
                    periodSeconds = 0;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown parameter '" + firstParameter + "'!");
            }
        } else
            throw new IllegalArgumentException("No parameters specified!");
    }

    private void parseNetLogParameters() {
        fileLogPath = null;
        if (args.length >= 3) {
            if (args[0].toLowerCase().equals(TCPLOGSTRING)) {
                logType = LogType.TCPLOG;
            } else if (args[0].toLowerCase().equals(UDPLOGSTRING)) {
                logType = LogType.UDPLOG;
            }
            hostname = args[1];
            try {
                port = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cannot parse port number!", e);
            }
            parsePeriod(3);
        } else
            throw new IllegalArgumentException("Missing arguments for " + args[0].toLowerCase() + "!");
    }

    private void parseFileLogParameters() throws IllegalArgumentException {
        logType = LogType.FILELOG;
        hostname = null;
        port = 0;
        if (args.length >= 2) {
            fileLogPath = Paths.get(args[1]).toAbsolutePath();
            System.out.println(fileLogPath);
            try {
                fileLogPath = Files.createDirectories(fileLogPath);
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not create directory " + e.getMessage());
            }
            parsePeriod(2);
        } else
            throw new IllegalArgumentException("Missing filepath parameter!");
    }

    private void parsePeriod(int i) {
        try {
            periodSeconds = Integer.parseInt(args[i]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Could not parse log periodSeconds! Setting to default. " + e.getMessage());
        }
    }

    public LogType getLogType() {
        return logType;
    }

    public Path getFileLogPath() {
        return fileLogPath;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public int getPeriodSeconds() {
        return periodSeconds;
    }
}
