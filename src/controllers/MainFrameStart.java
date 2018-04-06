package controllers;

import model.ConnectDb;  
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.CredentialsManager;


public class MainFrameStart extends Application{ 
    
    final int prefferedWidth = 1313;
    final int prefferedHight = 800;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        ConnectDb.updateDbCred(CredentialsManager.getSelectedDatabase());  
        
        Rectangle2D prim = Screen.getPrimary().getVisualBounds();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainWindow.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 
        
        Scene scene = new Scene( root );  
                  
        if (prim.getWidth() < prefferedWidth){ 
            primaryStage.setWidth(prim.getWidth()); 
        }else{
            primaryStage.setWidth(prefferedWidth); 
        }
        
        if (prim.getHeight() < prefferedHight){
            primaryStage.setHeight(prim.getHeight()); 
        }else{
            primaryStage.setHeight(prefferedHight); 
        }
        
        System.out.println("Primary stage width: " + primaryStage.getWidth());
        System.out.println("Primary stage hight: " + primaryStage.getHeight());
        primaryStage.setX((prim.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((prim.getHeight() - primaryStage.getHeight()) / 2); 
        
        primaryStage.setMinWidth(530);
        primaryStage.setMinHeight(730);
         
        Image ikonica = new Image("img/appIcon.png");
        primaryStage.getIcons().add(ikonica); 
        primaryStage.setTitle("BandSync 1.0"); 
        primaryStage.setScene(scene);  
        
        MainWindowController mainCont = loader.getController(); 
        
        root.getScene().widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                        mainCont.getMainPane().layoutXProperty().setValue(newSceneWidth.intValue()/2 - 280);
                        mainCont.getLogo().layoutXProperty().setValue((newSceneWidth.intValue() / 2) - (mainCont.getLogo().getFitWidth()/2) - 20);
                        System.out.println("Height: " + newSceneWidth);
                    }
                });
        
        primaryStage.show(); 
        mainCont.setLogoPosition();
    } 
      
}
