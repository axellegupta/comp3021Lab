package base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Folder> folders;
	public NoteBook(){
		this.folders = new ArrayList<Folder>();
	}
	public NoteBook(String file){

		FileInputStream fis =null;
		ObjectInputStream in = null;
		try{
			fis=new FileInputStream(file);
			in=new ObjectInputStream(fis);
			NoteBook n =(NoteBook) in.readObject();
			in.close();
			this.folders = n.getFolders();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	private boolean insertNote(String folderName, Note note) { 
		Folder f = null; 
		boolean checkFolder = false;
		boolean checkNote = false;
		for (Folder f1 : folders) { 
			if (folderName.equals(f1.getName())){
				checkFolder= true;
				f = f1;
			}
		} 

		if (f == null && checkFolder == false) {
			f = new Folder(folderName);
			folders.add(f);
		} 
		else{
		}

		for (Note n : f.getNotes()) { 
			if(note.getTitle().equals(n.getTitle())){
				checkNote = true;
			}
		}

		if(checkNote){
			System.out.println("Creating note: " + note.getTitle()+" under folder " + folderName + " failed");
		}
		else{
			f.addNote(note);
		}
		return !checkNote;
	}


	public boolean createTextNote(String folderName, String title) { 
		TextNote note = new TextNote(title); 
		return insertNote(folderName, note); 
	}

	public boolean createTextNote(String folderName, String title, String content) { 
		TextNote note = new TextNote(title, content); 
		return insertNote(folderName, note); 
	}

	public boolean createImageNote(String folderName, String title) { 
		ImageNote note = new ImageNote(title); 
		return insertNote(folderName, note);  
	}

	public ArrayList<Folder> getFolders() { return folders; }

	public void sortFolders(){
		for (Folder f: folders){
			f.sortNotes();
		}
		Collections.sort(folders);
	}

	public List<Note> searchNotes(String Keywords){
		ArrayList<Note> newNotes = new ArrayList<Note>();
		for (Folder f: folders){
			for(Note n: f.searchNotes(Keywords)){
				newNotes.add(n);
			}
		}
		return newNotes;
	}

	public boolean save(String file){
		NoteBook object = new NoteBook();
		FileOutputStream fos = null;
		ObjectOutputStream out=null;
		try{
			fos=new FileOutputStream(file);
			out=new ObjectOutputStream(fos);
			out.writeObject(object);
			out.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}
