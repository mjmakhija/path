package com.emsays.path.gui;

import com.emsays.path.Database;
import com.emsays.path.Util;
import com.emsays.path.dao.CollectFromSer;
import com.emsays.path.dao.OfferSer;
import com.emsays.path.dao.PackageSer;
import com.emsays.path.dao.PaymentTypeSer;
import com.emsays.path.dto.CollectFromDTO;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.OfferDTO;
import com.emsays.path.dto.PackageDTO;
import com.emsays.path.dto.PaymentTypeDTO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class pnlAddCustomerChild extends javax.swing.JPanel
{

	MyFormWrapper vParent;
	boolean vModeAdd;
	CustomerDTO vObjCustomer;

	List<PaymentTypeDTO> paymentTypes;
	List<PackageDTO> packages;
	List<OfferDTO> offers;
	List<CollectFromDTO> collectFroms;

	String[] vColumnNames =
	{
		"Account No",
		"Customer Name",
		"House No 1",
		"House No 2",
		"House No 3",
		"Official Address",
		"Mobile",
		"Package",
		"Note",
	};

	public pnlAddCustomerChild(MyFormWrapper vParent)
	{
		initComponents();
		this.vParent = vParent;
		this.vModeAdd = true;

		mFillDropDowns();

		this.vObjCustomer = new CustomerDTO();
	}

	public pnlAddCustomerChild(MyFormWrapper vParent, CustomerDTO vObjCustomer)
	{
		initComponents();
		this.vParent = vParent;
		this.vModeAdd = false;

		mFillDropDowns();

		this.vObjCustomer = vObjCustomer;

		txtAccountNo.setText(String.valueOf(vObjCustomer.getAccountNo()));
		txtCustomerName.setText(vObjCustomer.getCustomerName());
		txtHouseNo1.setText(vObjCustomer.getHouseNo1());
		txtHouseNo2.setText(vObjCustomer.getHouseNo2());
		txtHouseNo3.setText(vObjCustomer.getHouseNo3());
		txtOfficialAddress.setText(vObjCustomer.getOfficialAddress());
		txtMobile.setText(vObjCustomer.getMobile());
		cmbPaymentType.setSelectedIndex(paymentTypes.indexOf(vObjCustomer.getPaymentType()) + 1);
		cmbPackage.setSelectedIndex(packages.indexOf(vObjCustomer.getPackage()) + 1);
		chkHome.setSelected(vObjCustomer.isHome());
		chkAmp.setSelected(vObjCustomer.isAmp());
		txtAmount.setText(String.valueOf(vObjCustomer.getAmount()));
		cmbOffer.setSelectedIndex(offers.indexOf(vObjCustomer.getOffer()) + 1);
		cmbCollectFrom.setSelectedIndex(collectFroms.indexOf(vObjCustomer.getCollectFrom()) + 1);
		txtNote.setText(vObjCustomer.getNote());

		mChildrenChangedHandle();

	}

	private void mFillDropDowns()
	{
		paymentTypes = new PaymentTypeSer(Database.getSessionCompanyYear()).retrieve();
		packages = new PackageSer(Database.getSessionCompanyYear()).retrieve();
		offers = new OfferSer(Database.getSessionCompanyYear()).retrieve();
		collectFroms = new CollectFromSer(Database.getSessionCompanyYear()).retrieve();

		cmbPaymentType.addItem("[SELECT]");
		cmbPackage.addItem("[SELECT]");
		cmbPackageChild.addItem("[SELECT]");
		cmbOffer.addItem("[SELECT]");
		cmbCollectFrom.addItem("[SELECT]");

		paymentTypes.forEach((object) ->
		{
			cmbPaymentType.addItem(object.getName());
		});

		packages.forEach((object) ->
		{
			cmbPackage.addItem(object.getName());
			cmbPackageChild.addItem(object.getName());
		});

		offers.forEach((object) ->
		{
			cmbOffer.addItem(object.getName());
		});

		collectFroms.forEach((object) ->
		{
			cmbCollectFrom.addItem(object.getName());
		});

	}

	private boolean checkIsValid()
	{
		if (txtAccountNo.getText() == null || txtAccountNo.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Account no is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (!Util.isNumeric(txtAccountNo.getText()))
		{
			JOptionPane.showMessageDialog(null, "Account no is invalid", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (txtCustomerName.getText() == null || txtCustomerName.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Customer name is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (txtHouseNo1.getText() == null || txtHouseNo1.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "House no 1 is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (txtLocality1.getText() == null || txtLocality1.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Locality 1 is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (cmbPaymentType.getSelectedIndex() == 0)
		{
			JOptionPane.showMessageDialog(null, "Payment type is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (cmbPackage.getSelectedIndex() == 0)
		{
			JOptionPane.showMessageDialog(null, "Package is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (txtAmount.getText() == null || txtAmount.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Amount is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (!Util.isNumeric(txtAmount.getText()))
		{
			JOptionPane.showMessageDialog(null, "Amount is invalid", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (cmbCollectFrom.getSelectedIndex() == 0)
		{
			JOptionPane.showMessageDialog(null, "Collect from is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}

	private boolean checkIsValidChild()
	{
		if (txtAccountNoChild.getText() == null || txtAccountNoChild.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Account no for child is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (!Util.isNumeric(txtAccountNoChild.getText()))
		{
			JOptionPane.showMessageDialog(null, "Account no for child is invalid", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (txtCustomerNameChild.getText() == null || txtCustomerNameChild.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Customer name for child is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (txtHouseNo1Child.getText() == null || txtHouseNo1Child.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "House no 1 for child is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (cmbPackageChild.getSelectedIndex() == 0)
		{
			JOptionPane.showMessageDialog(null, "Package for child is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}

	private void clearBoxes()
	{
		txtAccountNo.setText(null);
		txtCustomerName.setText(null);
		txtHouseNo1.setText(null);
		txtHouseNo2.setText(null);
		txtHouseNo3.setText(null);
		txtLocality1.setText(null);
		txtLocality2.setText(null);
		txtArea.setText(null);
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

		mClearChildBoxes();
		DefaultTableModel model = (DefaultTableModel) tblChildren.getModel();
		model.setRowCount(0);

		txtAccountNo.requestFocus();
	}

	private void mClearChildBoxes()
	{
		txtAccountNoChild.setText(null);
		txtCustomerNameChild.setText(null);
		txtHouseNo1Child.setText(null);
		txtHouseNo2Child.setText(null);
		txtHouseNo3Child.setText(null);
		txtOfficialAddressChild.setText(null);
		txtMobileChild.setText(null);
		cmbPackageChild.setSelectedIndex(0);
		txtNoteChild.setText(null);

		txtAccountNoChild.requestFocus();
	}

	private void handleClickBtnSave()
	{
		if (!checkIsValid())
		{
			return;
		}

		vObjCustomer.setAccountNo(Integer.valueOf(txtAccountNo.getText()));
		vObjCustomer.setCustomerName(txtCustomerName.getText());
		vObjCustomer.setHouseNo1(txtHouseNo1.getText());
		vObjCustomer.setHouseNo2(txtHouseNo2.getText());
		vObjCustomer.setHouseNo3(txtHouseNo3.getText());
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

		/*
		
		if (new CustomerSer(Database.getSessionCompanyYear()).createOrUpdate(vObjCustomer))
		{
			JOptionPane.showMessageDialog(null, "Data saved successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Error saving data", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		 */
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
		if (vModeAdd)
		{
			this.mShowMenuForm();
		}
		else
		{
			this.showListForm();
		}
	}

	private void mBtnSaveChildClickHandler()
	{
		if (!checkIsValidChild())
		{
			return;
		}

		CustomerDTO customer = new CustomerDTO();

		customer.setAccountNo(Integer.valueOf(txtAccountNoChild.getText()));
		customer.setCustomerName(txtCustomerNameChild.getText());
		customer.setHouseNo1(txtHouseNo1Child.getText());
		customer.setHouseNo2(txtHouseNo2Child.getText());
		customer.setHouseNo3(txtHouseNo3Child.getText());
		customer.setOfficialAddress(txtOfficialAddressChild.getText());
		customer.setMobile(txtMobileChild.getText());
		customer.setPackage(packages.get(cmbPackageChild.getSelectedIndex() - 1));
		customer.setNote(txtNoteChild.getText());

		this.vObjCustomer.addChild(customer);

		mClearChildBoxes();

		mChildrenChangedHandle();

	}

	private void handleClickBtnDelete()
	{
		if (tblChildren.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION)
			{
				int[] vSelectedIndices = tblChildren.getSelectedRows();
				for (int i : vSelectedIndices)
				{
					vObjCustomer.removeChild(vObjCustomer.getChilds().get(i));
				}
				//mLoadData();
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

				mChildrenChangedHandle();
			}
		}
	}

	private void mChildrenChangedHandle()
	{
		mLoadChildrenData(this.vObjCustomer.getChilds());
	}

	private void mLoadChildrenData(List<CustomerDTO> childs)
	{

		DefaultTableModel vDTM = new DefaultTableModel();
		vDTM.setColumnIdentifiers(vColumnNames);
		childs.forEach((child) ->
		{
			Object[] o = new Object[]
			{
				child.getAccountNo(),
				child.getCustomerName(),
				child.getHouseNo1(),
				child.getHouseNo2(),
				child.getHouseNo3(),
				child.getOfficialAddress(),
				child.getMobile(),
				child.getPackage(),
				child.getNote(),
			};
			vDTM.addRow(o);
		});

		tblChildren.setModel(vDTM);

	}

	private void showListForm()
	{
		vParent.addPanel(new pnlListCustomer(vParent));
	}

	private void mShowMenuForm()
	{
		//vParent.addPanel(new pnlMenu(vParent));
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
                jPanel1 = new javax.swing.JPanel();
                jLabel12 = new javax.swing.JLabel();
                txtMobile = new javax.swing.JTextField();
                txtHouseNo3 = new javax.swing.JTextField();
                txtAmount = new javax.swing.JTextField();
                jLabel4 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                txtLocality1 = new javax.swing.JTextField();
                jLabel15 = new javax.swing.JLabel();
                jLabel13 = new javax.swing.JLabel();
                txtNote = new javax.swing.JTextField();
                cmbPaymentType = new javax.swing.JComboBox<>();
                jLabel11 = new javax.swing.JLabel();
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
                jLabel8 = new javax.swing.JLabel();
                txtLocality2 = new javax.swing.JTextField();
                txtArea = new javax.swing.JTextField();
                cmbCollectFrom = new javax.swing.JComboBox<>();
                jLabel5 = new javax.swing.JLabel();
                txtAccountNo = new javax.swing.JTextField();
                chkHome = new javax.swing.JCheckBox();
                jLabel1 = new javax.swing.JLabel();
                txtHouseNo2 = new javax.swing.JTextField();
                jPanel2 = new javax.swing.JPanel();
                txtMobileChild = new javax.swing.JTextField();
                txtHouseNo3Child = new javax.swing.JTextField();
                jLabel18 = new javax.swing.JLabel();
                txtNoteChild = new javax.swing.JTextField();
                jLabel20 = new javax.swing.JLabel();
                cmbPackageChild = new javax.swing.JComboBox<>();
                txtHouseNo1Child = new javax.swing.JTextField();
                txtOfficialAddressChild = new javax.swing.JTextField();
                txtCustomerNameChild = new javax.swing.JTextField();
                jLabel9 = new javax.swing.JLabel();
                jLabel22 = new javax.swing.JLabel();
                jLabel23 = new javax.swing.JLabel();
                jLabel24 = new javax.swing.JLabel();
                txtAccountNoChild = new javax.swing.JTextField();
                jLabel26 = new javax.swing.JLabel();
                txtHouseNo2Child = new javax.swing.JTextField();
                jScrollPane2 = new javax.swing.JScrollPane();
                tblChildren = new javax.swing.JTable();
                btnSaveChild = new javax.swing.JButton();
                btnDeleteChild = new javax.swing.JButton();

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

                jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Parent"));

                jLabel12.setText("Amount");

                jLabel4.setText("Locality");

                jLabel10.setText("Payment Type");

                jLabel15.setText("Note");

                jLabel13.setText("Offer");

                jLabel11.setText("Package");

                jLabel14.setText("Collect From");

                jLabel2.setText("Customer Name");

                jLabel6.setText("Official Address");

                chkAmp.setText("Amplifier");

                jLabel3.setText("House No");

                jLabel8.setText("Mobile");

                jLabel5.setText("Area");

                chkHome.setText("Home");

                jLabel1.setText("Account No");

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel15))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtAmount, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtArea, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtLocality1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtLocality2))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtHouseNo1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtHouseNo2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtHouseNo3))
                                        .addComponent(txtCustomerName, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtAccountNo, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbPackage, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(chkHome)
                                                .addGap(57, 57, 57)
                                                .addComponent(chkAmp))
                                        .addComponent(txtOfficialAddress, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtMobile, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbPaymentType, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbOffer, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbCollectFrom, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                );
                jPanel1Layout.setVerticalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel1))
                                        .addComponent(txtAccountNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtHouseNo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(txtHouseNo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtHouseNo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtLocality1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtLocality2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtOfficialAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbPackage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(chkHome)
                                        .addComponent(chkAmp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbOffer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbCollectFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15))
                                .addContainerGap())
                );

                jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Child"));

                jLabel18.setText("Note");

                jLabel20.setText("Package");

                jLabel9.setText("Customer Name");

                jLabel22.setText("Official Address");

                jLabel23.setText("House No");

                jLabel24.setText("Mobile");

                jLabel26.setText("Account No");

                tblChildren.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][]
                        {
                                {},
                                {},
                                {},
                                {}
                        },
                        new String []
                        {

                        }
                ));
                jScrollPane2.setViewportView(tblChildren);

                btnSaveChild.setText("Save");
                btnSaveChild.setPreferredSize(new java.awt.Dimension(75, 32));
                btnSaveChild.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnSaveChildActionPerformed(evt);
                        }
                });

                btnDeleteChild.setText("Delete");
                btnDeleteChild.setPreferredSize(new java.awt.Dimension(75, 32));
                btnDeleteChild.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnDeleteChildActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(btnSaveChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDeleteChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel22)
                                                        .addComponent(jLabel24)
                                                        .addComponent(jLabel20)
                                                        .addComponent(jLabel26)
                                                        .addComponent(jLabel9)
                                                        .addComponent(jLabel23)
                                                        .addComponent(jLabel18))
                                                .addGap(9, 9, 9)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                                .addComponent(txtHouseNo1Child, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtHouseNo2Child, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtHouseNo3Child))
                                                        .addComponent(txtCustomerNameChild, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtAccountNoChild, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cmbPackageChild, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txtOfficialAddressChild, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtMobileChild, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtNoteChild, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel26))
                                        .addComponent(txtAccountNoChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(txtCustomerNameChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtHouseNo1Child, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel23)
                                        .addComponent(txtHouseNo2Child, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtHouseNo3Child, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtOfficialAddressChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel22))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtMobileChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel24))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbPackageChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtNoteChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDeleteChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSaveChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
                );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(456, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(39, Short.MAX_VALUE))
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

        private void btnSaveChildActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSaveChildActionPerformed
        {//GEN-HEADEREND:event_btnSaveChildActionPerformed
		// TODO add your handling code here:
		mBtnSaveChildClickHandler();
        }//GEN-LAST:event_btnSaveChildActionPerformed

        private void btnDeleteChildActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDeleteChildActionPerformed
        {//GEN-HEADEREND:event_btnDeleteChildActionPerformed
		// TODO add your handling code here:
		handleClickBtnDelete();
        }//GEN-LAST:event_btnDeleteChildActionPerformed


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnCancel;
        private javax.swing.JButton btnDeleteChild;
        private javax.swing.JButton btnSave;
        private javax.swing.JButton btnSaveChild;
        private javax.swing.JCheckBox chkAmp;
        private javax.swing.JCheckBox chkHome;
        private javax.swing.JComboBox<String> cmbCollectFrom;
        private javax.swing.JComboBox<String> cmbOffer;
        private javax.swing.JComboBox<String> cmbPackage;
        private javax.swing.JComboBox<String> cmbPackageChild;
        private javax.swing.JComboBox<String> cmbPaymentType;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel18;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel20;
        private javax.swing.JLabel jLabel22;
        private javax.swing.JLabel jLabel23;
        private javax.swing.JLabel jLabel24;
        private javax.swing.JLabel jLabel26;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JTable tblChildren;
        private javax.swing.JTextField txtAccountNo;
        private javax.swing.JTextField txtAccountNoChild;
        private javax.swing.JTextField txtAmount;
        private javax.swing.JTextField txtArea;
        private javax.swing.JTextField txtCustomerName;
        private javax.swing.JTextField txtCustomerNameChild;
        private javax.swing.JTextField txtHouseNo1;
        private javax.swing.JTextField txtHouseNo1Child;
        private javax.swing.JTextField txtHouseNo2;
        private javax.swing.JTextField txtHouseNo2Child;
        private javax.swing.JTextField txtHouseNo3;
        private javax.swing.JTextField txtHouseNo3Child;
        private javax.swing.JTextField txtLocality1;
        private javax.swing.JTextField txtLocality2;
        private javax.swing.JTextField txtMobile;
        private javax.swing.JTextField txtMobileChild;
        private javax.swing.JTextField txtNote;
        private javax.swing.JTextField txtNoteChild;
        private javax.swing.JTextField txtOfficialAddress;
        private javax.swing.JTextField txtOfficialAddressChild;
        // End of variables declaration//GEN-END:variables
}
