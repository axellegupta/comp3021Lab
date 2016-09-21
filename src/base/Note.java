package base;

import java.util.Collections;
import java.util.Date;

public class Note implements Comparable<Note>{
	private Date date;
	private String title;
	
	public Note(String title){
		this.title = title;
		date = new Date(System. currentTimeMillis ());
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public Date getDate(){
		return this.date;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Note other = (Note) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int compareTo(Note o) {
		int compare = o.getDate().compareTo(this.date);
		
		if (compare==0)
			return 0;
		else if(compare>0)
			return 1;
		else
			return -1;
	}
	
}
