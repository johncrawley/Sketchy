package com.jacstuff.sketchy;

public class Profiler {

    private long previousTime;
    private String prefix;

    public Profiler(){
        this("");
    }

    public Profiler(String prefix){
        this.prefix = prefix;
    }



    public void start(String tag){
        log(tag + ": Started!");
        previousTime = System.nanoTime();
    }

    public void start(){
        start("");
    }


    public void check(String item){
        long difference = System.nanoTime() - previousTime;
        difference /= 1000;
        log("finished: " + item + " took " + difference  + " millis.");
        previousTime =System.nanoTime();
    }


    public void stop(){
        log("Stopped!");
    }

    private boolean isMuted;

    public void mute(){
        this.isMuted = true;
    }


    private void log(String msg){
        if(isMuted){
            return;
        }
        System.out.println("^^^ Profiler: " + prefix + " " + msg);
    }
}
