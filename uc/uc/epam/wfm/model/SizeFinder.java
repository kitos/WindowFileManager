/**
 * 
 */
package uc.epam.wfm.model;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * @author kitos_000
 * 
 */
public class SizeFinder extends Observable implements Runnable {
    private Item item;

    public SizeFinder(Item item, Observer parent) {
	this.item = item;
	addObserver(parent);
    }

    @Override
    public void run() {
	File file = new File(item.getAbsolutePath());
	long result = Model.itemLength(file);
	item.setSize(result);
	setChanged();
	notifyObservers();
    }
}
