package ui;

import java.util.ArrayList;

import java.util.List;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert; 
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	final TextArea textAreaNote = new TextArea("");

	final ListView<String> titleslistView = new ListView<String>();

	final ComboBox<String> foldersComboBox = new ComboBox<String>();

	NoteBook noteBook = null;

	String currentFolder = "";

	Folder curFolder = new Folder("");
	
	TextNote curNote = new TextNote("");

	String currentSearch = "";

	Stage stage;

	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;		
		loadNoteBook();
		BorderPane border = new BorderPane();

		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("Default NB");
		stage.show();
	}


	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load");
		buttonLoad.setPrefSize(100, 20);
		buttonLoad.setDisable(false);

		buttonLoad.setOnAction(new EventHandler<ActionEvent>() {  

			@Override public void handle(ActionEvent event) { 

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please choose a file that contains an NB Option");
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					loadNoteBook(file);
					stage.setTitle(file.getName());
					textAreaNote.setText("");

				}
			}
		});

		Button buttonSave = new Button("Save");
		buttonSave.setPrefSize(100, 20);
		buttonSave.setDisable(false);
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {  

			@Override public void handle(ActionEvent event) { 

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save File");
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showSaveDialog(stage);
				if (file != null) {
					try{
						noteBook.save(file.getAbsolutePath());
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Successfully saved");
						alert.setContentText("You file has been saved to file "
								+ file.getName());
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK) {
								System.out.println("Pressed OK.");
							}
						});
					}
					catch(Exception e){
						throw e;
					}

				}
			}
		});

		TextField textField = new TextField();

		Button buttonSearch = new Button("Search"); 
		buttonSearch.setOnAction(new EventHandler<ActionEvent>() {  
			@Override public void handle(ActionEvent event) { 
				currentSearch = textField.getText();
				for(Folder f: noteBook.getFolders()){
					if(f.getName().equals(currentFolder)){
						curFolder = f;
					}
				}
				updateListView(curFolder, true);
			}
		});

		Button buttonClear = new Button("Search Clear"); 
		buttonClear.setOnAction(new EventHandler<ActionEvent>() {  
			@Override public void handle(ActionEvent event) { 
				currentSearch = "";
				for(Folder f: noteBook.getFolders()){
					if(f.getName().equals(currentFolder)){
						curFolder = f;
					}
				}
				updateListView(curFolder, true);
			}
		});

		hbox.getChildren().addAll(buttonLoad, buttonSave);
		hbox.getChildren().add(new Label("Search: ")); 
		hbox.getChildren().add(textField); 
		hbox.getChildren().add(buttonSearch); 
		hbox.getChildren().add(buttonClear); 

		return hbox;
	}
	
	private HBox anotherHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		ImageView saveView = new ImageView(new Image(new File("save.png").toURI().toString()));
		saveView.setFitHeight(18);
		saveView.setFitWidth(18);
		saveView.setPreserveRatio(true);
		Button saveNote = new Button("Save Note");
		
		ImageView delView = new ImageView(new Image(new File("file:///Users/axellegupta/Git/comp3021Lab/src/ui/delete.png").toURI().toString()));
		delView.setFitHeight(18);
		delView.setFitWidth(18);
		delView.setPreserveRatio(true);
		Button delNote = new Button("Delete Note");
		
		hbox.getChildren().addAll(saveView,saveNote, delView, delNote);
		return hbox;
	}

		

	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes
		for(Folder f: noteBook.getFolders()){
			foldersComboBox.getItems().addAll(f.getName());
		}
		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				currentFolder = t1.toString();
				// this contains the name of the folder selected
				// TODO update listview
				for (Folder f: noteBook.getFolders()){
					if(f.getName().equals(currentFolder)){
						curFolder = f;
					}
				}
				updateListView(curFolder, false);

			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				TextNote newNote = new TextNote(title);

				for (Note tNote: curFolder.getNotes()){
					if(tNote.getTitle().equals(title)){
						if (tNote instanceof TextNote){
							newNote = (TextNote) tNote;
						}
					}
				}


				String content = newNote.getContent();
				textAreaNote.setText(content);

			}
		});
		Button addFolder = new Button("Add Folder");

		addFolder.setOnAction(new EventHandler<ActionEvent>() {  
			@Override public void handle(ActionEvent event) { 

				TextInputDialog dialog = new TextInputDialog("Add a Folder");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a new folder for your notebook:");
				dialog.setContentText("Please enter the name you want to create:");
				//Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					Boolean exists = false;
					for(Folder f: noteBook.getFolders()){
						if (f.getName().equals(result.get()))
						{exists = true;}
					}

					if(exists){
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("FAILED");
						alert.setContentText(result.get()+" : Already Exists");
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK) {
								System.out.println("Failed");
							}
						});
					}
					else{
						
						if (result.get().equals("")){
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("EMPTY");
							alert.setContentText("Empty string is illegal. Enter some text.");
							alert.showAndWait().ifPresent(rs -> {
								if (rs == ButtonType.OK) {
									System.out.println("null string");
								}
							});
						}
						else{
							noteBook.addFolder(result.get());
							foldersComboBox.getItems().addAll(result.get());
						}
					}
				}
				else{
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("EMPTY");
					alert.setContentText("Empty string is illegal. Enter some text.");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("null string");
						}
					});

				}
			}

		});


		Button addNote = new Button("Add Note");
		addNote.setOnAction(new EventHandler<ActionEvent>() {  
			@Override public void handle(ActionEvent event) { 

				TextInputDialog dialog = new TextInputDialog("Add a Note");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a new Note for your folder:");
				dialog.setContentText("Please enter the name you want to create:");
				//Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					Boolean exists = false;
					for(Note n: curFolder.getNotes()){
						if (n.getTitle().equals(result.get()))
						{exists = true;}
					}

					if(exists){
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("FAILED");
						alert.setContentText(result.get()+" : Already Exists");
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK) {
								System.out.println("Failed");
							}
						});
					}
					else{
						
						if (result.get().equals("")){
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("EMPTY");
							alert.setContentText("Empty string is illegal. Enter some text.");
							alert.showAndWait().ifPresent(rs -> {
								if (rs == ButtonType.OK) {
									System.out.println("null string");
								}
							});
						}
						else{
							noteBook.createTextNote(currentFolder, result.get(), "");
							updateListView(curFolder, true);
						}
					}
				}
				else{
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("SURE?");
					alert.setContentText("Wanna exit?");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("cancelled creation");
						}
					});

				}
			}

		});

		vbox.getChildren().add(new Label("Choose folder: "));
		vbox.getChildren().add(foldersComboBox);
		vbox.getChildren().add(addFolder);
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(addNote);

		return vbox;
	}

	private void updateListView(Folder fold, boolean search) {
		ArrayList<String> list = new ArrayList<String>();
		List<Note> notes = fold.searchNotes(currentSearch);


		for(Note t: notes){
			if(t instanceof TextNote){
				list.add(t.getTitle());}
		}

		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		if (!search || (search && notes.size()==0)){
			textAreaNote.setText("");}
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		HBox newHBox = anotherHBox();
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		
		grid.add(newHBox, 0, 0);
		grid.add(textAreaNote, 0, 1);

		return grid;
	}

	private void loadNoteBook(File file) {
		String nameOfFile = file.getAbsolutePath();
		NoteBook nb = new NoteBook(nameOfFile);
		noteBook = nb;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called â€œthe most shocking play in NFL historyâ€� and the Washington Redskins dubbed the â€œThrowback Specialâ€�: the November 1985 play in which the Redskinsâ€™ Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Awardâ€“winning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everythingâ€”until it wasnâ€™t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliantâ€”a part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwetherâ€™s Daddy Was a Number Runner and Dorothy Allisonâ€™s Bastard Out of Carolina, Jacqueline Woodsonâ€™s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthoodâ€”the promise and peril of growing upâ€”and exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}

}
