package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>{
	
	private ArrayList<Note> notes; 
	private String name;
	
	public Folder(String name) { 
		this.name = name;
		notes = new ArrayList<Note>();
		} 
	
	public void addNote(Note note) {notes.add(note);}
	
	public String getName(){ return this.name; } 
	public ArrayList<Note> getNotes() { return notes; }
	public String toString(){
		int nText = 0; 
		int nImage = 0; 
		// TODO 
		
		for(Note note : notes){
			if (note instanceof TextNote){
				nText++;
			}
			else if (note instanceof ImageNote){
				nImage++;
			}
		};
		return name + ":" + nText + ":" + nImage;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Folder other = (Folder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Folder o) {
		if (this.name.length()==o.getName().length())
			return 0;
		else if(this.name.length()>o.getName().length())
			return 1;
		else
			return -1;
	}
	
	public void sortNotes(){
		Collections.sort(notes);
	}
	
	public List<Note> searchNotes(String Keywords){
		
		ArrayList<Note> newNotes = new ArrayList<Note>();
		String[] given = Keywords.split(" ");
		String[] s;
		s[0] = given[0];		
		
		for(int i = 0 ; i<given.length; ++i){
			if (given[i].equalsIgnoreCase("or"))
				s[i] = " || ";
			else
				s[i] = "&& ";			
		}
			
			for (Note n: notes){
				if(n.getTitle().toLowerCase().contains(s.toLowerCase())){
					newNotes.add(n);
				}
				else if(n instanceof TextNote){
					if (((TextNote) n).getContent().toLowerCase().contains(s.toLowerCase())){
						newNotes.add(n);
					}
					
				}
			}
		return newNotes;
		
	}
}
