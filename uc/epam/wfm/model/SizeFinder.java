/**
 * 
 */
package uc.epam.wfm.model;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * @author kitos_000
 * 
 */
public class SizeFinder extends Observable implements Runnable {
    private File file;
    private Item item;
    private FileSystemView view;

    public SizeFinder(Item item, Observer parent) {
	view = FileSystemView.getFileSystemView();
	file = new File(item.getAbsolutePath());
	this.item = item;

	addObserver(parent);
    }

    @Override
    public void run() {
	long result = itemLength(file);
	String fileType = new JFileChooser().getTypeDescription(file);
	Icon icon = view.getSystemIcon(new File(file.getAbsolutePath()));
	item.setSize(result);
	item.setType(fileType);
	item.setIcon(icon);
	setChanged();
	notifyObservers(item);
    }

    public long itemLength(File file) {
	long result = 0;
	if (file.isFile()) {
	    result = file.length();
	} else {
	    if (file.listFiles() != null) {
		for (File fileObj : file.listFiles()) {
		    if (fileObj.isFile()) {
			result += fileObj.length();
		    } else {
			result += itemLength(fileObj);
		    }
		}
	    }
	}
	return result;
    }

}
