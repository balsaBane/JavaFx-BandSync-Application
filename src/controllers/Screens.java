package controllers;

import model.RowData;
import model.SugData;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Screens {

    public enum Screen {
        MAINSCREEN, SONGBOOK, EDITOR, TODO, FILES, CHAT, STATS, INTROFRAME, CREDENTIALS
    }
    
    static RowData editData;
    
    static SugData toDoData;

    Screen screen;

    AnchorPane pane;

    public Screens(Screen screen, AnchorPane anchorPane) {
        this.screen = screen;
        this.pane = anchorPane;
        initialise();
    }
     
    public void initialise() {
        
        AnchorPane temp;
         
            if (null != screen) switch (screen) {
                
 // FOR INTRO FRAMES
                
                case INTROFRAME:{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/intro.fxml"));
                    AnchorPane anc = null;
                    try {
                        anc = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(CredentialsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pane.getChildren().setAll(anc);
                    break;
                } 
                
                case CREDENTIALS:{
                    
                    break;
                }
                
 // FOR MAIN APPLICATION FRAMES
                
                case MAINSCREEN:{
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainWindow.fxml"));
                    
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    pane.getChildren().setAll(root);
                    
                    getStage().setMinWidth(530);
                    getStage().setMinHeight(730);
                    
                    MainWindowController mainCont = loader.getController();
                    mainCont.getMainPane().layoutXProperty().setValue(pane.getScene().getWidth()  / 2 - 280);
                    
                    mainCont.setLogoPosition();
                    
                    root.getScene().widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                            mainCont.getMainPane().layoutXProperty().setValue(newSceneWidth.intValue() / 2 - 280);
                            mainCont.getLogo().layoutXProperty().setValue((newSceneWidth.intValue() / 2) - (mainCont.getLogo().getFitWidth()/2) - 20);
                            System.out.println("Height: " + newSceneWidth);
                        }
                    });
                    
                    
                
                    break;
                } 
                                
                //***************
                
                case SONGBOOK:{
                    
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/songbook.fxml"));
                    AnchorPane anc = null;
                    try {
                        anc = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pane.getChildren().setAll(anc);
                    Stage stage = (Stage)pane.getScene().getWindow();
                    stage.setMinWidth(824);
                    stage.setMinHeight(765);
                    pane.setCursor(Cursor.DEFAULT);
                    SongbookController cont = loader.getController(); 
                    cont.setLogoPosition();
                    cont.getMainPane().layoutXProperty().setValue(pane.getScene().getWidth()/2 - 480);
                    pane.getScene().widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                            cont.getMainPane().layoutXProperty().setValue(newSceneWidth.intValue()/2 - 480);
                            cont.getLogo().layoutXProperty().setValue((newSceneWidth.intValue() / 2) - (cont.getLogo().getFitWidth()/2) - 20);
                            
                        }
                    });
                    pane.getScene().heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                            System.out.println(newSceneHeight);
                        }
                    });
                        break;
                    }
                
                //***********
                
                case EDITOR:{
                    
                    //editData = null;
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editor.fxml"));
                    try {
                        temp = loader.load();
                        pane.getChildren().setAll(temp);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Editor error");
                    }
                    EditorController edit = loader.getController();
                    
                    if (editData !=null){
                        edit.setEditData(editData);
                        edit.isItEdit(true);
                    }else{
                        edit.isItEdit(false);
                        if (toDoData != null){
                            edit.setToDoData(toDoData);
                        }
                    }
                    
                    pane.setCursor(Cursor.DEFAULT);
                    Stage stage = (Stage) pane.getScene().getWindow();
                    stage.setMinHeight(680);
                    stage.setMinWidth(1310);
                    edit.getTextArea().setPrefHeight(pane.getScene().getHeight()- 90);
                    pane.getScene().heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                            edit.getTextArea().setPrefHeight(newSceneHeight.intValue() - 90);
                            System.out.println("Height: " + newSceneHeight);
                        }
                    });
                    pane.getScene().widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                            edit.getPane().setLayoutX((newSceneHeight.intValue() - 1189) / 2 + 20);
                        }
                    });
                        break;
                    }
                   
                //*********
                
                case TODO:{
                    
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/toDo.fxml"));
                    try {
                        temp = loader.load();
                        pane.getChildren().setAll(temp);
                        pane.getScene().getStylesheets().add(getClass().getResource("/view/style.css").toExternalForm());
                        
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                    pane.setCursor(Cursor.DEFAULT);
                    
                    getStage().setMinWidth(824);
                    getStage().setMinHeight(770);
                    
                    
                    
                    ToDoController cont = loader.getController(); 
                    
                     
                    cont.getMainPane().layoutXProperty().setValue(pane.getScene().getWidth()/2 - 460);
                    
                    cont.setLogoPosition();
                    pane.getScene().widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWight, Number newSceneWidth) {
                            cont.getMainPane().layoutXProperty().setValue(newSceneWidth.intValue()/2 - 460);
                            cont.getLogo().layoutXProperty().setValue((newSceneWidth.intValue() / 2) - (cont.getLogo().getFitWidth()/2) - 20);
                        }
                    });
                    pane.getScene().heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                            System.out.println(newSceneHeight);
                        }
                    });
                    
                        break;
                    }
                
                //**********
                                 
                case FILES:{
                    
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/files.fxml"));
                    
                    try {
                        temp = loader.load();
                        pane.getChildren().setAll(temp);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                    pane.setCursor(Cursor.DEFAULT);
                        
                        
                    getStage().setMinWidth(824);
                    getStage().setMinHeight(710);
                    
                    FilesController cont = loader.getController();
                    
                    cont.getMainPane().layoutXProperty().setValue(pane.getScene().getWidth() / 2 - 470);

                    cont.setLogoPosition();
                    
                    pane.getScene().widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWight, Number newSceneWidth) {
                            cont.getMainPane().layoutXProperty().setValue(newSceneWidth.intValue() / 2 - 470);
                            cont.getLogo().layoutXProperty().setValue((newSceneWidth.intValue() / 2) - (cont.getLogo().getFitWidth()/2) - 20);
                        }
                    });
                    pane.getScene().heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                            System.out.println(newSceneHeight);
                        }
                    });
                       break; 
                        
                    }
                
                //**********
                
                case STATS:{
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/stats.fxml"));
                    
                    
                    // dodaj bindove 
                    
                    try {
                        temp = loader.load();
                        pane.getChildren().setAll(temp);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                    
                    pane.setCursor(Cursor.DEFAULT);
                    StatsController cont2 = loader.getController();
                    cont2.setLogoPosition();
                    
                    
                        break;
                    }  
                  
                    default:
                        break;

                } 
    }  
     
    
    public static void setEditData(RowData data){
        Screens.editData = data;
    }
    
    public static void setToDoData(SugData a){
        Screens.toDoData = a;
    }
    
    public Stage getStage(){
        return (Stage) pane.getScene().getWindow();
    } 

}
