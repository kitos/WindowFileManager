package uc.epam.wfm.model;

import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Item {
    private String name;
    private String absPath;
    private String type;
    private long size;
    private long date;
    private Icon icon;

    public Item(File file) {
	name = file.getName();
	absPath = file.getAbsolutePath();
	type = "";
	size = 0;
	date = file.lastModified();
	icon = new ImageIcon("./icones/empty_file.png");
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setSize(long size) {
	this.size = size;
    }

    public void setType(String fileType) {
	this.type = fileType;
    }

    public void setIcon(Icon icon) {
	this.icon = icon;
    }

    public String getName() {
	return name;
    }

    public String getAbsolutePath() {
	return absPath;
    }

    public String getType() {
	return type;
    }

    public long getSize() {
	return size;
    }

    public long getDate() {
	return date;
    }

    public Icon getIcon() {
	return icon;
    }
}
