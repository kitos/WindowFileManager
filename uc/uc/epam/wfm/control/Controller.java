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
    private JComboBox<String> pathComboBox;
    private JTable ownTable;
    private TableModel ownTM;
    private TableModel targetTM;

    public void initComponents(Window window, JComboBox<String> pathComboBox,
	    JTable table, TableModel ownTableModel,
	    TableModel targetTableModel, Vector<String> paths) {
	this.window = window;
	this.pathComboBox = pathComboBox;
	this.ownTable = table;
	this.ownTM = ownTableModel;
	this.targetTM = targetTableModel;
	this.paths = paths;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
	switch (event.getActionCommand()) {
	case "upt": // update table
	    setPath();
	    break;
	case "back": // go to parent directory
	    goParentDirectiory();
	    break;
	case "del":
	    deleteItem();
	    break;
	case "newfile":
	    createFile();
	    break;
	case "newfolder":
	    createFolder();
	    break;
	case "copyto":
	    copyOrMoveItemTo();
	    break;
	case "rename":
	    renameItem();
	    break;
	case "prop": // properties dialog
	    properties();
	    break;
	}
    }

    private void setPath() {
	if (ownTM.setDirectory(pathComboBox.getSelectedItem().toString())) {
	    pathComboBox.setSelectedItem(ownTM.getAbsPath());
	    if (!paths.contains(ownTM.getAbsPath())) {
		paths.add(ownTM.getAbsPath());
	    }
	} else {
	    View.showWrongPathError(window);
	    pathComboBox.setSelectedItem(ownTM.getAbsPath());
	}
    }

    private void goParentDirectiory() {
	ownTM.setParentDirectory();
	pathComboBox.setSelectedItem(ownTM.getAbsPath());
	if (!paths.contains(ownTM.getAbsPath())) {
	    paths.add(ownTM.getAbsPath());
	}

    }

    public void tableCellClicked(MouseEvent event) {
	if ((event.getButton() == MouseEvent.BUTTON1)
		&& (event.getClickCount() > 1)) {
	    Point p = event.getPoint();
	    File file = new File(ownTM.getAbsPath() + File.separator
		    + ownTable.getValueAt(ownTable.rowAtPoint(p), 1));
	    if (file.isDirectory()) {
		ownTM.setDirectory(file.getAbsolutePath());
		ownTable.repaint();
		pathComboBox.setSelectedItem(file.getAbsolutePath());
	    } else {
		try {
		    Desktop.getDesktop().open(file);
		} catch (IOException e) {
		    View.showOpenFileError(e.getMessage());
		}
	    }
	} else if (event.getButton() == MouseEvent.BUTTON3) {
	    Point p = event.getPoint();
	    ownTable.getSelectionModel().setSelectionInterval(
		    ownTable.rowAtPoint(p), ownTable.rowAtPoint(p));

	    JPopupMenu popup = new JPopupMenu();
	    JMenuItem propMenu = new JMenuItem("Properties");
	    propMenu.addActionListener(this);
	    propMenu.setActionCommand("prop");
	    popup.add(propMenu);

	    popup.show(ownTable, event.getX(), event.getY());
	}
    }

    public void deleteItem() {
	while (ownTable.getRowCount() != 0
		&& ownTable.getSelectedRowCount() != 0) {
	    int selectedRow = ownTable.getSelectedRow();
	    String fileName = ownTable.getValueAt(selectedRow, 1).toString();
	    File file = new File(ownTM.getAbsPath() + File.separator + fileName);
	    if (View.showConfirmationDeletion(window, file)) {
		if (!file.exists() || !Model.deleteItem(file)) {
		    View.showDeletingError(window, file);
		}
	    }
	    ownTable.getSelectionModel().removeSelectionInterval(selectedRow,
		    selectedRow);
	}
	ownTM.updateTable();
	ownTable.repaint();
    }

    public void createFile() {
	String fileName = View.showNewFileDialog(window);
	if (fileName != null) {
	    String filePath = ownTM.getAbsPath();
	    filePath += File.separator + fileName;
	    try {
		if (!Model.createFile(filePath)) {
		    View.showNewFileError(window, fileName);
		}
	    } catch (IOException e) {
		View.showIOError(window, e.getMessage());
	    }
	    ownTM.updateTable();

	}
    }

    public void createFolder() {
	String folderName = View.showNewFolderDialog(window);
	if (folderName != null) {
	    String folderPath = ownTM.getAbsPath();
	    folderPath += File.separator + folderName;
	    if (!Model.createFolder(folderPath)) {
		View.showCreationFolderError(window, folderName);
	    } else {
		ownTM.updateTable();
	    }
	}
    }

    public void copyOrMoveItemTo() {
	while (ownTable.getRowCount() != 0
		&& ownTable.getSelectedRowCount() != 0) {

	    int selectedRow = ownTable.getSelectedRow();
	    String fileName = ownTable.getValueAt(selectedRow, 1).toString();
	    String fileAbsPath = ownTM.getAbsPath() + File.separator + fileName;
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
		    View.showIOError(window, e.getMessage());
		}
		targetTM.updateTable();
	    }
	    ownTable.getSelectionModel().removeSelectionInterval(selectedRow,
		    selectedRow);
	}
	ownTM.updateTable();
	ownTable.repaint();
    }

    public void renameItem() {
	if (ownTable.getSelectedRow() != -1) {
	    String fileName = ownTable.getValueAt(ownTable.getSelectedRow(), 1)
		    .toString();
	    String filePath = ownTM.getAbsPath();
	    String newFileName = View.showRenameDialog(window, fileName);
	    filePath += File.separator + fileName;
	    Model.renameItem(filePath, newFileName);
	    ownTM.updateTable();
	}
    }

    /**
     * Run dialog, showing information about selected file in table or parent
     * directory, if no one file selected of that table
     */
    public void properties() {
	String path = ownTM.getAbsPath();
	if (ownTable.getSelectedRow() != -1) {
	    path += File.separator
		    + ownTable.getValueAt(ownTable.getSelectedRow(), 1);
	}
	new PropertiesDialog(new File(path));
    }
}
