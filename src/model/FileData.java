

package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class FileData {
    
    public SimpleIntegerProperty FileId  = new SimpleIntegerProperty();
    public SimpleStringProperty  FileName = new SimpleStringProperty() ;
    public SimpleStringProperty  Size     = new SimpleStringProperty()  ;
    public SimpleStringProperty  DateAdded = new SimpleStringProperty() ;
    public SimpleStringProperty  Extention = new SimpleStringProperty() ;

    
   public Integer getFileId() {
      return FileId.get();
   } 

   public String getFileName() {
      return FileName.get();
   } 
   
   public String getSize() {
      return Size.get();
   }

   public String getDateAdded() {
      return DateAdded.get();
   }

   public String getExtention() {
      return Extention.get();
   }
}
