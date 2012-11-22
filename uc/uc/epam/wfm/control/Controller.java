package uc.epam.wfm.control;

import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import uc.epam.wfm.model.Model;
import uc.epam.wfm.model.TableModel;
import uc.epam.wfm.view.PropertiesDialog;
import uc.epam.wfm.view.View;
import uc.epam.wfm.view.Window;

public class Controller implements ActionListener {

    private Window window;
    private Vector<String> paths;

    public Controller(Window window) {
	this.window = window;
	this.paths = window.getPaths();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
	switch (event.getActionCommand()) {
	case "ult":
	    setPath(window.getLeftComboBox(), window.getLeftTableModel());
	    break;
	case "urt":
	    setPath(window.getRightComboBox(), window.getRightTableModel());
	    break;
	case "lb":
	    goParentDirectiory(window.getLeftComboBox(),
		    window.getLeftTableModel());
	    break;
	case "rb":
	    goParentDirectiory(window.getRightComboBox(),
		    window.getRightTableModel());
	    break;
	case "ldel":
	    deleteFile(window.getLeftTable(), window.getLeftTableModel());
	    break;
	case "rdel":
	    deleteFile(window.getRightTable(), window.getRightTableModel());
	    break;
	case "lnfile":
	    createFile(window.getLeftTableModel());
	    break;
	case "rnfile":
	    createFile(window.getRightTableModel());
	    break;
	case "lnfolder":
	    createFolder(window.getLeftTableModel());
	    break;
	case "rnfolder":
	    createFolder(window.getRightTableModel());
	    break;
	case "ctr":
	    copyOrMoveItemTo(window.getLeftTable(), window.getLeftTableModel(),
		    window.getRightTableModel());
	    break;
	case "ctl":
	    copyOrMoveItemTo(window.getRightTable(),
		    window.getRightTableModel(), window.getLeftTableModel());
	    break;
	case "lren":
	    renameItem(window.getLeftTable(), window.getLeftTableModel());
	    break;
	case "rren":
	    renameItem(window.getRightTable(), window.getRightTableModel());
	    break;
	case "lprop":
	    properties(window.getLeftTable(), window.getLeftTableModel());
	    break;
	case "rprop":
	    properties(window.getRightTable(), window.getRightTableModel());
	    break;
	}
    }

    private void setPath(JComboBox<String> cb, TableModel tm) {
	if (tm.setDirectory(cb.getSelectedItem().toString())) {
	    cb.setSelectedItem(tm.getAbsPath());
	    if (!paths.contains(tm.getAbsPath())) {
		paths.add(tm.getAbsPath());
	    }
	} else {
	    View.showWrongPathError(window);
	    cb.setSelectedItem(tm.getAbsPath());
	}
    }

    private void goParentDirectiory(JComboBox<String> cb, TableModel tm) {
	tm.setParentDirectory();
	cb.setSelectedItem(tm.getAbsPath());
	if (!paths.contains(tm.getAbsPath())) {
	    paths.add(tm.getAbsPath());
	}

    }

