package base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

		return this.name.compareTo(o.getName());
	}

	public void sortNotes(){
		Collections.sort(notes);
	}

	public boolean searchNotesOld(Note n, String Keywords){
		String[] given = Keywords.split(" ");
		boolean add = false;
		if(n instanceof TextNote){
			add=(((TextNote) n).getContent().toLowerCase().contains(given[0].toLowerCase())||n.getTitle().toLowerCase().contains(given[0].toLowerCase()));
			}
		else add = n.getTitle().toLowerCase().contains(given[0].toLowerCase());
			
		for(int i=1; i<given.length;++i){
			
			if (given[i-1].equalsIgnoreCase("or")){
				if(add==true){continue;}
				else {
					if(n instanceof TextNote){
					add= add ||(((TextNote) n).getContent().toLowerCase().contains(given[i].toLowerCase())||n.getTitle().toLowerCase().contains(given[i].toLowerCase()));
					}
					else add = add || n.getTitle().toLowerCase().contains(given[i].toLowerCase());
					};
				}
		
			else if (!given[i].equalsIgnoreCase("or")){
				if(n instanceof TextNote){
					add= add &&(((TextNote) n).getContent().toLowerCase().contains(given[i].toLowerCase())||n.getTitle().toLowerCase().contains(given[i].toLowerCase()));
				}
				else add = add && n.getTitle().toLowerCase().contains(given[i].toLowerCase());

			}
			else{}
		}

		return add;

	}

	public List<Note> searchNotes(String Keywords){

		ArrayList<Note> newNotes = new ArrayList<Note>();
		boolean add = false;

		for (Note n: notes){
			add = searchNotesOld(n, Keywords);
			if(add==true){newNotes.add(n);}
		}
		return newNotes;

	}
}
