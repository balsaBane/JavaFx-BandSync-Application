 
package controllers;

import model.DatabaseSingleton;
import model.RowData;
import model.SugData;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

 
public class EditorController implements Initializable {
  
    private RowData editData; 
    
    private SugData toDoData;
    
    boolean isItEdit;
    
    String firstPickedRadio, secondPickedRadio;

    @FXML
    private AnchorPane ancPane;  //main AnchorPane
    
    @FXML
    private Pane pane;

    @FXML
    private ComboBox choise;

    @FXML
    private TextArea chordsField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField artistField, songNameField, tempoField, youtubeField;

    @FXML
    private ImageView backView;

    @FXML
    private RadioButton radio1, radio2, radio3, radio4, radio5;

    @FXML
    private Slider slider;

    private static final double FONT_INIT_VALUE = 12;

    @FXML
    private TextField fontSizeField;

    ToggleGroup toogleGroup, toogleGroup2; //Two groups of radio buttons

    Image back = new Image("img/back.png");
    Image backPressed = new Image("img/backPressed.png");

    DatabaseSingleton single = DatabaseSingleton.getSingleton();  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        backView.setCursor(Cursor.HAND);

        fontSizeField.setText(new Double(FONT_INIT_VALUE).toString());
        fontSizeField.textProperty().bindBidirectional(slider.valueProperty(), NumberFormat.getNumberInstance());
        fontSizeField.setEditable(false);
                
        artistField.setPromptText("Artist");
        songNameField.setPromptText("Song name");
        tempoField.setPromptText("Tempo");
        youtubeField.setPromptText("Youtube link...");

        ToggleGroup group = new ToggleGroup(); 
        radio1.setUserData("Domestic");
        radio1.setToggleGroup(group); 
        radio2.setUserData("Foreign");
        radio2.setToggleGroup(group);

        ToggleGroup group2 = new ToggleGroup();
       
        radio3.setToggleGroup(group2);
        radio3.setUserData("Slow");
        radio4.setToggleGroup(group2);
        radio4.setUserData("Medium");
        radio5.setToggleGroup(group2);
        radio5.setUserData("Fast");

        slider.setValue(FONT_INIT_VALUE);
        slider.setMin(6);
        slider.setMax(36);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true); 
        slider.setMajorTickUnit(6);
        slider.setBlockIncrement(10);
        chordsField.styleProperty().bind(Bindings.format("-fx-font-size: %.2f", slider.valueProperty()));
        
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == null) {
                    fontSizeField.setText("");
                    return;
                }
                fontSizeField.setText(Math.round(newValue.intValue()) + "");
            }
        });
        
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (group.getSelectedToggle() != null) {

                    firstPickedRadio = group.getSelectedToggle().getUserData().toString();
                    
                } 
            }
        });

        group2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (group2.getSelectedToggle() != null) {

                    secondPickedRadio = group2.getSelectedToggle().getUserData().toString(); 
                    
                } 
            }
        });

    }
     
    public void setEditData(RowData temp) {
        
        editData = temp;
        
        artistField.setText(editData.getArtist());
        songNameField.setText(editData.getSongName()); 
        youtubeField.setText(editData.getYoutube());
        tempoField.setText(Integer.toString(editData.getTempo()));
        chordsField.setText(editData.getSong());
        
        if (editData.getDomFor().equals("Domestic")){
            radio1.setSelected(true);
        } else{
            radio2.setSelected(true);
        }
        
        if (editData.getCategory().equals("Slow")){
            radio3.setSelected(true);
        }else if (editData.getCategory().equals("Medium")){
            radio4.setSelected(true);
        }else{
            radio5.setSelected(true);
        }
    } 

    @FXML
    private void MouseClicked(MouseEvent t) { //when back button is pressed - this happens
        if (t.getSource() == backView) {

            backView.setImage(backPressed);

        }
    }

    @FXML
    private void MouseReleased(MouseEvent t) {
        if (t.getSource() == backView) {

            backView.setImage(back);
            
            Screens screen = new Screens(Screens.Screen.MAINSCREEN, ancPane); 
           

        } else if (t.getSource() == saveButton) {

            //if some field is empty, alert is shown
            if (isItEdit){
                updateTheSong();
            }else{
                saveTheSong();
            }
        }
    }
    
    public void updateTheSong(){
        if (!checkTheFields()) return; 

                //puting the song in a database
                single.update(chordsField.getText(), songNameField.getText(),artistField.getText(),
                        secondPickedRadio, Integer.parseInt(tempoField.getText()), 
                        single.getDateAndTime(), firstPickedRadio, youtubeField.getText(), editData.getSongId());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText(null);
                alert.setContentText("The song has been updated!");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

                Screens screen = new Screens(Screens.Screen.SONGBOOK, ancPane);
                 
        
    }
    
    public void saveTheSong(){  
        
                if (!checkTheFields()) return; 

                //puting the song in a database
                single.add(chordsField.getText(), songNameField.getText(),artistField.getText(),
                        secondPickedRadio, Integer.parseInt(tempoField.getText()), single.getDateAndTime(),
                        single.getDateAndTime(), firstPickedRadio, youtubeField.getText());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText(null);
                alert.setContentText("Your song is added to database!");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();

                Screens screen = new Screens(Screens.Screen.SONGBOOK, ancPane);
                
    }
    
    public boolean checkTheFields() {
        if (chordsField.getText().equals("") || songNameField.getText().equals("")
                || artistField.getText().equals("") || !radio1.isSelected() && !radio2.isSelected()
                || !radio3.isSelected() && !radio4.isSelected() && !radio5.isSelected()) {
            // boolean isTempoAnInt;
            String alertString;
            try {
                Integer.parseInt(tempoField.getText());
                //isTempoAnInt = true;
                alertString = "You left something out!";
            } catch (Exception e) {
                alertString = "Tempo has to be a number!";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wait... what?");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setHeaderText(null);
            alert.setContentText(alertString);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }
    
    public void isItEdit(boolean edit){
        isItEdit = edit;
    }
    
    public void setToDoData(SugData a){
        this.songNameField.setText(a.getSongName());
        this.artistField.setText(a.getArtist());
        this.youtubeField.setText(a.getYoutube());
    } 
    
    public TextArea getTextArea(){
        return chordsField;
    }
    
    public Pane getPane(){
        return pane;
    }
}
