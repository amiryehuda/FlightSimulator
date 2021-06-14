package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MainFXMLController extends Pane implements Observer,Initializable {

    @FXML
    classButtons buttons;

    @FXML
    classClocksPannel clocksPannel;

    @FXML
    classGraphs graphs;

    @FXML
    classJoystock joystock;

    @FXML
    classListView listView;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }




//    public void openCSV(ActionEvent event){
//        FileChooser fc = new FileChooser();
//        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter());
//
//
//    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
