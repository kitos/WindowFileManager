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
import javax.swing.JOptionPane;
import javax.swing.JTable;

import uc.epam.wfm.model.Model;
import uc.epam.wfm.model.TableModel;
import uc.epam.wfm.view.Window;

public class Controller implements ActionListener {

    private Window window;
    private Vector<String> paths;

    private final int LEFT_TABLE = 1;
    private final int RIGHT_TABLE = 0;

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
	    deleteFile(window.getLeftTable());
	    break;
	case "rdel":
	    deleteFile(window.getRightTable());
	    break;
	case "lnfile":
	    createFile(LEFT_TABLE);
	    break;
	case "rnfile":
	    createFile(RIGHT_TABLE);
	    break;
	case "lnfolder":
	    createFolder(LEFT_TABLE);
	    break;
	case "rnfolder":
	    createFolder(RIGHT_TABLE);
	    break;
	case "ctr":
	    copyItemTo(window.getLeftTable());
	    break;
	case "ctl":
	    copyItemTo(window.getRightTable());
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
	    JOptionPane.showMessageDialog(window,
		    "Wrong path! Please enter correct one.", "Warning",
		    JOptionPane.WARNING_MESSAGE);
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
	if (event.getClickCount() > 1) {
	    JTable table = (JTable) event.getSource();
	    Point p = event.getPoint();
	    TableModel tm = (TableModel) table.getModel();
	    File file = new File(tm.getAbsPath() + "/"
		    + table.getValueAt(table.rowAtPoint(p), 1));
	    if (file.isDirectory()) {
		tm.setDirectory(file.getAbsolutePath());
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
	}
    }

    public void deleteFile(JTable table) {
	if (table.getSelectedRow() != -1) {
	    File file = new File(((TableModel) table.getModel()).getAbsPath()
		    + "\\"
		    + table.getValueAt(table.getSelectedRow(), 1).toString());
	    if (JOptionPane
		    .showConfirmDialog(
			    window,
			    "Do you really want to delete "
				    + (file.isFile() ? "file" : "folder")
				    + ":\n"
				    + file.getAbsolutePath()
				    + ((file.isDirectory() && file.list().length != 0) ? "\n and all it's files"
					    : "") + "?", "Deletion",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
		Model.deleteItem(file);
	    }
	}
    }

    public void createFile(int table) {
	String fileName = JOptionPane.showInputDialog(window,
		"Enter file name: ", "Create file",
		JOptionPane.QUESTION_MESSAGE);
	if (fileName != null) {
	    String filePath = (table == LEFT_TABLE) ? window
		    .getLeftTableModel().getAbsPath() : window
		    .getRightTableModel().getAbsPath();
	    filePath += ("\\" + fileName);
	    if (!Model.createFile(filePath)) {
		JOptionPane.showMessageDialog(window, "Unable to create file: "
			+ fileName, "Error", JOptionPane.ERROR_MESSAGE);
	    } else {
		if (table == LEFT_TABLE) {
		    window.getLeftTableModel().updateTable();
		} else {
		    window.getRightTableModel().updateTable();
		}
	    }
	}
    }

    public void createFolder(int table) {
	String folderName = JOptionPane.showInputDialog(window,
		"Enter folder name: ", "Create folder",
		JOptionPane.QUESTION_MESSAGE);
	if (folderName != null) {
	    String folderPath = (table == LEFT_TABLE) ? window
		    .getLeftTableModel().getAbsPath() : window
		    .getRightTableModel().getAbsPath();
	    folderPath += ("\\" + folderName);
	    if (!Model.createFolder(folderPath)) {
		JOptionPane.showMessageDialog(window,
			"Unable to create folder: " + folderName, "Error",
			JOptionPane.ERROR_MESSAGE);
	    } else {
		if (table == LEFT_TABLE) {
		    window.getLeftTableModel().updateTable();
		} else {
		    window.getRightTableModel().updateTable();
		}
	    }
	}
    }

    public void copyItemTo(JTable table) {
	String fileName = (String) table.getValueAt(table.getSelectedRow(), 1);
	String file = ((TableModel) table.getModel()).getAbsPath() + "\\"
		+ fileName;
	String target;
	if (table.equals(window.getLeftTable())) {
	    target = window.getRightTableModel().getAbsPath() + "\\" + fileName;
	} else {
	    target = window.getLeftTableModel().getAbsPath() + "\\" + fileName;
	}
	if (!new File(target).exists()
		|| JOptionPane.showConfirmDialog(window,
			"The destination already has file with a name \""
				+ fileName + "\".\nReplace it?",
			"Replace or skip", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
	    if (Model.copyItemTo(file, target)) {
		if (table.equals(window.getLeftTable())) {
		    window.getRightTableModel().updateTable();
		} else {
		    window.getLeftTableModel().updateTable();
		}
	    }
	}
    }
}
