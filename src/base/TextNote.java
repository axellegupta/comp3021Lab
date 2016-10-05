package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TextNote extends Note {
	private String content;
	public TextNote(String title) {
		super(title);
	}
	public TextNote(String title, String content) {
		super(title);
		this.content=content;
		
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	private String getTextFromFile(String absolutePath){
		StringBuffer result = new StringBuffer("");
		File file = new File(absolutePath);
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);

			int content;
			while ((content = fis.read()) != -1) {
				// convert to char and display it
				result.append((char) content);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return result.toString();
	}
	
	public void exportTextToFile(String pathFolder){
		String title = this.getTitle().replaceAll(" ","_");
		File file = new File(pathFolder + title + ".txt");
		try{
			FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(this.content);
			out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
