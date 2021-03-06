package com.emsays.path.gui;

import com.emsays.path.dao.app.CompanyDAO;
import com.emsays.path.dto.app.CompanyDTO;
import javax.swing.JOptionPane;

public class pnlEditCompany extends javax.swing.JPanel
{

	frmWrapper vParent;
	CompanyDTO objCompany;

	/**
	 * Creates new form pnlLogin
	 */
	public pnlEditCompany(frmWrapper vParent, CompanyDTO objCompany)
	{
		initComponents();
		this.vParent = vParent;
		this.objCompany = objCompany;

		txtName.setText(objCompany.getName());
	}

	private void btnSaveClickHandler()
	{
		if (checkIsValid())
		{
			objCompany.setName(txtName.getText());
			if (new CompanyDAO().mSave(objCompany))
			{
				JOptionPane.showMessageDialog(null, "Company updated successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
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
                jLabel1 = new javax.swing.JLabel();

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

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        private javax.swing.JTextField txtName;
        // End of variables declaration//GEN-END:variables
}
