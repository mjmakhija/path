package com.emsays.path.gui;

import com.emsays.path.CommonUIActions;
import static com.emsays.path.CommonUIActions.showMessageBox;
import com.emsays.path.Database;
import com.emsays.path.Util;
import com.emsays.path.dao.AreaSer;
import com.emsays.path.dao.CollectFromSer;
import com.emsays.path.dao.CustomerSer;
import com.emsays.path.dao.OfferSer;
import com.emsays.path.dao.PackageSer;
import com.emsays.path.dao.PaymentTypeSer;
import com.emsays.path.dto.AreaDTO;
import com.emsays.path.dto.CollectFromDTO;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.OfferDTO;
import com.emsays.path.dto.PackageDTO;
import com.emsays.path.dto.PaymentTypeDTO;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class pnlAddCustomerOnly extends javax.swing.JPanel
{

	MyFormWrapper frmWrapper;
	MyPanelBack pnlBack;
	boolean vModeAdd;
	CustomerDTO customerOld;
	CustomerDTO vObjCustomer;

	List<AreaDTO> areas;
	List<PaymentTypeDTO> paymentTypes;
	List<PackageDTO> packages;
	List<OfferDTO> offers;
	List<CollectFromDTO> collectFroms;
	CustomerSer customerSer;

	public pnlAddCustomerOnly(MyFormWrapper vParent, MyPanelBack pnlBack)
	{
		initComponents();
		myInit();
		this.frmWrapper = vParent;
		this.pnlBack = pnlBack;
		this.vModeAdd = true;

		fillDropDowns();

		this.vObjCustomer = new CustomerDTO();
		SwingUtilities.getRootPane((Component) vParent).setDefaultButton(btnSave);
	}

	public pnlAddCustomerOnly(MyFormWrapper vParent, MyPanelBack pnlBack, CustomerDTO vObjCustomer)
	{
		initComponents();
		myInit();
		this.frmWrapper = vParent;
		this.pnlBack = pnlBack;
		this.vModeAdd = false;

		fillDropDowns();

		this.customerOld = new CustomerDTO(vObjCustomer);

		this.vObjCustomer = vObjCustomer;
		txtAccountNo.setText(String.valueOf(vObjCustomer.getAccountNo()));
		txtCustomerName.setText(vObjCustomer.getCustomerName());
		txtHouseNo1.setText(vObjCustomer.getHouseNo1());
		txtHouseNo2.setText(vObjCustomer.getHouseNo2());
		txtHouseNo3.setText(vObjCustomer.getHouseNo3());
		cmbArea.setSelectedItem(vObjCustomer.getArea().getName());
		txtOfficialAddress.setText(vObjCustomer.getOfficialAddress());
		txtMobile.setText(vObjCustomer.getMobile());

		if (vObjCustomer.getPaymentType() == null)
		{
			cmbPaymentType.setSelectedIndex(0);
		}
		else
		{
			cmbPaymentType.setSelectedItem(vObjCustomer.getPaymentType().getName());
		}

		if (vObjCustomer.getPackage() == null)
		{
			cmbPackage.setSelectedIndex(0);
		}
		else
		{
			cmbPackage.setSelectedItem(vObjCustomer.getPackage().getName());
		}
		chkHome.setSelected(vObjCustomer.isHome());
		chkAmp.setSelected(vObjCustomer.isAmp());
		txtAmount.setText(String.valueOf(vObjCustomer.getAmount()));

		if (vObjCustomer.getOffer() == null)
		{
			cmbOffer.setSelectedIndex(0);
		}
		else
		{
			cmbOffer.setSelectedItem(vObjCustomer.getOffer().getName());
		}

		if (vObjCustomer.getCollectFrom() == null)
		{
			cmbCollectFrom.setSelectedIndex(0);
		}
		else
		{
			cmbCollectFrom.setSelectedItem(vObjCustomer.getCollectFrom().getName());
		}

		txtNote.setText(vObjCustomer.getNote());

	}

	private void myInit()
	{
		customerSer = new CustomerSer(Database.getSessionCompanyYear());
	}

	private void fillDropDowns()
	{
		areas = new AreaSer(Database.getSessionCompanyYear()).retrieve();
		paymentTypes = new PaymentTypeSer(Database.getSessionCompanyYear()).retrieve();
		packages = new PackageSer(Database.getSessionCompanyYear()).retrieve();
		offers = new OfferSer(Database.getSessionCompanyYear()).retrieve();
		collectFroms = new CollectFromSer(Database.getSessionCompanyYear()).retrieve();

		CommonUIActions.fillComboBox(cmbArea, new ArrayList<>(areas));
		CommonUIActions.fillComboBox(cmbPaymentType, new ArrayList<>(paymentTypes));
		CommonUIActions.fillComboBox(cmbPackage, new ArrayList<>(packages));
		CommonUIActions.fillComboBox(cmbOffer, new ArrayList<>(offers));
		CommonUIActions.fillComboBox(cmbCollectFrom, new ArrayList<>(collectFroms));

	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{
		if (txtAccountNo.getText() == null || txtAccountNo.getText().equals(""))
		{
			errorMsg.append("Account no is required");
			return false;
		}

		if (!Util.isNumeric(txtAccountNo.getText()))
		{
			errorMsg.append("Account no is invalid");
			return false;
		}

		if (txtCustomerName.getText() == null || txtCustomerName.getText().equals(""))
		{
			errorMsg.append("Customer name is required");
			return false;
		}

		if (txtHouseNo1.getText() == null || txtHouseNo1.getText().equals(""))
		{
			errorMsg.append("House no 1 is required");
			return false;
		}

		if (cmbArea.getSelectedIndex() == 0)
		{
			errorMsg.append("Area is required");
			return false;
		}

		if (cmbPaymentType.getSelectedIndex() == 0)
		{
			errorMsg.append("Payment type is required");
			return false;
		}

		if (cmbPackage.getSelectedIndex() == 0)
		{
			errorMsg.append("Package is required");
			return false;
		}

		if (txtAmount.getText() == null || txtAmount.getText().equals(""))
		{
			errorMsg.append("Amount is required");
			return false;
		}

		if (!Util.isNumeric(txtAmount.getText()))
		{
			errorMsg.append("Amount is invalid");
			return false;
		}

		if (cmbCollectFrom.getSelectedIndex() == 0)
		{
			errorMsg.append("Collect from is required");
			return false;
		}

		return true;
	}

	private void clearBoxes()
	{
		vObjCustomer = new CustomerDTO();

		txtAccountNo.setText(null);
		txtCustomerName.setText(null);
		txtHouseNo1.setText(null);
		txtHouseNo2.setText(null);
		txtHouseNo3.setText(null);
		cmbArea.setSelectedIndex(0);
		txtOfficialAddress.setText(null);
		txtMobile.setText(null);
		cmbPaymentType.setSelectedIndex(0);
		cmbPackage.setSelectedIndex(0);
		chkHome.setSelected(false);
		chkAmp.setSelected(false);
		txtAmount.setText(null);
		cmbOffer.setSelectedIndex(0);
		cmbCollectFrom.setSelectedIndex(0);
		txtNote.setText(null);

		txtAccountNo.requestFocus();
	}

	private void handleClickBtnSave()
	{
		StringBuilder errorMsg = new StringBuilder();
		if (!checkIsValid(errorMsg))
		{
			showMessageBox(errorMsg.toString());
			return;
		}

		vObjCustomer.setAccountNo(Integer.valueOf(txtAccountNo.getText()));
		vObjCustomer.setCustomerName(txtCustomerName.getText());
		vObjCustomer.setHouseNo1(txtHouseNo1.getText());
		vObjCustomer.setHouseNo2(txtHouseNo2.getText());
		vObjCustomer.setHouseNo3(txtHouseNo3.getText());
		vObjCustomer.setArea(areas.get(cmbArea.getSelectedIndex() - 1));
		vObjCustomer.setOfficialAddress(txtOfficialAddress.getText());
		vObjCustomer.setMobile(txtMobile.getText());
		vObjCustomer.setPaymentType(paymentTypes.get(cmbPaymentType.getSelectedIndex() - 1));
		vObjCustomer.setPackage(packages.get(cmbPackage.getSelectedIndex() - 1));
		vObjCustomer.setHome(chkHome.isSelected());
		vObjCustomer.setAmp(chkAmp.isSelected());
		vObjCustomer.setAmount(Integer.valueOf(txtAmount.getText()));
		if (cmbOffer.getSelectedIndex() == 0)
		{
			vObjCustomer.setOffer(null);
		}
		else
		{
			vObjCustomer.setOffer(offers.get(cmbOffer.getSelectedIndex() - 1));
		}
		vObjCustomer.setCollectFrom(collectFroms.get(cmbCollectFrom.getSelectedIndex() - 1));
		vObjCustomer.setNote(txtNote.getText());

		boolean res;
		if (vModeAdd)
		{
			res = (customerSer.create(vObjCustomer, errorMsg));
		}
		else
		{
			res = (customerSer.update(customerOld, vObjCustomer, errorMsg));
		}

		if (res)
		{
			CommonUIActions.showMessageBox("Data saved successfully");
		}
		else
		{
			CommonUIActions.showMessageBox(errorMsg.toString().equals("") ? "Error saving data" : errorMsg.toString());
			return;
		}

		if (vModeAdd)
		{
			clearBoxes();
		}
		else
		{
			this.showListForm();
		}

	}

	private void handleClickBtnCancel()
	{
		this.showListForm();
	}

	private void showListForm()
	{
		pnlBack.refreshData();
		frmWrapper.addPanel((JPanel) pnlBack);
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
                cmbPackage = new javax.swing.JComboBox<>();
                txtHouseNo1 = new javax.swing.JTextField();
                jLabel14 = new javax.swing.JLabel();
                txtOfficialAddress = new javax.swing.JTextField();
                txtCustomerName = new javax.swing.JTextField();
                jLabel2 = new javax.swing.JLabel();
                cmbOffer = new javax.swing.JComboBox<>();
                jLabel6 = new javax.swing.JLabel();
                chkAmp = new javax.swing.JCheckBox();
                jLabel3 = new javax.swing.JLabel();
                jLabel12 = new javax.swing.JLabel();
                txtMobile = new javax.swing.JTextField();
                jLabel8 = new javax.swing.JLabel();
                txtHouseNo3 = new javax.swing.JTextField();
                txtAmount = new javax.swing.JTextField();
                cmbCollectFrom = new javax.swing.JComboBox<>();
                jLabel5 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                txtAccountNo = new javax.swing.JTextField();
                chkHome = new javax.swing.JCheckBox();
                jLabel15 = new javax.swing.JLabel();
                jLabel1 = new javax.swing.JLabel();
                jLabel13 = new javax.swing.JLabel();
                txtHouseNo2 = new javax.swing.JTextField();
                txtNote = new javax.swing.JTextField();
                cmbPaymentType = new javax.swing.JComboBox<>();
                jLabel11 = new javax.swing.JLabel();
                cmbArea = new javax.swing.JComboBox<>();

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

                jLabel14.setText("Collect From");

                jLabel2.setText("Customer Name");

                jLabel6.setText("Official Address");

                chkAmp.setText("Amplifier");

                jLabel3.setText("House No");

                jLabel12.setText("Amount");

                jLabel8.setText("Mobile");

                jLabel5.setText("Area");

                jLabel10.setText("Payment Type");

                chkHome.setText("Home");

                jLabel15.setText("Note");

                jLabel1.setText("Account No");

                jLabel13.setText("Offer");

                jLabel11.setText("Package");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel10))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtAccountNo)
                                        .addComponent(cmbPaymentType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbArea, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbOffer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtMobile)
                                        .addComponent(txtAmount)
                                        .addComponent(cmbCollectFrom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbPackage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtOfficialAddress)
                                        .addComponent(txtCustomerName)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(chkHome)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(chkAmp))
                                                .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtHouseNo1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtHouseNo2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtHouseNo3))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel1))
                                        .addComponent(txtAccountNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtHouseNo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtHouseNo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtHouseNo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtOfficialAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbPackage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(chkHome)
                                        .addComponent(chkAmp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbOffer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbCollectFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15))
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
        private javax.swing.JCheckBox chkAmp;
        private javax.swing.JCheckBox chkHome;
        private javax.swing.JComboBox<String> cmbArea;
        private javax.swing.JComboBox<String> cmbCollectFrom;
        private javax.swing.JComboBox<String> cmbOffer;
        private javax.swing.JComboBox<String> cmbPackage;
        private javax.swing.JComboBox<String> cmbPaymentType;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JTextField txtAccountNo;
        private javax.swing.JTextField txtAmount;
        private javax.swing.JTextField txtCustomerName;
        private javax.swing.JTextField txtHouseNo1;
        private javax.swing.JTextField txtHouseNo2;
        private javax.swing.JTextField txtHouseNo3;
        private javax.swing.JTextField txtMobile;
        private javax.swing.JTextField txtNote;
        private javax.swing.JTextField txtOfficialAddress;
        // End of variables declaration//GEN-END:variables
}
