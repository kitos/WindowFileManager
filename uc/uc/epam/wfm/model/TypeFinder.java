package uc.epam.wfm.model;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;

public class TypeFinder extends Observable implements Runnable {

    private Item item;

    public TypeFinder(Item item, Observer parent) {
	this.item = item;
	addObserver(parent);
    }

    @Override
    public void run() {
	File file = new File(item.getAbsolutePath());
	String fileType = new JFileChooser().getTypeDescription(file);
	item.setType(fileType);
	setChanged();
	notifyObservers();
    }

}
