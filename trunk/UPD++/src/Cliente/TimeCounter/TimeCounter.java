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
        this.timeCountList = new ArrayList<Long>();
        for (int i = 0; i < packetsNumber + 1; i++) {
            timeCountList.add(Long.valueOf(0));
        }
        this.estimatedRTT = estimatedRTT;
        this.alpha = (float) 0.125;
        this.beta = (float) 0.25;
        this.keeprunning = true;
        System.out.println("estimated: " + this.estimatedRTT);
        System.out.println("criou o timecounter! tamanho: " + timeCountList.size() + "" + packetsNumber);


    }

    public ArrayList getTimeCountList() {
        ArrayList newList = new ArrayList();

        for (int i = 1; i < timeCountList.size(); i++) {
            newList.add(timeCountList.get(i));
        }

        return newList;
    }

    public void setTimeCountList(int index, long value) {
        synchronized (timeCountList) {
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
        System.out.println("sampleRTT: " + sampleRTT);
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
        System.out.println("estimatedRRT: " + estimatedRTT);
    }

    /*
     * Calcula desvio padrão do RTT
     */
    private void calculateDevRTT() {
        long newdevRTT = (long) ((1 - beta) * devRTT + beta * (sampleRTT - estimatedRTT));
        devRTT = newdevRTT;
        System.out.println("devRTT: " + devRTT);
    }

    private void calculateTimeOut() {
        timeout = estimatedRTT + 4 * devRTT;
        System.out.println("timeout: " + timeout);
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
                        if (MainCliente.getSender().getTamanhoJanela() > 1) {
                            MainCliente.getSender().setTamanhoJanelaInicial(
                                    MainCliente.getSender().getTamanhoJanela() / 2);
                        }
                    }
                }
            }
        }
    }
}
