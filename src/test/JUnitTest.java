package test;

import static org.junit.Assert.*;
import org.junit.runners.JUnit4;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import base.Note;
import base.NoteBook;
import base.TextNote;
@RunWith(JUnit4.class)
public class JUnitTest {

	@Test
	public void testSearchNote() {
		
		System.out.println("\nSearchNote PASSES TEST:");
		NoteBook nb = new NoteBook();
		nb.createTextNote("Note1", "Java", "comp3021");
		nb.createTextNote("Note2", "Assignment", "due on 2016-10-16");
		nb.createTextNote("Note3", "lab","need to attend weekly");
		nb.createTextNote("Note4", "lab4","testing");
		List<Note> notes = nb.searchNotes("java or DUE or testing");
		System.out.println("\tNo. of notes is "+notes.size());
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
		
		TextNote TestNote = new TextNote("Jav", "comp3021");
		
		Character exp = 'J';
		Character actual = TestNote.countLetters();
		System.out.println("countLetters RESOLVED-BUG:");
		System.out.println("\tAfter bug resolution, the output of countLetters is \""+ actual+"\" instead of \" \"");
		
		assertEquals(exp,actual);
		
		TextNote TestNote1 = new TextNote("Java", "comp");
		TextNote TestNote2 = new TextNote("Java", "comp30211");
		TextNote TestNote3 = new TextNote("Javav", "comp3021");
		exp = 'a';
		assertEquals(exp,TestNote1.countLetters());
		assertEquals(exp,TestNote2.countLetters());
		assertEquals(exp,TestNote3.countLetters());
	}
}
