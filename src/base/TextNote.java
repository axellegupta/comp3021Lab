package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class TextNote extends Note implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public Character countLetters(){
		HashMap<Character,Integer> count = new HashMap<Character,Integer>();
		String a = this.getTitle() + this.getContent();
		int b = 0;
		Character r = ' ';
		for (int i = 0; i < a.length(); i++) {
			Character c = a.charAt(i);
			if (c <= 'Z' && c >= 'A' || c <= 'z' && c >= 'a') // checks if c is an alphabet
			{
				if (!count.containsKey(c)) { // if mappping for key c does not exist in hashmap count
					count.put(c, 1); // maps value 1 to key c
				} else {
					count.put(c, count.get(c) + 1); // increments value mapped to key c
				}
				if (count.get(c) > b) { // checks if value of c if greater that b, the temporary greatest value
					b = count.get(c); // updates b to that value
					r = c; // updates r to the first letter with max freq
				}
			}
		}
		return r;
	}


}
