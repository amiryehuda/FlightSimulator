package Algorithms;

import Server.AnomalyReport;
import Server.StatLib;
import Server.TimeSeries;

import java.util.ArrayList;
import java.util.List;

import static Server.StatLib.avg;
import static Server.StatLib.var;

public class ZScore implements Server.TimeSeriesAnomalyDetector {

    public float[] thresholdArray;

    public ZScore(TimeSeries ts) {
        thresholdArray = new float[ts.getCols().length];
    }


    @Override
    public void learnNormal(TimeSeries ts) {

        for (int i = 0; i < ts.getCols().length; i++) {

            float maxZScore = -9;

            for (int j = 2; j < ts.getCols()[i].getFloats().size(); j++) {

                float average = StatLib.avg(ts.fromListToArr(ts.getCols()[i].getFloats().subList(1, j))); //the first argument is the name of the feature

                float standardDeviation = (float) Math.sqrt(StatLib.var(ts.fromListToArr(ts.getCols()[i].getFloats().subList(1, j)))); //the first argument is the name of the feature

                for (int k = 0; k < j; k++) {
                    float zScore;

                    if (standardDeviation == 0)
                        zScore = 0;
                    else
                        zScore = Math.abs(ts.getCols()[i].getFloats().get(k) - average) / standardDeviation;

                    if (zScore > maxZScore)
                        maxZScore = zScore;

                }

            }

            thresholdArray[i] = maxZScore;

        }

    }



    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {

        List<AnomalyReport> detect = new ArrayList<>();

        for (int i = 0; i < ts.getCols().length; i++) {

            for (int j = 2; j < ts.getCols()[i].getFloats().size(); j++) {

                float average = StatLib.avg(ts.fromListToArr(ts.getCols()[i].getFloats().subList(1, j)));

                float standardDeviation = (float) Math.sqrt(StatLib.var(ts.fromListToArr(ts.getCols()[i].getFloats().subList(1, j))));

                if (standardDeviation != 0) {
                    for (int k = 0; k < j; k++) {
                        float zScore = Math.abs(ts.getCols()[i].getFloats().get(k) - average) / standardDeviation;

                        if (zScore > thresholdArray[i]) {
                            AnomalyReport anomalyReport = new AnomalyReport(ts.getCols()[i].getName(), k + 1);

                            if (!detect.contains(anomalyReport))
                                detect.add(anomalyReport);

                        }
                    }
                }
            }
        }
        
        return detect;
    }



}
