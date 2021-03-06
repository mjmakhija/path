package com.emsays.path.gui;

import com.emsays.path.Database;
import com.emsays.path.dao.SMSTemplateSer;
import com.emsays.path.dto.SMSTemplateDTO;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class pnlListSMSTemplate extends javax.swing.JPanel implements MyPanelBack
{

	MyFormWrapper vParent;
	List<SMSTemplateDTO> vLstObjSMSTemplate;

	public pnlListSMSTemplate(MyFormWrapper vParent)
	{
		initComponents();

		this.vParent = vParent;
		handleClickBtnSearch();
	}

	private void mLoadData(List<SMSTemplateDTO> vLstObjSMSTemplate)
	{
		this.vLstObjSMSTemplate = vLstObjSMSTemplate;

		DefaultListModel<String> lstModel = new DefaultListModel<String>();
		for (SMSTemplateDTO smsTemplateDTO : vLstObjSMSTemplate)
		{
			lstModel.addElement(smsTemplateDTO.getName());
		}
		lstTemplates.setModel(lstModel);

	}

	private void handleClickBtnSearch()
	{
		this.mLoadData(new SMSTemplateSer(Database.getSessionCompanyYear()).retrieve());
		taMessage.setText(null);
	}

	private void handleClickBtnEdit()
	{
		if (lstTemplates.getSelectedIndices().length == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (lstTemplates.getSelectedIndices().length > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		this.vParent.addPanel(new pnlAddSMSTemplate(vParent, this, vLstObjSMSTemplate.get(lstTemplates.getSelectedIndex())));

	}

	private void handleClickBtnDelete()
	{

		if (lstTemplates.getSelectedIndices().length == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION)
			{
				SMSTemplateSer objSMSTemplateSer = new SMSTemplateSer(Database.getSessionCompanyYear());

				int[] vSelectedIndices = lstTemplates.getSelectedIndices();
				for (int i : vSelectedIndices)
				{
					objSMSTemplateSer.delete(vLstObjSMSTemplate.get(i));
				}
				//mLoadData();
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				handleClickBtnSearch();
			}
		}

	}

	private void handleTemplateChange()
	{
		taMessage.setText(vLstObjSMSTemplate.get(lstTemplates.getSelectedIndex()).getMessage());
	}

	private void mBtnAddClickHandler()
	{
		vParent.addPanel(new pnlAddSMSTemplate(vParent, this));
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
                pm = new javax.swing.JPopupMenu();
                miAdd = new javax.swing.JMenuItem();
                miEdit = new javax.swing.JMenuItem();
                miDelete = new javax.swing.JMenuItem();
                jScrollPane3 = new javax.swing.JScrollPane();
                taMessage = new javax.swing.JTextArea();
                jLabel1 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                jScrollPane2 = new javax.swing.JScrollPane();
                lstTemplates = new javax.swing.JList<>();

                fileChooser.setAcceptAllFileFilterUsed(false);

                miAdd.setText("Add");
                miAdd.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miAddActionPerformed(evt);
                        }
                });
                pm.add(miAdd);

                miEdit.setText("Edit");
                miEdit.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miEditActionPerformed(evt);
                        }
                });
                pm.add(miEdit);

                miDelete.setText("Delete");
                miDelete.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miDeleteActionPerformed(evt);
                        }
                });
                pm.add(miDelete);

                taMessage.setEditable(false);
                taMessage.setColumns(20);
                taMessage.setRows(5);
                jScrollPane3.setViewportView(taMessage);

                jLabel1.setText("Template");

                jLabel8.setText("Message");

                lstTemplates.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                lstTemplates.setComponentPopupMenu(pm);
                lstTemplates.addVetoableChangeListener(new java.beans.VetoableChangeListener()
                {
                        public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException
                        {
                                lstTemplatesVetoableChange(evt);
                        }
                });
                lstTemplates.addListSelectionListener(new javax.swing.event.ListSelectionListener()
                {
                        public void valueChanged(javax.swing.event.ListSelectionEvent evt)
                        {
                                lstTemplatesValueChanged(evt);
                        }
                });
                jScrollPane2.setViewportView(lstTemplates);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        }// </editor-fold>//GEN-END:initComponents

        private void miAddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miAddActionPerformed
        {//GEN-HEADEREND:event_miAddActionPerformed
		mBtnAddClickHandler();
        }//GEN-LAST:event_miAddActionPerformed

        private void miEditActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miEditActionPerformed
        {//GEN-HEADEREND:event_miEditActionPerformed
		handleClickBtnEdit();
        }//GEN-LAST:event_miEditActionPerformed

        private void miDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miDeleteActionPerformed
        {//GEN-HEADEREND:event_miDeleteActionPerformed
		handleClickBtnDelete();
        }//GEN-LAST:event_miDeleteActionPerformed

        private void lstTemplatesVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException//GEN-FIRST:event_lstTemplatesVetoableChange
        {//GEN-HEADEREND:event_lstTemplatesVetoableChange

        }//GEN-LAST:event_lstTemplatesVetoableChange

        private void lstTemplatesValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_lstTemplatesValueChanged
        {//GEN-HEADEREND:event_lstTemplatesValueChanged
		if (!evt.getValueIsAdjusting() && lstTemplates.getSelectedIndex() != -1)//This line prevents double events
		{
			handleTemplateChange();
		}
        }//GEN-LAST:event_lstTemplatesValueChanged


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JFileChooser fileChooser;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JList<String> lstTemplates;
        private javax.swing.JMenuItem miAdd;
        private javax.swing.JMenuItem miDelete;
        private javax.swing.JMenuItem miEdit;
        private javax.swing.JPopupMenu pm;
        private javax.swing.JTextArea taMessage;
        // End of variables declaration//GEN-END:variables
}
