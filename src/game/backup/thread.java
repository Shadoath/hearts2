package game.backup;

import java.text.DecimalFormat;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class thread extends Thread
{
    private static final String TAG = thread.class.getSimpleName();
    
    private final static int MAX_FPS = 50;
    private final static int MAX_FRAME_SKIPS = 5;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    
    private DecimalFormat df = new DecimalFormat("0.##");
    private final static int STAT_INTERVAL = 1000;
    private final static int FPS_HISTORY_NR = 10;
    private long statusIntervalTimer = 0l;
    private long totalFramesSkipped = 0l;
    private long framesSkippedPerStatCycle = 0l;
    
    private int frameCountPerStatCycle = 0;
    private long totalFrameCount = 0l;
    private double fpsStore[];
    private long statsCount = 0;
    private double averageFps = 0.0;
    
    private SurfaceHolder surfaceHolder;
    private heartsPanel gamePanel;
    
    private boolean running;
    public void setRunning(boolean running){
        this.running = running;
    }
    
    public thread(SurfaceHolder surfaceHolder, heartsPanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    
    public void run(){
        Canvas canvas;
        Log.d(TAG, "Starting Game Loop");
        initTimingElements();
        
        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;
        
        sleepTime = 0;
        
        while(running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;
                    
                    this.gamePanel.update();
                    this.gamePanel.render(canvas);
                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);
                    
                    if(sleepTime > 0){
                        try {
                            Thread.sleep(sleepTime);
                        }catch (InterruptedException e) {}
                    }
                    
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
                        this.gamePanel.update();
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                    
                    framesSkippedPerStatCycle += framesSkipped;
                    storeStats();
                }
            } finally {
                if (canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    
    private void storeStats() {
        frameCountPerStatCycle++;
        totalFrameCount++;
        statusIntervalTimer += FRAME_PERIOD;
        
        if(statusIntervalTimer >= STAT_INTERVAL){
            double actualFps = (double)(frameCountPerStatCycle / (STAT_INTERVAL / 1000));
            fpsStore[(int)statsCount % FPS_HISTORY_NR] = actualFps;
            statsCount++;
            double totalFps = 0.0;
            
            for(int i =0; i < FPS_HISTORY_NR; i++) {
                totalFps += fpsStore[i];
            }
            
            if(statsCount < FPS_HISTORY_NR){
                averageFps = totalFps / statsCount;
            }else {
                averageFps = totalFps / FPS_HISTORY_NR;
            }
            
            totalFramesSkipped += framesSkippedPerStatCycle;
            framesSkippedPerStatCycle = 0;
            statusIntervalTimer = 0;
            frameCountPerStatCycle = 0;
            gamePanel.setAvgFps("FPS: " + df.format(averageFps));
            
        }
    }
    
    private void initTimingElements(){
        fpsStore = new double[FPS_HISTORY_NR];
        for(int i = 0; i < FPS_HISTORY_NR; i++){
            fpsStore[i] = 0.0;
        }
        Log.d(TAG + ".initTimingElements()","Timing elements for stats intialized");
    }
    
}
