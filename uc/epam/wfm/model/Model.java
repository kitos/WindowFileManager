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

    public static boolean createFile(String filePath) {
	try {
	    return (new File(filePath).createNewFile());
	} catch (IOException e) {
	    return false;
	}
    }

    public static boolean createFolder(String foldePath) {
	return (new File(foldePath).mkdir());
    }

    public static boolean copyItemTo(String file, String path) {
	try {
	    Files.copy(new File(file).toPath(), new File(path).toPath(),
		    StandardCopyOption.REPLACE_EXISTING);
	    return true;
	} catch (IOException e) {
	    return false;
	}
    }
}
