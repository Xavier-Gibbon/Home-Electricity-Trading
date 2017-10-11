package gui_application;

import java.awt.EventQueue;
import java.awt.Rectangle;
import gui_application.MiddleMan;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class MainMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
		MiddleMan.SetMenu(this);
	}
	
	private JTextArea txtConsole;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Frame Initialization
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//The code for the title
		JLabel lblTitle = new JLabel("Home Electricity Agent");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitle.setBounds(159, 11, 366, 90);
		frame.getContentPane().add(lblTitle);
		
		//Exit button functionality
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnExit.setBounds(585, 367, 89, 23);
		frame.getContentPane().add(btnExit);
		
		//Settings button functionality
		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SettingsWindow theWindow = new SettingsWindow();
				theWindow.setVisible(true);
			}
		});
		
		btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSettings.setBounds(481, 284, 130, 49);
		frame.getContentPane().add(btnSettings);
		
		//Buy button functionality
		JButton btnBuy = new JButton("Buy");
		btnBuy.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBuy.setBounds(73, 284, 130, 49);
		frame.getContentPane().add(btnBuy);
		
		//Sell button functionality
		JButton btnSell = new JButton("Sell");
		btnSell.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSell.setBounds(277, 284, 130, 49);
		frame.getContentPane().add(btnSell);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 98, 539, 151);
		frame.getContentPane().add(scrollPane);
		
		txtConsole = new JTextArea();
		txtConsole.setEditable(false);
		scrollPane.setViewportView(txtConsole);
	}
	
	public void RecieveMessage(String theMessage)
	{
		txtConsole.append(theMessage + "\n");
	}
}
