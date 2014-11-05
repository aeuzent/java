/**
 * @filename Server.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server{
	private LMIS lmis;
	private ServerSocket server;
	
	/**
	 * Constructor for server
	 * @param lmis
	 */
	public Server(LMIS lmis) {
		this.lmis = lmis;
		try{
			server = new ServerSocket(2020);
			System.out.println("Server Up");
			runServer();
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	
	/**
	 * Method to handle multiple 
	 * client connections
	 */
	private void runServer(){
		Socket curr;
		try{
			while(true){
			
				curr = server.accept();
				ServerThread go = new ServerThread(curr);
                (new Thread(go)).start();
                
			}	
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	
	
	/**
	 * Represents a single client connected
	 * to the server
	 */
	public class ServerThread implements Runnable{
		private Socket socket = null;
		private BufferedReader in;
		private PrintWriter out;
		
		
		/**
		 * Constructor for ServerThread
		 * @param socket
		 */
		public ServerThread(Socket socket){
			this.socket = socket;
		}
		
		
		/**
		 * Called when thread is started
		 */
		public void run(){
			String temp;
			try{
			
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				while(!(temp=in.readLine()).equals("done")){
					String header = temp.substring(0,4);
					switch(header){
						case "COut":
						case "BBac": 
									boolean job = parseItemCommand(temp);
									out.println(job);
									break;
						
						case "Char": String table = packageTable();
									out.println(table);
									break;
					}
				}
                in.close();
                out.close();
                System.err.println("Closing socket.");
                socket.close();
				
			} catch (IOException e) {
				e.printStackTrace(System.err);
			} catch (NullPointerException n){
				n.printStackTrace();
			}
			
		}
	}
	
	
	/**
	 * Parses a request from a client and 
	 * responds
	 * @param command
	 * @return
	 */
	private synchronized boolean parseItemCommand(String command){
		boolean result = false;
		String[] parts = command.split("#");
		if(parts[0].equals("COut") || parts[0].equals("BBack")){
			if(parts[parts.length-1].equals("End")){
				//checkout or bring back
				String[] data = parts[1].split(",");
				int userId = Integer.parseInt(data[0]);
				int itemId = Integer.parseInt(data[1]);
				if(parts[0].equals("COut")){
					result = lmis.checkOut(userId, itemId);
				} else if (parts[0].equals("BBack")){
					result = lmis.bringBack(userId, itemId);
				}
			}
		}
		return result;
	}
	
	
	/**
	 * Formats table of currently checked out items
	 * for transmission to client
	 * @return
	 */
	private synchronized String packageTable(){
		String[][] table = lmis.totalOut();
		String out = "Table#";
		for(int x = 0; x < table.length; x++){
			for(int z = 0; z < table[x].length; z++){
				if(z == table[x].length-1){
					out += table[x][z];
				} else {
					out += table[x][z] + ",";
				}
			}
			if(x != table.length-1){
				out+=";";
			}
		}
		out+= "#End";
		return out;
	}
}