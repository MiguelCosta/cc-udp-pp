/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InterfaceServidor.java
 *
 * Created on 13/Mai/2011, 19:19:18
 */

package Interfaces;

import Servidor.RecieverListener;
import Servidor.ConnectionAccepterEvent;
import Servidor.ConnectionAccepterListener;
import Servidor.SenderEvent;
import Servidor.SenderListener;
import java.io.IOException;
import java.net.SocketException;
import javax.swing.JOptionPane;

/**
 *
 * @author goku
 */
public class InterfaceServidor extends javax.swing.JDialog {

    private ConnectionAccepterListener cal;
    private RecieverListener rl;
    private SenderListener sl;


    /** Creates new form InterfaceServidor */
    public InterfaceServidor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        cal = new ConnectionAccepterListener() {

            public void clienteLigouse(ConnectionAccepterEvent e) {
                jList_Clientes.setListData(ControllerServidor.getClientes());

                if ( jList_Clientes.getSelectedValue() == null )
                    jList_Clientes.setSelectedIndex(0);

                String ip = (String) jList_Clientes.getSelectedValue();

                estadoViewCliente(ip);

                ControllerServidor.setToogle(ip, jToggleButton_ActConfirmacoes.isSelected());
            }

            public void recebeuTerminoLigacao(ConnectionAccepterEvent e) {
                Object[] c = ControllerServidor.getClientes();
                jList_Clientes.setListData(c);

                if (c.length == 0)
                    estadoServidorLigado();
                else{
                    jList_Clientes.setSelectedIndex(0);

                    String ip = (String) jList_Clientes.getSelectedValue();

                    estadoViewCliente(ip);
                    estadoViewCliente(ip);
                }
            }
        };

        rl = new RecieverListener() {

            public void recebeuPacote(Servidor.RecieverEvent e) {
                String ip = (String) jList_Clientes.getSelectedValue();

                if ( ip != null ){
                    jLabel_NumPacRecebidos.setText(""+ControllerServidor.getNumPacotesRecebidos(ip));
                    jList_NaoConfirmados.setListData(ControllerServidor.getPacotesPorConfirmar(ip));
                }
            }

            public void recebeuTamanhoPacotesReceber(Servidor.RecieverEvent e) {
                String ip = (String) jList_Clientes.getSelectedValue();

                if ( ip != null )
                    jLabel_NumPacTotal.setText(""+ControllerServidor.getNumPacotesTotal(ip));
            }
        };

        sl = new SenderListener() {

            public void confirmouPacote(SenderEvent e) {
                String ip = (String) jList_Clientes.getSelectedValue();

                if ( ip != null ) {
                    jList_Confirmados.setListData(ControllerServidor.getPacotesConfirmados(ip));
                    jList_NaoConfirmados.setListData(ControllerServidor.getPacotesPorConfirmar(ip));
                }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList_Clientes = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_MaxLigacoes = new javax.swing.JTextField();
        jPanel_ClienteEspecifico = new javax.swing.JPanel();
        jToggleButton_ActConfirmacoes = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel_NumPacTotal = new javax.swing.JLabel();
        jLabel_NumPacRecebidos = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList_NaoConfirmados = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList_Confirmados = new javax.swing.JList();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton_ConfirmaPacote = new javax.swing.JButton();
        jButton_Start = new javax.swing.JButton();
        jButton_Kick = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextfield_PortaLigacoes = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextfield_TamPacotes = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(551, 507));
        setResizable(false);

        jList_Clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList_ClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList_Clientes);

        jLabel1.setText("Clientes");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Máximo de Ligações:");

