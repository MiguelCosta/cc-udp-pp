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
    private int lostPackets;
    private int threshold;
    private int ackCounter;

    /*
     * Construtor
     * Recebe array de tempos dos pacotes, o RTT
     */
    public TimeCounter(int packetsNumber, Long estimatedRTT) {
        this.timeCountList = new ArrayList<Long>();
        for (int i = 0; i < packetsNumber +1; i++) {
            timeCountList.add(Long.valueOf(0));
        }
        this.estimatedRTT = 2 * estimatedRTT;
        this.alpha = (float) 0.125;
        this.beta = (float) 0.25;
        this.keeprunning = true;
        this.lostPackets = 0;
        threshold=0;
        ackCounter=0;
        estimateRTT();
        calculateDevRTT();
        calculateTimeOut();

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
        if (timeCountList.get(index) != -1) {
            timeCountList.set(index, Long.valueOf(0));
            MainCliente.getSender().setTamanhoJanelaUtilizado(
                    MainCliente.getSender().getTamanhoJanelUtilizado() - 1);
            MainCliente.getReciever().setConfirmacoesRecebidas(
                    MainCliente.getReciever().getConfirmacoesRecebidas() + 1);
            ackCounter++;
            if(ackCounter==MainCliente.getSender().getTamanhoJanela()){
                if(threshold>ackCounter || threshold==0)
                    MainCliente.getSender().setTamanhoJanelaInicial(ackCounter*2);
                else
                    MainCliente.getSender().setTamanhoJanelaInicial(ackCounter+1);
                ackCounter=0;
            }
        }
        estimateRTT();
        calculateDevRTT();
        calculateTimeOut();
    }

    /*
     * Estima o RTT baseando-se no actual e na nova medicao
     */
    private void estimateRTT() {
        long newRTT = (long) ((1 - alpha) * estimatedRTT + alpha * sampleRTT);
        estimatedRTT = newRTT;
        System.out.println(" || estimatedRTT: " + estimatedRTT);
    }

    /*
     * Calcula desvio padrão do RTT
     */
    private void calculateDevRTT() {
        long newdevRTT = (long) ((1 - beta) * devRTT + beta * (Math.abs(sampleRTT - estimatedRTT)));
        devRTT = newdevRTT;
        System.out.println(" || devRTT: " + devRTT);
    }

    private void calculateTimeOut() {
        timeout = estimatedRTT + 4 * devRTT;
        System.out.println(" || timeout: " + timeout);
    }

    @Override
    public void run() {
        Long current, total, time;
        int i;
        while (keeprunning) {
            for (i = 0; i < timeCountList.size(); i++) {
                time = timeCountList.get(i);
                if (time != 0 && time != -1) {
                    current = System.currentTimeMillis();
                    total = current - time;
                    if (total > timeout) {
                        lostPackets++;
                        timeCountList.set(i, Long.valueOf(-1));
                        MainCliente.getSender().setTamanhoJanelaUtilizado(
                                MainCliente.getSender().getTamanhoJanelUtilizado() - 1);
                        MainCliente.desPausa();
                        threshold=MainCliente.getSender().getTamanhoJanela()/2;
                        ackCounter=0;
                        System.out.print("***\nTIMEOUT OCURRED!!!\nRTT: " + total + " || timeout: " + timeout);
                        System.out.println(" || Pacotes perdidos: " + lostPackets);
                        System.out.println(" || TimeOut : " + i);
                        System.out.println(" || Tamanho janela utilizado :" + MainCliente.getSender().getTamanhoJanelUtilizado()+"\n***");
                        //falta disparar evento do timeout |||GOKU|||
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
