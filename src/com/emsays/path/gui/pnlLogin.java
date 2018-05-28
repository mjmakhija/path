package com.emsays.path.gui;

import com.emsays.path.dao.app.LoginDAO;
import com.emsays.path.dto.app.LoginDTO;
import javax.swing.*;

public class pnlLogin extends javax.swing.JPanel
{

	frmWrapper vParent;
	LoginDAO loginDAO;

	public pnlLogin(frmWrapper vParent, LoginDAO loginDAO)
	{
		initComponents();

		this.vParent = vParent;
		this.loginDAO = loginDAO;

		JRootPane rootPane = SwingUtilities.getRootPane(vParent);
		rootPane.setDefaultButton(btnLogin);
	}

	private boolean checkIsValid()
	{
		System.out.println(txtUsername.getText());
		if (txtUsername.getText() == null || txtUsername.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Username is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else if (txtPassword.getText() == null || txtPassword.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Password is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}

	private void handleClickBtnLogin()
	{
		if (checkIsValid())
		{
			LoginDTO l = loginDAO.findByUsername(txtUsername.getText());
			if (l.getPassword().equals(txtPassword.getText()))
			{
				vParent.addPanel(new pnlGateway(vParent));
			}
		}
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

                btnLogin = new javax.swing.JButton();
                txtUsername = new javax.swing.JTextField();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                txtPassword = new javax.swing.JPasswordField();

                setMinimumSize(new java.awt.Dimension(0, 0));
                setPreferredSize(new java.awt.Dimension(280, 103));

                btnLogin.setText("Login");
                btnLogin.setName("btnLogin"); // NOI18N
                btnLogin.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnLoginActionPerformed(evt);
                        }
                });

                txtUsername.setName(""); // NOI18N

                jLabel1.setText("Username");

                jLabel2.setText("Password");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2))
                                                .addGap(6, 6, 6)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtPassword)
                                                        .addComponent(txtUsername)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap(199, Short.MAX_VALUE)
                                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel1))
                                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLogin)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                txtUsername.getAccessibleContext().setAccessibleName("");
        }// </editor-fold>//GEN-END:initComponents

        private void btnLoginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnLoginActionPerformed
        {//GEN-HEADEREND:event_btnLoginActionPerformed
		handleClickBtnLogin();
        }//GEN-LAST:event_btnLoginActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnLogin;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JPasswordField txtPassword;
        private javax.swing.JTextField txtUsername;
        // End of variables declaration//GEN-END:variables
}
