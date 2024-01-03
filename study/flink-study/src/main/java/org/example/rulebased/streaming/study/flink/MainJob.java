package org.example.rulebased.streaming.study.flink;

import org.example.rulebased.streaming.study.flink.fireDetected.FireDetectedJob;


public class MainJob {

    public static void main(String[] args) throws Exception {
        
        // FirstSampleJob.executeJob();
        FireDetectedJob.executeJob();
    }
    
}
