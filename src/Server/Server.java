package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

	public interface ClientHandler{
		// define...
		public void handle(OutputStream out, InputStream in);
	}


	private volatile boolean stop = false;

	ServerSocket server;
	Thread thread;
	AnomalyDetectionHandler ch;

	public Server() {
		stop=false;
	}


	private void startServer(int port, ClientHandler ch) {

		while(!this.stop) {

			try {
				this.server.setSoTimeout(1000);
				Socket client = this.server.accept();
				InputStream in = client.getInputStream();
				OutputStream out = client.getOutputStream();

				ch.handle(out, in); // print menu

				in.close();
				client.close();

			} catch (SocketException eSoc) {} catch (IOException eIO) {}
		}

	}

	// runs the server in its own thread
	public void start(int port, ClientHandler ch) {

		try {
			this.server = new ServerSocket(port);
		} catch (IOException eIO) {
			eIO.printStackTrace();
		}
		new Thread(()->startServer(port,ch)).start();
	}

	public void stop() {
		stop=true;
	}

	public void setClientHandler (AnomalyDetectionHandler ch) {
		this.ch = ch;
	}

}
