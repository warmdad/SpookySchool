package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import network.Client;

public class ChatPanel extends JPanel{
	private JTextField typeArea;
	private JButton sendMessage;
	private JTextArea messageList;
	private GameFrame home;
	private String playerName;
	private Client client;
	
	public ChatPanel(GameFrame display, String playerName, Client client) {
		super(new BorderLayout());
		this.setOpaque(true);
		home = display;
		this.playerName = playerName;
		this.client = client;
        this.setSize(288, 488);
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLACK);
        southPanel.setLayout(new GridBagLayout());

        typeArea = new JTextField(30);
      
        sendMessage = new JButton("Send");
        sendMessage.addActionListener(new sendMessageButtonListener());

        messageList = new JTextArea();
        messageList.setEditable(false);
        messageList.setOpaque(true);
        try {
    		Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("slkscr.ttf"));
    		typeArea.setFont(font.deriveFont(Font.TRUETYPE_FONT, 12f));
    		messageList.setFont(font.deriveFont(Font.TRUETYPE_FONT, 12f));
    		
    	} catch (Exception e) {}
        
        messageList.setLineWrap(true);
        JScrollPane area = new JScrollPane(messageList);
        
        this.add(area, BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(typeArea, left);
        southPanel.add(sendMessage, right);

        this.add(BorderLayout.SOUTH, southPanel);

        this.setVisible(true);
    }
	
	public void addChange(List<String> changes){
		for(String change: changes)
			messageList.append(change+"\n");
	}
	
	public Client getClient(){
		return client;
	}
	
    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (typeArea.getText().length() < 1) {
                // do nothing
            } else {
            	String chat = "CHAT :  " + typeArea.getText();
                typeArea.setText("");
                
                getClient().sendCommand(chat);
            }
            home.refocus();
        }
    }
}
