package Algorithms;

import Server.*;

import java.util.ArrayList;
import java.util.List;

public class LinearRegression implements TimeSeriesAnomalyDetector
{

	private List<CorrelatedFeatures> corrArr = new ArrayList<CorrelatedFeatures>();

	public static float threshold;


	public void learnNormal(TimeSeries ts)
	{
		String[] nameStr = new String[ts.getCols().length];
		
		
		for (int i = 0; i < ts.getCols().length; i++) {
			nameStr[i] = ts.getCols()[i].getName();
		}
		
		float maxCorr = -2;
		float returnFromPearson;
		int corrIndex = 0;
		
		for (int i = 0; i < nameStr.length; i++) ////////////////////// A
		{
			List<Float> tempList = ts.getCols()[i].getFloats();
			float[] tempColArr = ts.fromListToArr(tempList);
			
			for (int j = i + 1; j < nameStr.length; j++) ////////////// C
			{
				if (nameStr[i] != nameStr[j])
				{
					List<Float> tempList2 = ts.getCols()[j].getFloats();
					
					float[] tempColArr2 = ts.fromListToArr(tempList2); 

					returnFromPearson = Math.abs(StatLib.pearson(tempColArr, tempColArr2));
					
					if(maxCorr < returnFromPearson && returnFromPearson > 0.9)
					{
						maxCorr = returnFromPearson;
						
						corrIndex = j;
					}
				}
			}

			if (maxCorr > 0) //create line and treshold
			{
				Point[] pointArr =  ts.fromArrToPoint(tempColArr, ts.fromListToArr(ts.getCols()[corrIndex].getFloats()));
				
				Line regLine = StatLib.linear_reg(pointArr);
				
				float maxTreshold = 0;
				
				for(int k = 0; k < pointArr.length; k++)
					if (maxTreshold < StatLib.dev(pointArr[k], regLine))
						maxTreshold = StatLib.dev(pointArr[k], regLine);

				corrArr.add(new CorrelatedFeatures(nameStr[i], nameStr[corrIndex], maxCorr, regLine, maxTreshold + (float) 0.0389));
			}
			

			maxCorr = -2;
			corrIndex = 0;
		}
	}



	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	public LinearRegression(float newVal) {
		setThreshold(newVal);
	}


	public List<AnomalyReport> detect(TimeSeries ts) {
		
		List<AnomalyReport> AR_list = new ArrayList<AnomalyReport>();
		
		for (int i = 0; i < corrArr.size(); i++)
		{
		
			String feature1 = corrArr.get(i).feature1;
			
			String feature2 = corrArr.get(i).feature2;
			
			List<Float> valuesFeature1 = new ArrayList<>();
			
			List<Float> valuesFeature2 = new ArrayList<>();
			
			
			for (int j = 0; j < ts.getCols().length; j++)
			{
				if(ts.getCols()[j].getName().equals(feature1)) {
					valuesFeature1 = ts.getCols()[j].getFloats();
				}
				if(ts.getCols()[j].getName().equals(feature2)) {
					valuesFeature2 = ts.getCols()[j].getFloats();
				}
			}
			
			float[] arrValuesF1 = ts.fromListToArr(valuesFeature1);
			
			float[] arrValuesF2 = ts.fromListToArr(valuesFeature2);
			
			
			Point[] pointsArr = ts.fromArrToPoint(arrValuesF1, arrValuesF2);
			
			
			for (int j = 0; j < pointsArr.length; j++)
			{
				float distanceFromLine = StatLib.dev(pointsArr[j], corrArr.get(i).lin_reg);
				float temp_Treshold = corrArr.get(i).threshold;
				
				if(distanceFromLine > temp_Treshold)
				{
					AnomalyReport storAR = new AnomalyReport((feature1 + "-" + feature2), j + 1);
					
					AR_list.add(storAR);
				}
			}
			
		}
		
		return AR_list;
	}
	
	
	
	
	public List<CorrelatedFeatures> getNormalModel(){
		return corrArr;
	}
}
