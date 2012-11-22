package uc.epam.wfm.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Model {

    public static boolean deleteItem(String path) {
	return deleteItem(new File(path));
    }

    public static boolean deleteItem(File file) {
	boolean result = false;
	if (file.isFile() || file.list().length == 0) {
	    result = file.delete();
	} else {
	    for (File f : file.listFiles()) {
		deleteItem(f);
	    }
	    result = deleteItem(file);
	}
	return result;
    }

    public static boolean createFile(String filePath) throws IOException {
	return (new File(filePath).createNewFile());
    }

    public static boolean createFolder(String foldePath) {
	return (new File(foldePath).mkdir());
    }

    public static void copyItemTo(String copiedFile, String targetPath)
	    throws IOException {
	if (!copiedFile.equals(targetPath)) {
	    File file = new File(copiedFile);
	    File dir = new File(targetPath + File.separator + file.getName());

	    if (file.isFile() || file.list().length == 0) {
		Files.copy(file.toPath(), dir.toPath(),
			StandardCopyOption.REPLACE_EXISTING);
	    } else {
		if (!dir.exists()) {
		    dir.mkdir();
		}
		for (File f : file.listFiles()) {
		    copyItemTo(f.getAbsolutePath(), dir.getAbsolutePath());
		}
	    }
	}
    }

    public static void moveItemTo(String copiedFile, String targetPath)
	    throws IOException {
	if (!copiedFile.equals(targetPath)) {
	    File file = new File(copiedFile);
	    File dir = new File(targetPath + File.separator + file.getName());

	    if (file.isFile() || file.list().length == 0) {
		Files.move(file.toPath(), dir.toPath(),
			StandardCopyOption.REPLACE_EXISTING);
	    } else {
		if (!dir.exists()) {
		    dir.mkdir();
		}
		for (File f : file.listFiles()) {
		    moveItemTo(f.getAbsolutePath(), dir.getAbsolutePath());
		}
		file.delete();
	    }
	}
    }

    public static boolean renameItem(String filePath, String newFileName) {
	return renameItem(new File(filePath), newFileName);
    }

    public static boolean renameItem(File file, String newFileName) {
	return file.renameTo(new File(file.getParent() + File.separator
		+ newFileName));
    }

    public static long itemLength(File file) {
	long result = 0;
	if (file.isFile()) {
	    result = file.length();
	} else {
	    if (file.listFiles() != null) {
		for (File fileObj : file.listFiles()) {
		    if (fileObj.isFile()) {
			result += fileObj.length();
		    } else {
			result += itemLength(fileObj);
		    }
		}
	    }
	}
	return result;
    }
}