    public void tableCellClicked(MouseEvent event) {
	if ((event.getButton() == MouseEvent.BUTTON1)
		&& (event.getClickCount() > 1)) {
	    JTable table = (JTable) event.getSource();
	    Point p = event.getPoint();
	    TableModel tm = (TableModel) table.getModel();
	    File file = new File(tm.getAbsPath() + File.separator
		    + table.getValueAt(table.rowAtPoint(p), 1));
	    if (file.isDirectory()) {
		tm.setDirectory(file.getAbsolutePath());
		table.repaint();
		if (tm.equals(window.getLeftTableModel())) {
		    window.getLeftComboBox().setSelectedItem(
			    file.getAbsolutePath());
		} else {
		    window.getRightComboBox().setSelectedItem(
			    file.getAbsolutePath());
		}
	    } else {
		try {
		    Desktop.getDesktop().open(file);
		} catch (IOException e1) {
		    JOptionPane.showMessageDialog(null,
			    "I don't know why, but a can't open this file.",
			    "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	} else if (event.getButton() == MouseEvent.BUTTON3) {
	    JTable table = (JTable) event.getSource();
	    Point p = event.getPoint();
	    table.getSelectionModel().setSelectionInterval(table.rowAtPoint(p),
		    table.rowAtPoint(p));

	    JPopupMenu popup = new JPopupMenu();
	    JMenuItem propMenu = new JMenuItem("Properties");
	    propMenu.addActionListener(this);
	    propMenu.setActionCommand(table.equals(window.getLeftTable()) ? "lprop"
		    : "rprop");
	    popup.add(propMenu);

	    popup.show(table, event.getX(), event.getY());
	}
    }

    public void deleteFile(JTable sourceTable, TableModel sourceTM) {
	while (sourceTable.getRowCount() != 0
		&& sourceTable.getSelectedRowCount() != 0) {
	    int selectedRow = sourceTable.getSelectedRow();
	    String fileName = sourceTable.getValueAt(selectedRow, 1).toString();
	    File file = new File(sourceTM.getAbsPath() + File.separator
		    + fileName);
	    if (View.showConfirmationDeletion(window, file)) {
		if (!file.exists() || !Model.deleteItem(file)) {
		    View.showDeletingError(window, file);
		}
		sourceTable.getSelectionModel().removeSelectionInterval(
			selectedRow, selectedRow);
	    }
	}
	sourceTM.updateTable();
	sourceTable.repaint();
    }

    public void createFile(TableModel tableModel) {
	String fileName = View.showNewFileDialog(window);
	if (fileName != null) {
	    String filePath = tableModel.getAbsPath();
	    filePath += File.separator + fileName;
	    try {
		if (!Model.createFile(filePath)) {
		    View.showNewFileError(window, fileName);
		}
	    } catch (IOException e) {
		View.showIOError(window);
	    }
	    tableModel.updateTable();

	}
    }

    public void createFolder(TableModel tableModel) {
	String folderName = View.showNewFolderDialog(window);
	if (folderName != null) {
	    String folderPath = tableModel.getAbsPath();
	    folderPath += File.separator + folderName;
	    if (!Model.createFolder(folderPath)) {
		View.showCreationFolderError(window, folderName);
	    } else {
		tableModel.updateTable();
	    }
	}
    }

    public void copyOrMoveItemTo(JTable sourceTable, TableModel sourceTM,
	    TableModel targetTM) {
	while (sourceTable.getRowCount() != 0
		&& sourceTable.getSelectedRowCount() != 0) {

	    int selectedRow = sourceTable.getSelectedRow();
	    String fileName = sourceTable.getValueAt(selectedRow, 1).toString();
	    String fileAbsPath = sourceTM.getAbsPath() + File.separator
		    + fileName;
	    String targetPath = targetTM.getAbsPath();

	    if (!new File(targetPath + File.separator + fileName).exists()
		    || View.showConfirmationReplacingDialog(window, fileName)) {
		try {
		    if (window.mustCopy()) {
			Model.copyItemTo(fileAbsPath, targetPath);
		    } else {
			Model.moveItemTo(fileAbsPath, targetPath);
		    }
		} catch (IOException e) {
		    View.showIOError(window);
		}
		targetTM.updateTable();
	    }
	    sourceTable.getSelectionModel().removeSelectionInterval(
		    selectedRow, selectedRow);
	}
	sourceTM.updateTable();
	sourceTable.repaint();
    }

    public void renameItem(JTable table, TableModel tableModel) {
	if (table.getSelectedRow() != -1) {
	    String fileName = table.getValueAt(table.getSelectedRow(), 1)
		    .toString();
	    String filePath = tableModel.getAbsPath();
	    String newFileName = View.showRenameDialog(window, fileName);
	    filePath += File.separator + fileName;
	    Model.renameItem(filePath, newFileName);
	    tableModel.updateTable();
	}
    }

    /**
     * Run dialog, showing information about selected file in table or parent
     * directory, if no one file selected
     * 
     * @param table
     *            thats information we must show
     * @param tableModel
     *            of that table
     */
    public void properties(JTable table, TableModel tableModel) {
	String path = tableModel.getAbsPath();
	if (table.getSelectedRow() != -1) {
	    path += File.separator
		    + table.getValueAt(table.getSelectedRow(), 1);
	}
	new PropertiesDialog(new File(path));
    }
}
