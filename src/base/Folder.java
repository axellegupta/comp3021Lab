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
		String[] s = new String[given.length+3];
		String finals = "";
		int j= 1;
		s[0]=given[0];
		for(int i = 1 ; i<given.length; ++i){
			if (given[i].equalsIgnoreCase("or")){
				s[j-1] = "("+s[j-1];
				s[j] = " || ";
				j=j+1;}
			else if(given[i-1].equalsIgnoreCase("or")){
				s[j] = given[i];
				s[j+1]=" ) ";	
				j=j+2;}				
			else {
				s[j] = " && ";
				s[j+1]= given[i];	
				j=j+2;}
		}
		
		for(String s0: s){
			finals.concat(s0);
		}
		
			
			for (Note n: notes){
				if(n.getTitle().toLowerCase().contains(finals.toLowerCase())){
					newNotes.add(n);
				}
				else if(n instanceof TextNote){
					if (((TextNote) n).getContent().toLowerCase().contains(finals.toLowerCase())){
						newNotes.add(n);
					}
					
				}
			}
		return newNotes;
		
	}
}
