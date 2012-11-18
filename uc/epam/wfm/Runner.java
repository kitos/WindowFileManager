package uc.epam.wfm;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import uc.epam.wfm.view.Window;

public class Runner {

    /**
     * @param args
     */
    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e) {
	    JOptionPane.showMessageDialog(null, "Unknown interface error",
		    "Error", JOptionPane.WARNING_MESSAGE);
	}
	Window window = new Window();
	window.setTitle("File Manager");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setVisible(true);
    }

}
