 
package controllers;

import model.DatabaseSingleton;
import model.SugData;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType; 
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
 
public class ToDoController implements Initializable {
    
    static Stage stage;
    
    private ObservableList<SugData> data; 

    DatabaseSingleton single = DatabaseSingleton.getSingleton();

    @FXML
    private TableColumn songNameCol, youtubeCol, artistCol, something;

    @FXML
    private TableView<SugData> tabela3;

    Image back = new Image("img/back.png");
    Image backPressed = new Image("img/backPressed.png");

    Image paste = new Image("img/paste.png");
    Image pastePressed = new Image("img/pastePressed.png");

    Image addSuggestion = new Image("img/addSuggestion.png");
    Image addSuggestionPressed = new Image("img/addSuggestionPressed.png");

    Image delete = new Image("img/deleteSong.png");
    Image deletePressed = new Image("img/deleteSongPressed.png");

    Image upImage = new Image("img/up.png");
    Image upImagePressed = new Image("img/upPressed.png");

    Image downImage = new Image("img/down.png");
    Image downImagePressed = new Image("img/downPressed.png");
    
    Image youtube = new Image("img/youtube.png");
    Image youtubePressed = new Image("img/youtubePressed.png");
    
    Image doIt = new Image("img/doIt.png");
    Image doItPressed = new Image("img/doItPressed.png");
    Image watch = new Image("img/watch.png");
    Image watchPressed = new Image("img/watchPressed.png");

    @FXML
    private ImageView backView, pasteView, addView, deleteSongView, up, down, doItView, watchView, logo; 
    
    @FXML
    private AnchorPane ancPane;
    
    @FXML
    private Pane pane;

    @FXML
    private TextField youtubeField, songNameField, artistField; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        getData("SELECT * FROM suggestions"); 
        backView.setCursor(Cursor.HAND);
        pasteView.setCursor(Cursor.HAND);
        addView.setCursor(Cursor.HAND);
        up.setCursor(Cursor.HAND);
        down.setCursor(Cursor.HAND);
        deleteSongView.setCursor(Cursor.HAND);
        watchView.setCursor(Cursor.HAND);
        doItView.setCursor(Cursor.HAND);  
        
        disableTheButtons(true);

        songNameCol.setCellValueFactory(new PropertyValueFactory<>("songName"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
        youtubeCol.setCellValueFactory(new PropertyValueFactory<>("youtube"));
        tabela3.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
             
            if (newSelection != null) {
                disableTheButtons(false); 
            } else {
                disableTheButtons(true);
            }

        }); 
         
    }

    @FXML
    private void mouseClicked(MouseEvent t) {
        if (t.getSource() == backView) {

            backView.setImage(backPressed);

        } else if (t.getSource() == pasteView) {

            pasteView.setImage(pastePressed);

        } else if (t.getSource() == addView) {

            addView.setImage(addSuggestionPressed);

        } else if (t.getSource() == deleteSongView) {

            deleteSongView.setImage(deletePressed);

        } else if (t.getSource() == up) {

            up.setImage(upImagePressed);

        } else if (t.getSource() == down) {

            down.setImage(downImagePressed);

        } else if (t.getSource() == doItView) {

            doItView.setImage(doItPressed);
            
        } else if (t.getSource() == watchView) {

            watchView.setImage(watchPressed);
            
        }
    }

    @FXML
    private void mouseReleased(MouseEvent t) {
        if (t.getSource() == backView) {

            Screens screen = new Screens(Screens.Screen.MAINSCREEN, ancPane); 
            
        } else if (t.getSource() == pasteView) {

            pasteView.setImage(paste);
            youtubeField.setText(getClipboardContents());

        } else if (t.getSource() == addView) {

            addView.setImage(addSuggestion);

            if (!songNameField.getText().equals("") || !artistField.getText().equals("") || !youtubeField.getText().equals("")) {
                addSuggestion(songNameField.getText(), artistField.getText(), youtubeField.getText());
                getData("SELECT * FROM suggestions");

            } else {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setContentText("Something's missing. Also, be a bro and add youtube link.");
                info.setHeaderText(null);
                info.setTitle("Info");
                info.showAndWait();
            }
        } else if (t.getSource() == deleteSongView) {

            deleteSongView.setImage(delete);
            deleteSuggestion();

        } else if (t.getSource() == up) {

            up.setImage(upImage);

        } else if (t.getSource() == down) {

            down.setImage(downImage);

        } else if (t.getSource() == doItView) {

            doItView.setImage(doIt);
            Screens.setToDoData(tabela3.getSelectionModel().getSelectedItem());
            Screens screen = new Screens(Screens.Screen.EDITOR, ancPane);
            //reseting the to do data
            Screens.setToDoData(null);
            
            
        } else if (t.getSource() == watchView) {

            watchView.setImage(watch);
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(tabela3.getSelectionModel().getSelectedItem().getYoutube()));
                } catch (URISyntaxException | IOException ex) {
                    Logger.getLogger(ToDoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    } 
    public Pane getMainPane(){
        return pane;
    }

    private void getData(String sql) {

        data = FXCollections.observableArrayList();

        String SQL = sql;
        ResultSet rs = null;
        try {
            rs = single.getConnection().createStatement().executeQuery(SQL);
            while (rs.next()) {
                SugData sd = new SugData();
                sd.SongId.set(rs.getInt("rowid"));
                sd.SongName.set(rs.getString("songName"));
                sd.Artist.set(rs.getString("artist"));
                sd.Youtube.set(rs.getString("youtube"));
                data.add(sd);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FilesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabela3.setItems(data);
    }

    private void addSuggestion(String songName, String artist, String youtube) {
        PreparedStatement prep = null;
        String sql = "insert into suggestions (songName, artist, youtube ) values(?,?,?)";
        try {
            prep = single.getConnection().prepareStatement(sql);
            prep.setString(1, songName);
            prep.setString(2, artist);
            prep.setString(3, youtube);
            prep.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                prep.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private String getClipboardContents() {
        String result = "";
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText
                = (contents != null)
                && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
        return result;
    }

    private void deleteSuggestion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Be careful!");
        alert.setHeaderText(null);
        alert.setContentText("Deleting a suggestion??");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            SugData a = tabela3.getSelectionModel().getSelectedItem();
            
                single.delete(a.getSongId(), "suggestions");
                getData("Select * from suggestions");
  
        } else {

        }
    }
      
private static void openWebpage(String urlString) {
    try {
        Desktop.getDesktop().browse(new URL(urlString).toURI());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void disableTheButtons(boolean bool){
    if (bool == true){
        deleteSongView.setOpacity(0.5);
        deleteSongView.setDisable(true);
        up.setOpacity(0.5);
        up.setDisable(true);
        down.setOpacity(0.5);
        down.setDisable(true);
        watchView.setOpacity(0.5);
        watchView.setDisable(true);
        doItView.setOpacity(0.5);
        doItView.setDisable(true);
    }else{
        watchView.setOpacity(1.0);
        watchView.setDisable(false);
        doItView.setOpacity(1.0);
        doItView.setDisable(false);
        up.setOpacity(1.0);
        up.setDisable(false);
        down.setOpacity(1.0);
        down.setDisable(false);
        deleteSongView.setOpacity(1.0);
        deleteSongView.setDisable(false);
    }
}

private void setStage(Stage astage){
    stage = astage;
}

    public void setLogoPosition() {
        int a = (int) (ancPane.getScene().getWidth() / 2 - (logo.getFitWidth() / 2));
        
        logo.layoutXProperty().setValue(a);
    }

    public ImageView getLogo() {
        return logo;
    }  
}
