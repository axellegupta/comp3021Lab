package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import base.Note;
import base.NoteBook;
import base.TextNote;

public class JUnitTest {

	@Test
	public void testSearchNote() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("Note1", "Java", "comp3021");
		nb.createTextNote("Note2", "Assignment", "due on 2016-10-16");
		nb.createTextNote("Note3", "lab","need to attend weekly");
		nb.createTextNote("Note4", "lab4","testing");
		List<Note> notes = nb.searchNotes("java or DUE or testing");
		System.out.println(notes);
		assertEquals("The size of the search results is not match", 3, notes.size(), 0.0);
		HashSet<String> titles = new HashSet<String>();
		for (Note note : notes) {
			titles.add(note.getTitle());
		}
		HashSet<String> expectedOutputs = new HashSet<String>();
		expectedOutputs.add("Java");
		expectedOutputs.add("Assignment");
		expectedOutputs.add("lab4");
		assertEquals("The search results is not match", expectedOutputs, titles);
	}
	
	@Test
	public void testCountLetters() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("Note1", "JjJj", "comp3021qqq");
		List<Note> notes = nb.searchNotes("");
		
		TextNote TestNote = (TextNote) notes.get(0);
		
		Character exp1 = 'j';
		Character exp2 = 'J';
		boolean exp = false;
		Character actual = TestNote.countLetters();
		System.out.println("Currently, the output of countLetters is "+ actual + " because j and J are treated as different characters. \nIn total, there are 3 q's, 2 j's and 2J's. If there was no bug, the number of combined j and J's would be 4 and thus, the output would not be q. ");
		
		if(exp1.equals(actual) ||exp2.equals(actual)) {exp=true;}
		assertEquals("Since j and J should be treated as the same character", exp,true);
	}
}
