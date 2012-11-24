/**
 * 
 */
package uc.epam.wfm.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import uc.epam.wfm.control.Controller;
import uc.epam.wfm.control.TableMouseAdapter;
import uc.epam.wfm.model.TableModel;

/**
 * @author Никита
 * 
 */
public class Window extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private Controller lControl;
    private Controller rControl;
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
    private JToolBar cToolBar;
    private JRadioButton copyRadioButton;
    private JRadioButton moveRadioButton;
    private Vector<String> paths = new Vector<String>(5);

    public Window() {
	initComponents();
	setUpComposition();
	lControl.initComponents(this, lComboBox, lTable, lTableModel,
		rTableModel, paths);
	rControl.initComponents(this, rComboBox, rTable, rTableModel,
		lTableModel, paths);
	setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public boolean mustCopy() {
	return copyRadioButton.isSelected();
    }

    private void initComponents() {
	lControl = new Controller();
	rControl = new Controller();
	lTableModel = new TableModel();
	rTableModel = new TableModel();

	lToolBar = new JToolBar();
	lToolBar.setFloatable(false);
	rToolBar = new JToolBar();
	rToolBar.setFloatable(false);
	cToolBar = new JToolBar();
	cToolBar.setFloatable(false);

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
	lComboBox.setActionCommand("upt");
	rComboBox.setActionCommand("upt");
	lComboBox.addActionListener(lControl);
	rComboBox.addActionListener(rControl);
	lSetPathButton = new JButton();
	rSetPathButton = new JButton();
	ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/set_dir.png")));
	lSetPathButton.setIcon(icon);
	rSetPathButton.setIcon(icon);
	lSetPathButton.setActionCommand("upt");
	rSetPathButton.setActionCommand("upt");
	lSetPathButton.addActionListener(lControl);
	rSetPathButton.addActionListener(rControl);

	lTable = new JTable(lTableModel);
	rTable = new JTable(rTableModel);
	// lTable.setAutoCreateRowSorter(true);
	// rTable.setAutoCreateRowSorter(true);
	lTable.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
	rTable.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
	lTable.setRowHeight(18);
	rTable.setRowHeight(18);
	lTable.setShowGrid(false);
	rTable.setShowGrid(false);
	lTable.addMouseListener(new TableMouseAdapter(lControl));
	rTable.addMouseListener(new TableMouseAdapter(rControl));

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
	ImageIcon icon;
	JButton lBackButton = new JButton();
	JButton rBackButton = new JButton();
	lBackButton.setActionCommand("back");
	rBackButton.setActionCommand("back");
	lBackButton.addActionListener(lControl);
	rBackButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/go_back.png")));
	lBackButton.setIcon(icon);
	rBackButton.setIcon(icon);

	JButton lNewFileButton = new JButton();
	JButton rNewFileButton = new JButton();
	lNewFileButton.setActionCommand("newfile");
	rNewFileButton.setActionCommand("newfile");
	lNewFileButton.addActionListener(lControl);
	rNewFileButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/new_file.png")));
	lNewFileButton.setIcon(icon);
	rNewFileButton.setIcon(icon);

	JButton lNewFolderButton = new JButton();
	JButton rNewFolderButton = new JButton();
	lNewFolderButton.setActionCommand("newfolder");
	rNewFolderButton.setActionCommand("newfolder");
	lNewFolderButton.addActionListener(lControl);
	rNewFolderButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/new_folder.png")));
	lNewFolderButton.setIcon(icon);
	rNewFolderButton.setIcon(icon);

	JButton lDeleteButton = new JButton();
	JButton rDeleteButton = new JButton();
	lDeleteButton.setActionCommand("del");
	rDeleteButton.setActionCommand("del");
	lDeleteButton.addActionListener(lControl);
	rDeleteButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/delete.png")));
	lDeleteButton.setIcon(icon);
	rDeleteButton.setIcon(icon);

	JButton lCutButton = new JButton();
	JButton rCutButton = new JButton();
	lCutButton.setActionCommand("cut");
	rCutButton.setActionCommand("cut");
	lCutButton.addActionListener(lControl);
	rCutButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/cut.png")));
	lCutButton.setIcon(icon);
	rCutButton.setIcon(icon);

	JButton lCopyButton = new JButton();
	JButton rCopyButton = new JButton();
	lCopyButton.setActionCommand("copy");
	rCopyButton.setActionCommand("copy");
	lCopyButton.addActionListener(lControl);
	rCopyButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/copy.png")));
	lCopyButton.setIcon(icon);
	rCopyButton.setIcon(icon);

	JButton lPasteButton = new JButton();
	JButton rPasteButton = new JButton();
	lPasteButton.setActionCommand("paste");
	rPasteButton.setActionCommand("paste");
	lPasteButton.addActionListener(lControl);
	rPasteButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/paste.png")));
	lPasteButton.setIcon(icon);
	rPasteButton.setIcon(icon);

	JButton lRenameButton = new JButton();
	JButton rRenameButton = new JButton();
	lRenameButton.setActionCommand("rename");
	rRenameButton.setActionCommand("rename");
	lRenameButton.addActionListener(lControl);
	rRenameButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/rename.png")));
	lRenameButton.setIcon(icon);
	rRenameButton.setIcon(icon);

	JButton lPropertiesButton = new JButton();
	JButton rPropertiesButton = new JButton();
	lPropertiesButton.setActionCommand("prop");
	rPropertiesButton.setActionCommand("prop");
	lPropertiesButton.addActionListener(lControl);
	rPropertiesButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/properties.png")));
	lPropertiesButton.setIcon(icon);
	rPropertiesButton.setIcon(icon);

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

    private void setUtCenterToolBar() {
	cToolBar.setOrientation(JToolBar.VERTICAL);
	ImageIcon icon;

	copyRadioButton = new JRadioButton();
	moveRadioButton = new JRadioButton();
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/mcopy.png")));
	copyRadioButton.setIcon(icon);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/mmove_dis.png")));
	moveRadioButton.setIcon(icon);
	copyRadioButton.setMnemonic(KeyEvent.VK_C);
	moveRadioButton.setMnemonic(KeyEvent.VK_M);
	copyRadioButton.setActionCommand("rbc");
	moveRadioButton.setActionCommand("rbc");
	copyRadioButton.addActionListener(this);
	moveRadioButton.addActionListener(this);
	copyRadioButton.setToolTipText("Copy");
	moveRadioButton.setToolTipText("Move");
	copyRadioButton.setSelected(true);

	ButtonGroup radioGrup = new ButtonGroup();
	radioGrup.add(copyRadioButton);
	radioGrup.add(moveRadioButton);

	JButton ctlButton = new JButton();
	JButton ctrButton = new JButton();
	ctlButton.setActionCommand("copyto");
	ctrButton.setActionCommand("copyto");
	ctlButton.addActionListener(lControl);
	ctrButton.addActionListener(rControl);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/copy_to_right.png")));
	ctlButton.setIcon(icon);
	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
		getClass().getResource("/src/copy_to_left.png")));
	ctrButton.setIcon(icon);

	cToolBar.add(copyRadioButton);
	cToolBar.add(moveRadioButton);
	cToolBar.addSeparator();
	cToolBar.add(ctlButton);
	cToolBar.add(ctrButton);
    }

    private void setUpComposition() {
	setUpToolBars();
	setUtCenterToolBar();

	GridBagLayout mainLayout = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	setLayout(mainLayout);

	JScrollPane ltp = new JScrollPane(lTable);
	JScrollPane rtp = new JScrollPane(rTable);
	ltp.getViewport().setBackground(lTable.getBackground());
	rtp.getViewport().setBackground(rTable.getBackground());

	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.weightx = 0.0;
	gbc.gridwidth = 3;
	gbc.gridx = 0;
	gbc.gridy = 0;
	mainLayout.setConstraints(lToolBar, gbc);

	gbc.gridx = 4;
	gbc.gridy = 0;
	mainLayout.setConstraints(rToolBar, gbc);

	gbc.gridwidth = 1;
	gbc.insets = new Insets(5, 5, 5, 5);
	gbc.gridx = 0;
	gbc.gridy = 1;
	mainLayout.setConstraints(lPathLabel, gbc);

	gbc.gridx = 4;
	gbc.gridy = 1;
	mainLayout.setConstraints(rPathLabel, gbc);

	gbc.weightx = 1.0;
	gbc.gridx = 1;
	gbc.gridy = 1;
	gbc.insets = new Insets(5, 5, 5, 0);
	mainLayout.setConstraints(lComboBox, gbc);

	gbc.gridx = 5;
	gbc.gridy = 1;
	mainLayout.setConstraints(rComboBox, gbc);

	gbc.fill = GridBagConstraints.NONE;
	gbc.weightx = 0.0;
	gbc.gridx = 2;
	gbc.gridy = 1;
	gbc.insets = new Insets(5, 0, 5, 5);
	mainLayout.setConstraints(lSetPathButton, gbc);

	gbc.gridx = 6;
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

	gbc.gridx = 4;
	gbc.gridy = 2;
	mainLayout.setConstraints(rtp, gbc);

	gbc.gridwidth = 1;
	gbc.weightx = 0.0;
	gbc.fill = GridBagConstraints.NONE;
	gbc.gridx = 3;
	gbc.gridy = 2;
	mainLayout.setConstraints(cToolBar, gbc);

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
	add(cToolBar);
	setMinimumSize(getPreferredSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("rbc")) {
	    ImageIcon icon;
	    if (copyRadioButton.isSelected()) {
		icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
			getClass().getResource("/src/mcopy.png")));
		copyRadioButton.setIcon(icon);
		icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
			getClass().getResource("/src/mmove_dis.png")));
		moveRadioButton.setIcon(icon);
	    } else {
		icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
			getClass().getResource("/src/mcopy_dis.png")));
		copyRadioButton.setIcon(icon);
		icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
			getClass().getResource("/src/mmove.png")));
		moveRadioButton.setIcon(icon);
	    }
	}
    }
}
