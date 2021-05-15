package Algorithms;

import Server.*;

import java.util.ArrayList;
import java.util.List;

public class Hybrid implements TimeSeriesAnomalyDetector {

    private List<CorrelatedFeaturesCircle> corArr = new ArrayList<CorrelatedFeaturesCircle>();
    //private List<Circle> corrArr = new ArrayList<>();

    @Override
    public void learnNormal(TimeSeries ts) {
        String[] name = new String[ts.getCols().length];

        //save the name
        for(int i =0 ; i< ts.getCols().length; i ++){
            name[i] = ts.getCols()[i].getName();
        }

        float maxCorr = -2;
        float returnFromPerson;
        int corrIndex = 0;


        // for feature[i]
        for(int i =0 ; i < name.length; i++){
            List<Float> tempList = ts.getCols()[i].getFloats();
            float[] tempColArr = ts.fromListToArr(tempList);

            //check whit other feature
            for( int j = i+1; j < name.length; j++){
                if (!name[i].equals(name[j])){

                    List<Float> tempList2 = ts.getCols()[j].getFloats();

                    float[] tempColArr2 = ts.fromListToArr(tempList2);

                    returnFromPerson = Math.abs(StatLib.pearson(tempColArr, tempColArr2));

                    if(maxCorr < returnFromPerson && returnFromPerson < 0.9 && returnFromPerson > 0.5)
                    {
                        maxCorr = returnFromPerson;
                        corrIndex = j;
                        //algo 2
                    }


                }
            }

            //creat line and threshold
            if(maxCorr > 0){
                Point[] pointArr =
                        ts.fromArrToPoint(tempColArr, ts.fromListToArr(ts.getCols()[corrIndex].getFloats()));

                Point[] C = new Point[pointArr.length];
                Circle welzlC = StatLibCircle.welzlCircle(pointArr, C, pointArr.length);

                Circle c = new Circle(welzlC.p, welzlC.r);
                corArr.add(new CorrelatedFeaturesCircle(name[i], name[corrIndex], maxCorr, c));
                //corrArr.add(new Circle(welzlC.p, welzlC.r));
            }

            maxCorr = -2;
            corrIndex = 0;

        }
    }



    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        List<AnomalyReport> W_list = new ArrayList<AnomalyReport>();

        for (int i = 0; i < corArr.size(); i++)
        {
            String feature1 = corArr.get(i).feature1;

            String feature2 = corArr.get(i).feature2;

            List<Float> valuesFeature1 = new ArrayList<>();

            List<Float> valuesFeature2 = new ArrayList<>();


            for (int j = 0; j < ts.getCols().length; j++)
            {
                if(ts.getCols()[j].getFloats().equals(feature1)) {
                    valuesFeature1 = ts.getCols()[j].getFloats();
                }
                if(ts.getCols()[j].getFloats().equals(feature2)) {
                    valuesFeature2 = ts.getCols()[j].getFloats();
                }
            }

            float[] arrValuesF1 = ts.fromListToArr(valuesFeature1);

            float[] arrValuesF2 = ts.fromListToArr(valuesFeature2);


            Point[] pointsArr = ts.fromArrToPoint(arrValuesF1, arrValuesF2);


            for (int j = 0; j < pointsArr.length; j++) {
                //float distanceFromLine = StatLib.dev(pointsArr[j], corrArr.get(i).lin_reg);
                Circle temp_Treshold = corArr.get(i).smallCircle;

                if(!(StatLibCircle.isInside(temp_Treshold,pointsArr[j])))
                {
                    AnomalyReport storAR = new AnomalyReport((feature1 + "-" + feature2), j + 1);

                    W_list.add(storAR);
                }
            }

        }

        return W_list;
    }




    public List<CorrelatedFeaturesCircle> getNormalModel(){
        return corArr;
    }

}
