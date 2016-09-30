package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import game.SpookySchool;
import ui.CreateServerPanel;

public class Server extends Thread {

	private SpookySchool game;
	private int nclients = 4; //Number of connected that can still connect.
	private int port;
	private CreateServerPanel serverPanel;

	private boolean printFull = true; //To stop printing server full on the server print area multiple times

	public Server(Integer port, CreateServerPanel serverPanel) {
		this.game = new SpookySchool();
		this.port = port.intValue();
		this.serverPanel = serverPanel;
	}

	/**
	 * Run the server. Listen on the socket for new clients.
	 */
	@Override
	public void run() {
		this.serverPanel.printToTextPrintArea("Server Started: Listening for new clients");

		try {

			PlayerThread[] connections = new PlayerThread[nclients];

			ServerSocket serverSocket = new ServerSocket(port);
			this.serverPanel
					.updateServerStatusField("Server running on: " + InetAddress.getLocalHost().getHostAddress());

			while (1 == 1) {

				//Listen for new clients if new players can join.
				if (this.nclients > 0) {
					this.serverPanel.printToTextPrintArea("Spots Avaliable on server: " + this.nclients);
					Socket socket = serverSocket.accept(); // Wait for a socket/incoming connection.
					this.serverPanel.printToTextPrintArea("ACCEPTED CONNECTION FROM: " + socket.getInetAddress());

					PlayerThread pT = new PlayerThread(socket, game, this.serverPanel); //Create the player thread
					this.addPlayerThread(pT, connections); //Add the player thread to the array of player threads that exists on this server.
					pT.start(); //Start the player thread.

				} else {
					if (this.printFull) {
						this.serverPanel.printToTextPrintArea("SERVER FULL");
						this.printFull = false;
					} //Print full if you haven't already.
				}

				this.removeDisconnectedPlayers(connections); //Remove all players that have been disconnected from this server.
			}

		} catch (IOException e) {
			this.serverPanel.updateServerStatusField("FAILED TO CREATE SERVER");
		}
	}


	/**
	 * Remove all players that have been disconnected. This allows more players to join.
	 * @param connections
	 */
	private void removeDisconnectedPlayers(PlayerThread... connections) {
		for (int i = 0; i < connections.length; i++) {
			PlayerThread pT = connections[i];
			if (pT != null && !pT.isAlive()) {
				connections[i] = null; //Clear this connection.
				nclients++; //More space available.
				this.serverPanel.printToTextPrintArea("Removed a client from server list.");
				this.printFull = true;
			}
		}
	}

	/**
	 * Add the player thread to the array of player threads running on the server.
	 * @param pT player thread to add to the connections array.
	 * @param connections Array of playerthreads running on the server.
	 */
	private void addPlayerThread(PlayerThread pT, PlayerThread[] connections) {

		for (int i = 0; i < connections.length; i++) {
			if (connections[i] == null) {
				connections[i] = pT; //Add this thread to player
				this.nclients--;
				return;
			}
		}
	}

}