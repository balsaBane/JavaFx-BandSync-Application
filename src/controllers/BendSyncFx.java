 
package controllers;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage; 

/*

    Welcome to BandSync.

This class starts the intro windows that enables you to add credentials for connecting
to online database. 

In the controllers package, there is a "Screens" class that's used for instantiating
all the differect screens that the program consists of. Every screen has it's fxml file,
along with the respective controller. Therefore, the main funcionality of the program is 
switching between those pairs which manupulate the data and do the actuall work once
they're instantiated. 

The intro part of the program consists of these elements:

intro.fxml       ||  IntroController.java
credentials.fxml ||  CredentialsController.java

The main window has:

mainWindow.fxml  ||  MainWindowController.java
songbook.fxml    ||  SongbookController.java
editor.fxml      ||  EditorController.java
toDo.fxml        ||  ToDoController.java
files.fxml       ||  FilesController.java 
stats.fxml       ||  StatsController.java

(chat.fxml and the controller are under construction)

See "Screens" class to see how each of these are instantiated.

*/
 
public class BendSyncFx extends Application {
          
    Image appIcon = new Image("img/appIcon.png");
    
    @Override
    public void start(Stage primaryStage) {
        
        //Loading the intro window
        //Fxml file - "intro" and the respective controller (IntroController) is instantiated
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/intro.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 
        
        Scene scene = new Scene(root);    
         
        primaryStage.getIcons().add(appIcon); 
        primaryStage.setWidth(520);
        primaryStage.setHeight(430);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to BandSync!"); 
        primaryStage.setScene(scene); 
        primaryStage.show(); 
          
    }
 
    public static void main(String[] args) {
        launch(args);
    }
    
     
    
    
}
