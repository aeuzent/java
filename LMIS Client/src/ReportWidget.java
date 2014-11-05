/**
 * @filename ReportWidget.java
 * @author Alex Euzent
 * @date 5/5/2014
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class ReportWidget {

	private JDialog top;
	private JLabel title;
	private JTable data;
	private JButton end;
	
	
	/**
	 * Constructor for ReportWidget
	 * assembles window and displays report
	 * @param inData
	 */
	public ReportWidget(JTable inData) {
		top = new JDialog();
		top.setLayout(new BorderLayout());	
		title = new JLabel("Current Items Out");
		JScrollPane scrollPane = new JScrollPane();
		data = inData;
		scrollPane.setViewportView(data);
		
		JPanel endPanel = new JPanel();
		end = new JButton("Ok");
		end.addActionListener(endClick());
		endPanel.add(end);
		
		top.add(title, BorderLayout.NORTH);
		top.add(scrollPane, BorderLayout.CENTER);
		top.add(endPanel, BorderLayout.SOUTH);
		top.pack();
        top.setLocationRelativeTo(null);
		top.setVisible(true);
	}
	
	
	/**
	 * ActionListener for closing window
	 * @return
	 */
	private ActionListener endClick() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent g) {
				top.setVisible(false);
			}			
		};
	}
}
