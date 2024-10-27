package org.example.rulebased.streaming.flinkdroolsdynamic;


public class Main {
    
    public static void main(String[] args) throws Exception {

        var fireDetectJob = new FireDetectJob();
        fireDetectJob.run();
    }
}
