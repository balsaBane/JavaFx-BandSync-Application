package controllers;

import model.FileData;
import model.DatabaseSingleton;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent; 
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane; 
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

 
public class FilesController implements Initializable {
    
    int selected, selectedRow, selectedColumn;
    
    Task task;
    
    SimpleIntegerProperty opa = new SimpleIntegerProperty();
    
    @FXML
    private ProgressBar progress;
    
    int numberOfFiles;
    
    @FXML
    private Pane pane;
    
    private ObservableList<FileData> data;
    
    private byte[] byteFile;
    
    private final DatabaseSingleton single = DatabaseSingleton.getSingleton();
    
    @FXML
    private TableColumn fileNameCol, sizeCol, dateAddedCol, extentionCol;

    @FXML
    private AnchorPane ancPane;
    
    @FXML
    private GridPane grid;
    
    @FXML
    private ScrollPane fileSystem;
    
    @FXML
    private ImageView updateView;
    
    @FXML
    private ImageView searchButtonView;
    
    @FXML 
    private ImageView recentView,allFilesView,categoriesView, deleteSongView, uploadView, backView, downloadView, logo;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private TableView <FileData> tabela2; 
    
    Image deleteFileImage= new Image("/img/deleteSong.png");
    Image deleteFilePressed = new Image("/img/deleteSongPressed.png");
    
    Image upload= new Image("/img/a.png");
    Image uploadPressedImage = new Image("/img/b.png");
    
    Image back = new Image("img/back.png");
    Image backPressed = new Image("img/backPressed.png");
    
    Image update = new Image("img/update.png");
    Image updatePressedImage = new Image("img/updatePressed.png");
    
    Image download = new Image("img/download.png");
    Image downloadPressed = new Image("img/downloadPressed.png");
    
    Image searchButtonImage = new Image("img/searchButton.png");
    Image searchButtonImagePressed = new Image("img/searchButtonPressed.png");
    
    Image wordIcon = new Image ("img/fileIcons/word.png");
    Image pdfIcon = new Image("img/fileIcons/pdf.png");
    Image txtIcon = new Image("img/fileIcons/txt.png");
    Image mp3Icon = new Image("img/fileIcons/mp3.png");
    Image fileIcon = new Image("img/fileIcons/file.png");
    Image picIcon  = new Image ("img/fileIcons/pic.png");
    
    private final Background focusBackground = new Background( new BackgroundFill( Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY ) );
    private final Background unfocusBackground = new Background( new BackgroundFill( Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY ) );
    private final Border border = new Border( new BorderStroke( Color.BLUE, BorderStrokeStyle.SOLID, null, null ) );

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        progress.progressProperty().bind(opa);
        pane.requestFocus();
        backView.setCursor(Cursor.HAND);
        deleteSongView.setCursor(Cursor.HAND);
        updateView.setCursor(Cursor.HAND);
        uploadView.setCursor(Cursor.HAND);
        downloadView.setCursor(Cursor.HAND);
        deleteSongView.setCursor(Cursor.HAND); 
        
        deleteSongView.setOpacity(0.5);
        deleteSongView.setDisable(true);
        downloadView.setOpacity(0.5);
        downloadView.setDisable(true);
        searchField.setPromptText("Search for a file");
        Label placeholder = new Label("Please update the file database.");
        //tabela2.setPlaceholder(placeholder);
        
         
         
