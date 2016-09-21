package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook {
	private ArrayList<Folder> folders;
	public NoteBook(){
		this.folders = new ArrayList<Folder>();
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
}
