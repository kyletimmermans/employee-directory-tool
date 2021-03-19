/*
 	Kyle Timmermans
 	Employee Directory Tool
 	March 19th, 2021
*/

package application;
	
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


public class EmployeeDirectoryTool extends Application 
{
	Text showSelected;    // Global Variable to hold the string value
	char selectedText = '1';  // Global Variable to hold the actual string value
	String textPost;  // Global Variable to store the selected radio button for file
	
	@Override
	public void start(Stage primaryStage) 
	{
		
		fileIO employees = new fileIO();
		
		try 
		{	
			// Labels and text fields for first part
			Label firstName    = new Label("First Name : ");
			TextField firstNameField   = new TextField();
			Label lastName        = new Label("Last Name : ");
			TextField lastNameField   = new TextField();
			Label email      = new Label("Email : ");
			TextField emailField = new TextField();
			Label salary      = new Label("Salary : ");
			TextField salaryField = new TextField();
			Label dateOfBirth      = new Label("Date of Birth : ");
			TextField dateOfBirthField = new TextField();
			Label dateFormat = new Label("mm/dd/yyyy");
			
			// Column constraints to prevent resizing
			ColumnConstraints column2 = new ColumnConstraints(100,100,Double.MAX_VALUE);  // In actuality, is column 1
			column2.setHgrow(Priority.NEVER);
			 
			// Create ComboBox (dropdown)
			Label departmentLabel = new Label("Department");
			ComboBox dropdown = new ComboBox();  
			dropdown.setVisibleRowCount(7);  // Don't let dropdown drop below bottom of application
			dropdown.getItems().add("Human Resources");		// Populate ComboBox
			dropdown.getItems().add("Information Technology");
			dropdown.getItems().add("Developer");
			dropdown.getItems().add("Sales");		
			dropdown.getItems().add("General Counsel");
			dropdown.getItems().add("Translator");
			dropdown.getItems().add("Public Relations");
			dropdown.getItems().add("R&D");
			dropdown.getItems().add("Marketing");
			dropdown.getItems().add("Social Media");
			dropdown.getItems().add("Accounting & Finance");
			dropdown.getItems().add("Management");
			dropdown.getItems().add("Legal");
			dropdown.getItems().add("Janitorial");
			dropdown.getItems().add("International");
			dropdown.getItems().add("Operations");
			dropdown.getItems().add("Strategy");
			dropdown.getItems().add("Chief Officer");
			dropdown.getItems().add("Other");
			
			// Radio buttons
			Label availability = new Label("Availability: ");
			ToggleGroup radioGroup = new ToggleGroup();  // Only one is able to be selected
			RadioButton weekdays = new RadioButton("Weekdays");
	        RadioButton weekends = new RadioButton("Weekends");
	        RadioButton both = new RadioButton("Weekdays & Weekends");
	        weekdays.setToggleGroup(radioGroup);
	        weekends.setToggleGroup(radioGroup);
	        both.setToggleGroup(radioGroup);
	        
	        // Formatting so we can get larger numbers and spacing for GridPane
	     	Label emptyLabel1 = new Label("    "); 
	     	Label emptyLabel2 = new Label("    ");
	     	Label emptyLabel3 = new Label("    ");
	     	String separatorSpaces1 = new String(new char[24]).replace('\0', ' ');   // Several spaces string for saveClearSplit
	     	String separatorSpaces2 = new String(new char[26]).replace('\0', ' ');   // Several spaces string for clearExitSplit
	     	Label exitClearSplit = new Label(separatorSpaces1);   // Used to split Save and Exit Button
	     	Label clearExitSplit = new Label(separatorSpaces2);
	     	
	     	
			// Initialize alert box
			Alert a = new Alert(AlertType.NONE);
			
			// Global variable Label for what is selected, global so we can edit with thread
			showSelected = new Text("Selected: "); 
			
			// Save and exit buttons
			Button save = new Button("Save");
			save.setOnAction(new EventHandler<ActionEvent>()
	        {
	            @Override public void handle(ActionEvent e)
	            {
	            	// Save all values shown on screen
	            	employees.writeToFile("First Name: " + firstNameField.getText());
	            	employees.writeToFile("Last Name: " + lastNameField.getText());
	            	employees.writeToFile("Email: " + emailField.getText());
	            	employees.writeToFile("Salary: " + salaryField.getText());
	            	employees.writeToFile("Date of Birth: " + dateOfBirthField.getText());
	            	employees.writeToFile("Department: " + dropdown.getValue());
	            	employees.writeToFile("Availability: " + textPost);  // Private to global var to here
	            	a.setAlertType(AlertType.INFORMATION); 
	            	a.setContentText("Succesfully created employees.txt!");  // Show success alert popup
	            	a.show();
	            }
	        });
			
			
			Button clear = new Button("Clear Values");
			clear.setOnAction(new EventHandler<ActionEvent>()
	        {
	            @Override public void handle(ActionEvent e)
	            {
	            	firstNameField.clear();  // Clear Text Fields
	            	lastNameField.clear(); 
	            	emailField.clear(); 
	            	salaryField.clear(); 
	            	dateOfBirthField.clear(); 
	            	
	            	weekdays.setSelected(false);  // Clear Radio Button selections
	            	weekends.setSelected(false);
	            	both.setSelected(false);
	            	
	            	dropdown.setValue(null);  // Clear dropdown menu selection
	            	
	            	selectedText = '1';  // Clear "Selected: " text
	            	
	            }
	        });
			
			Button exit = new Button("Exit");
			exit.setOnAction(new EventHandler<ActionEvent>()
	        {
	            @Override public void handle(ActionEvent e)
	            {
	                primaryStage.close();   // Exit program window on click of 'Cancel' Button
	                System.exit(0);  // Close process
	            }
	        });
	        
	        
	        // Give radio buttons an action on click with a listener for each radio button
	        weekdays.selectedProperty().addListener(new ChangeListener<Boolean>() {  // Start listeners, essentially adding an action
	            @Override
	            public void changed(ObservableValue<? extends Boolean> obs, Boolean previousValue, Boolean currentValue) {
	                if (currentValue) {    // Basically, if clicked
	                	selectedText = '2';  // If specific radio button is selected
	                	RadioButton textPre = (RadioButton) radioGroup.getSelectedToggle();  // How to get only text from toggleGroup select
	                	textPost = textPre.getText();
	                }
	            }
	        });
	        weekends.selectedProperty().addListener(new ChangeListener<Boolean>() {
	            @Override
	            public void changed(ObservableValue<? extends Boolean> obs, Boolean previousValue, Boolean currentValue) {
	                if (currentValue) { 
	                	selectedText = '3';  // Selected text is a global variable so the threads can have access to it when changing the "Selected: " label
	                	RadioButton textPre = (RadioButton) radioGroup.getSelectedToggle();  // How to get only text from toggleGroup select
	                	textPost = textPre.getText();
	                }
	            }
	        });
	        both.selectedProperty().addListener(new ChangeListener<Boolean>() {
	            @Override
	            public void changed(ObservableValue<? extends Boolean> obs, Boolean previousValue, Boolean currentValue) {
	                if (currentValue) { 
	                    selectedText = '4';
	                    RadioButton textPre = (RadioButton) radioGroup.getSelectedToggle();  // How to get only text from toggleGroup select
	                	textPost = textPre.getText();
	                }
	            }
	        });
			
			
			// Adding elements into Grid Pane (root)
			GridPane root = new GridPane();
			root.getColumnConstraints().addAll(column2);  // Add in the column constraints defined earlier
			root.add(firstName, 0,1);          // Adding text fields and labels
			root.add(firstNameField, 1, 1);
			root.add(lastName, 0, 2);
			root.add(lastNameField, 1, 2);
			root.add(email, 0, 3);
			root.add(emailField, 1, 3);
			root.add(salary, 0, 4);
			root.add(salaryField, 1, 4);
			root.add(dateOfBirth, 0, 5);
			root.add(dateOfBirthField, 1, 5);
			root.add(dateFormat, 1, 6);
			root.add(emptyLabel1, 0, 7);  // Formatting before buttons, extra grid pane maximums and spaces
			root.add(departmentLabel, 0, 8);  // Add dropdown
			root.add(dropdown, 1, 8);
			
			GridPane root2 = new GridPane();
			root2.add(availability, 0, 1);
			root2.add(emptyLabel2, 0, 2);
			root2.add(weekdays, 0, 3);
			root2.add(weekends, 0, 4);
			root2.add(both, 0, 5);
			root2.add(emptyLabel3, 0, 6);
			root2.add(showSelected, 0, 7);  // Global var, changed by button listeners and thread 'refreshSelected'
			
			// HBox for the bottom buttons
			HBox hbox = new HBox(save, clear, exit);
			hbox.setSpacing(66.5);  // Separates all 3 perfectly
			
			// VBox actually fixes resize issues for the issue when text is too long and pushes other elements right (Panes cannot interact with one another
			VBox vbox = new VBox(root, root2, hbox);  // Separate into 3 parts: text fields and dropdown, radio buttons, and save/exit buttons
			vbox.setSpacing(25);
			
			refreshSelected();  // Update "Selected: " part for radio buttons and add current selected radio button value
			
			// Run window and set window attributes
			primaryStage.setTitle("Employee Directory Tool");
			Scene scene = new Scene(vbox,300,385);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);  // Resized window looks weird when not default
			primaryStage.show();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	// File writer class, object method that accepts string to write to "employees.txt" (The employee directory)
	public class fileIO 
	{
	 		public void writeToFile(String dataStr)
	 		{
	 	        FileWriter fwg = null;
	 	        try 
	 	        {
	 	        	// open the file in append write mode
	 	        	fwg = new FileWriter("employees.txt", true);
	 	        }
	 	        catch (IOException e)
	 	        {
	 	        	// TODO Auto-generated catch block
	 	        	e.printStackTrace();
	 	        }
	 	   	    
	 	        BufferedWriter bwg = new BufferedWriter(fwg);
	 	        PrintWriter outg   = new PrintWriter(bwg);
	 			
	 	        String timeStamp = new SimpleDateFormat("MM-dd-yyyy HH.mm.ss").format(new Date());
	 	        
	 	        outg.println(timeStamp + " : " + dataStr);
	 	        
	 	        outg.close();
	 		}
	 }
	 
	
	 // Thread that continually updates "Selected: " under radio boxes 
	 private void refreshSelected()  
	 {
	 	Thread refreshSelected = new Thread()
			   {  
				  public void run()
				  {	 
					while (true)
					{	
						
						String finalString = "null";
					    switch(selectedText) {
				   			case '1':
				   				finalString = "";
				   				break;
				   			case '2':
				   				finalString = "Weekdays";
				   				break;
				   			case '3':
				   				finalString = "Weekends";
				   				break;
				   			case '4':
				   				finalString = "Weekdays & Weekends";
				   				break;
					    }
				   		
				   		showSelected.setText("Selected: " + finalString);
						
					    try
					    {
						   sleep(500L);
					    }
					    catch (InterruptedException e) 
					    {
						   // TODO Auto-generated catch block
						   e.printStackTrace();
					    }
					  
		            }  // end while ( true )
					 
			    } // end run thread
			 };

			 refreshSelected.start();
	 }  
	 
	
	// Launch program
	public static void main(String[] args) {
		launch(args);
	}
}