package Cliente.TimeCounter;

import java.util.ArrayList;

public class TimeCounter extends Thread{
    
    ArrayList<Long> timeOut;
    long estimatedRTT;
    long sampleRTT;
    int alpha;
    
    TimeCounter(ArrayList<Long> timeOut, Long estimatedRTT, int alpha){
        this.timeOut=timeOut;
        this.estimatedRTT=estimatedRTT;
        this.alpha=alpha;
    }
    
    public void newAck(int index){
        sampleRTT= System.currentTimeMillis() - timeOut.get(index);
        timeOut.set(index, Long.valueOf(0));
        this.estimateRTT();
    }
    
    public void estimateRTT(){
        long newRTT=(1-alpha)*estimatedRTT+alpha*sampleRTT;
        estimatedRTT=newRTT;
    }
    
    
    public void run(){
        
    }
}
