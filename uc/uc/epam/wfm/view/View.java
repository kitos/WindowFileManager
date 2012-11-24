package uc.epam.wfm.view;

import java.awt.Component;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class View {

    public static String sizeConverter(long size) {
	String prefixes[] = { "b", "Kb", "Mb", "Gb", "Tb" };
	float s = size;
	int i = 0;
	while (s >= 1024 && i < 5) {
	    s /= 1024;
	    i++;
	}
	return (size == 0) ? "" : String.format("%.1f %s", s, prefixes[i]);
    }

    public static String dateConverter(long date) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	return dateFormat.format(new Date(date));
    }

    public static void showIOError(Component parentComponent, String errorText) {
	JOptionPane.showMessageDialog(parentComponent, errorText,
		"Input/Output error!", JOptionPane.ERROR_MESSAGE);
    }

    public static void showDeletingError(Component parentComponent, File file) {
	JOptionPane.showMessageDialog(parentComponent, "Unable to delete: "
		+ file.getName(), "Error", JOptionPane.WARNING_MESSAGE);
    }

    public static boolean showConfirmationReplacingDialog(
	    Component parentComponent, String fileName) {
	return JOptionPane.showConfirmDialog(parentComponent,
		"The destination already has file with a name \"" + fileName
			+ "\".\nReplace it?", "Replace or skip",
		JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION;
    }

    public static boolean showConfirmationDeletion(Component parentComponent,
	    File file) {
	return JOptionPane
		.showConfirmDialog(
			parentComponent,
			"Do you really want to delete "
				+ (file.isFile() ? "file" : "folder")
				+ ":\n"
				+ file.getAbsolutePath()
				+ ((file.isDirectory() && file.list().length != 0) ? "\n and all it's files"
					: "") + "?", "Deletion",
			JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static String showRenameDialog(Component parentComponent, String name) {
	return JOptionPane.showInputDialog(parentComponent, "Enter new name",
		"Rename", JOptionPane.QUESTION_MESSAGE, null, null, name)
		.toString();
    }

    public static String showNewFolderDialog(Component parentComponent) {
	return JOptionPane.showInputDialog(parentComponent,
		"Enter folder name: ", "Create folder",
		JOptionPane.QUESTION_MESSAGE);
    }

    public static void showCreationFolderError(Component parentComponent,
	    String folderName) {
	JOptionPane.showMessageDialog(parentComponent,
		"Unable to create folder: " + folderName, "Error",
		JOptionPane.ERROR_MESSAGE);
    }

    public static String showNewFileDialog(Component parentComponent) {
	return JOptionPane.showInputDialog(parentComponent,
		"Enter file name: ", "Create file",
		JOptionPane.QUESTION_MESSAGE);
    }

    public static void showNewFileError(Component parentComponent,
	    String fileName) {
	JOptionPane.showMessageDialog(parentComponent,
		"Unable to create file: " + fileName, "Error",
		JOptionPane.ERROR_MESSAGE);
    }

    public static void showWrongPathError(Component parentComponent) {
	JOptionPane.showMessageDialog(parentComponent,
		"Wrong path! Please enter correct one.", "Warning",
		JOptionPane.WARNING_MESSAGE);
    }

    public static void showOpenFileError(String error) {
	JOptionPane.showMessageDialog(null, error, "Error",
		JOptionPane.ERROR_MESSAGE);
    }
}
