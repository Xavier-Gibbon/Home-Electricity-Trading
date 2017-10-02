package gui_application;

import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Home Electricity Agent");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitle.setBounds(159, 11, 366, 90);
		frame.getContentPane().add(lblTitle);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(585, 367, 89, 23);
		frame.getContentPane().add(btnExit);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSettings.setBounds(432, 284, 182, 49);
		frame.getContentPane().add(btnSettings);
		
		JButton btnBuy = new JButton("Buy");
		btnBuy.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBuy.setBounds(73, 284, 182, 49);
		frame.getContentPane().add(btnBuy);
	}
}
