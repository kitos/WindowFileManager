package uc.epam.wfm.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableMouseAdapter extends MouseAdapter {

    Controller control;

    public TableMouseAdapter(Controller control) {
	this.control = control;
    }

    public void mousePressed(MouseEvent event) {
	control.tableCellClicked(event);
    }
}
