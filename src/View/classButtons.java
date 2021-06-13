package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class classButtons implements Initializable {

    Button playButton;
    Button pauseButton;
    Button stopButton;
    Button runForward_15SecButton;
    Button rewind_15SecButton;
    Button runForward_30SecButton;
    Button rewind_30SecButton;

    Slider slider;


    @FXML
    Pane pane;


    @FXML
    public void openCSV(ActionEvent event){

        final DirectoryChooser csv_chooser= new DirectoryChooser();

        Stage stage = (Stage) pane.getScene().getWindow();
        File file = csv_chooser.showDialog(stage);

        if(file != null){
            System.out.println("path : " + file.getAbsolutePath());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //TODO
    }


}
