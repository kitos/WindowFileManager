package uc.epam.wfm.model;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class IconFinder extends Observable implements Runnable {
    private Item item;

    public IconFinder(Item item, Observer parent) {
	this.item = item;
	addObserver(parent);
    }

    @Override
    public void run() {
	FileSystemView view = FileSystemView.getFileSystemView();
	Icon icon = view.getSystemIcon(new File(item.getAbsolutePath()));
	item.setIcon(icon);
	setChanged();
	notifyObservers();
    }

}
