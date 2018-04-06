 
package controllers;
 
import model.DatabaseSingleton;
import model.RowData;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList; 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class SongbookController implements Initializable  {
    
   
    public static RowData temp;
    
    int numberOfSongs;
    
    private ObservableList<RowData> data;
    
    DatabaseSingleton single = DatabaseSingleton.getSingleton();

    @FXML
    private AnchorPane ancPane;
    
    @FXML
    private MenuBar menu;
    
    @FXML
    private Pane contentPane;
    
    @FXML 
    private ImageView recentView,allFilesView,categoriesView, deleteSongView, editSongView, backView, updateView, searchButtonView;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private TableView <RowData> tabela;
    
    @FXML
    private Label statusLabel, connectionLabel;
    
    @FXML
    private ImageView connectionView, logo;
    
    @FXML
    private HBox categoryHbox;
    
    @FXML
    private TableColumn songNameCol, artistCol, categoryCol, tempoCol, dateCreatedCol, lastModifiedCol, domForCol ;
    
    @FXML
    private Button slow, medium, fast;
    
    Image recentImage= new Image("/img/recent.png");
    Image recentPressed = new Image("/img/recentPressed.png");
    
    Image allFilesImage= new Image("/img/allFiles.png");
    Image allFilesPressed = new Image("/img/allFilesPressed.png");
    
    Image categoryImage= new Image("/img/categories.png");
    Image categoryPressed = new Image("/img/categoriesPressed.png");
    
    Image deleteSongImage= new Image("/img/deleteSong.png");
    Image deleteSongPressed = new Image("/img/deleteSongPressed.png");
    
    Image editSongImage= new Image("/img/editSong.png");
    Image editSongPressed = new Image("/img/editSongPressed.png");
    
    Image back = new Image("img/back.png");
    Image backPressed = new Image("img/backPressed.png");
    
    Image update = new Image("img/update.png");
    Image updatePressed = new Image("img/updatePressed.png");
    
    Image connOkImage = new Image("img/connOk.png");
    Image connFailedImage = new Image("img/connNotOk.png");
    
    Image searchButton = new Image("img/searchButton.png");
    Image searchButtonPressed = new Image("img/searchButtonPressed.png");
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        disableTheButtons(true);
        
        getData("SELECT * FROM repertoire"); 
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuBar.fxml"));
                    
                    Parent root = null;
                    try {
                        menu = new MenuBar();
                        menu = (MenuBar)loader.load();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                     
        backView.setCursor(Cursor.HAND);
        allFilesView.setCursor(Cursor.HAND);
        categoriesView.setCursor(Cursor.HAND);
        recentView.setCursor(Cursor.HAND);
        updateView.setCursor(Cursor.HAND);
        editSongView.setCursor(Cursor.HAND);
        deleteSongView.setCursor(Cursor.HAND);
        searchButtonView.setCursor(Cursor.HAND);
        
        categoryHbox.setVisible(false);
        
        searchField.setPromptText("Search a song");
        Label placeholder = new Label("Please update the song database.");
        tabela.setPlaceholder(placeholder);
        
        connectionView.setVisible(false);
        connectionLabel.setVisible(false);
        statusLabel.setVisible(false);
        
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        disableTheButtons(false);
    }else{
        disableTheButtons(true);
    }
}); 
        songNameCol.setCellValueFactory(new PropertyValueFactory<>("songName"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        tempoCol.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        lastModifiedCol.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
        domForCol.setCellValueFactory(new PropertyValueFactory<>("domFor")); 

    }
    
    
    public Pane getMainPane(){
        return contentPane;
    }
         
    @FXML
    private void onEnter(ActionEvent event){
        search();
    } 
    
    @FXML
    private void mouseClicked(MouseEvent e){
        if (e.getSource() == recentView){
            
            recentView.setImage(recentPressed); 
            
        }else if (e.getSource() == allFilesView){
            
            allFilesView.setImage(allFilesPressed);
            
        }else if (e.getSource() == categoriesView){
            
            categoriesView.setImage(categoryPressed);
            
        }else if (e.getSource() == backView) {

            backView.setImage(backPressed);

        }else if (e.getSource() == updateView) {

            

        }else if (e.getSource() == deleteSongView) {

            deleteSongView.setImage(deleteSongPressed);

        }else if (e.getSource() == editSongView) {

            editSongView.setImage(editSongPressed);

        }else if (e.getSource() == slow){ 
                
            getData("Select * from repertoire where category = \"slow\" "); 
            categoryHbox.setVisible(false);
            
        }else if (e.getSource() == medium){
            
            getData("Select * from repertoire where category = \"medium\" ");
            categoryHbox.setVisible(false);
            
        }else if (e.getSource() == fast){
            
            getData("Select * from repertoire where category = \"fast\" ");
            categoryHbox.setVisible(false);
            
        } else if (e.getSource() == searchButtonView) {

            searchButtonView.setImage(searchButtonPressed);

        }
        
        
    }
    
    @FXML
    private void mouseReleased(MouseEvent e){
        if (e.getSource() == recentView){
            
            recentView.setImage(recentImage);
            
            try {
                getData("Select * from repertoire order by dateModified desc limit 5");
            } catch ( Exception ex) {
                Logger.getLogger(SongbookController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else if (e.getSource() == allFilesView){
            
            allFilesView.setImage(allFilesImage);
            
                getData("Select * from repertoire"); 
              
            
        }else if (e.getSource() == categoriesView){
            
            categoriesView.setImage(categoryImage);
            categoryHbox.setVisible(true);
             
        }else if (e.getSource() == backView) {
             
            backView.setImage(back);
            Screens screen = new Screens(Screens.Screen.MAINSCREEN, ancPane); 
            
        }
        else if (e.getSource() == updateView) {  
          
                getData("Select * from repertoire");  
                
            RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), updateView);
            rotation.setCycleCount(1);
            rotation.setByAngle(360);

            rotation.play();
                
        } else if (e.getSource() == deleteSongView) {

            deleteSongView.setImage(deleteSongImage);
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Be careful!");
            alert.setHeaderText(null);
            alert.setContentText("Delete a song??");
            Optional <ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK){
                
                RowData a = tabela.getSelectionModel().getSelectedItem();
                
                    single.delete(a.getSongId(), "repertoire");
                    getData("Select * from repertoire");
                    
            }

        } else if (e.getSource() == editSongView) {

            editSongView.setImage(editSongImage);
             
            temp = tabela.getSelectionModel().getSelectedItems().get(0);
            Screens.setEditData(temp);
            Screens screen = new Screens(Screens.Screen.EDITOR, ancPane);
            //after editor screen is instantiated, reset the edit data
            Screens.setEditData(null); 
            
            
            ancPane.setCursor(Cursor.DEFAULT);
            
        } else if (e.getSource() == searchButtonView) {

            searchButtonView.setImage(searchButton);
            search();
            
        }
    }  
    
    
    private void search(){
         
            /*
            
            searchList() method takes the search statement and the result is
            a list of songs that match the search, ie. their IDs. So, the
            list is a list of integers (indexes). If the search was successfull,
            collectResults will fetch from the database songs with coresponding
            indexes.
            
            */
            List<Integer> list = searchList(searchField.getText()); 
            if (list.size() == 0){
                tabela.setItems(null);
                Label placeholder = new Label("No results found.");
                tabela.setPlaceholder(placeholder);
            }else{
                collectResults(list);
            }
    } 
    
    private List<Integer> searchList(String text) {
        
        /*
        
        Search algorithm works in this way:
        
        All the names of the songs are put in one list.
        Each word of the search query is put in another list.
        
        Each word is then matched aginst the whole song names list, and its 
        "score" of repetitions in each song name is recorded in an array.
        So, if there is, let's say, 5 songs and one of them is Stairway to heaven,
        if we search only the term "stairway", the return array will look like 
        this (for example) : [0, 0, 1, 0 , 0]. If we search "Stairway to", the array will be:
        [0, 0, 2, 0, 0].
        
        */
        
        numberOfSongs = data.get(data.size()-1).getSongId();
        
        List<String> results = resultsToList();
        List<String> inputWords = new ArrayList<>();

        for (String word : text.split(" ")) {
            inputWords.add(word.toLowerCase());
        }  // ***************************** Search query is now a list

        int[] array = new int[numberOfSongs];

        for (int i = 0; i < inputWords.size(); i++) {  // running through individual words

            for (int j = 0; j < numberOfSongs; j++) {  // running through the song names

                ArrayList<String> tempList = new ArrayList<>();

                for (String word : results.get(j).split(" ")) {
                    tempList.add(word.toLowerCase());
                } // Particular song name turned into list
                
                int num = 0;
                
                for (int k = 0; k < tempList.size(); k++) { // running through a particual song name

                    if (inputWords.get(i).equals(tempList.get(k))) {
                        num++;
                    }
                }
                array[j] = array[j] + num;
                num = 0;
            }
        }

//-------------------------------
        
        /*
        
        We now have an unsorted list of results. 
        We'll create a map that will contain every score WITH the index of the 
        song as string. 
        So in previous example, we had indexes and scores sorted as such: 
        [ 0=0, 1=0, 2=0, 3=1, 4=0, 5=0 ]
        we need to sort it and have:
        [ 3=1, 0=0, 1=0, 2=0, 4=0, 5=0 ] 
        
        */

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int s = 0; s < array.length; s++) {
            map.put(Integer.toString(s), array[s]);
            
        } // list added to a map

        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(bvc);

        System.out.println("Unsorted map: " + map);
        sorted.putAll(map);
        System.out.println("Sorted: " + sorted);

        List<Integer> finalList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {

            if (entry.getValue() > 0) {
                
                finalList.add(Integer.parseInt(entry.getKey()) + 0x1);
                
            }
        }
        return finalList;
    }
    
    private void collectResults(List<Integer> list){
        
        ObservableList<RowData> tempData = FXCollections.observableArrayList();
        
        for (int i = 0; i <list.size(); i++){
            ResultSet rs = null;
            String sql = "SELECT * FROM repertoire WHERE rowid = " + Integer.toString(list.get(i));
            try {
                rs = single.getConnection().createStatement().executeQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(SongbookController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                while(rs.next()){
                    RowData rd = new RowData();
                    rd.songId.set(rs.getInt("rowid"));
                    rd.songName.set(rs.getString("songName"));
                    rd.artist.set(rs.getString("artist"));
                    rd.category.set(rs.getString("category"));
                    rd.tempo.set(rs.getInt("tempo"));
                    rd.dateCreated.set(rs.getString("dateCreated"));
                    rd.dateModified.set(rs.getString("dateModified"));
                    rd.domFor.set(rs.getString("domFor"));
                    rd.youtube.set(rs.getString("youtube"));
                    rd.song.set(rs.getString("song"));
                    tempData.add(rd); 
                }   } catch (SQLException ex) {
                Logger.getLogger(SongbookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        tabela.setItems(tempData);
    } 
    }
    
    
    public List<String> resultsToList (){
        List <String> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++){
            list.add(data.get(i).getSongName() + " " + data.get(i).getArtist());
        }  
        return list;
    } 
    
    public void getData(String sql) {
        
    data = FXCollections.observableArrayList();
         
        String SQL = sql;            
        ResultSet rs = null;
        try {
            rs = single.getConnection().createStatement().executeQuery(SQL);
            while(rs.next()){
            RowData rd = new RowData();
            rd.songId.set(rs.getInt("rowid"));    
            rd.songName.set(rs.getString("songName"));
            rd.artist.set(rs.getString("artist"));
            rd.category.set(rs.getString("category"));
            rd.tempo.set(rs.getInt("tempo"));
            rd.dateCreated.set(rs.getString("dateCreated"));
            rd.dateModified.set(rs.getString("dateModified"));
            rd.domFor.set(rs.getString("domFor"));
            rd.youtube.set(rs.getString("youtube"));
            rd.song.set(rs.getString("song"));
            data.add(rd); 
            
                connectionView.setImage(connOkImage);
                connectionView.setVisible(true);
                connectionLabel.setTextFill(Color.BLUE);
                connectionLabel.setVisible(true);
                statusLabel.setText("ok.");
                statusLabel.setTextFill(Color.BLUE);
                statusLabel.setVisible(true);
        }
            tabela.setItems(data); 
        } catch (Exception e) {
            connectionView.setImage(connFailedImage);
            connectionView.setVisible(true);
            connectionLabel.setTextFill(Color.RED);
            connectionLabel.setVisible(true);
            
            statusLabel.setText("failed.");
            statusLabel.setTextFill(Color.RED);
            statusLabel.setVisible(true);
        }
       
}  
    
    public void setLogoPosition(){
        int a = (int)(ancPane.getScene().getWidth() / 2 - (logo.getFitWidth()/2)); 
        logo.layoutXProperty().setValue(a);
    }
     
    public ImageView getLogo(){
        return logo;
    }
    
    private void disableTheButtons(boolean bool){
        if (bool == true){
            editSongView.setOpacity(0.5);
            editSongView.setDisable(true);
            deleteSongView.setOpacity(0.5);
            deleteSongView.setDisable(true);
        }else{
            editSongView.setOpacity(1.0);
            editSongView.setDisable(false);
            deleteSongView.setOpacity(1.0);
            deleteSongView.setDisable(false);
        }
    } 
}









class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    @Override
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
