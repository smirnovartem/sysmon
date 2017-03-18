package com.iu5.sysmon.logtasks;

import com.iu5.sysmon.commandline.CommandLineParameters;
import com.iu5.sysmon.timertask.FileLogTask;
import com.iu5.sysmon.timertask.TcpLogTask;
import com.iu5.sysmon.timertask.UdpLogTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LogTasks {
    private static Runnable createLogTask(CommandLineParameters params) {
        switch (params.getLogType()) {
            case FILELOG:
                return new FileLogTask(params.getFileLogPath());
            case TCPLOG:
                return new TcpLogTask(params.getHostname(), params.getPort());
            case UDPLOG:
                return new UdpLogTask(params.getHostname(), params.getPort());
            default:
                return null;
        }
    }

    public static void runTask(CommandLineParameters params) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> logFuture = scheduler.scheduleWithFixedDelay(
                createLogTask(params), 0, params.getPeriodSeconds(), TimeUnit.SECONDS
        );
    }
}
