/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InterfaceCliente.java
 *
 * Created on 13/Mai/2011, 19:39:23
 */

package Interfaces;

import Cliente.RecieverEvent;
import Cliente.RecieverListener;
import Cliente.SenderEvent;
import Cliente.SenderListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author goku
 */
public class InterfaceCliente extends javax.swing.JDialog{
    private RecieverListener rl;
    private SenderListener sl;
    private boolean pausado;

    /** Creates new form InterfaceCliente */
    public InterfaceCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        pausado = false;

        rl = new RecieverListener() {

            public void coneccaoEstabelecida(RecieverEvent e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Info : "
                    + "Ligação Estabelecida" , "INFO", JOptionPane.INFORMATION_MESSAGE);

                estadoFicheiro();
            }

            public void terminoConeccao(RecieverEvent e) {
                try {
                    javax.swing.JOptionPane.showMessageDialog(null, "Info : " +
                            "Recebido Pedido de terminacao de ligação", "INFO", JOptionPane.INFORMATION_MESSAGE);
                    ControllerCliente.closeConnection();
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Erro : " +
                            ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }

            public void confirmacaoRecebida(RecieverEvent e) {
                jLabel_ConfirmacoesRecebidas.setText("" + ControllerCliente.getConfirmacoes());
            }

        };

