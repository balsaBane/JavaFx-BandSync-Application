/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import model.DatabaseSingleton; 
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
 

public class StatsController implements Initializable {
    
    Image back = new Image("img/back.png");
    Image backPressed = new Image("img/backPressed.png");
    Image underConstImage  = new Image("img/underConst.png"); 
    
    DatabaseSingleton single = DatabaseSingleton.getSingleton();
    
    @FXML
    private ListView <String> lista;
    
    @FXML
    private AnchorPane ancPane;
    
    @FXML
    private ImageView backView, logo, underConst;
    
    @FXML
    private PieChart pie;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
        ObservableList<PieChart.Data> pieChartData = null;
        underConst.setImage(underConstImage);
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Slow", countSongs("slow")),
                new PieChart.Data("Medium", countSongs("medium")),
                new PieChart.Data("Fast", countSongs("fast"))); 
        pie.dataProperty().set(pieChartData);
        pie.setTitle("Songs by tempo");  
        pie.setTitleSide(Side.BOTTOM);
        backView.setCursor(Cursor.HAND);
        
        Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : pie.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(String.valueOf(data.getPieValue()) + "%");
                }
            });
        }
    } 
    
    private int countSongs(String category)  {
        String sql = null;
        switch(category){
            case "slow" :
                sql = "SELECT COUNT(category)FROM repertoire WHERE category=\"Slow\" ";
                break;
            case "medium" :
                sql = "SELECT COUNT(category)FROM repertoire WHERE category=\"Medium\" ";
                break;
            case "fast" :
                sql = "SELECT COUNT(category)FROM repertoire WHERE category=\"Fast\" ";
                break;
                
        }
        PreparedStatement prep = null;
        ResultSet number = null;
        int temp = 0;
       
       
        try {
            prep = single.getConnection().prepareStatement(sql);
            number = prep.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
            
        try {
            while (number.next()) {
                temp = number.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        } 

        try {
            number = prep.executeQuery();
        } catch (SQLException e) {
        }
        
        return temp;
    }
     
    public void mouseClicked(MouseEvent e){
         if (e.getSource() == backView) {

            backView.setImage(backPressed);

        }
    } 
    
    public void mouseReleased(MouseEvent e){
        if (e.getSource() == backView) {
            
            backView.setImage(back);
            
            Screens screen = new Screens(Screens.Screen.MAINSCREEN, ancPane); 
                        
        }
    }
    
    public void setLogoPosition() {
        int a = (int) (ancPane.getScene().getWidth() / 2 - (logo.getFitWidth() / 2));
        logo.layoutXProperty().setValue(a);
    }

    public ImageView getLogo() {
        return logo;
    }
    
}
