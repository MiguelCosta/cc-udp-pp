package Cliente.TimeCounter;

import Cliente.MainCliente;
import java.util.ArrayList;

public class TimeCounter extends Thread {

    private ArrayList<Long> timeCountList;
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
    public TimeCounter(int packetsNumber, Long estimatedRTT) {
        this.timeCountList = new ArrayList<Long>(packetsNumber);
        for (long l : timeCountList) {
            l = 0;
        }
        this.estimatedRTT = estimatedRTT;
        this.alpha = (float) 0.125;
        this.beta = (float) 0.25;
        this.keeprunning = true;

    }

    public ArrayList getTimeCountList() {
        ArrayList newList = new ArrayList();

        for (int i = 1; i < timeCountList.size(); i++) {
            newList.add(timeCountList.get(i));
        }

        return newList;
    }

    public void setTimeCountList(int index, long value) {
        synchronized (timeCountList.get(index)) {
            timeCountList.set(index, value);
        }
    }

    /*
     * Parar a Thread
     */
    public void stoprunning() {
        keeprunning = false;
    }

    /*
     * Trata a chegada de um novo Ack
     */
    public void newAck(int index) {
        sampleRTT = System.currentTimeMillis() - timeCountList.get(index);
        timeCountList.set(index, Long.valueOf(0));
        this.estimateRTT();
        this.calculateDevRTT();
        this.calculateTimeOut();
    }

    /*
     * Estima o RTT baseando-se no actual e na nova medicao
     */
    private void estimateRTT() {
        long newRTT = (long) ((1 - alpha) * estimatedRTT + alpha * sampleRTT);
        estimatedRTT = newRTT;
    }

    /*
     * Calcula desvio padrão do RTT
     */
    private void calculateDevRTT() {
        long newdevRTT = (long) ((1 - beta) * devRTT + beta * (sampleRTT - estimatedRTT));
        devRTT = newdevRTT;
    }

    private void calculateTimeOut() {
        timeout = estimatedRTT + 4 * devRTT;
    }
    
    public void startCounter(int index){
      timeCountList.set(index, System.currentTimeMillis());
    }
    public void stopCounter(int index){
        timeCountList.set(index, Long.valueOf(0));
    }

    @Override
    public void run() {
        Long current, total;
        while (keeprunning) {
            for (long time : timeCountList) {
                if (time != 0) {
                    current = System.currentTimeMillis();
                    total = current - time;
                    if (total > timeout) {
                        MainCliente.getSender().setTamanhoJanelaInicial(
                                MainCliente.getSender().getTamanhoJanela() / 2);
                    }
                }
            }
        }
    }
}
