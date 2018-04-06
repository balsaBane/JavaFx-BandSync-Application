 
package controllers;

import controllers.Screens.Screen;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.AnimatedIcon; 

 
public class MainWindowController implements Initializable {

      
    @FXML 
    private MenuItem about, close, logOut;
        
    @FXML
    private AnchorPane ancPane;
    
    @FXML 
    private Pane contentPane; 
    
    @FXML
    private ImageView logo;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
        AnimatedIcon songbookIcon  =  new AnimatedIcon("img/songbook.png", 100, 100);
        
        songbookIcon.setLayoutX(25);
        songbookIcon.setLayoutY(110);
        songbookIcon.setOnMouseReleased((event) -> {   
            Screens screen = new Screens(Screen.SONGBOOK, ancPane); 
        });  
        setCursors(songbookIcon);
        
         
        AnimatedIcon newSongIcon = new AnimatedIcon("img/newSong.png", 80, 80);
        newSongIcon.setLayoutX(230);
        newSongIcon.setLayoutY(115); 
        newSongIcon.setOnMouseReleased((event) -> {   
            Screens screen = new Screens(Screen.EDITOR, ancPane); 
        });  
        setCursors(newSongIcon);
 
        AnimatedIcon toDoIcon = new AnimatedIcon("img/toDo.png", 90, 90);
        toDoIcon.setLayoutX(425);
        toDoIcon.setLayoutY(115); 
        toDoIcon.setOnMouseReleased((event) -> {   
            Screens screen = new Screens(Screen.TODO, ancPane); 
        }); 
        setCursors(toDoIcon);
         
        AnimatedIcon filesIcon = new AnimatedIcon("img/files.png", 100, 100);
        filesIcon.setLayoutX(20);
        filesIcon.setLayoutY(265);  
        filesIcon.setOnMouseReleased((event) -> {   
            Screens screen = new Screens(Screen.FILES, ancPane); 
        }); 
        setCursors(filesIcon);
         
        AnimatedIcon chatIcon = new AnimatedIcon("img/chat.png", 110, 110);
        chatIcon.setLayoutX(220);
        chatIcon.setLayoutY(265); 
        chatIcon.setOnMouseReleased((event) -> {   
            Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setContentText("Chat is not currently available");
                info.setHeaderText(null);
                info.setTitle("Info");
                info.showAndWait(); 
                chatIcon.setCursor(Cursor.HAND);
                ancPane.setCursor(Cursor.DEFAULT);
        });  
        setCursors(chatIcon);
        
        AnimatedIcon statsIcon = new AnimatedIcon("img/stats.png", 90, 90);
        statsIcon.setLayoutX(425);
        statsIcon.setLayoutY(265);
        statsIcon.setOnMouseReleased((event) -> {   
            Screens screen = new Screens(Screen.STATS, ancPane); 
        }); 
        setCursors(statsIcon);
           
        contentPane.getChildren().addAll(songbookIcon, newSongIcon,toDoIcon, filesIcon, chatIcon, statsIcon);
        
        addLabelsBelowTheIcons();
        setDropShadowEffect(songbookIcon);
        setDropShadowEffect(newSongIcon);
        setDropShadowEffect(toDoIcon);
        setDropShadowEffect(filesIcon);
        setDropShadowEffect(chatIcon);
        setDropShadowEffect(statsIcon);
         
    } 
    
    private void addLabelsBelowTheIcons(){
        
        Label songbookLabel = new Label("Songbook");
        Label newSongLabel = new Label("New song"); 
        Label toDoLabel = new Label("To-do list"); 
        Label filesLabel = new Label("Files"); 
        Label chatLabel = new Label("Chat"); 
        Label statsLabel = new Label("Stats");  
         
        ArrayList <Label> listOfLabels = new ArrayList<>();
        listOfLabels.add(songbookLabel); listOfLabels.add(newSongLabel);
        listOfLabels.add(toDoLabel);     listOfLabels.add(filesLabel);
        listOfLabels.add(chatLabel);     listOfLabels.add(statsLabel); 
        
        for (int i = 0 ; i< listOfLabels.size(); i++){
            
            contentPane.getChildren().add(listOfLabels.get(i));
            setTextEffect(listOfLabels.get(i));
            setFont(listOfLabels.get(i));
            if (i <= 2) {
                listOfLabels.get(i).setLayoutX((i * 200) + 20);
                listOfLabels.get(i).setLayoutY(210);
            } else {
                listOfLabels.get(i).setLayoutX(((i - 3) * 200) + 40);
                listOfLabels.get(i).setLayoutY(370);
            }
        } 
    }
    
    public Pane getMainPane(){
        return contentPane;
    }
    
    private void setFont(Label textLabel){
        textLabel.setFont(new Font("Forte", 24));
        //textLabel.setFont(new Font("Bradley Hand ITC", 10));
        //textLabel.setStyle("-fx-font-weight: bold");
        textLabel.setTextFill(Color.web("#4f4a4a"));
    }
    
    private void setTextEffect(Label textLabel){
        DropShadow ds = new DropShadow();
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        textLabel.setEffect(ds);
    }
    
    private void setDropShadowEffect(AnimatedIcon icon){
        DropShadow ds = new DropShadow();
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        icon.setEffect(ds);
    }
    
    private void setCursors(AnimatedIcon icon){
        icon.setCursor(Cursor.HAND); 
        icon.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                icon.setCursor(Cursor.WAIT);
                ancPane.setCursor(Cursor.WAIT);
            }
        });
    }
    
    public void setLogoPosition(){
        int a = (int) (ancPane.getScene().getWidth() / 2 - (logo.getFitWidth() / 2));
        logo.layoutXProperty().setValue(a);
    }
     
    public ImageView getLogo(){
        return logo;
    }

      
 
    
}
