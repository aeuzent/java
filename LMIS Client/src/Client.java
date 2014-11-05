/**
 * @filename Client.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;


public class Client implements WindowListener{

	private int tag;
	private JFrame top;
	private JPanel topPanel, inPanel, userPanel, idPanel, barPanel, buttonPanel;
	private JLabel userLabel, itemLabel;
	private JButton goButton, pause, cancel, report;
	private JTable outReport;
	private JTextField userText, itemText;
	private JRadioButton outRB, backRB;
	private ButtonGroup radioButtons;
	private JProgressBar progressBar;
    private volatile boolean paused = false;    
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private ReportWidget reportWidget;
	private ExecutorService exe = Executors.newSingleThreadExecutor();
	private final String[] cols = {"User ID", "Item Type", "Title", "Item Index", "Author Index"};
	private Task runTask;
	
	/**
	 * Constructor for client
	 * Creates connection to server
	 * and displays error if unavailable
	 */
	public Client() {
		try {
			socket = new Socket("localhost", 2020);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			updateTable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		tag = 0;
		top = new JFrame();
		top.setLayout(new BorderLayout());
		top.setTitle("Library Client");
		goButton = new JButton("Check Out");
		
		topPanel = new JPanel();
		buildTopPanel();
		
		inPanel = new JPanel();
		buildInPanel();
		
		barPanel = new JPanel();
		buildBarPanel();
		
		top.add(topPanel, BorderLayout.NORTH);
		top.add(inPanel, BorderLayout.CENTER);
		top.add(barPanel, BorderLayout.SOUTH);
		top.pack();
        top.setLocationRelativeTo(null);
		top.addWindowListener(this);
		
		if(socket != null){
			top.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Error: Server not found");
			System.exit(0);
		}
	}
	
	/**
	 * Assembles top gui panel
	 */
	private void buildTopPanel(){
		
		
		//radio buttons
		radioButtons = new ButtonGroup();
		outRB = new JRadioButton("Check Out");
		outRB.setActionCommand("out");
		outRB.setSelected(true);
		outRB.addActionListener(radioClick());
		radioButtons.add(outRB);
		backRB = new JRadioButton("Bring Back");
		backRB.setActionCommand("back");
		backRB.addActionListener(radioClick());
		radioButtons.add(backRB);
		
		//report button
		report = new JButton("Current Items Out");
		report.addActionListener(reportClick());
		topPanel.add(outRB);
		topPanel.add(backRB);
		topPanel.add(report);
	}

	/**
	 * Assembles middle JPanel
	 */
	private void buildInPanel(){
		userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());
		userLabel = new JLabel("User ID");
		userText = new JTextField(8);
		userPanel.add(userLabel, BorderLayout.NORTH);
		userPanel.add(userText, BorderLayout.SOUTH);
		
		idPanel = new JPanel();
		idPanel.setLayout(new BorderLayout());
		itemLabel = new JLabel("Item ID");
		itemText = new JTextField(8);
		idPanel.add(itemLabel, BorderLayout.NORTH);
		idPanel.add(itemText, BorderLayout.SOUTH);
		
		goButton.addActionListener(goClick());
		
		inPanel.add(userPanel);
		inPanel.add(idPanel);
		inPanel.add(goButton);
	}

	
	/**
	 * Assembles lower JPanel
	 */
	private void buildBarPanel(){
		progressBar = new JProgressBar(0,100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		pause = new JButton("Pause");
		pause.addActionListener(pauseClick());
		cancel = new JButton("Cancel");
		cancel.addActionListener(cancelClick());
		buttonPanel.add(pause, BorderLayout.NORTH);
		buttonPanel.add(cancel, BorderLayout.SOUTH);
		
		barPanel.add(progressBar);
		barPanel.add(buttonPanel);		
	}
	
	
	/**
	 * ActionListener for checkout/return button
	 * Spawns Task thread to process job and power
	 * progress bar
	 * @return
	 */
	private ActionListener goClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent h) {
				String userTmp = userText.getText();
				int user = Integer.parseInt(userTmp);
				String itemTmp = itemText.getText();
				int item = Integer.parseInt(itemTmp);
				goButton.setEnabled(false);
				
							
				runTask = new Task(user, item);
				runTask.addPropertyChangeListener(barListener());
				exe.submit(runTask);

				
				
			}
		};
	}	
	
	
	/**
	 * ActionListener for checkout/return
	 * Radio buttons, changes tag and
	 * button label
	 * @return
	 */
	private ActionListener radioClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent h) {
				String command = h.getActionCommand();
				if (command.equals("out")){
					goButton.setText("Check Out");
					tag = 0;
				} else if (command.equals("back")){
					goButton.setText("Return");
					tag = 1;
				}
				
			}
		};
	}	
	
	
	/**
	 * ActionListener for the current
	 * items out report
	 * @return
	 */
	private ActionListener reportClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent h) {
				updateTable();
				reportWidget = new ReportWidget(outReport);
			}
		};
	}
	
	
	/**
	 * ActionListener for pause click
	 * @return
	 */
	private ActionListener pauseClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent h) {
				paused = !paused;
                pause.setText(paused?"Resume":"Pause");
                if(paused){
                	runTask.suspend();
                } else {
                	runTask.resume();
                }
			}			
		};
	}	
	
	
	/**
	 * ActionListener for cancel click
	 * @return
	 */
	private ActionListener cancelClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent h) {
				runTask.stop();
			}			
		};
	}
	
	
	/**
	 * PropertyChangeListener for updating
	 * progress bar
	 * @return
	 */
	private PropertyChangeListener barListener() {
		return new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if ("progress".equals(e.getPropertyName())) {
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(runTask.getProgress());
                }
			}			
		};
	}
	
	
	/**
	 * Method to fetch and format
	 * the items out report
	 */
	private void updateTable(){
		try{
			String cmd = "Chart#End";
			out.println(cmd);
			String table = in.readLine();
			String[] tmp = table.split("#");
			String[] rows = tmp[1].split(";");
			String[][] doneTable = new String[rows.length][];
			for(int x = 0; x<rows.length; x++){
				doneTable[x] = rows[x].split(",");
		
			}
			outReport = new JTable(doneTable, cols);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	/**
	 * List of WindowListenerEvents used to
	 * close socket when window exits
	 * Unused ones can't be removed and will be 
	 * ignored on the UML
	 */
	public void windowClosing(WindowEvent e)
    {
            try {
				in.close();
				out.close();
            	socket.close();
    			System.exit(0);
            } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    }
    public void windowClosed(WindowEvent e)         {
    	 try {
				in.close();
				out.close();
				socket.close();
				System.exit(0);
         } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    }
    public void windowOpened(WindowEvent e)        {   }
    public void windowActivated(WindowEvent e)     {   }
    public void windowDeactivated(WindowEvent e) {   }
    public void windowIconified(WindowEvent e)      {   }
    public void windowDeiconified(WindowEvent e)   {   }
    
    
	/**
	 * Inner class task for running job
	 * and load progress bar
	 */
	class Task extends SwingWorker<Void, Void>{
		Object lock = new Object();
		private int user, item;
        private volatile boolean suspended = false;  
	
		
		/**
		 * Constructor for Task initializes
		 * parameters
		 * @param user
		 * @param item
		 */
		public Task(int user, int item){
			this.user = user;
			this.item = item;
			
		}
		

		/**
		 * Spawns confirmation windows when job is done
		 */
		public void finished(){
			if(tag == 0){
				if(checkOut()){
					updateTable();
					JOptionPane.showMessageDialog(null, "Item Checked Out Successfully");
				} else {
					JOptionPane.showMessageDialog(null, "Item Failed to Check Out");
				}
			} else if (tag == 1){
				if(bringBack()){
					updateTable();
					JOptionPane.showMessageDialog(null, "Item Returned Successfully");
				} else {
					JOptionPane.showMessageDialog(null, "Item Failed to Return");
				}
			
			}
			paused = true;
			progressBar.setValue(0);
		    progressBar.setIndeterminate(true);
			goButton.setEnabled(true);
			userText.setText("");
			itemText.setText("");
			suspend();
		}

		
		/**
		 * Method for pausing the task
		 */
		public void suspend(){          
            suspended = true;  
        }  
		
		
		/**
		 * Method for resuming the task
		 */
        public void resume(){       
            suspended = false;  
            this.notify();  
        }  
        
        
		/**
		 * Method for processing a checkout
		 * across the server
		 * @return
		 */
		private boolean checkOut(){
			boolean result = false;
			try{
				String cmd = "COut#" + user + "," + item + "#End";
				out.println(cmd);
				String cmdRslt = in.readLine();
				if(cmdRslt.equals("true")){
					result = true;
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		
		/**
		 * Method for processing a return
		 * across the server
		 * @return
		 */
		private boolean bringBack(){
			boolean result = false;
			try{
				String cmd = "BBack#" + user + "," + item + "#End";
				out.println(cmd);
				String cmdRslt = in.readLine();
				if(cmdRslt.equals("true")){
					result = true;
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		
        /**
         * Ends task and resets UI
         */
        public void stop(){
			paused = true;
			progressBar.setValue(0);
		    progressBar.setIndeterminate(true);
			goButton.setEnabled(true);
			userText.setText("");
			itemText.setText("");
			suspend();
			this.cancel(true);
		}

        
        /**
         * Method is part of SwingWorker
         * runs with task
         */
        @Override
		public Void doInBackground() throws Exception {
        	int progress = 0;
        	setProgress(0);
        	try{
        		while(progress < 100 && !isCancelled() ){
					if(suspended){
					    synchronized(this){
		                	Thread.sleep(1000);
		                }
		            } else {
						// Update value
						progress += 10;
						setProgress(progress);
						Thread.sleep(200);
					}
        		}
        		if(progress == 100 && !isCancelled()){
					finished();
				}
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			return null;
		}
	}
}