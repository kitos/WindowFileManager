package uc.epam.wfm.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import uc.epam.wfm.model.Model;
import uc.epam.wfm.model.TableModel;

public class PropertiesDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    public PropertiesDialog(File file) {
	super();

	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	setLayout(gbl);

	FileSystemView view = FileSystemView.getFileSystemView();
	Icon icon = view.getSystemIcon(file);
	JLabel iconLabel = new JLabel(icon);
	JTextField fileName = new JTextField(file.getName());
	fileName.setEditable(false);
	JLabel typeLabel = new JLabel("Type:");
	JLabel fileType = new JLabel(
		new JFileChooser().getTypeDescription(file));
	JLabel pathLabel = new JLabel("Path:");
	JLabel filePath = new JLabel(file.getAbsolutePath());
	JLabel sizeLabel = new JLabel("Size:");
	JLabel fileSize = new JLabel(TableModel.sizeConverter(Model
		.itemLength(file)));
	JLabel containsLabel = new JLabel("Contains:");
	int filesCount = 0;
	int folderCount = 0;

	if (file.isDirectory()) {
	    for (File f : file.listFiles()) {
		if (f.isFile()) {
		    filesCount++;
		} else {
		    folderCount++;
		}
	    }
	}
	JLabel fileContent = new JLabel(filesCount + " files and "
		+ folderCount + " folders");
	JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);

	gbc.weightx = 0.5;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.insets = new Insets(5, 5, 5, 5);
	gbc.gridy = 0;
	gbc.gridx = 0;
	gbl.setConstraints(iconLabel, gbc);

	gbc.gridy = 0;
	gbc.gridx = 1;
	gbl.setConstraints(fileName, gbc);

	gbc.gridy = 1;
	gbc.gridx = 0;
	gbc.gridwidth = 2;
	gbl.setConstraints(separator, gbc);

	gbc.gridwidth = 1;
	gbc.gridy = 2;
	gbc.gridx = 0;
	gbl.setConstraints(typeLabel, gbc);

	gbc.gridy = 2;
	gbc.gridx = 1;
	gbl.setConstraints(fileType, gbc);

	gbc.gridy = 3;
	gbc.gridx = 0;
	gbl.setConstraints(pathLabel, gbc);

	gbc.gridy = 3;
	gbc.gridx = 1;
	gbl.setConstraints(filePath, gbc);

	gbc.gridy = 4;
	gbc.gridx = 0;
	gbl.setConstraints(sizeLabel, gbc);

	gbc.gridy = 4;
	gbc.gridx = 1;
	gbl.setConstraints(fileSize, gbc);

	gbc.gridy = 5;
	gbc.gridx = 0;
	gbl.setConstraints(containsLabel, gbc);

	gbc.gridy = 5;
	gbc.gridx = 1;
	gbl.setConstraints(fileContent, gbc);

	add(iconLabel);
	add(fileName);
	add(separator);
	add(typeLabel);
	add(fileType);
	add(pathLabel);
	add(filePath);
	add(sizeLabel);
	add(fileSize);
	if (file.isDirectory()) {
	    add(containsLabel);
	    add(fileContent);
	}

	pack();
	setResizable(false);
	setTitle("Properties: " + file.getName());
	setLocationRelativeTo(null);
	setModal(true);
	setVisible(true);
    }
}
