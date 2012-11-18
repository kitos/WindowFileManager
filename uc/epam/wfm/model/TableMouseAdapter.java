package uc.epam.wfm.model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import uc.epam.wfm.control.Controller;

public class TableMouseAdapter extends MouseAdapter {

    Controller control;

    public TableMouseAdapter(Controller control) {
	this.control = control;
    }

    public void mousePressed(MouseEvent event) {
	control.tableCellClicked(event);
    }
}
