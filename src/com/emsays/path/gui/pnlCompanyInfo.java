package com.emsays.path.gui;

import com.emsays.path.CommonUIActions;
import com.emsays.path.Database;
import com.emsays.path.dao.CompanyYearInfoSer;

public class pnlCompanyInfo extends javax.swing.JPanel
{

	MyFormWrapper frmWrapper;
	boolean vModeAdd;

	CompanyYearInfoSer companyYearInfoSer;

	public pnlCompanyInfo(MyFormWrapper vParent)
	{
		initComponents();

		this.frmWrapper = vParent;

		companyYearInfoSer = new CompanyYearInfoSer(Database.getSessionCompanyYear());
		
		
		txtAddress.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.Address));
		
		txtName.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.CompanyName));
		txtContactNo.setText(companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.ContactNo));
		
		

	}

	private void handleClickBtnSave()
	{

		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.Address, txtAddress.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.CompanyName, txtName.getText());
		companyYearInfoSer.set(CompanyYearInfoSer.CompanyYearInfoKey.ContactNo, txtContactNo.getText());

		CommonUIActions.showMessageBox("Data saved successfully");

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
                java.awt.GridBagConstraints gridBagConstraints;

                txtName = new javax.swing.JTextField();
                txtAddress = new javax.swing.JTextField();
                txtCity = new javax.swing.JTextField();
                cmbState = new javax.swing.JComboBox<>();
                txtPin = new javax.swing.JTextField();
                txtContactNo = new javax.swing.JTextField();
                txtEmail = new javax.swing.JTextField();
                txtGSTNo = new javax.swing.JTextField();
                txtContactPerson = new javax.swing.JTextField();
                jScrollPane1 = new javax.swing.JScrollPane();
                taTnC = new javax.swing.JTextArea();
                btnSave = new javax.swing.JButton();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();

                setLayout(new java.awt.GridBagLayout());
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(txtName, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(txtAddress, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(txtCity, gridBagConstraints);

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 6;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(cmbState, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 8;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(txtPin, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 10;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(txtContactNo, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 12;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(txtEmail, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 14;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(txtGSTNo, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 16;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(txtContactPerson, gridBagConstraints);

                taTnC.setColumns(20);
                taTnC.setRows(5);
                jScrollPane1.setViewportView(taTnC);

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 18;
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.weighty = 1.0;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
                add(jScrollPane1, gridBagConstraints);

                btnSave.setText("Save");
                btnSave.setPreferredSize(new java.awt.Dimension(75, 32));
                btnSave.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnSaveActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 20;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
                add(btnSave, gridBagConstraints);

                jLabel1.setText("Company Name");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel1, gridBagConstraints);

                jLabel2.setText("Address");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel2, gridBagConstraints);

                jLabel3.setText("City");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel3, gridBagConstraints);

                jLabel4.setText("State");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 6;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel4, gridBagConstraints);

                jLabel5.setText("PIN");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 8;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel5, gridBagConstraints);

                jLabel6.setText("Contact No");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 10;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel6, gridBagConstraints);

                jLabel7.setText("Contact Person");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 16;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel7, gridBagConstraints);

                jLabel8.setText("Email");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 12;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel8, gridBagConstraints);

                jLabel9.setText("GST No");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 14;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel9, gridBagConstraints);

                jLabel10.setText("Terms");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 18;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(7, 5, 5, 27);
                add(jLabel10, gridBagConstraints);
        }// </editor-fold>//GEN-END:initComponents

        private void btnSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSaveActionPerformed
        {//GEN-HEADEREND:event_btnSaveActionPerformed
		handleClickBtnSave();
        }//GEN-LAST:event_btnSaveActionPerformed


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnSave;
        private javax.swing.JComboBox<String> cmbState;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTextArea taTnC;
        private javax.swing.JTextField txtAddress;
        private javax.swing.JTextField txtCity;
        private javax.swing.JTextField txtContactNo;
        private javax.swing.JTextField txtContactPerson;
        private javax.swing.JTextField txtEmail;
        private javax.swing.JTextField txtGSTNo;
        private javax.swing.JTextField txtName;
        private javax.swing.JTextField txtPin;
        // End of variables declaration//GEN-END:variables
}