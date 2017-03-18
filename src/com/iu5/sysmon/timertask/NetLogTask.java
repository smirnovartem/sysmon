package com.iu5.sysmon.timertask;

abstract class NetLogTask implements Runnable {

    protected String hostname;
    protected int port = 0;

    public NetLogTask(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

}
