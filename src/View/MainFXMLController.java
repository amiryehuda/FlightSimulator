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
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Scanner;

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

    boolean stop=false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }



    @Override
    public void update(Observable o, Object arg) {

    }

    public void openCSV(javafx.event.ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fc.setTitle("Select a CSV file");
        fc.setInitialDirectory(new File("/"));
        File chosenCSV = fc.showOpenDialog(null);
//        chosen_CSVFilePath.set(chosenCSV.getAbsolutePath());

        if (chosenCSV != null){
            Scanner scanner = null;
            try {
                scanner = new Scanner(new BufferedReader(new FileReader(chosenCSV.getAbsolutePath())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            String[] featursName;
            featursName=scanner.next().split(",");
            System.out.println(featursName);

            listView = new classListView();
            listView.attList.getItems().add(featursName);

        }
    }


    public void play(javafx.event.ActionEvent actionEvent) throws FileNotFoundException {

        stop=false;

        Thread playThread = new Thread(()->{

            Socket fg= null;
            try {
                fg = new Socket("localhost", 5400);
            } catch (IOException e) {}

            Scanner scanner = null;
            try {
                scanner = new Scanner(new BufferedReader(new FileReader("C:\\Users\\ASUS\\IdeaProjects\\PTM3\\src\\Model\\reg_flight.csv")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            PrintWriter out= null;
            try {
                out = new PrintWriter(fg.getOutputStream());
            } catch (IOException e) {}

            String line = null;

            while(!this.stop) {
                if (!((line=scanner.nextLine())!=null))
                    break;
                out.println(line);
                out.flush();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}

                if (this.stop==true)
                    break;
            }

            out.close();
            try {
                fg.close();
            } catch (IOException e) {}

        });

        playThread.start();

    }

    public void stop (javafx.event.ActionEvent actionEvent) {
        stop = true;
    }

}
