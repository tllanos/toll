package co.edu.eafit.dis.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI implements ActionListener {
	private boolean quit;
	public boolean state;
	private JFrame frame;
	private JButton btnStart, btnStop, btnClear;
	JTextArea txtRel, txtOutput;
	
	public GUI() {
		frame = new JFrame ("Tollbooth Simulation");
	    frame.setSize(500, 400);
		
		JPanel middlePanel = new JPanel ();
	    
	    txtOutput = new JTextArea(20,30);
	    
	    JScrollPane scroll = new JScrollPane (txtOutput);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants
	    		.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    scroll.setBorder(new TitledBorder(new EtchedBorder(), "System Output"));
	    
	    txtRel = new JTextArea(20,20);
	    
	    JScrollPane scroll2 = new JScrollPane (txtRel);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants
	    		.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    scroll2.setBorder(new TitledBorder(new EtchedBorder(), "Graph Relations"));
	    
	    middlePanel.add(scroll);
	    middlePanel.add(scroll2);
	    
	    btnStart = new JButton("START");
	    //btnStart.setBounds(x, y, width, height);
	    btnStart.addActionListener(this);
	    middlePanel.add(btnStart);
	    
	    btnStop = new JButton("STOP");
	    //btnStart.setBounds(x, y, width, height);
	    btnStop.setEnabled(false);
	    btnStop.addActionListener(this);
	    middlePanel.add(btnStop);
	    
	    btnClear = new JButton("CLEAR");
	    btnClear.setEnabled(false);
	    //btnStart.setBounds(x, y, width, height);
	    btnClear.addActionListener(this);
	    
	    middlePanel.add(btnClear);
	    
	    frame.add(middlePanel);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    state = false;
	    quit = false;
	    
	    frame.addWindowListener(new java.awt.event.WindowAdapter() {
	    	@Override
    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	    		GUI.this.setQuit();
	    	}
	    });
	}
	
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
        	btnClear.setEnabled(false);
        	btnStart.setEnabled(false);
        	btnStop.setEnabled(true);
        	state = true;
            // action
        }
        if (e.getSource() == btnStop) {
        	btnStop.setEnabled(false);
        	btnClear.setEnabled(true);
        	state = false;
            // action
        }
        if (e.getSource() == btnClear) {
        	btnStart.setEnabled(true);
        	btnClear.setEnabled(false);
            txtOutput.setText("");
            txtRel.setText("");
        }
    }
	
	public void setTextOutput(String text) {
	 	txtOutput.setText(text);
	 }
	  
	 public void setTextRel(String text) {
	 	txtRel.setText(text);
	 }

	public boolean checkstate() {
		return state;
	}
	
	public void setQuit(){
		quit = true;
	}

	public boolean quit() {
		return false;
	}
}
