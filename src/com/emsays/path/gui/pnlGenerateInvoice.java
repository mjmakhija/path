package com.emsays.path.gui;

import com.emsays.path.CommonUIActions;
import com.emsays.path.Database;
import com.emsays.path.ObjectForCombobox;
import com.emsays.path.dao.InvoiceSer;
import com.emsays.path.dao.PaymentTypeSer;
import com.emsays.path.dto.PaymentTypeDTO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

public class pnlGenerateInvoice extends javax.swing.JPanel
{

	MyFormWrapper vParent;
	boolean vModeAdd;
	List<PaymentTypeDTO> paymentTypes;

	public pnlGenerateInvoice(MyFormWrapper vParent)
	{
		initComponents();

		myInit();

		this.vParent = vParent;
		this.vModeAdd = true;

	}

	private void myInit()
	{
		paymentTypes = new PaymentTypeSer(Database.getSessionCompanyYear()).retrieve();

		CommonUIActions.fillComboBox(cmbPaymentType, new ArrayList<ObjectForCombobox>(paymentTypes));

		Calendar cal = Calendar.getInstance();

		cmbYear.addItem(String.valueOf(cal.get(Calendar.YEAR)));
		cmbYear.addItem(String.valueOf(cal.get(Calendar.YEAR) + 1));

		cmbMonth.addItem(String.valueOf(cal.get(Calendar.MONTH) + 1));
		cal.add(Calendar.MONTH, 1);
		cmbMonth.addItem(String.valueOf(cal.get(Calendar.MONTH) + 1));
	}

	private void handleClickBtnSave()
	{
		StringBuilder errorMsg = new StringBuilder();
		if (!checkIsValid(errorMsg))
		{
			CommonUIActions.showMessageBox(errorMsg.toString());
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{

			InvoiceSer invoiceSer = new InvoiceSer(Database.getSessionCompanyYear());

			invoiceSer.create(
				paymentTypes.get(cmbPaymentType.getSelectedIndex() - 1),
				Integer.parseInt(cmbMonth.getSelectedItem().toString()),
				(Integer.parseInt(cmbYear.getSelectedItem().toString()))
			);

			this.showListForm();
		}

	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{
		if (cmbPaymentType.getSelectedIndex() < 1)
		{
			errorMsg.append("Invalid payment type");
			return false;
		}
		return true;
	}

	private void handleClickBtnCancel()
	{
		this.showListForm();
	}

	private void showListForm()
	{
		vParent.addPanel(new pnlListCustomer(vParent));
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents()
        {

                cmbPaymentType = new javax.swing.JComboBox<>();
                cmbYear = new javax.swing.JComboBox<>();
                cmbMonth = new javax.swing.JComboBox<>();
                btnSave = new javax.swing.JButton();
                btnCancel = new javax.swing.JButton();
                jLabel10 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jLabel12 = new javax.swing.JLabel();

                btnSave.setText("Save");
                btnSave.setPreferredSize(new java.awt.Dimension(75, 32));
                btnSave.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnSaveActionPerformed(evt);
                        }
                });

                btnCancel.setText("Cancel");
                btnCancel.setPreferredSize(new java.awt.Dimension(75, 32));
                btnCancel.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnCancelActionPerformed(evt);
                        }
                });

                jLabel10.setText("Year");

                jLabel11.setText("Month");

                jLabel12.setText("Payment Type");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel10)
                                                        .addComponent(jLabel11)
                                                        .addComponent(jLabel12))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cmbPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10))
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        }// </editor-fold>//GEN-END:initComponents

        private void btnSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSaveActionPerformed
        {//GEN-HEADEREND:event_btnSaveActionPerformed
		// TODO add your handling code here:
		handleClickBtnSave();
        }//GEN-LAST:event_btnSaveActionPerformed

        private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
        {//GEN-HEADEREND:event_btnCancelActionPerformed
		// TODO add your handling code here:
		handleClickBtnCancel();
        }//GEN-LAST:event_btnCancelActionPerformed


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnCancel;
        private javax.swing.JButton btnSave;
        private javax.swing.JComboBox<String> cmbMonth;
        private javax.swing.JComboBox<String> cmbPaymentType;
        private javax.swing.JComboBox<String> cmbYear;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        // End of variables declaration//GEN-END:variables
}
