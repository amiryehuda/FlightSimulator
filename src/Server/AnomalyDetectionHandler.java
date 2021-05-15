package Server;


import Server.Commands.DefaultIO;
import Server.Server.ClientHandler;

import java.io.*;
import java.util.Scanner;


public class AnomalyDetectionHandler implements ClientHandler{

	public void handle(OutputStream out, InputStream in) {
		FileIO fio = new FileIO("input.txt", "output.txt");
		CLI cli = new CLI(fio);
		cli.start();
		fio.close();
	}

	public class SocketIO implements DefaultIO{
		Scanner in;
		PrintWriter out;

		public SocketIO(String inputFileName,String outputFileName) {

			try {
				in = new Scanner(new FileReader(inputFileName));
				out = new PrintWriter(new FileWriter(outputFileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public String readText() {
			return in.nextLine();
		}

		@Override
		public void write(String text) {
			out.print(text);
		}

		@Override
		public float readVal() {
			return in.nextFloat();
		}

		@Override
		public void write(float val) {
			out.print(val);
		}

		public void close() {
			in.close();
			out.close();
		}

	}


}