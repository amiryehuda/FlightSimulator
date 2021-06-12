package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MainFXMLController extends Pane implements Observer,Initializable {

    @FXML
    ButtonsController buttonsController;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }




    public void openCSV(ActionEvent event){

        final DirectoryChooser csv_chooser= new DirectoryChooser();

        Stage stage = (Stage) buttonsController.pane.getScene().getWindow();
        File file = csv_chooser.showDialog(stage);

        if(file != null){
            System.out.println("path : " + file.getAbsolutePath());
        }

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
