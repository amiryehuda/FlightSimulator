package Model;

public abstract class AllModels {

    public abstract void Model_LoadXML(String XMLPath);

    public abstract void Model_OpenCSV(String CSVPath);

    public abstract void Model_Play();

    public abstract void Model_Pause();

    public abstract void Model_Stop();

    public abstract void Model_RunForward_15Sec();

    public abstract void Model_Rewind_15Sec();

    public abstract void Model_RunForward_30Sec();

    public abstract void Model_Rewind_30Sec();

    public abstract void Model_ChooseSpeed(String speed);

    //3 פונקציות לגרפים
    //LoadAlgorithem



}
