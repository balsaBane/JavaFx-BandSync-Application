package controllers;
  
import model.CredentialsManager;
import model.dbCredentials;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Cursor;  
import javafx.stage.Stage;

public class IntroController implements Initializable{
     
    
    private ObservableList<dbCredentials> tableCredentialsList = FXCollections.observableArrayList();
    
    @FXML
    private IntroController credController;
    
    @FXML
    private ImageView loadButton, editButton, deleteButton, makeNewButton;
    
    @FXML 
    private AnchorPane AncPane;
    
    @FXML 
    private TableView <dbCredentials> table;
          
    Image loadImage = new Image("img/load.png");
    Image loadPressedImage = new Image("img/loadPressed.png");
    Image editImage = new Image("img/edit.png");
    Image editPressedImage = new Image("img/editPressed.png");
    Image deleteImage = new Image("img/delete.png");
    Image deletePressedImage = new Image("img/deletePressed.png");
    Image makeNewImage = new Image("img/makeNew.png");
    Image makeNewPressedImage = new Image("img/makeNewPressed.png");
 
   @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        updateTheTable();
        
        loadButton.setCursor(Cursor.HAND);
        editButton.setCursor(Cursor.HAND);
        deleteButton.setCursor(Cursor.HAND);
        makeNewButton.setCursor(Cursor.HAND); 

        disableTheButtons(true); // disables first three buttons if not one account is selected at the very begining.

        // disables first three buttons if not one account is selected for good.
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                disableTheButtons(false);
            } else {
                disableTheButtons(true);
            }
        });
    }  
    
    public void mouseClicked(MouseEvent mouseEvent) {
        
      if (mouseEvent.getSource()  == loadButton){ 
               
      loadButton.setImage(loadPressedImage);
         
      } else if (mouseEvent.getSource()  == editButton){ 
          
      editButton.setImage(editPressedImage);
         
      }else if (mouseEvent.getSource()  == deleteButton){ 
          
      deleteButton.setImage(deletePressedImage);
         
      }else if (mouseEvent.getSource()  == makeNewButton){ 
          
      makeNewButton.setImage(makeNewPressedImage);
          
      }
      
    } 
    
    public void mouseReleased(MouseEvent mouseEvent) throws IOException{
         
         // If the load button is released 
         
                if (mouseEvent.getSource() == loadButton) {

                        loadButton.setImage(loadImage); 

                        dbCredentials selected;                 
                        selected = table.getSelectionModel().getSelectedItems().get(0);
                        
                        // sends the right credentials to CredentialsManager class
                        
                        CredentialsManager.setSelectedDatabase(selected);

                        // starts the main window
                        
                        MainFrameStart mainWindow = new MainFrameStart();  

                        try {
                            mainWindow.start(new Stage());
                        } catch (Exception ex) {
                            Logger.getLogger(IntroController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Stage stage = (Stage) AncPane.getScene().getWindow();
                        stage.close(); // closes the previews stage, brings the one with the main window

                }  
                
         // If edit button is released 
        
         else if (mouseEvent.getSource() == editButton) {
            
                editButton.setImage(editImage);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/credentials.fxml"));
                AnchorPane anc = loader.load();
                AncPane.getChildren().setAll(anc);
                CredentialsController con = loader.getController();

                con.setEditData(table.getSelectionModel().getSelectedItem(), table.getSelectionModel().getSelectedIndex());

                System.out.println("Change index: " + table.getSelectionModel().getSelectedIndex());

        // If delete button is released     
                    
        } else if (mouseEvent.getSource() == deleteButton) {
             
                deleteButton.setImage(deleteImage);
                
                ObservableList<dbCredentials> selected, allDbCreds;
                allDbCreds = table.getItems();
                selected = table.getSelectionModel().getSelectedItems();
                
                if (!selected.isEmpty()) { 
                    
                    CredentialsManager.delete(selected.get(0).getBandName());
                    selected.forEach(allDbCreds::remove);
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText(null);
                    alert.setContentText("The account has beed deleted!");
                    alert.showAndWait();
                    
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Wait... what?");
                    alert.setHeaderText(null);
                    alert.setContentText("No account has beed selected!");
                    alert.showAndWait();
                }
                
            // If "make new account" button is released    
                
        } else if (mouseEvent.getSource() == makeNewButton) {
             
                makeNewButton.setImage(makeNewImage);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/credentials.fxml"));
                AnchorPane anc = loader.load();
                AncPane.getChildren().setAll(anc);
        }
    }
     
    private void updateTheTable(){
        
            ArrayList<dbCredentials> list = CredentialsManager.readCreds();
            
            TableColumn column = new TableColumn<>();
            
            column.setPrefWidth(196);
            column.setCellValueFactory(new PropertyValueFactory<>("bandName"));
            column.setText("Band accounts:");
            column.setSortable(false);
            
            for (int i = 0; i < list.size(); i++) {
                tableCredentialsList.add(list.get(i)); //putting all the elements of the database list to table observable list
            }
            dbCredentials test = new dbCredentials("Test", "sql11231053", "c6hQzKJTTE", "sql11231053", "sql11.freesqldatabase.com", 3306);
            tableCredentialsList.add(test);
            table.setItems(tableCredentialsList);
            table.getColumns().add(column);
       
    } 
    
    private void disableTheButtons(boolean bool){
        if (bool == true){
            loadButton.setDisable(true); 
            editButton.setDisable(true);        
            deleteButton.setDisable(true);
            loadButton.setOpacity(0.5); 
            editButton.setOpacity(0.5);
            deleteButton.setOpacity(0.5);
        } else{
            loadButton.setDisable(false); 
            editButton.setDisable(false);        
            deleteButton.setDisable(false);
            loadButton.setOpacity(1); 
            editButton.setOpacity(1);
            deleteButton.setOpacity(1);
        }
    }  
}

