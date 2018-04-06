
package controllers;

import model.dbCredentials;
import java.net.URL; 
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.CredentialsManager; 
import javafx.scene.control.Button;

public class CredentialsController implements Initializable { 

    int changeIndex; 

    boolean isItEdit = false; // a flag that specifies whether the entry to the page
                              // will be for editing or adding new database credencials.
                              // True - editing, false - adding new
     
    @FXML
    private ImageView back;
    
    @FXML
    private TextField bandNameField, hostField, databaseNameField, databaseUserField,
                      databasePassField, portField;
    @FXML
    private AnchorPane AnchPane;

    @FXML
    private Button saveButton;

    Image addImage = new Image("img/add.png");
    Image addPressedImage = new Image("img/addPressed.png");
    Image backImage = new Image("img/back.png");
    Image backImagePressed = new Image("img/backPressed.png"); 
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setEditData(dbCredentials cred, int changeIndex) {
        isItEdit = true;
        
        try {
            this.changeIndex = changeIndex;
            saveButton.setText("Save"); 
            
            bandNameField.setText(cred.getBandName());
            hostField.setText(cred.getHost());
            databaseNameField.setText(cred.getDatabaseName());
            databaseUserField.setText(cred.getDatabaseUser());
            databasePassField.setText(cred.getDatabasePass());
            portField.setText(Integer.toString(cred.getPort()));
            
        } catch (Exception e) {
            saveButton.setText("Add the band");
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) { 
        
        if (mouseEvent.getSource() == back) {
            
            back.setImage(backImagePressed);
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() == saveButton) {

            dbCredentials temp = new dbCredentials();
            temp.setBandName(bandNameField.getText());
            temp.setDatabaseName(databaseNameField.getText());
            temp.setDatabaseUser(databaseUserField.getText());
            temp.setDatabasePass(databasePassField.getText());
            temp.setHost(hostField.getText());
            temp.setPort(Integer.parseInt(portField.getText()));
            
            if (isItEdit == false) {
 
                CredentialsManager.addNew(temp); 
                
                Screens screen = new Screens(Screens.Screen.INTROFRAME, AnchPane);
                
            } else { 
                
                CredentialsManager.update(temp, changeIndex);
                
                Screens screen = new Screens(Screens.Screen.INTROFRAME, AnchPane);
                
            }
        }

        if (mouseEvent.getSource() == back) {
            
            back.setImage(backImage);
            
                Screens screen = new Screens(Screens.Screen.INTROFRAME, AnchPane);
                 
        }
    } 

    

}
