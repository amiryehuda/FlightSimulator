package ViewModel;

import Model.AllModels;
import Model.Model;
import javafx.beans.Observable;
import java.util.Observer;

public class ViewModel extends AllViewModel {
    Model model;

    @Override
    public void VM_LoadXML() {
//        model.Model_LoadXML();
    }

    @Override
    public void VM_OpenCSV() {
        model.Model_OpenCSV();
    }

    @Override
    public void VM_Play() {

    }

    @Override
    public void VM_Pause() {

    }

    @Override
    public void VM_Stop() {

    }

    @Override
    public void VM_RunForward_15Sec() {

    }

    @Override
    public void VM_Rewind_15Sec() {

    }

    @Override
    public void VM_RunForward_30Sec() {

    }

    @Override
    public void VM_Rewind_30Sec() {

    }

    @Override
    public void VM_ChooseSpeed(String speed) {

    }
}
