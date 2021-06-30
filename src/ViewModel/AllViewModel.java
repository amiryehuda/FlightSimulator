package ViewModel;

import javafx.beans.Observable;
import java.util.Observer;

public abstract class AllViewModel {

    public abstract void VM_LoadXML();

    public abstract void VM_OpenCSV();

    public abstract void VM_Play();

    public abstract void VM_Pause();

    public abstract void VM_Stop();

    public abstract void VM_RunForward_15Sec();

    public abstract void VM_Rewind_15Sec();

    public abstract void VM_RunForward_30Sec();

    public abstract void VM_Rewind_30Sec();

    public abstract void VM_ChooseSpeed(String speed);

}
