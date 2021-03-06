package com.emsays.path.gui;

import com.emsays.path.GV;
import com.emsays.path.dao.app.LoginDAOImpl;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class frmWrapper extends javax.swing.JFrame
{

	public frmWrapper()
	{
		initComponents();

		addPanel(new pnlLogin(this, new LoginDAOImpl()));

		this.setVisible(true);

	}

	public void addPanel(JPanel vJPanel)
	{
		jPanel1.removeAll();

		if (vJPanel.getClass() == pnlMenu2.class
				|| vJPanel.getClass() == pnlListCollectFrom.class
				|| vJPanel.getClass() == pnlListOffer.class
				|| vJPanel.getClass() == pnlListPackage.class
				|| vJPanel.getClass() == pnlListPaymentType.class
				|| vJPanel.getClass() == pnlListCustomer.class
				|| vJPanel.getClass() == pnlListInvoice.class
				|| vJPanel.getClass() == pnlListReceipt.class)
		{
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(vJPanel);
		}
		else
		{
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(vJPanel, new GridBagConstraints());
		}
		this.repaint();
		this.revalidate();
	}

	private void closeApp()
	{
		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{
			GV.closeApp();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents()
        {

                jScrollPane3 = new javax.swing.JScrollPane();
                jPanel1 = new javax.swing.JPanel();

                setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                setTitle("Mateac");
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                setMinimumSize(new java.awt.Dimension(800, 500));
                addWindowListener(new java.awt.event.WindowAdapter()
                {
                        public void windowClosing(java.awt.event.WindowEvent evt)
                        {
                                formWindowClosing(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 563, Short.MAX_VALUE)
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 315, Short.MAX_VALUE)
                );

                jScrollPane3.setViewportView(jPanel1);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3)
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
        {//GEN-HEADEREND:event_formWindowClosing

			// TODO add your handling code here:
			closeApp();
        }//GEN-LAST:event_formWindowClosing

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JPanel jPanel1;
        private javax.swing.JScrollPane jScrollPane3;
        // End of variables declaration//GEN-END:variables
}
