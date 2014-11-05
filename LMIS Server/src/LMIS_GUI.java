/**
 * @filename LMIS_GUI.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

public class LMIS_GUI {

	private LMIS lmis;
	private JPanel treePanel, statusPanel, searchPanel;
	private JTree tree;
	private DefaultMutableTreeNode topNode;
	private JLabel topLabel, label1, label2, label3, label4, label5, label6;
	private ButtonGroup searchSelect;
	private JTextField input;
	private JTextArea results;
	private JFileChooser getFile;
	private JMenuBar menuBar;
	private JMenu fileMenu, sortMenu, authorMenu, bookMenu, journalMenu, clientMenu;
	private JMenuItem openFile, aByName, aByIndex, bByTitle, bByPrice, bByIndex, jByVolume, jByDate, currentOut, checkOut, bringBack;
	private int authSort, bookSort, journSort;
	private final String[] cols = {"User ID", "Item Type", "Title", "Item Index", "Author Index"};
	
	
	/**
	 * Constructor for LMIS_GUI
	 * Builds all of the static elements of the gui
	 */
	public LMIS_GUI(LMIS lmis) {
		this.lmis = lmis;
				
		authSort = 0;
		bookSort = 0;
		journSort = 0;
		JFrame top = new JFrame();
		top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		top.setTitle("Library Management System (LMIS)");
		top.setLayout(new BorderLayout());
		
		//menu bar
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openFile = new JMenuItem("Open");
		openFile.addActionListener(fileClick());

		
		clientMenu = new JMenu("Client");
		currentOut = new JMenuItem("List of Checked Out Items");
		currentOut.addActionListener(outReportClick());
		checkOut = new JMenuItem("Check Out Item");
		checkOut.addActionListener(clientClick());
		bringBack = new JMenuItem("Return Item");
		bringBack.addActionListener(clientClick());
		clientMenu.add(currentOut);
		clientMenu.add(checkOut);
		clientMenu.add(bringBack);
		
		
		sortMenu = new JMenu("Sort");
		
		authorMenu = new JMenu("Author");
		aByName = new JMenuItem("By Name");
		aByName.addActionListener(sortClick());
		aByIndex = new JMenuItem("By Author Index");
		aByIndex.addActionListener(sortClick());
		authorMenu.add(aByName);
		authorMenu.add(aByIndex);
		
		
		bookMenu = new JMenu("Book");
		bByTitle = new JMenuItem("By Title");
		bByTitle.addActionListener(sortClick());
		bByPrice = new JMenuItem("By Price");
		bByPrice.addActionListener(sortClick());
		bByIndex = new JMenuItem("By Book Index");
		bByIndex.addActionListener(sortClick());
		bookMenu.add(bByTitle);
		bookMenu.add(bByPrice);
		bookMenu.add(bByIndex);
		
		journalMenu = new JMenu("Journal");
		jByVolume = new JMenuItem("By Volume & Issue");
		jByVolume.addActionListener(sortClick());
		jByDate = new JMenuItem("By Date & Title");
		jByDate.addActionListener(sortClick());
		journalMenu.add(jByVolume);
		journalMenu.add(jByDate);
		
		sortMenu.add(authorMenu);
		sortMenu.add(bookMenu);
		sortMenu.add(journalMenu);
		
		
		
		fileMenu.add(openFile);
		
		menuBar.add(fileMenu);
		menuBar.add(clientMenu);
		menuBar.add(sortMenu);
		
		
		
		
		//build tree panel
		treePanel = new JPanel();
		topNode = new DefaultMutableTreeNode("Library");
		tree = new JTree(topNode);
		fillTree(authSort, bookSort, journSort);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(newSelectNode());
		JScrollPane treeView = new JScrollPane(tree);
		treePanel.add(treeView);
		
		//build status panel
		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		topLabel = new JLabel("", SwingConstants.CENTER);
		label1 = new JLabel("");
		label2 = new JLabel("");
		label3 = new JLabel("");
		label4 = new JLabel("");
		label5 = new JLabel("");
		label6 = new JLabel("");
		statusPanel.add(topLabel, BorderLayout.NORTH);
		JPanel subLabels = new JPanel();
		subLabels.setLayout(new BoxLayout(subLabels, BoxLayout.Y_AXIS));
		subLabels.add(label1);
		subLabels.add(label2);
		subLabels.add(label3);
		subLabels.add(label4);
		subLabels.add(label5);
		subLabels.add(label6);
		
		statusPanel.add(subLabels, BorderLayout.WEST);
		JScrollPane statusView = new JScrollPane(statusPanel);

		
		
		//build bottom search panel
		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		searchSelect = new ButtonGroup();
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BorderLayout());
		JLabel searchLabel = new JLabel("Search", SwingConstants.CENTER);
		JLabel typeLabel = new JLabel("Select search type:", SwingConstants.LEFT);
		labelPanel.add(searchLabel, BorderLayout.NORTH);
		labelPanel.add(typeLabel, BorderLayout.WEST);
		
		JPanel radioGroup = new JPanel();
		radioGroup.setLayout(new GridLayout(3,2));
		
		JRadioButton authName = new JRadioButton("Author's Name");
		authName.setActionCommand("authName");
		authName.setSelected(true);
		JRadioButton authIndex = new JRadioButton("Author's Index#");
		authIndex.setActionCommand("authIndex");
		JRadioButton bookTitle = new JRadioButton("Book Title");
		bookTitle.setActionCommand("bookTitle");
		JRadioButton bookIndex = new JRadioButton("Book Index#");
		bookIndex.setActionCommand("bookIndex");
		JRadioButton genre = new JRadioButton("Genre");
		genre.setActionCommand("genre");

	
		
		searchSelect.add(authName);
		searchSelect.add(authIndex);
		searchSelect.add(bookTitle);
		searchSelect.add(bookIndex);
		searchSelect.add(genre);
		
		radioGroup.add(authName);
		radioGroup.add(authIndex);
		radioGroup.add(bookTitle);
		radioGroup.add(bookIndex);
		radioGroup.add(genre);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
		input = new JTextField();
		JButton go = new JButton("Go");
		go.addActionListener(searchClick());
		inputPanel.add(input);
		inputPanel.add(go);
		
		JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
		JLabel resultsLabel = new JLabel("Results");
		results = new JTextArea();
		results.setEditable(false);
		JScrollPane resultsPane = new JScrollPane(results);
		resultsPane.setPreferredSize(new Dimension(300,300));
		resultsPanel.add(resultsLabel);
		resultsPanel.add(resultsPane);
		
		searchPanel.add(labelPanel);
		searchPanel.add(radioGroup);
		searchPanel.add(inputPanel);
		searchPanel.add(resultsPanel);
		
		
		
		
		//clean up
		top.setJMenuBar(menuBar);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(statusView);
		
	     treeView.setMinimumSize(new Dimension(300, 300));

		splitPane.setDividerLocation(200);
		splitPane.setPreferredSize(new Dimension(600, 300));
		
		top.add(splitPane, BorderLayout.NORTH);
		top.add(searchPanel, BorderLayout.SOUTH);
		top.pack();
        top.setLocationRelativeTo(null);
		top.setVisible(true);
	}
	
	
	/**
	 * Empties and rebuilds the tree from the LMIS database
	 * based on sort parameters
	 */
	private void fillTree(int authTag, int bookTag, int journTag){
		topNode.removeAllChildren();
		
		tree.setModel(lmis.loadTree(authTag, bookTag, journTag));
		
		((DefaultTreeModel)tree.getModel()).reload();
		tree.expandRow(0);
	}

	
	/**
	 * Action listener for when a new element is 
	 * selected in the tree
	 * @return
	 */
	private TreeSelectionListener newSelectNode() {
		return new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				
				DefaultMutableTreeNode current = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if(current == null) {return;}
				Object element = current.getUserObject();
				if(element instanceof Book){
					bookStatus((Book)element);
				} else if (element instanceof Author){
					authorStatus((Author)element);
				} else if (element instanceof Journal){
					journalStatus((Journal)element);
				}
			}
		};
	}
	
	
	/**
	 * Loads info about the selected author
	 * @param author
	 */
	private void authorStatus(Author author){
		topLabel.setText("Author");
		label1.setText("Name: " + author.getName());
		label2.setText("Address: " + author.getAddress());
		label3.setText("Total Books: " + author.totalBooks());
		label4.setText("Total Journals: " + author.totalJournals());
		label5.setText("Index#: " + author.getIndex());
		label6.setText("");
	}
	
	
	/**
	 * Loads info about the selected book
	 * @param book
	 */
	private void bookStatus(Book book){
		topLabel.setText("Book");
		label1.setText("Title: " + book.getTitle());
		label2.setText("Author: " + lmis.getAuthor(book.getAuthIndex()).getName());
		label3.setText("Genre: " + book.getGenre());
		label4.setText("Price : $" + book.getPrice());
		label5.setText("Index#: " + book.getIndex());
		label6.setText(book.getStock() + " out of " + book.getTotalBooks() + " available");
	}

	
	/**
	 * Loads info about the selected journal
	 * @param journal
	 */
	private void journalStatus(Journal journal){
		topLabel.setText("Journal");
		label1.setText("Title: " + journal.getTitle());
		label2.setText("Author: " + lmis.getAuthor(journal.getAuthIndex()).getName());
		label3.setText("Vol. " + journal.getVolume() + " Issue. " + journal.getIssue());
		label4.setText("Publication Date: " + journal.getPubDateStr());
		label5.setText("Index#: " + journal.getIndex());
		label6.setText(journal.getStock() + " out of " + journal.getTotalJournals() + " available");
	}
	
	
	/**
	 * Clears the status panel
	 */
	private void blankStatus(){
		topLabel.setText("");
		label1.setText("");
		label2.setText("");
		label3.setText("");
		label4.setText("");
		label5.setText("");
	}
	
	
	/**
	 * Clears the tree display
	 */
	private void blankTree(){
		topNode.removeAllChildren();
		tree.setModel(new DefaultTreeModel(topNode));
		((DefaultTreeModel)tree.getModel()).reload();
		tree.expandRow(0);
	}

	
	/**
	 * Action listener for the search function
	 * @return
	 */
	private ActionListener searchClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent d) {
				String search = input.getText();
				String type = searchSelect.getSelection().getActionCommand();
				runSearch(search, type);
			}

		};
	}
	
	
	/**
	 * Action listener for opening a file
	 * @return
	 */
	private ActionListener fileClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent f) {
				if(getFile == null) {
					getFile = new JFileChooser();
					getFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
				}
				int retValue = getFile.showOpenDialog(getFile);
				if(retValue == JFileChooser.APPROVE_OPTION) {
					File inFile = getFile.getSelectedFile();
					if(lmis.inputFile(inFile)){
						fillTree(authSort, bookSort, journSort);
						blankStatus();
					} else {
						blankTree();
						blankStatus();
						label1.setText("Error opening file");
					}


				}
			}

		};
	}
	
	
	/**
	 * Runs the given search type on the given term
	 * @param term
	 * @param type
	 */
	private void runSearch(String term, String type){
		int index = 0;
		String temp = "";
		
		switch(type){
			case "authName":
				ArrayList<Author> nameSearch = lmis.getNameContains(term);
				if(nameSearch.size()>0){
					for(Author current : nameSearch){
						temp += current.toStringB() + "\n";
					}
					results.setText(temp);
				} else {
					results.setText("Name not found");
				}
				break;
							
			case "authIndex":
				try{
					index = Integer.parseInt(term);
					if(lmis.hasAuthor(index)){
						temp = lmis.getAuthor(index).toStringB();
						results.setText(temp);
					} else {
						results.setText("Author not found");
					}
				} catch (NumberFormatException n) {
					results.setText("ERROR: Indexes must be in number format");
				}
				break;
							
			case "bookTitle":
				ArrayList<Book> titleSearch = lmis.getTitleContains(term);
				if(titleSearch.size()>0){
					for(Book current : titleSearch){
						temp += current.toStringB() + "\n";
					}
					results.setText(temp);
				} else {
					results.setText("Title not found");
				}
				break;
							
			case "bookIndex":
				try{
					index = Integer.parseInt(term);
					if(lmis.hasBook(index)){
						temp = lmis.getBook(index).toStringB();
						results.setText(temp);
					} else {
						results.setText("Book not found");
					}
				} catch (NumberFormatException n) {
					results.setText("ERROR: Indexes must be in number format");
				}
				break;
							
							
			case "genre":
				ArrayList<Book> genreSearch = lmis.getGenre(term);
				if(genreSearch.size()>0){
					for(Book current : genreSearch){
						temp += current.toStringB() + "\n";
					}
					results.setText(temp);
				} else {
					results.setText("Genre not found");
				}
				break;
		}
		
	}
	
	
	/**
	 * Action listener for sorting results
	 * @return
	 */
	private ActionListener sortClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent f) {
				JMenuItem current = (JMenuItem)f.getSource();
				String call = current.getText();
				switch(call){
				case "By Name": authSort = 0;
						break;
				case "By Author Index": authSort = 1;
						break;
				case "By Title": bookSort = 0;
						break;
				case "By Price": bookSort = 1;
						break;
				case "By Book Index": bookSort = 2;
						break;
				case "By Volume & Issue": journSort = 0;
						break;
				case "By Date & Title": journSort = 1;
						break;
				}
				tree.clearSelection();
				blankStatus();
				fillTree(authSort, bookSort, journSort);
			}			
		};
	}
	
	
	/**
	 * ActionListener that creates a ReportWidget
	 * object to display currently checked out
	 * items
	 * @return
	 */
	private ActionListener outReportClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent g) {
				String[][] data = lmis.totalOut();
				JTable report = new JTable(data, cols);
				ReportWidget reportWindow = new ReportWidget(report);
				
			}			
		};
	}

	
	/**
	 * ActionListener for client options.
	 * Determines if job is a check out or 
	 * return and spawns correct thread
	 * @return
	 */
	private ActionListener clientClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent h) {
				int tag = 0;
				JMenuItem current = (JMenuItem)h.getSource();
				String call = current.getText();
				switch(call){
					case "Check Out Item": tag = 0;
							break;
					
					case "Return Item": tag = 1;
							break;
				}
				(new Thread(new LocalClient(tag,lmis))).start();
			}			
		};
	}

	
}


