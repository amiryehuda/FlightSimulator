package Server;

import Server.Commands.Command;
import Server.Commands.DefaultIO;

import java.util.ArrayList;

public class CLI {

	ArrayList<Command> commands;
	DefaultIO dio;
	Commands c;
	
	public CLI(DefaultIO dio) {
		this.dio=dio;
		c=new Commands(dio); 
		commands = new ArrayList<>();

		commands.add(c.new UploadCSVfile());
		commands.add(c.new AlgorithmSettings());
		commands.add(c.new DetectAnomalies());
		commands.add(c.new DisplayResults());
		commands.add(c.new UploadAnomaliesAndAnalyzeResults());
		commands.add(c.new Exit());
	}
	
	public void start() {
		// implement
		int showMenuFlag = 1;
		int commandSelect = 0;
		String selectInString;

		while (showMenuFlag == 1) {
			dio.write("Welcome to the Anomaly Detection Server.\nPlease choose an option:\n");
			for (int i = 0; i < commands.size(); i++) {
				dio.write(commands.get(i).description);
			}

			selectInString = dio.readText();
			commandSelect = Integer.parseInt(selectInString) - 1;
			if (commandSelect == 5) {
				showMenuFlag = 0;
			}

			commands.get(commandSelect).execute();
		}
	}

}



