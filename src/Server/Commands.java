package Server;

import Algorithms.LinearRegression;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Commands {
	
	// Default IO interface
	public interface DefaultIO {
		public String readText();
		public void write(String text);
		public float readVal();
		public void write(float val);
		public void close();

		// you may add default methods here
	}
	
	// the default IO to be used in all commands
	DefaultIO dio;
	public Commands(DefaultIO dio) {this.dio=dio;}
	
	// you may add other helper classes here
	
	
	
	// the shared state of all commands
	private class SharedState {
		// implement here whatever you need
		LinearRegression simpleAd = new LinearRegression(0.9f);
		String train_CSVfile = "anomalyTrain.csv";
		String test_CSVfile = "anomalyTest.csv";
		TimeSeries newTSTrain;
		TimeSeries newTSTest;
		List<AnomalyReport> AnomalyReportList = new ArrayList<>();
	}
	
	private  SharedState sharedState = new SharedState();

	
	// Command abstract class
	public abstract class Command {
		protected String description;
		
		public Command(String description) {
			this.description=description;
		}
		
		public abstract void execute();
	}

	public ArrayList<Long[]> readNumbers() {
		ArrayList<Long[]> array = new ArrayList<>();
		String[] line =dio.readText().split(",");

		while (!line[0].equals("done")){
			Long[] temp = new Long[2];
			temp[0] = Long.parseLong(line[0]);
			temp[1] = Long.parseLong(line[1]);
			array.add(temp);
			line = dio.readText().split(",");
		}
		return array;
	}

	public ArrayList<Long[]> NumberToList(List<AnomalyReport> AnomalyReportList) {
		ArrayList<Long[]> reports = new ArrayList<Long[]>();
		int indexOfreports = 0;
		int start = 0, end = 0;
		Long[] temp = null;

		for(int i = 0; i<AnomalyReportList.size();i++){
			start = i;
			end = i;
			while ((i < AnomalyReportList.size() - 1) &&
					AnomalyReportList.get(end).description.equals(AnomalyReportList.get(end + 1).description) &&
					AnomalyReportList.get(end).timeStep + 1 == (AnomalyReportList.get(end + 1).timeStep))
			{
				i++;
				end++;
			}
			temp = new Long[2];
			temp[0] = AnomalyReportList.get(start).timeStep;
			temp[1] = AnomalyReportList.get(end).timeStep;
			reports.add(indexOfreports, temp);
			indexOfreports++;
		}
		return reports;
	}

	public void uploadFile(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName);
			String line = dio.readText();
			while (!line.equals("done")) {
				writer.print(line);
				writer.print("\n");
				line = dio.readText();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////////////////////////////////////////
	public class UploadCSVfile extends Command											//command 1
	{
		public UploadCSVfile() { super("1. upload a time series csv file\n"); }

		@Override
		public void execute() {
			dio.write("Please upload your local train CSV file.\n");
			uploadFile(sharedState.train_CSVfile);
			sharedState.newTSTrain = new TimeSeries(sharedState.train_CSVfile);
			dio.write("Upload complete.\n");

			dio.write("Please upload your local test CSV file.\n");
			uploadFile(sharedState.test_CSVfile);
			sharedState.newTSTest = new TimeSeries(sharedState.test_CSVfile);
			dio.write("Upload complete.\n");
		}
	}

	public class AlgorithmSettings extends Command										//command 2
	{
		public AlgorithmSettings() { super("2. algorithm settings\n"); }

		@Override
		public void execute() {
			String tempThresh = dio.readText();
			float userThreshold = Float.parseFloat(tempThresh);
			dio.write("The current correlation threshold is " + sharedState.simpleAd.getThreshold() + "\n");
			dio.write("Type a new threshold\n");

			while (userThreshold < 0 && userThreshold > 1) {
				dio.write("please choose a value between 0 and 1.\n");
				dio.write("Type a new threshold\n");
				userThreshold = dio.readVal();
			}
			sharedState.simpleAd.setThreshold(userThreshold);
		}
	}

	public class  DetectAnomalies extends Command										//command 3
	{
		public DetectAnomalies() { super("3. detect anomalies\n"); }

		@Override
		public void execute() {
			sharedState.simpleAd.learnNormal(sharedState.newTSTrain);
			sharedState.AnomalyReportList = sharedState.simpleAd.detect(sharedState.newTSTest);
			dio.write("anomaly detection complete.\n");
		}
	}

	public class DisplayResults extends Command											//command 4
	{
		public DisplayResults() { super("4. display results\n"); }

		@Override
		public void execute() {

			for (int i = 0; i < sharedState.AnomalyReportList.size(); i++) {
				dio.write(sharedState.AnomalyReportList.get(i).timeStep + "\t " + sharedState.AnomalyReportList.get(i).description + "\n");
			}
			dio.write("Done.\n");
		}
	}

	public class  UploadAnomaliesAndAnalyzeResults extends Command						//command 5
	{
			public UploadAnomaliesAndAnalyzeResults() { super("5. upload anomalies and analyze results\n"); }

			@Override
			public void execute() {
				String localAnomalies_CSV_file = "anomalyTrain.csv";
				dio.write("Please upload your local anomalies file.\n");
				ArrayList<Long[]> exceptions = readNumbers();
				dio.write("Upload complete.\n");
				ArrayList<Long[]> reports = NumberToList(sharedState.AnomalyReportList);

				int flag = 0;
				double TP = 0, FN = 0, FP = 0, N;
				double true_positive_rate, false_positive_rate;
				N = sharedState.newTSTest.getCols()[0].floats.size();
				for(int i = 0; i < exceptions.size(); i++)
				{
					flag = 0;
					for(int j = 0; j < reports.size(); j++) {
						if (	((reports.get(j)[0] <= exceptions.get(i)[0]) && ( exceptions.get(i)[0] <= reports.get(j)[1])) ||
								((reports.get(j)[0] <= exceptions.get(i)[1]) && (exceptions.get(i)[1] <= reports.get(j)[1])) ||
								((exceptions.get(i)[0] <= reports.get(j)[0]) && ( reports.get(j)[0] <=exceptions.get(i)[1])) ||
								((exceptions.get(i)[0] <= reports.get(j)[1]) && (reports.get(j)[1] <= exceptions.get(i)[1])))
						{
							TP++;
							flag = 1;
						}
					}
					// An unreported exception was found
					if (flag == 0)
						FN++;
					N = N - (exceptions.get(i)[1] - exceptions.get(i)[0] + 1);
				}
				FP = reports.size() - TP;
				true_positive_rate = (TP / exceptions.size());
				false_positive_rate = (FP / N);
				dio.write("True Positive Rate: " + (float)(Math.floor(true_positive_rate*1000)/1000) +"\n");
				dio.write("False Positive Rate: " + (float)(Math.floor(false_positive_rate*1000)/1000) +"\n");
			}
		}

	public class Exit extends Command													//command 6
	{
		public Exit() {
			super("6. exit\n");
		}

		@Override
		public void execute() { dio.close(); }
	}

}