        sl = new SenderListener() {

            public void pacotesGerados(SenderEvent e) {
                Object[] packs = ControllerCliente.getPacotesEnviar();
                jList_Pacotes.setListData(packs);
                jLabel_TotalPacotes.setText("" + packs.length);
                estadoSemiFinal();
            }

            public void pacotesEnviados(SenderEvent e) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Info : "
                    + "OS pacotes foram todos enviados" , "INFO", JOptionPane.INFORMATION_MESSAGE);
            }

            public void pacoteEnviado(SenderEvent e) {
                jLabel_TotalEnviados.setText("" + ControllerCliente.getTotalEnviados());
            }
        };
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel_Defs = new javax.swing.JPanel();
        jPanel_FICH = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_FichEnviar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton_GerarPacotes = new javax.swing.JButton();
        jButton_FileChooser = new javax.swing.JButton();
        jTextField_TamanhoPacotes = new javax.swing.JTextField();
        jPanel_CON = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField_IP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField_Porta = new javax.swing.JTextField();
        jButton_Ligar = new javax.swing.JButton();
        jPanel_Pacotes = new javax.swing.JPanel();
        jButton_Enviar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_Pacotes = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel_TotalPacotes = new javax.swing.JLabel();
        jLabel_ConfirmacoesRecebidas = new javax.swing.JLabel();
        jButton_Pause = new javax.swing.JButton();
        jButton_Stop = new javax.swing.JButton();
        jLabel_TotalEnviados = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox_Pacotes = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jTextField_TamJanela = new javax.swing.JTextField();
        jLabel_Perdas = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel_TamJanela = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel_FICH.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Ficheiro a Enviar");

        jLabel2.setText("Tamanho dos Pacotes");

        jButton_GerarPacotes.setText("Gerar Pacotes");
        jButton_GerarPacotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GerarPacotesActionPerformed(evt);
            }
        });

        jButton_FileChooser.setText("...");
        jButton_FileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_FileChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_FICHLayout = new javax.swing.GroupLayout(jPanel_FICH);
        jPanel_FICH.setLayout(jPanel_FICHLayout);
        jPanel_FICHLayout.setHorizontalGroup(
            jPanel_FICHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_FICHLayout.createSequentialGroup()
                .addGroup(jPanel_FICHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_FichEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_FileChooser)
                .addGap(18, 18, 18)
                .addGroup(jPanel_FICHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_TamanhoPacotes, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
            .addGroup(jPanel_FICHLayout.createSequentialGroup()
                .addComponent(jButton_GerarPacotes)
                .addContainerGap())
        );
        jPanel_FICHLayout.setVerticalGroup(
            jPanel_FICHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_FICHLayout.createSequentialGroup()
                .addGroup(jPanel_FICHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_FICHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_TamanhoPacotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_FichEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_FileChooser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_GerarPacotes))
        );

        jPanel_CON.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setText("IP");

        jLabel4.setText("Porta");

        jButton_Ligar.setText("Ligar");
        jButton_Ligar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LigarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_CONLayout = new javax.swing.GroupLayout(jPanel_CON);
        jPanel_CON.setLayout(jPanel_CONLayout);
        jPanel_CONLayout.setHorizontalGroup(
            jPanel_CONLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_CONLayout.createSequentialGroup()
                .addGroup(jPanel_CONLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_CONLayout.createSequentialGroup()
                        .addGroup(jPanel_CONLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jTextField_IP, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_CONLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_Porta, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jButton_Ligar))
                .addContainerGap())
        );
        jPanel_CONLayout.setVerticalGroup(
            jPanel_CONLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_CONLayout.createSequentialGroup()
                .addGroup(jPanel_CONLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_CONLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(jTextField_Porta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_CONLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(12, 12, 12)
                        .addComponent(jTextField_IP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Ligar))
        );

        javax.swing.GroupLayout jPanel_DefsLayout = new javax.swing.GroupLayout(jPanel_Defs);
        jPanel_Defs.setLayout(jPanel_DefsLayout);
        jPanel_DefsLayout.setHorizontalGroup(
            jPanel_DefsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DefsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_DefsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_CON, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_FICH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_DefsLayout.setVerticalGroup(
            jPanel_DefsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DefsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_CON, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jPanel_FICH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Definições", jPanel_Defs);

        jButton_Enviar.setText("Enviar");
        jButton_Enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EnviarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList_Pacotes);

        jLabel5.setText("Total de Pacotes:");

        jLabel6.setText("Confirmações Recebidas:");

        jLabel_TotalPacotes.setText("0");

        jLabel_ConfirmacoesRecebidas.setText("0");

        jButton_Pause.setText("Pause");
        jButton_Pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PauseActionPerformed(evt);
            }
        });

        jButton_Stop.setText("Stop");
        jButton_Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_StopActionPerformed(evt);
            }
        });

        jLabel_TotalEnviados.setText("0");

        jLabel10.setText("Total Enviados:");

        jComboBox_Pacotes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "Enviados", "Perdidos" }));

        jLabel11.setText("Tam. Janela Inicial (8 defeito):");

        jTextField_TamJanela.setText("8");

        jLabel_Perdas.setText("0");

        jLabel13.setText("Perdas:");

        jLabel14.setText("Tamanho Janela");

        jLabel_TamJanela.setText("0");

        javax.swing.GroupLayout jPanel_PacotesLayout = new javax.swing.GroupLayout(jPanel_Pacotes);
        jPanel_Pacotes.setLayout(jPanel_PacotesLayout);
        jPanel_PacotesLayout.setHorizontalGroup(
            jPanel_PacotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PacotesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_PacotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_Pacotes, 0, 250, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_PacotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_PacotesLayout.createSequentialGroup()
                        .addComponent(jButton_Enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(jButton_Pause, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jButton_Stop, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5)
                    .addGroup(jPanel_PacotesLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_TamJanela, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_TotalPacotes, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_TotalEnviados, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_ConfirmacoesRecebidas, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Perdas, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_TamJanela, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_PacotesLayout.setVerticalGroup(
            jPanel_PacotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PacotesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_PacotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_PacotesLayout.createSequentialGroup()
                        .addGroup(jPanel_PacotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_TamJanela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_TotalPacotes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_TotalEnviados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_ConfirmacoesRecebidas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_Perdas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_TamJanela))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_PacotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_PacotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_Stop)
                        .addComponent(jButton_Pause))
                    .addComponent(jComboBox_Pacotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Enviar))
                .addContainerGap())
        );

        jTabbedPane.addTab("Pacotes", jPanel_Pacotes);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Sair");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);
        estadoInicial();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_FileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FileChooserActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setApproveButtonText("Ok");

        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (!fc.getCurrentDirectory().getAbsolutePath().equals("") ) {
                jTextField_FichEnviar.setText(fc.getSelectedFile().getAbsolutePath());
            }
        }
    }//GEN-LAST:event_jButton_FileChooserActionPerformed

    private void jButton_GerarPacotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GerarPacotesActionPerformed
        try {
            String toSend = jTextField_FichEnviar.getText();
            int tamPacotes = Integer.parseInt(jTextField_TamanhoPacotes.getText());

            ControllerCliente.setFicheiroCliente(toSend, tamPacotes);
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO : "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_GerarPacotesActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton_LigarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LigarActionPerformed
        try {
            String ip = jTextField_IP.getText();
            int port = Integer.parseInt(jTextField_Porta.getText());
            ControllerCliente.criaCliente(ip, port, rl, sl);
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO : "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_LigarActionPerformed

    private void jButton_EnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EnviarActionPerformed
        try {
            estadoFinal();

            int tamanho = Integer.parseInt(jTextField_TamJanela.getText());

            ControllerCliente.setTamanhoInicialPacotes(tamanho);
            ControllerCliente.sendPackages();
        } catch (InterruptedException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO : "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_EnviarActionPerformed

    private void jButton_PauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PauseActionPerformed
        try {
            if (!pausado){
                pausado = true;
                ControllerCliente.pausaSender();
                jButton_Pause.setText("Continue");
            } else{
                ControllerCliente.despausaSender();
                jButton_Pause.setText("Pause");
            }
        } catch (InterruptedException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "ERRO : "
                    + ex.getMessage() , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_PauseActionPerformed

    private void jButton_StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_StopActionPerformed
        ControllerCliente.stopSender();
    }//GEN-LAST:event_jButton_StopActionPerformed

    private void estadoInicial(){
        jTextField_IP.setText("192.168.10.8");
        jTextField_Porta.setText("4545");

        jTabbedPane.setEnabledAt(1, false);
        
        jTextField_FichEnviar.setEnabled(false);
        jButton_FileChooser.setEnabled(false);
        jTextField_TamanhoPacotes.setEnabled(false);
        jButton_GerarPacotes.setEnabled(false);
    }

    private void estadoFicheiro(){
        jTextField_IP.setEditable(false);
        jTextField_Porta.setEditable(false);
        jButton_Ligar.setEnabled(false);

        jTextField_FichEnviar.setEnabled(true);
        jButton_FileChooser.setEnabled(true);
        jTextField_TamanhoPacotes.setEnabled(true);
        jButton_GerarPacotes.setEnabled(true);
    }

    private void estadoSemiFinal(){
        jTabbedPane.setEnabledAt(1, true);
        jTabbedPane.setSelectedIndex(1);

        jButton_Pause.setEnabled(false);
        jButton_Stop.setEnabled(false);
    }

    private void estadoFinal(){
        jTextField_TamJanela.setEditable(false);
        jButton_Enviar.setEnabled(false);

        jTextField_FichEnviar.setEnabled(false);
        jButton_FileChooser.setEnabled(false);
        jTextField_TamanhoPacotes.setEnabled(false);
        jButton_GerarPacotes.setEnabled(false);

        jButton_Pause.setEnabled(true);
        jButton_Stop.setEnabled(true);
    }

    /**
    * @param args the command line arguments
    */
    public static void main() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InterfaceCliente dialog = new InterfaceCliente(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Enviar;
    private javax.swing.JButton jButton_FileChooser;
    private javax.swing.JButton jButton_GerarPacotes;
    private javax.swing.JButton jButton_Ligar;
    private javax.swing.JButton jButton_Pause;
    private javax.swing.JButton jButton_Stop;
    private javax.swing.JComboBox jComboBox_Pacotes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel_ConfirmacoesRecebidas;
    private javax.swing.JLabel jLabel_Perdas;
    private javax.swing.JLabel jLabel_TamJanela;
    private javax.swing.JLabel jLabel_TotalEnviados;
    private javax.swing.JLabel jLabel_TotalPacotes;
    private javax.swing.JList jList_Pacotes;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel_CON;
    private javax.swing.JPanel jPanel_Defs;
    private javax.swing.JPanel jPanel_FICH;
    private javax.swing.JPanel jPanel_Pacotes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField jTextField_FichEnviar;
    private javax.swing.JTextField jTextField_IP;
    private javax.swing.JTextField jTextField_Porta;
    private javax.swing.JTextField jTextField_TamJanela;
    private javax.swing.JTextField jTextField_TamanhoPacotes;
    // End of variables declaration//GEN-END:variables

}
