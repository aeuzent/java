/**
 * @filename LocalClient.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;



public class LocalClient implements Runnable{

	private LMIS go;
	private int tag;
	private JDialog top;
	private JPanel inPanel, userPanel, idPanel, barPanel, buttonPanel;
	private JLabel userLabel, itemLabel;
	private JButton goButton, pause, cancel;
	private JTextField userText, itemText;
	private JProgressBar progressBar;
	private Task runTask;
    private volatile boolean paused = false;    
	private ExecutorService exe = Executors.newSingleThreadExecutor();


	/**
	 * Constructor for client sets tag
	 * and creates reference to LMIS
	 * @param tag
	 * @param currSys
	 */
	public LocalClient(int tag, LMIS currSys) {
		go = currSys; 
		this.tag = tag;
	}
	
	
	/**
	 * Method to be executed when thread is started
	 */
	public void run(){
		top = new JDialog();
		top.setLayout(new BorderLayout());

		if(tag == 0){
			top.setTitle("Check Out Item");
			goButton = new JButton("Check Out");
		} else if (tag == 1){
			top.setTitle("Return Item");
			goButton = new JButton("Return");
		}
		
		inPanel = new JPanel();
		buildInPanel();
		
		barPanel = new JPanel();
		buildBarPanel();
		
		
		top.add(inPanel, BorderLayout.NORTH);
		top.add(barPanel, BorderLayout.SOUTH);
		top.pack();
        top.setLocationRelativeTo(null);
		top.setVisible(true);
	}
	

	/**
	 * Assembles upper JPanel
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
	 * Inner class task for running job
	 * and load progress bar
	 */
	class Task extends SwingWorker<Void, Void>{
		
		private int user, item;
		Object lock = new Object();
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
				if(go.checkOut(user, item)){
					JOptionPane.showMessageDialog(null, "Item Checked Out Successfully");
				} else {
					JOptionPane.showMessageDialog(null, "Item Failed to Check Out");
				}
			} else if (tag == 1){
				if(go.bringBack(user, item)){
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
        public synchronized void resume(){       
            suspended = false;
            this.notify();
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
