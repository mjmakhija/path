package com.emsays.path.gui;

import com.emsays.path.Database;
import com.emsays.path.Util;
import com.emsays.path.dao.app.CompanyDAO;
import com.emsays.path.dto.app.CompanyDTO;
import com.emsays.path.dto.app.YearDTO;
import javax.swing.JOptionPane;

public class pnlAddCompanyAndYear extends javax.swing.JPanel
{

	frmWrapper vParent;

	/**
	 * Creates new form pnlLogin
	 */
	public pnlAddCompanyAndYear(frmWrapper vParent)
	{
		initComponents();
		this.vParent = vParent;
	}

	private void btnSaveClickHandler()
	{
		if (checkIsValid())
		{
			CompanyDTO objCompany = new CompanyDTO(txtName.getText());
			objCompany.addYear(new YearDTO(
				Util.mConvertStringToDate(txtDateFrom.getText()),
				Util.mConvertStringToDate(txtDateTo.getText())
			));
			if (new CompanyDAO().mSave(objCompany))
			{
				Database.createDB(Integer.toString(objCompany.getYears().get(0).getId()));
				JOptionPane.showMessageDialog(null, "Company created successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				vParent.addPanel(new pnlGateway(vParent));
			}
		}

	}

	private boolean checkIsValid()
	{
		if (txtName.getText() == null || txtName.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Name is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if (txtDateFrom.getText() == null || txtDateFrom.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Financial year date from is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if (!Util.isDateValid(txtDateFrom.getText()))
		{
			JOptionPane.showMessageDialog(null, "Date from is invalid", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if (txtDateTo.getText() == null || txtDateTo.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Financial year date to is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if (!Util.isDateValid(txtDateTo.getText()))
		{
			JOptionPane.showMessageDialog(null, "Date to is invalid", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}

	private void btnCancelClickHandler()
	{
		vParent.addPanel(new pnlGateway(vParent));
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

                btnSave = new javax.swing.JButton();
                btnCancel = new javax.swing.JButton();
                txtName = new javax.swing.JTextField();
                txtDateFrom = new javax.swing.JTextField();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                txtDateTo = new javax.swing.JTextField();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();

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

                jLabel1.setText("Company Name");

                jLabel2.setText("Date From (Financital Year)");

                jLabel3.setText("Date To (Financial Year)");

                jLabel4.setText("DD-MM-YYYY");

                jLabel5.setText("DD-MM-YYYY");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3))
                                                .addGap(7, 7, 7)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                                        .addComponent(txtDateFrom)
                                                        .addComponent(txtDateTo))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel1))
                                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        }// </editor-fold>//GEN-END:initComponents

        private void btnSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSaveActionPerformed
        {//GEN-HEADEREND:event_btnSaveActionPerformed
		// TODO add your handling code here:
		btnSaveClickHandler();
        }//GEN-LAST:event_btnSaveActionPerformed

        private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
        {//GEN-HEADEREND:event_btnCancelActionPerformed
		// TODO add your handling code here:
		btnCancelClickHandler();
        }//GEN-LAST:event_btnCancelActionPerformed


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnCancel;
        private javax.swing.JButton btnSave;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JTextField txtDateFrom;
        private javax.swing.JTextField txtDateTo;
        private javax.swing.JTextField txtName;
        // End of variables declaration//GEN-END:variables
}