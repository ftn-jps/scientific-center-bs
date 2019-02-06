package ftnjps.scientificcenter.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
public class FileUtils {

	@Value("${documents.path}")
	private String documentsPath;

	public String readFromFile(String filePath) {
		String result = "";
		File file = new File(documentsPath + filePath);
		try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {

			byte fileData[] = new byte[(int) file.length()];
			input.read(fileData);
			result = Base64Utils.encodeToString(fileData);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void writeToFile(String message, String filePath) {
		try {
			File newFile = new File(documentsPath + filePath);
			if(newFile.exists())
				return;
			newFile.createNewFile();

			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(newFile));
			output.write(Base64Utils.decodeFromString(message));
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
