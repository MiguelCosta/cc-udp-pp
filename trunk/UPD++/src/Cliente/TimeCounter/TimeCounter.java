package Cliente.TimeCounter;

import Cliente.MainCliente;
import java.util.ArrayList;

public class TimeCounter extends Thread{
    
    public ArrayList<Long> timeCountList;
    private long timeout;
    private long estimatedRTT;
    private long sampleRTT;
    private long devRTT;
    private float alpha, beta;
    private boolean keeprunning;
    
    /*
     * Construtor
     * Recebe array de tempos dos pacotes, o RTT
     */
    TimeCounter(ArrayList<Long> timeCountList, Long estimatedRTT){
        this.timeCountList=timeCountList;
        this.estimatedRTT=estimatedRTT;
        this.alpha=(float) 0.125;
        this.beta=(float) 0.25;
        this.keeprunning=true;
        
    }
    
    /*
     * Parar a Thread
     */
    public void stoprunning(){
        keeprunning=false;
    }
    
    /*
     * Trata a chegada de um novo Ack
     */
    public void newAck(int index){
        sampleRTT= System.currentTimeMillis() - timeCountList.get(index);
        timeCountList.set(index, Long.valueOf(0));
        this.estimateRTT();
        this.calculateDevRTT();
        this.calculateTimeOut();
    }
    
    /*
     * Estima o RTT baseando-se no actual e na nova medicao
     */
    public void estimateRTT(){
        long newRTT=(long) ((1-alpha)*estimatedRTT+alpha*sampleRTT);
        estimatedRTT=newRTT;
    }
    
    /*
     * Calcula desvio padrão do RTT
     */
    public void calculateDevRTT(){
        long newdevRTT=(long) ((1-beta)*devRTT+beta*(sampleRTT-estimatedRTT));
        devRTT=newdevRTT;
    }
    
    public void calculateTimeOut(){
        timeout=estimatedRTT+4*devRTT;
    }
    
    
    @Override
    public void run(){
        Long current, total;
        while(keeprunning){
            for(long time : timeCountList){
                if(time!=0){
                    current=System.currentTimeMillis();
                    total=current-time;
                    if(total>timeout)
                        MainCliente.getSender().setTamanhoJanelaInicial(
                                MainCliente.getSender().getTamanhoJanela()/2);
                }
            }
        }
    }
}
