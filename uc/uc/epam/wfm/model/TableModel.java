/**
 * 
 */
package uc.epam.wfm.model;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;
import uc.epam.wfm.view.View;

/**
 * @author Никита
 * 
 */
public class TableModel extends AbstractTableModel implements Observer {

    private static final long serialVersionUID = 1L;

    private File curPath;
    private String columnNames[] = { "", "Name", "Type", "Size", "Date" };
    private LinkedList<Item> data = new LinkedList<Item>();

    public TableModel() {
	setDirectory(".");
    }

    public String getAbsPath() {
	try {
	    return curPath.getCanonicalPath();
	} catch (IOException e) {
	    e.printStackTrace();
	    return "";
	}
    }

    public boolean setDirectory(String path) {
	boolean result = false;
	File newPath = new File(path);
	if (newPath.exists() && newPath.isDirectory()) {
	    curPath = newPath;
	    updateTable();
	    result = true;
	}
	return result;
    }

    public void setParentDirectory() {
	if (curPath.getParent() != null) {
	    setDirectory(curPath.getParent());
	}
    }

    public void updateTable() {
	data.clear();
	if (curPath.list() != null) {
	    for (File file : curPath.listFiles()) {
		if (file.isHidden())
		    continue;
		Item item = new Item(file);
		data.add(item);
		IconFinder icf = new IconFinder(item, this);
		Thread thread = new Thread(icf, "icon_thread" + data.size());
		thread.setPriority(5);
		thread.start();

		TypeFinder tf = new TypeFinder(item, this);
		thread = new Thread(tf, "type_thread" + data.size());
		thread.setPriority(3);
		thread.start();
		if (file.isFile()) {
		    SizeFinder sf = new SizeFinder(item, this);
		    thread = new Thread(sf, "size_thread" + data.size());
		    thread.setPriority(3);
		    thread.start();
		}
	    }
	    if (data.size() != 0) {
		fireTableDataChanged();
	    }
	}
    }

    public String getColumnName(int col) {
	return columnNames[col];
    }

    @Override
    public int getColumnCount() {
	return columnNames.length;
    }

    @Override
    public int getRowCount() {
	return data.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
	switch (col) {
	case 0:
	    return data.get(row).getIcon();
	case 1:
	    return data.get(row).getName();
	case 2:
	    return data.get(row).getType();
	case 3:
	    return View.sizeConverter(data.get(row).getSize());
	case 4:
	    return View.dateConverter(data.get(row).getDate());
	default:
	    return "";
	}
    }

    public Class<? extends Object> getColumnClass(int column) {
	return getValueAt(0, column).getClass();
    }

    public void setValueAt(Object obj, int row, int col) {
	data.get(row).setName(obj.toString());
    }

    @Override
    public void update(Observable o, Object obj) {
	fireTableDataChanged();
    }
}
