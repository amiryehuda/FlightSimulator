package View;

import ViewModel.ViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
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


    // Variable settings

    ViewModel viewModel;

    String speed;
    String colName;

    IntegerProperty numOfRow;

    StringProperty openCSV_Result;
    StringProperty chosen_CSVFilePath;
    StringProperty time;
    StringProperty LoadXML_Result;
    StringProperty chosen_XMLFilePath;

    DoubleProperty minRudder;
    DoubleProperty maxRudder;
    DoubleProperty minThrottle;
    DoubleProperty maxThrottle;
    DoubleProperty maxtimeSlider;

    FloatProperty rudderstep;
    FloatProperty throttlestep;
    FloatProperty aileronstep;
    FloatProperty elevatorstep;
    FloatProperty altimeterstep;
    FloatProperty airspeedstep;
    FloatProperty directionstep;
    FloatProperty pitchstep;
    FloatProperty rollstep;
    FloatProperty yawstep;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }




//    public void openCSV(ActionEvent event){
//        FileChooser fc = new FileChooser();
//        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
//        fc.setTitle("Select a CSV file");
//        fc.setInitialDirectory(new File("/"));
//        File chosenCSV = fc.showOpenDialog(null);
//        chosen_CSVFilePath.set(chosenCSV.getAbsolutePath());
//
//        if (chosenCSV != null){
//            viewModel.VM_OpenCSV();
//
//        }
//
//
//    }


    @Override
    public void update(Observable o, Object arg) {

    }

    public void openCSV(javafx.event.ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fc.setTitle("Select a CSV file");
        fc.setInitialDirectory(new File("/"));
        File chosenCSV = fc.showOpenDialog(null);
        chosen_CSVFilePath.set(chosenCSV.getAbsolutePath());


        if (chosenCSV != null){
//            viewModel.VM_OpenCSV();
//            System.out.println("hakol");
        }

    }
}