        grid.setLayoutY(100);
        grid.setPadding(new Insets(8));
        fileSystem.setStyle("-fx-focus-color: transparent; ");
        getData("SELECT * FROM files");
         
          
}  
    
    
    public Pane getMainPane(){
        return pane;
    } 
    
    @FXML
    private void mouseClicked(MouseEvent e){
         if (e.getSource() == backView) {

            backView.setImage(backPressed);

        } else if (e.getSource() == updateView) {

            updateView.setImage(updatePressedImage);             

        } else if (e.getSource() == uploadView){
            
            uploadView.setImage(uploadPressedImage); 
            
        } else if (e.getSource() == deleteSongView){
            
            deleteSongView.setImage(deleteFilePressed);
            
        } else if (e.getSource() == downloadView){
            
            downloadView.setImage(downloadPressed); 
            
        } else if (e.getSource() == searchButtonView){
            
            searchButtonView.setImage(searchButtonImagePressed); 
            
        }
    }
    
    @FXML
    private void mouseReleased(MouseEvent e) {
        if (e.getSource() == backView) {
            
            backView.setImage(back);
            
            Screens screens  = new Screens(Screens.Screen.MAINSCREEN, ancPane);
             
           
        } else if (e.getSource() == updateView) {
            
            updateView.setImage(update);            
            getData("SELECT * FROM files");
        }
        if (e.getSource() == uploadView) {
            
            uploadView.setImage(upload);
            
            addFile();
            
        }   else if (e.getSource() == deleteSongView){
            
            deleteSongView.setImage(deleteFileImage);
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Be careful!");
            alert.setHeaderText(null);
            alert.setContentText("Deleting a file??");
            Optional <ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK){
                 
                grid.getChildren().clear();
                single.delete(selected, "files");
                getData("Select * from files");
            }else{
                
            }
        }  else if (e.getSource() == downloadView){
            downloadView.setImage(download);
            writeFile();
        }  else if (e.getSource() == searchButtonView){
            searchButtonView.setImage(searchButtonImage);
            search();
        }    
    }   
    
    @FXML
    private void onEnter(ActionEvent event){
        searchField.setText("Search is yet to be implemented.");
    }  
                
    public void search() {
        
        /*
            The explanation of the same process can be found in SongbookControler.java
        
        */
        
        resultsToList();
        List<Integer> list = searchList(searchField.getText());
        if (list.size() == 0) {
            tabela2.setItems(null);
            Label placeholder = new Label("No results found.");
            tabela2.setPlaceholder(placeholder);
        } else {
            collectResults(list);
        }
    }

    public void collectResults(List<Integer> list) {
        ObservableList<FileData> tempData = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            tempData.add(getFromDatabase(list.get(i)));
        }
        tabela2.setItems(tempData);
    }

    public List<Integer> searchList(String text) {
        
        numberOfFiles = data.get(data.size() - 1).getFileId();

        List<String> results = resultsToList();
        List<String> inputWords = new ArrayList<>();

        for (String word : text.split(" ")) {
            inputWords.add(word.toLowerCase());
        }  // ***************************** String je pretvoren u listu reci 

        int[] array = new int[numberOfFiles];

        for (int i = 0; i < inputWords.size(); i++) {  // pretraga pojedinacnih reci

            for (int j = 0; j < numberOfFiles; j++) {  // pretraga u rezultatima

                ArrayList<String> temp = new ArrayList<>();

                for (String rec : results.get(j).split(" ")) {
                    temp.add(rec.toLowerCase());
                } // Rezultat string izdeljen
                int num = 0;
                for (int k = 0; k < temp.size(); k++) { // pretraga u pojedinacnom rezultatu

                    if (inputWords.get(i).equals(temp.get(k))) {
                        num++;
                    }
                }
                array[j] = array[j] + num;
                num = 0;
            }
        }//-------------------------------

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int s = 0; s < array.length; s++) {
            map.put(Integer.toString(s), array[s]);
            
        } // kreirana je hashMapa

        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(bvc);

        System.out.println("unsorted map: " + map);
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

    public List<String> resultsToList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getFileName());
        }
        return list;
    }

    private FileData getFromDatabase(int index) {

        FileData rd = null;
        String SQL = "SELECT * FROM files WHERE rowid = " + Integer.toString(index);
        ResultSet rs = null;
        try {
            rs = single.getConnection().createStatement().executeQuery(SQL);
            while (rs.next()) {
                rd = new FileData();
                rd.FileId.set(rs.getInt("rowid"));
                rd.FileName.set(rs.getString("fileName"));
                rd.Size.set(rs.getString("size"));
                rd.DateAdded.set(rs.getString("dateAdded"));
                rd.Extention.set(rs.getString("extention"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(SongbookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rd;

    }

    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    private String getNameWithOutExtention(String path, String extention) {
        int extentionLength = extention.length() + 1;
        String name = path.substring(0, path.length() - extentionLength);
        return name;
    }

    private void getData(String sql) {

        data = FXCollections.observableArrayList();

        String SQL = sql;
        ResultSet rs = null;
        try {
            rs = single.getConnection().createStatement().executeQuery(SQL);
            while (rs.next()) {
                FileData fl = new FileData();
                fl.FileId.set(rs.getInt("rowid"));
                fl.FileName.set(rs.getString("fileName"));
                fl.Size.set(rs.getString("size"));
                fl.DateAdded.set(rs.getString("dateAdded"));
                fl.Extention.set(rs.getString("extention"));
                data.add(fl);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FilesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateFiles();
    }

    private void updateFiles() {
        for (int i = 0; i < data.size(); i++) {
            String ext = data.get(i).getExtention();
            if (ext.equals("docx")) {
                VBox a = new VBox(); 
                Label label = new Label(data.get(i).getFileName());
                a.getChildren().add(new ImageView(wordIcon));
                a.getChildren().add(label);
                a.setAlignment(Pos.CENTER);
                setVBoxProperties(a);
                grid.add(a, i % 5, i / 5);
                
            } else if (ext.equals("pdf")) {
                VBox b = new VBox();
                Label label2 = new Label(data.get(i).getFileName());
                b.getChildren().add(new ImageView(pdfIcon));
                b.getChildren().add(label2);
                b.setAlignment(Pos.CENTER);
                setVBoxProperties(b);
                grid.add(b, i % 5, i / 5);
                
            } else if (ext.equals("txt")) {
                VBox c = new VBox();
                Label label3 = new Label(data.get(i).getFileName());
                c.getChildren().add(new ImageView(txtIcon));
                c.getChildren().add(label3);
                c.setAlignment(Pos.CENTER);
                setVBoxProperties(c);
                grid.add(c, i % 5, i / 5);
                
            } else if (ext.equals("mp3")) {
                VBox d = new VBox();
                Label label4 = new Label(data.get(i).getFileName());
                d.getChildren().add(new ImageView(mp3Icon));
                d.getChildren().add(label4);
                d.setAlignment(Pos.CENTER);
                setVBoxProperties(d);
                grid.add(d, i % 5, i / 5);
                
            } else if (ext.equals("jpeg") || ext.equals("png") || ext.equals("jpg")){
                VBox e = new VBox();
                Label label5 = new Label(data.get(i).getFileName());
                e.getChildren().add(new ImageView(picIcon));
                e.getChildren().add(label5);
                e.setAlignment(Pos.CENTER);
                setVBoxProperties(e);
                grid.add(e, i % 5, i / 5);
            
            
            }else {
                VBox f = new VBox();
                Label label5 = new Label(data.get(i).getFileName());
                f.getChildren().add(new ImageView(fileIcon));
                f.getChildren().add(label5);
                f.setAlignment(Pos.CENTER);
                setVBoxProperties(f);
                grid.add(f, i % 5, i / 5);
            }
        }
    }

    private void setVBoxProperties(VBox box) {
        box.setPrefHeight(200);
        box.focusedProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                downloadView.setOpacity(1.0);
                downloadView.setDisable(false);
                deleteSongView.setOpacity(1.0);
                deleteSongView.setDisable(false);
            } else {
                downloadView.setOpacity(0.5);
                downloadView.setDisable(true);
                deleteSongView.setOpacity(0.5);
                deleteSongView.setDisable(true);
            }

        });
        box.setOnMouseClicked((e)
                -> {
            box.requestFocus();
            selected = grid.getRowIndex(box) * 5 + grid.getColumnIndex(box) + 1; 
        });
        box.backgroundProperty().bind(Bindings
                .when(box.focusedProperty())
                .then(focusBackground)
                .otherwise(unfocusBackground)
        );
    }

    private void addFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        progress.setVisible(true);
        try {
            String path = fileChooser.showOpenDialog((Stage) ancPane.getScene().getWindow()).getPath();

            if (path != null) {

                FileInputStream input;
                PreparedStatement myStat = null;
                FileInputStream fis = null;
                try {
                    File fajl = new File(path);
                    int numOfCycles = (int) (fajl.length() / 1024);
                    int count = 1;

                    fis = new FileInputStream(path);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    for (int readNum; (readNum = fis.read(buf)) != -1;) {
                        baos.write(buf, 0, readNum);
                    }
                    byteFile = baos.toByteArray();

                    String sql = "INSERT into files (fileName,  size, dateAdded, extention, file) values (?, ? ,? ,? ,?)";

                    myStat = single.getConnection().prepareStatement(sql);

                    double kilobytes = (fajl.length() / 1024);
                    String extention = getFileExtension(fajl);

                    input = new FileInputStream(fajl);
                    myStat.setString(1, getNameWithOutExtention(fajl.getName(), extention));
                    myStat.setString(2, Double.toString(kilobytes) + " Kb");
                    myStat.setString(3, single.getDateAndTime());
                    myStat.setString(4, extention);
                    myStat.setBytes(5, byteFile);
                    myStat.execute();

                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                } finally {
                    try {
                        try {
                            fis.close();
                            myStat.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            }
        } catch (Exception e) { 
        }
        single.fixTheIndexes("files");
        getData("SELECT * FROM files");
        progress.setVisible(false);

    }

    public void writeFile() {
        String selectSQL = "SELECT * FROM files WHERE rowid = ?";
        ResultSet rs = null;
        FileOutputStream fos = null;
        PreparedStatement pstmt = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.setInitialFileName(data.get(selected - 1).getFileName() + "." + data.get(selected - 1).getExtention());
        File file = fileChooser.showSaveDialog((Stage) ancPane.getScene().getWindow());
        if (file != null) {
            try {
                fos = new FileOutputStream(file);
                pstmt = single.getConnection().prepareStatement(selectSQL);
                pstmt.setInt(1, selected);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    System.out.println("Writing BLOB to file ");
                    InputStream input = rs.getBinaryStream("file");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (SQLException ex) {
                Logger.getLogger(FilesController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {

                }
            }
        }
    }  
    
    public void setLogoPosition() {
        int a = (int) (ancPane.getScene().getWidth() / 2 - (logo.getFitWidth() / 2));
        System.out.println("a je " + a);
        logo.layoutXProperty().setValue(a);
    }

    public ImageView getLogo() {
        return logo;
    }
}
