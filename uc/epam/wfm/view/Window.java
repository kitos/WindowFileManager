/**
 * 
 */
package uc.epam.wfm.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import uc.epam.wfm.control.Controller;
import uc.epam.wfm.model.TableModel;
import uc.epam.wfm.model.TableMouseAdapter;

/**
 * @author Никита
 * 
 */
public class Window extends JFrame {
    private static final long serialVersionUID = 1L;

    private Controller control;
    private JLabel lPathLabel;
    private JLabel rPathLabel;
    private JComboBox<String> lComboBox;
    private JComboBox<String> rComboBox;
    private JButton lSetPathButton;
    private JButton rSetPathButton;
    private TableModel lTableModel;
    private TableModel rTableModel;
    private JTable lTable;
    private JTable rTable;
    private JToolBar lToolBar;
    private JToolBar rToolBar;
    private Vector<String> paths = new Vector<String>(5);

    public Window() {
	initComponents();
	setUpComposition();
	setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public JComboBox<String> getLeftComboBox() {
	return lComboBox;
    }

    public JComboBox<String> getRightComboBox() {
	return rComboBox;
    }

    public JTable getLeftTable() {
	return lTable;
    }

    public JTable getRightTable() {
	return rTable;
    }

    public TableModel getLeftTableModel() {
	return lTableModel;
    }

    public TableModel getRightTableModel() {
	return rTableModel;
    }

    public Vector<String> getPaths() {
	return paths;
    }

    private void initComponents() {
	control = new Controller(this);
	lTableModel = new TableModel();
	rTableModel = new TableModel();

	lToolBar = new JToolBar();
	lToolBar.setFloatable(false);
	rToolBar = new JToolBar();
	rToolBar.setFloatable(false);

	lPathLabel = new JLabel("Current path:");
	rPathLabel = new JLabel("Current path:");

	paths.add(lTableModel.getAbsPath());
	for (File file : File.listRoots()) {
	    if (file.canRead()) {
		paths.add(file.getAbsolutePath());
	    }
	}

	lComboBox = new JComboBox<String>(paths);
	rComboBox = new JComboBox<String>(paths);
	lComboBox.setEditable(true);
	rComboBox.setEditable(true);
	lComboBox.setActionCommand("ult");
	rComboBox.setActionCommand("urt");
	lComboBox.addActionListener(control);
	rComboBox.addActionListener(control);

	lSetPathButton = new JButton("Set path");
	rSetPathButton = new JButton("Set path");
	lSetPathButton.setActionCommand("ult");
	rSetPathButton.setActionCommand("urt");
	lSetPathButton.addActionListener(control);
	rSetPathButton.addActionListener(control);

	lTable = new JTable(lTableModel);
	rTable = new JTable(rTableModel);
	lTable.setAutoCreateRowSorter(true);
	rTable.setAutoCreateRowSorter(true);
	lTable.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
	rTable.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
	lTable.setRowHeight(18);
	rTable.setRowHeight(18);
	lTable.setShowGrid(false);
	rTable.setShowGrid(false);
	lTable.addMouseListener(new TableMouseAdapter(control));
	rTable.addMouseListener(new TableMouseAdapter(control));

	lTable.getColumnModel().getColumn(4).setMaxWidth(100);
	rTable.getColumnModel().getColumn(4).setMaxWidth(100);
	lTable.getColumnModel().getColumn(3).setMaxWidth(70);
	rTable.getColumnModel().getColumn(3).setMaxWidth(70);
	lTable.getColumnModel().getColumn(0).setMaxWidth(20);
	rTable.getColumnModel().getColumn(0).setMaxWidth(20);
	lTable.getColumnModel().getColumn(0).setResizable(false);
	rTable.getColumnModel().getColumn(0).setResizable(false);
	lTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	rTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void setUpToolBars() {
	JButton lBackButton = new JButton();
	JButton rBackButton = new JButton();
	lBackButton.setActionCommand("lb");
	rBackButton.setActionCommand("rb");
	lBackButton.addActionListener(control);
	rBackButton.addActionListener(control);
	if (new File("./icones/go_back.png").exists()) {
	    ImageIcon icon = new ImageIcon("./icones/go_back.png");
	    lBackButton.setIcon(icon);
	    rBackButton.setIcon(icon);
	} else {
	    lBackButton.setText("Back");
	    rBackButton.setText("Back");
	}

	JButton lNewFileButton = new JButton();
	JButton rNewFileButton = new JButton();
	lNewFileButton.setActionCommand("lnfile");
	rNewFileButton.setActionCommand("rnfile");
	lNewFileButton.addActionListener(control);
	rNewFileButton.addActionListener(control);
	if (new File("./icones/new_file.png").exists()) {
	    ImageIcon icon = new ImageIcon("./icones/new_file.png");
	    lNewFileButton.setIcon(icon);
	    rNewFileButton.setIcon(icon);
	} else {
	    lNewFileButton.setText("New File");
	    rNewFileButton.setText("New File");
	}

	JButton lNewFolderButton = new JButton();
	JButton rNewFolderButton = new JButton();
	lNewFolderButton.setActionCommand("lnfolder");
	rNewFolderButton.setActionCommand("rnfolder");
	lNewFolderButton.addActionListener(control);
	rNewFolderButton.addActionListener(control);
	if (new File("./icones/new_folder.png").exists()) {
	    ImageIcon icon = new ImageIcon("./icones/new_folder.png");
	    lNewFolderButton.setIcon(icon);
	    rNewFolderButton.setIcon(icon);
	} else {
	    lNewFolderButton.setText("New Folder");
	    rNewFolderButton.setText("New Folder");
	}

	JButton lDeleteButton = new JButton();
	JButton rDeleteButton = new JButton();
	lDeleteButton.setActionCommand("ldel");
	rDeleteButton.setActionCommand("rdel");
	lDeleteButton.addActionListener(control);
	rDeleteButton.addActionListener(control);
	if (new File("./icones/delete.png").exists()) {
	    ImageIcon icon = new ImageIcon("./icones/delete.png");
	    lDeleteButton.setIcon(icon);
	    rDeleteButton.setIcon(icon);
	} else {
	    lDeleteButton.setText("Delete");
	    rDeleteButton.setText("Delete");
	}

	JButton lCutButton = new JButton();
	JButton rCutButton = new JButton();
	lCutButton.setActionCommand("lcut");
	rCutButton.setActionCommand("rcut");
	lCutButton.addActionListener(control);
	rCutButton.addActionListener(control);
	if (new File("./icones/cut.png").exists()) {
	    ImageIcon icon = new ImageIcon("./icones/cut.png");
	    lCutButton.setIcon(icon);
	    rCutButton.setIcon(icon);
	} else {
	    lCutButton.setText("Cut");
	    rCutButton.setText("Cut");
	}

	JButton lCopyButton = new JButton();
	JButton rCopyButton = new JButton();
	lCopyButton.setActionCommand("lcopy");
	rCopyButton.setActionCommand("rcopy");
	lCopyButton.addActionListener(control);
	rCopyButton.addActionListener(control);
	if (new File("./icones/copy.png").exists()) {
	    ImageIcon icon = new ImageIcon("./icones/copy.png");
	    lCopyButton.setIcon(icon);
	    rCopyButton.setIcon(icon);
	} else {
	    lCopyButton.setText("Copy");
	    rCopyButton.setText("Copy");
	}

	JButton lPasteButton = new JButton();
	JButton rPasteButton = new JButton();
	lPasteButton.setActionCommand("lpaste");
	rPasteButton.setActionCommand("rpaste");
	lPasteButton.addActionListener(control);
	rPasteButton.addActionListener(control);
	if (new File("./icones/paste.png").exists()) {
	    ImageIcon icon = new ImageIcon("./icones/paste.png");
	    lPasteButton.setIcon(icon);
	    rPasteButton.setIcon(icon);
	} else {
	    lPasteButton.setText("Paste");
	    rPasteButton.setText("Paste");
	}

	JButton lRenameButton = new JButton();
	JButton rRenameButton = new JButton();
	lRenameButton.setActionCommand("lren");
	rRenameButton.setActionCommand("rren");
	lRenameButton.addActionListener(control);
	rRenameButton.addActionListener(control);
	if (new File("./icones/rename.png").exists()) {
	    ImageIcon icon = new ImageIcon("./icones/rename.png");
	    lRenameButton.setIcon(icon);
	    rRenameButton.setIcon(icon);
	} else {
	    lRenameButton.setText("Rename");
	    rRenameButton.setText("Rename");
	}

	JButton lPropertiesButton = new JButton();
	JButton rPropertiesButton = new JButton();
	lPropertiesButton.setActionCommand("lprop");
	rPropertiesButton.setActionCommand("rprop");
	lPropertiesButton.addActionListener(control);
	rPropertiesButton.addActionListener(control);
	if (new File("./icones/properties.png").exists()) {
	    ImageIcon icon = new ImageIcon("./icones/properties.png");
	    lPropertiesButton.setIcon(icon);
	    rPropertiesButton.setIcon(icon);
	} else {
	    lPropertiesButton.setText("Properties");
	    rPropertiesButton.setText("Properties");
	}

	lToolBar.add(lBackButton);
	rToolBar.add(rBackButton);
	lToolBar.addSeparator();
	rToolBar.addSeparator();
	lToolBar.add(lNewFileButton);
	rToolBar.add(rNewFileButton);
	lToolBar.add(lNewFolderButton);
	rToolBar.add(rNewFolderButton);
	lToolBar.add(lDeleteButton);
	rToolBar.add(rDeleteButton);
	lToolBar.addSeparator();
	rToolBar.addSeparator();
	lToolBar.add(lCutButton);
	rToolBar.add(rCutButton);
	lToolBar.add(lCopyButton);
	rToolBar.add(rCopyButton);
	lToolBar.add(lPasteButton);
	rToolBar.add(rPasteButton);
	lToolBar.addSeparator();
	rToolBar.addSeparator();
	lToolBar.add(lRenameButton);
	rToolBar.add(rRenameButton);
	lToolBar.add(lPropertiesButton);
	rToolBar.add(rPropertiesButton);
    }

    private void setUpComposition() {
	setUpToolBars();

	GridBagLayout mainLayout = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	setLayout(mainLayout);

	JScrollPane ltp = new JScrollPane(lTable);
	JScrollPane rtp = new JScrollPane(rTable);

	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.weightx = 0.0;
	gbc.gridwidth = 3;
	gbc.gridx = 0;
	gbc.gridy = 0;
	mainLayout.setConstraints(lToolBar, gbc);

	gbc.gridx = 3;
	gbc.gridy = 0;
	mainLayout.setConstraints(rToolBar, gbc);

	gbc.gridwidth = 1;
	gbc.insets = new Insets(5, 5, 5, 5);
	gbc.gridx = 0;
	gbc.gridy = 1;
	mainLayout.setConstraints(lPathLabel, gbc);

	gbc.gridx = 3;
	gbc.gridy = 1;
	mainLayout.setConstraints(rPathLabel, gbc);

	gbc.weightx = 1.0;
	gbc.gridx = 1;
	gbc.gridy = 1;
	gbc.insets = new Insets(5, 5, 5, 0);
	mainLayout.setConstraints(lComboBox, gbc);

	gbc.gridx = 4;
	gbc.gridy = 1;
	mainLayout.setConstraints(rComboBox, gbc);

	gbc.fill = GridBagConstraints.NONE;
	gbc.weightx = 0.0;
	gbc.gridx = 2;
	gbc.gridy = 1;
	gbc.insets = new Insets(5, 0, 5, 5);
	mainLayout.setConstraints(lSetPathButton, gbc);

	gbc.gridx = 5;
	gbc.gridy = 1;
	mainLayout.setConstraints(rSetPathButton, gbc);

	gbc.gridx = 0;
	gbc.gridy = 2;
	gbc.gridwidth = 3;
	gbc.weighty = 1.0;
	gbc.weightx = 1.0;
	gbc.insets = new Insets(5, 5, 5, 5);
	gbc.fill = GridBagConstraints.BOTH;
	mainLayout.setConstraints(ltp, gbc);

	gbc.gridx = 3;
	gbc.gridy = 2;
	mainLayout.setConstraints(rtp, gbc);

	add(lPathLabel);
	add(rPathLabel);
	add(lComboBox);
	add(rComboBox);
	add(lSetPathButton);
	add(rSetPathButton);
	add(ltp);
	add(rtp);
	add(lToolBar);
	add(rToolBar);
	setMinimumSize(getPreferredSize());
    }
}
