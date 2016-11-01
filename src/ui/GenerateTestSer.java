package ui;
import base.NoteBook;

public class GenerateTestSer {
	
	public static void main(String[] args) {
		
		NoteBook nb = new NoteBook();
		
		nb.createTextNote("JAVA", "CourseInfo", "Intro, DS, Abstract&Interface, GUI, EventHandling, ExceptionHandling, LambdaExpressions, MultiThreading");
		nb.createTextNote("JAVA", "Settings", "ClassPath, Environment Variables");
		
		nb.createTextNote("DataMining", "Midterm", "Preprocessing & Classififying");
		nb.createTextNote("DataMining", "Final","Perceptrons, svm");
		
		nb.createTextNote("FYP", "Brian Mak","Doing Project");
		nb.createTextNote("FYP", "Grace Wong","Presenting Project");
		
	    nb.save("example.ser");
	}
}
