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

	public List<List<String>> makeArray(String Keywords){
		String[] given = Keywords.split(" ");
		List<List<String>> listOfLists = new ArrayList<List<String>>();
		ArrayList<String> row = new ArrayList<String>();
		row.add(given[0]);
		listOfLists.add(row);

		for(int i=1, j=0; i<given.length;++i){

			if (i>3 && given[i-1].equalsIgnoreCase("or") && !given[i-3].equalsIgnoreCase("or"))
			{
				ArrayList<String> rowNew = new ArrayList<String>();
				rowNew.add(given[i]);
				listOfLists.add(rowNew);
				++j;
			}
			else if (given[i-1].equalsIgnoreCase("or")){
				listOfLists.get(j).add(given[i]);
			}
			else if (!given[i].equalsIgnoreCase("or"))
			{
				ArrayList<String> rowNew = new ArrayList<String>();
				rowNew.add(given[i]);
				listOfLists.add(rowNew);
				++j;
			}
			else{
				continue;
			}
		}

		return listOfLists;

	}

	public boolean decideAddition(Note n, String Keywords){
		List<List<String>> given = makeArray(Keywords);

		for(List<String> list: given){
			boolean temp = false;

			for(int i=0; i<list.size();++i){

				if(!temp){
					if(n instanceof TextNote){
						temp= (((TextNote) n).getContent().toLowerCase().contains(list.get(i).toLowerCase())||n.getTitle().toLowerCase().contains(list.get(i).toLowerCase()));
					}
					else temp = n.getTitle().toLowerCase().contains(list.get(i).toLowerCase());
				}
			}

			if (!temp) {return false;}
		}
		return true;
	}

	public List<Note> searchNotes(String Keywords){

		ArrayList<Note> newNotes = new ArrayList<Note>();
		boolean add = false;

		for (Note n: notes){
			add = decideAddition(n, Keywords);
			if(add==true){newNotes.add(n);}
		}
		return newNotes;

	}
	
	public boolean removeNotes(String title) {
		boolean remove = false;
		for(Note n: this.notes){
			if(n.getTitle().equals(title)){
				notes.remove(n);
				remove=true;
			}
		}
		return remove;
	}
}
