package com.emsays.path;

import com.emsays.path.gui.pnlListCollectFrom;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CommonUIActions
{

	public static void fillComboBox(JComboBox comboBox, List<ObjectForCombobox> items)
	{
		comboBox.addItem("[SELECT]");

		items.forEach((item) ->
		{
			comboBox.addItem(item.getDisplayName());
		});
	}

	public static void convertToExcelEventHandler(JTable table)
	{

		JFileChooser fileChooser = new JFileChooser();

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xlsx");
		fileChooser.setFileFilter(filter);

		_convertToExcelEventHandler(table, fileChooser);

	}

	private static void _convertToExcelEventHandler(JTable table, JFileChooser fileChooser)
	{
		if (fileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION)
		{
			File file = new File(fileChooser.getSelectedFile().getPath() + ".xlsx");

			if (file.exists())
			{
				int dialogResult = JOptionPane.showConfirmDialog(null, "File already exists. Do you want to overwrite it?", "Message", JOptionPane.YES_NO_OPTION);

				if (dialogResult == JOptionPane.YES_OPTION)
				{
					if (!file.delete())
					{
						JOptionPane.showMessageDialog(null, "Can't delete file. Please try again", "Message", JOptionPane.INFORMATION_MESSAGE);
						_convertToExcelEventHandler(table, fileChooser);
						return;
					}
				}
				else
				{
					_convertToExcelEventHandler(table, fileChooser);
					return;
				}
			}

			try
			{
				ExcelHandler.createExcel(table, file.getPath());
				JOptionPane.showMessageDialog(null, "File created successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			catch (Exception ex)
			{
				Logger.getLogger(pnlListCollectFrom.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static void showMessageBox(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
	}

}
