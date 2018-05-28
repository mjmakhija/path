package com.emsays.path.gui;

import com.emsays.path.APIHelper;
import com.emsays.path.dao.app.CompanyDAO;
import com.emsays.path.Database;
import com.emsays.path.Download;
import com.emsays.path.GV;
import com.emsays.path.Util;
import com.emsays.path.dto.api.UpdaterResDTO;
import com.emsays.path.dao.app.AppInfoSer;
import com.emsays.path.dto.app.CompanyDTO;
import com.emsays.path.dto.app.YearDTO;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class pnlGateway extends javax.swing.JPanel
{

	frmWrapper vParent;
	List<CompanyDTO> vLstObjCompany;

	public pnlGateway(frmWrapper vParent)
	{
		initComponents();

		this.vParent = vParent;
		mLoadCompanies();
	}

	private void mLoadCompanies()
	{
		vLstObjCompany = new CompanyDAO().mRetrieve();

		DefaultListModel<String> lstModel = new DefaultListModel<String>();

		for (CompanyDTO company : vLstObjCompany)
		{
			lstModel.addElement(company.getName());
		}

		lstCompanies.setModel(lstModel);
	}

	// Event Handlers
	private void mLstCompanySelectionChangeHandler()
	{
		DefaultListModel<String> lstYearModel = new DefaultListModel<String>();

		for (YearDTO year : vLstObjCompany.get(lstCompanies.getSelectedIndex()).getYears())
		{
			lstYearModel.addElement(Util.mGetLocalDateFormat(year.getDate_from()) + " to " + Util.mGetLocalDateFormat(year.getDate_to()));
		}

		lstYear.setModel(lstYearModel);
	}

	// Button Click Handlers
	private void mBtnAddCompanyClickHandler()
	{
		vParent.addPanel(new pnlAddCompanyAndYear(vParent));
	}

	private void mBtnEditCompanyClickHandler()
	{
		if (lstCompanies.getSelectedIndex() == -1)
		{
			JOptionPane.showMessageDialog(null, "Select a company to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			vParent.addPanel(new pnlEditCompany(vParent, vLstObjCompany.get(lstCompanies.getSelectedIndex())));
		}
	}

	private void mBtnDeleteCompanyClickHandler()
	{
		if (lstCompanies.getSelectedIndex() == -1)
		{
			JOptionPane.showMessageDialog(null, "Select a company to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			CompanyDTO vSelectedObjCompany = vLstObjCompany.get(lstCompanies.getSelectedIndex());

			if (vSelectedObjCompany.getYears().size() > 0)
			{
				JOptionPane.showMessageDialog(null, "First delete all financial years for this company", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION)
				{
					if (new CompanyDAO().mDelete(vSelectedObjCompany))
					{
						JOptionPane.showMessageDialog(null, "Company deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
						mLoadCompanies();
					}
				}
			}
		}
	}

	private void mBtnAddYearClickHandler()
	{
		if (lstCompanies.getSelectedIndex() == -1)
		{
			JOptionPane.showMessageDialog(null, "Select a company first", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			vParent.addPanel(new pnlAddYear(vParent, vLstObjCompany.get(lstCompanies.getSelectedIndex())));
		}
	}

	private void mBtnDeleteYearClickHandler()
	{
		if (lstYear.getSelectedIndex() == -1)
		{
			JOptionPane.showMessageDialog(null, "Select a year to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			CompanyDTO vSelectedObjCompany = vLstObjCompany.get(lstCompanies.getSelectedIndex());
			YearDTO vSelectedObjYear = vSelectedObjCompany.getYears().get(lstYear.getSelectedIndex());

			int dialogResult = JOptionPane.showConfirmDialog(null, "Data for selected year will be deleted. \n Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION)
			{

				vSelectedObjCompany.removeYear(vSelectedObjYear);

				Database.deleteDB(Integer.toString(vSelectedObjYear.getId()));
				if (new CompanyDAO().mSave(vSelectedObjCompany))
				{
					JOptionPane.showMessageDialog(null, "Year deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
					mLstCompanySelectionChangeHandler();
				}

			}
		}
	}

	private void mBtnSelectClickHandler()
	{
		Database.startSessionFactoryCompanyYear(Integer.toString(vLstObjCompany.get(lstCompanies.getSelectedIndex())
				.getYears()
				.get(lstYear.getSelectedIndex())
				.getId()
		)
		);

		//vParent.addPanel(new pnlMenu(vParent));
		vParent.addPanel(new pnlMenu2(vParent));
	}

	private void mBtnChangePasswordClickHandler()
	{
	}

	private void mBtnActivationClickHandler()
	{
		vParent.addPanel(new pnlActivation(vParent));
	}

	private void mBtnUpdateClickHandler()
	{

		AppInfoSer appInfoSer = new AppInfoSer(Database.getSessionApp());
		String updaterVersion = appInfoSer.get(AppInfoSer.AppInfoKey.UpdaterVersion);

		UpdaterResDTO updaterResDTO = new APIHelper("", "", "", GV.APP_TYPE_ID, "", "", "").getNewUpdater();

		if (updaterResDTO.isDone())
		{
			if (!updaterVersion.equals(updaterResDTO.getData().getVersion()))
			{
				if (Download.Download(updaterResDTO.getData().getFile_path(), GV.INSTALLATION_DIR + "/um.exe"))
				{
					appInfoSer.set(AppInfoSer.AppInfoKey.UpdaterVersion, updaterResDTO.getData().getVersion());
				}
			}
			GV.startUpdateManager();
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

                jScrollPane1 = new javax.swing.JScrollPane();
                lstCompanies = new javax.swing.JList<>();
                btnAddCompany = new javax.swing.JButton();
                btnDeleteCompany = new javax.swing.JButton();
                jScrollPane2 = new javax.swing.JScrollPane();
                lstYear = new javax.swing.JList<>();
                btnAddYear = new javax.swing.JButton();
                btnDeleteYear = new javax.swing.JButton();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                btnEditCompany = new javax.swing.JButton();
                btnSelect = new javax.swing.JButton();
                btnActivation = new javax.swing.JButton();
                btnChangePassword = new javax.swing.JButton();
                btnUpdate = new javax.swing.JButton();

                setMinimumSize(new java.awt.Dimension(0, 0));

                lstCompanies.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                lstCompanies.addListSelectionListener(new javax.swing.event.ListSelectionListener()
                {
                        public void valueChanged(javax.swing.event.ListSelectionEvent evt)
                        {
                                lstCompaniesValueChanged(evt);
                        }
                });
                jScrollPane1.setViewportView(lstCompanies);

                btnAddCompany.setText("Add");
                btnAddCompany.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnAddCompanyActionPerformed(evt);
                        }
                });

                btnDeleteCompany.setText("Delete");
                btnDeleteCompany.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnDeleteCompanyActionPerformed(evt);
                        }
                });

                jScrollPane2.setViewportView(lstYear);

                btnAddYear.setText("Add");
                btnAddYear.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnAddYearActionPerformed(evt);
                        }
                });

                btnDeleteYear.setText("Delete");
                btnDeleteYear.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnDeleteYearActionPerformed(evt);
                        }
                });

                jLabel1.setText("Company");

                jLabel2.setText("Financial Year");

                btnEditCompany.setText("Edit");
                btnEditCompany.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnEditCompanyActionPerformed(evt);
                        }
                });

                btnSelect.setText("Select");
                btnSelect.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnSelectActionPerformed(evt);
                        }
                });

                btnActivation.setText("Activation");
                btnActivation.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnActivationActionPerformed(evt);
                        }
                });

                btnChangePassword.setText("Change Password");
                btnChangePassword.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnChangePasswordActionPerformed(evt);
                        }
                });

                btnUpdate.setText("Update");
                btnUpdate.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnUpdateActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnAddCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEditCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDeleteCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnAddYear, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnDeleteYear, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btnChangePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnActivation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jScrollPane2)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnActivation)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnChangePassword)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnUpdate)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnDeleteCompany)
                                        .addComponent(btnSelect)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnAddCompany)
                                                .addComponent(btnAddYear)
                                                .addComponent(btnDeleteYear)
                                                .addComponent(btnEditCompany)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        }// </editor-fold>//GEN-END:initComponents

        private void lstCompaniesValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_lstCompaniesValueChanged
        {//GEN-HEADEREND:event_lstCompaniesValueChanged
			// TODO add your handling code here:
			if (!evt.getValueIsAdjusting())//This line prevents double events
			{
				mLstCompanySelectionChangeHandler();
			}
        }//GEN-LAST:event_lstCompaniesValueChanged

        private void btnSelectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSelectActionPerformed
        {//GEN-HEADEREND:event_btnSelectActionPerformed
			// TODO add your handling code here:
			mBtnSelectClickHandler();
        }//GEN-LAST:event_btnSelectActionPerformed

        private void btnAddCompanyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddCompanyActionPerformed
        {//GEN-HEADEREND:event_btnAddCompanyActionPerformed
			// TODO add your handling code here:
			mBtnAddCompanyClickHandler();
        }//GEN-LAST:event_btnAddCompanyActionPerformed

        private void btnActivationActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnActivationActionPerformed
        {//GEN-HEADEREND:event_btnActivationActionPerformed
			// TODO add your handling code here:
			mBtnActivationClickHandler();
        }//GEN-LAST:event_btnActivationActionPerformed

        private void btnChangePasswordActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnChangePasswordActionPerformed
        {//GEN-HEADEREND:event_btnChangePasswordActionPerformed
			// TODO add your handling code here:
        }//GEN-LAST:event_btnChangePasswordActionPerformed

        private void btnEditCompanyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEditCompanyActionPerformed
        {//GEN-HEADEREND:event_btnEditCompanyActionPerformed
			// TODO add your handling code here:
			mBtnEditCompanyClickHandler();
        }//GEN-LAST:event_btnEditCompanyActionPerformed

        private void btnDeleteCompanyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDeleteCompanyActionPerformed
        {//GEN-HEADEREND:event_btnDeleteCompanyActionPerformed
			// TODO add your handling code here:
			mBtnDeleteCompanyClickHandler();
        }//GEN-LAST:event_btnDeleteCompanyActionPerformed

        private void btnAddYearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddYearActionPerformed
        {//GEN-HEADEREND:event_btnAddYearActionPerformed
			// TODO add your handling code here:
			mBtnAddYearClickHandler();
        }//GEN-LAST:event_btnAddYearActionPerformed

        private void btnDeleteYearActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDeleteYearActionPerformed
        {//GEN-HEADEREND:event_btnDeleteYearActionPerformed
			// TODO add your handling code here:
			mBtnDeleteYearClickHandler();
        }//GEN-LAST:event_btnDeleteYearActionPerformed

        private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnUpdateActionPerformed
        {//GEN-HEADEREND:event_btnUpdateActionPerformed
			// TODO add your handling code here:
			mBtnUpdateClickHandler();
        }//GEN-LAST:event_btnUpdateActionPerformed


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnActivation;
        private javax.swing.JButton btnAddCompany;
        private javax.swing.JButton btnAddYear;
        private javax.swing.JButton btnChangePassword;
        private javax.swing.JButton btnDeleteCompany;
        private javax.swing.JButton btnDeleteYear;
        private javax.swing.JButton btnEditCompany;
        private javax.swing.JButton btnSelect;
        private javax.swing.JButton btnUpdate;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JList<String> lstCompanies;
        private javax.swing.JList<String> lstYear;
        // End of variables declaration//GEN-END:variables
}
