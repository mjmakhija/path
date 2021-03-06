package com.emsays.path.gui;

import com.emsays.path.CommonUIActions;
import com.emsays.path.Database;
import com.emsays.path.Util;
import com.emsays.path.dao.OfferSer;
import com.emsays.path.dto.OfferDTO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class pnlListOffer extends javax.swing.JPanel implements MyPanelBack
{

	MyFormWrapper vParent;
	List<OfferDTO> vLstObjOffer;
	String[] vColumnNames =
	{
		"#",
		"Id",
		"Name",
		"Note",
	};

	/**
	 * Creates new form pnlLogin
	 */
	public pnlListOffer(MyFormWrapper vParent)
	{
		initComponents();

		this.vParent = vParent;
		handleClickBtnSearch();
	}

	private void mLoadData(List<OfferDTO> vLstObjOffer)
	{
		this.vLstObjOffer = vLstObjOffer;

		DefaultTableModel vDTM = new DefaultTableModel();
		vDTM.setColumnIdentifiers(vColumnNames);
		int i = 1;
		for (OfferDTO vObjOffer : vLstObjOffer)
		{
			Object[] o = new Object[]
			{
				i++,
				vObjOffer.getId(),
				vObjOffer.getName(),
				vObjOffer.getNote(),
			};
			vDTM.addRow(o);
		}
		tbl.setModel(vDTM);
		Util.resizeColumnWidth(tbl);

	}

	private void clearBoxes()
	{
		txtName.setText(null);
		txtNote.setText(null);
	}

	private void handleClickBtnSearch()
	{
		this.mLoadData(new OfferSer(Database.getSessionCompanyYear()).retrieve(txtName.getText()));
	}

	private void handleClickBtnAll()
	{
		clearBoxes();

		handleClickBtnSearch();
	}

	private void mBtnMenuClickHandler()
	{
		//vParent.addPanel(new pnlMenu(vParent));
	}

	private void handleClickBtnEdit()
	{
		if (tbl.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tbl.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		this.vParent.addPanel(new pnlAddOffer(vParent,this, vLstObjOffer.get(tbl.getSelectedRow())));

	}

	private void handleClickBtnDelete()
	{

		if (tbl.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION)
			{
				OfferSer objOfferSer = new OfferSer(Database.getSessionCompanyYear());

				int[] vSelectedIndices = tbl.getSelectedRows();
				for (int i : vSelectedIndices)
				{
					objOfferSer.delete(vLstObjOffer.get(i));
				}
				//mLoadData();
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				handleClickBtnSearch();
			}
		}

	}

	private void handleClickBtnConvert()
	{
		CommonUIActions.convertToExcelEventHandler(tbl);
	}
	
	@Override
	public void refreshData()
	{
		handleClickBtnSearch();
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

                fileChooser = new javax.swing.JFileChooser();
                btnSearch = new javax.swing.JButton();
                btnAll = new javax.swing.JButton();
                txtName = new javax.swing.JTextField();
                jLabel1 = new javax.swing.JLabel();
                txtNote = new javax.swing.JTextField();
                jLabel7 = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                tbl = new javax.swing.JTable();
                btnDelete = new javax.swing.JButton();
                btnEdit = new javax.swing.JButton();
                btnConvert = new javax.swing.JButton();
                btnMenu = new javax.swing.JButton();

                fileChooser.setAcceptAllFileFilterUsed(false);

                btnSearch.setText("Search");
                btnSearch.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnSearchActionPerformed(evt);
                        }
                });

                btnAll.setText("All");
                btnAll.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnAllActionPerformed(evt);
                        }
                });

                jLabel1.setText("Name");

                jLabel7.setText("Note");

                tbl.setModel(new javax.swing.table.DefaultTableModel(
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
                tbl.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                jScrollPane1.setViewportView(tbl);

                btnDelete.setText("Delete");
                btnDelete.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnDeleteActionPerformed(evt);
                        }
                });

                btnEdit.setText("Edit");
                btnEdit.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnEditActionPerformed(evt);
                        }
                });

                btnConvert.setText("Convert");
                btnConvert.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnConvertActionPerformed(evt);
                        }
                });

                btnMenu.setText("Menu");
                btnMenu.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnMenuActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addComponent(jLabel1)
                                                                .addGap(74, 74, 74)
                                                                .addComponent(jLabel7))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnAll, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(btnConvert, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch)
                                        .addComponent(btnAll))
                                .addGap(4, 4, 4)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnEdit)
                                        .addComponent(btnDelete)
                                        .addComponent(btnConvert)
                                        .addComponent(btnMenu))
                                .addContainerGap())
                );
        }// </editor-fold>//GEN-END:initComponents

        private void btnSearchActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSearchActionPerformed
        {//GEN-HEADEREND:event_btnSearchActionPerformed
		// TODO add your handling code here:
		handleClickBtnSearch();
        }//GEN-LAST:event_btnSearchActionPerformed

        private void btnAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAllActionPerformed
        {//GEN-HEADEREND:event_btnAllActionPerformed
		// TODO add your handling code here:
		handleClickBtnAll();
        }//GEN-LAST:event_btnAllActionPerformed

        private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDeleteActionPerformed
        {//GEN-HEADEREND:event_btnDeleteActionPerformed
		// TODO add your handling code here:
		handleClickBtnDelete();
        }//GEN-LAST:event_btnDeleteActionPerformed

        private void btnEditActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEditActionPerformed
        {//GEN-HEADEREND:event_btnEditActionPerformed
		// TODO add your handling code here:
		handleClickBtnEdit();
        }//GEN-LAST:event_btnEditActionPerformed

        private void btnConvertActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnConvertActionPerformed
        {//GEN-HEADEREND:event_btnConvertActionPerformed
		// TODO add your handling code here:
		handleClickBtnConvert();
        }//GEN-LAST:event_btnConvertActionPerformed

        private void btnMenuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnMenuActionPerformed
        {//GEN-HEADEREND:event_btnMenuActionPerformed
		// TODO add your handling code here:
		mBtnMenuClickHandler();
        }//GEN-LAST:event_btnMenuActionPerformed


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnAll;
        private javax.swing.JButton btnConvert;
        private javax.swing.JButton btnDelete;
        private javax.swing.JButton btnEdit;
        private javax.swing.JButton btnMenu;
        private javax.swing.JButton btnSearch;
        private javax.swing.JFileChooser fileChooser;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTable tbl;
        private javax.swing.JTextField txtName;
        private javax.swing.JTextField txtNote;
        // End of variables declaration//GEN-END:variables
}