        jTextField_MaxLigacoes.setText("8");
        jTextField_MaxLigacoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_MaxLigacoesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_MaxLigacoesKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_MaxLigacoesKeyTyped(evt);
            }
        });

        jPanel_ClienteEspecifico.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jToggleButton_ActConfirmacoes.setText("Enviar Confirmações");
        jToggleButton_ActConfirmacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_ActConfirmacoesActionPerformed(evt);
            }
        });

        jLabel3.setText("Número Pacotes Total:");

        jLabel_NumPacTotal.setText("0");

        jLabel_NumPacRecebidos.setText("0");

        jLabel6.setText("Número Pacotes Recebidos:");

        jScrollPane2.setViewportView(jList_NaoConfirmados);

        jScrollPane3.setViewportView(jList_Confirmados);

        jLabel7.setText("Não Confirmados");

        jLabel8.setText("Confirmados");

        jButton_ConfirmaPacote.setText("Corfirma Pacote");
        jButton_ConfirmaPacote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ConfirmaPacoteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_ClienteEspecificoLayout = new javax.swing.GroupLayout(jPanel_ClienteEspecifico);
        jPanel_ClienteEspecifico.setLayout(jPanel_ClienteEspecificoLayout);
        jPanel_ClienteEspecificoLayout.setHorizontalGroup(
            jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ClienteEspecificoLayout.createSequentialGroup()
                .addGroup(jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ClienteEspecificoLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jToggleButton_ActConfirmacoes))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ClienteEspecificoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_ClienteEspecificoLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel_NumPacRecebidos, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_ClienteEspecificoLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel_NumPacTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))))
                    .addGroup(jPanel_ClienteEspecificoLayout.createSequentialGroup()
                        .addGroup(jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(jButton_ConfirmaPacote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel_ClienteEspecificoLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel_ClienteEspecificoLayout.setVerticalGroup(
            jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ClienteEspecificoLayout.createSequentialGroup()
                .addComponent(jToggleButton_ActConfirmacoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ClienteEspecificoLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_ConfirmaPacote))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_NumPacTotal)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_ClienteEspecificoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel_NumPacRecebidos)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(24, 24, 24)
                .addComponent(jTextField_MaxLigacoes, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
            .addComponent(jPanel_ClienteEspecifico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_MaxLigacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_ClienteEspecifico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton_Start.setText("Start");
        jButton_Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_StartActionPerformed(evt);
            }
        });

        jButton_Kick.setText("Kick");
        jButton_Kick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_KickActionPerformed(evt);
            }
        });

        jLabel9.setText("Porta Lig.");

        jTextfield_PortaLigacoes.setText("4545");

        jLabel10.setText("Tam. Pacotes");

        jTextfield_TamPacotes.setText("1024");

        jMenu1.setText("File");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem2.setText("Desligar Servidor");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Sair");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Servidor : ");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);
        estadoInicial();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Kick, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(58, 58, 58))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextfield_TamPacotes, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextfield_PortaLigacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, 0)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jButton_Start, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextfield_PortaLigacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextfield_TamPacotes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_Start)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_Kick)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton_StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_StartActionPerformed
        try {
            int tamPacotes = Integer.parseInt(jTextfield_PortaLigacoes.getText());
            int numConeccoes = Integer.parseInt(jTextField_MaxLigacoes.getText());
            int portaLigacoes = Integer.parseInt(jTextfield_PortaLigacoes.getText());

            ControllerServidor.iniciaServidor(numConeccoes, tamPacotes, portaLigacoes, cal, rl, sl);

            estadoServidorLigado();
        } catch (SocketException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage() ,
                        "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_StartActionPerformed

    private void jList_ClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_ClientesMouseClicked
        if (jList_Clientes.getSelectedIndices().length == 1){

            String ip = (String) jList_Clientes.getSelectedValue();

            estadoViewCliente(ip);
        }
    }//GEN-LAST:event_jList_ClientesMouseClicked

    private void jToggleButton_ActConfirmacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_ActConfirmacoesActionPerformed
        if (jList_Clientes.getSelectedIndices().length == 1){

            String ip = (String) jList_Clientes.getSelectedValue();

            ControllerServidor.setToogle(ip, jToggleButton_ActConfirmacoes.isSelected());
        }
    }//GEN-LAST:event_jToggleButton_ActConfirmacoesActionPerformed

    private void jButton_ConfirmaPacoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ConfirmaPacoteActionPerformed
        if (jList_NaoConfirmados.getSelectedIndices().length == 1){
            try {
                String ip = (String) jList_Clientes.getSelectedValue();
                int pacote = (Integer) jList_NaoConfirmados.getSelectedValue();

                ControllerServidor.confirmaPacote(ip, pacote);
            } catch (IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage() ,
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton_ConfirmaPacoteActionPerformed

    private void jButton_KickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_KickActionPerformed
        if (jList_Clientes.getSelectedIndices().length == 1){
            try {
                String ip = (String) jList_Clientes.getSelectedValue();

                ControllerServidor.kickCliente(ip);
            } catch (IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage() ,
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton_KickActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            ControllerServidor.desligaServidor();
            estadoInicial();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage() ,
                        "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTextField_MaxLigacoesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_MaxLigacoesKeyReleased
        if (!jTextField_MaxLigacoes.getText().equals("")){
            try {
                int novoMax = Integer.parseInt(jTextField_MaxLigacoes.getText());
                ControllerServidor.mudaNumMaxClientes(novoMax, !jButton_Start.isEnabled());
            } catch (IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage() ,
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTextField_MaxLigacoesKeyReleased

    private void jTextField_MaxLigacoesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_MaxLigacoesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_MaxLigacoesKeyPressed

    private void jTextField_MaxLigacoesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_MaxLigacoesKeyTyped
        
    }//GEN-LAST:event_jTextField_MaxLigacoesKeyTyped

    private void estadoInicial(){
        jPanel_ClienteEspecifico.setVisible(false);
        jButton_Kick.setEnabled(false);

        jTextfield_PortaLigacoes.setEnabled(true);
        jTextfield_TamPacotes.setEnabled(true);
        jButton_Start.setEnabled(true);

        jMenu3.setText("Servidor : OFF");
        jMenu3.setEnabled(false);
    }

    private void estadoServidorLigado(){
        jPanel_ClienteEspecifico.setVisible(false);
        jButton_Kick.setEnabled(false);

        jTextfield_PortaLigacoes.setEnabled(false);
        jTextfield_TamPacotes.setEnabled(false);

        jButton_Start.setEnabled(false);
        jMenu3.setText("Servidor : ON");
    }

    private void estadoViewCliente(String ip){
        jButton_Kick.setEnabled(true);
        jPanel_ClienteEspecifico.setVisible(true);
        jToggleButton_ActConfirmacoes.setSelected(true);

        jList_Confirmados.setListData(ControllerServidor.getPacotesConfirmados(ip));
        jList_NaoConfirmados.setListData(ControllerServidor.getPacotesPorConfirmar(ip));

        jLabel_NumPacRecebidos.setText(""+ControllerServidor.getNumPacotesRecebidos(ip));
        jLabel_NumPacTotal.setText("" + ControllerServidor.getNumPacotesTotal(ip));
    }

    /**
    * @param args the command line arguments
    */
    public static void main() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InterfaceServidor dialog = new InterfaceServidor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_ConfirmaPacote;
    private javax.swing.JButton jButton_Kick;
    private javax.swing.JButton jButton_Start;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_NumPacRecebidos;
    private javax.swing.JLabel jLabel_NumPacTotal;
    private javax.swing.JList jList_Clientes;
    private javax.swing.JList jList_Confirmados;
    private javax.swing.JList jList_NaoConfirmados;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_ClienteEspecifico;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField_MaxLigacoes;
    private javax.swing.JTextField jTextfield_PortaLigacoes;
    private javax.swing.JTextField jTextfield_TamPacotes;
    private javax.swing.JToggleButton jToggleButton_ActConfirmacoes;
    // End of variables declaration//GEN-END:variables

}
