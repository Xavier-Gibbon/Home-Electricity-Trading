package gui_application;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.glass.ui.Application;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class SettingsWindow extends JFrame {

	private JPanel contentPane;
	private JTable tblApplianceList;
	private JTextField txtMinBuyRange;
	private JTextField txtMaxBuyRange;
	private JTextField txtMinSellRange;
	private JTextField txtMaxSellRange;
	private JTextField txtTradingHours;
	private JTextField txtTradingMinutes;
	private JTextField txtMaxOffers;
	private JTextField txtRejectionSeconds;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsWindow frame = new SettingsWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SettingsWindow() {
		//Frame Initialization
		setTitle("Home Agent Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Back button functionality
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(562, 367, 112, 23);
		contentPane.add(btnBack);
		
		//Appliance Label
		JLabel lblAppliance = new JLabel("Appliances");
		lblAppliance.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAppliance.setBounds(10, 11, 198, 33);
		contentPane.add(lblAppliance);
		
		//Save button functionality
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(562, 309, 112, 47);
		contentPane.add(btnSave);
		
		//Buy range label
		JLabel lblBuyRange = new JLabel("Buy Range");
		lblBuyRange.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblBuyRange.setBounds(272, 23, 112, 23);
		contentPane.add(lblBuyRange);
		
		//Buy min label
		JLabel lblMinBuyRange = new JLabel("Min");
		lblMinBuyRange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMinBuyRange.setBounds(272, 55, 46, 14);
		contentPane.add(lblMinBuyRange);
		
		//Buy min text box <-- stores the minimum buy range
		txtMinBuyRange = new JTextField();
		txtMinBuyRange.setToolTipText("The minimum price that the house will buy electricity at.");
		txtMinBuyRange.setBounds(272, 75, 86, 20);
		contentPane.add(txtMinBuyRange);
		txtMinBuyRange.setColumns(10);
		
		//Buy max label
		JLabel lblMaxBuyRange = new JLabel("Max");
		lblMaxBuyRange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMaxBuyRange.setBounds(403, 55, 46, 14);
		contentPane.add(lblMaxBuyRange);
		
		//Buy max text box <-- stores the maximum buy range
		txtMaxBuyRange = new JTextField();
		txtMaxBuyRange.setToolTipText("The Maximum price that the house will buy electricity at");
		txtMaxBuyRange.setBounds(403, 75, 86, 20);
		contentPane.add(txtMaxBuyRange);
		txtMaxBuyRange.setColumns(10);
		
		//Sell range label
		JLabel lblSellRange = new JLabel("Sell Range");
		lblSellRange.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSellRange.setBounds(272, 106, 112, 23);
		contentPane.add(lblSellRange);
		
		//Sell min label
		JLabel lblMinSellRange = new JLabel("Min");
		lblMinSellRange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMinSellRange.setBounds(272, 140, 46, 14);
		contentPane.add(lblMinSellRange);
		
		//Sell min text box <-- stores the minimum sell range
		txtMinSellRange = new JTextField();
		txtMinSellRange.setToolTipText("The Minimum the house will sell electricity at");
		txtMinSellRange.setBounds(272, 165, 86, 20);
		contentPane.add(txtMinSellRange);
		txtMinSellRange.setColumns(10);
		
		//Sell max label
		JLabel lblMaxSellRange = new JLabel("Max");
		lblMaxSellRange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMaxSellRange.setBounds(403, 140, 46, 14);
		contentPane.add(lblMaxSellRange);
		
		//Sell max text box <-- stores the maximum sell range
		txtMaxSellRange = new JTextField();
		txtMaxSellRange.setToolTipText("The Maximum price the house will sell electricity at");
		txtMaxSellRange.setBounds(403, 165, 86, 20);
		contentPane.add(txtMaxSellRange);
		txtMaxSellRange.setColumns(10);
		
		//Automatic interval label
		JLabel lblTimeInterval = new JLabel("Automatic Buy/Sell time interval");
		lblTimeInterval.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTimeInterval.setBounds(272, 235, 278, 41);
		contentPane.add(lblTimeInterval);
		
		//Automatic trading checkbox settings
		JCheckBox chkAutomaticTrading = new JCheckBox("Enable Automatic Trading");
		chkAutomaticTrading.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//This makes the trading hours and minutes textboxes enabled or disabled
				//Depending on the state of the checkbox
				boolean selected = chkAutomaticTrading.isSelected();
				txtTradingHours.setEnabled(selected);
				txtTradingHours.setEditable(selected);
				
				txtTradingMinutes.setEnabled(selected);
				txtTradingMinutes.setEditable(selected);
			}
		});
		chkAutomaticTrading.setToolTipText("Check to enable the house to automatically buy and sell electricity");
		chkAutomaticTrading.setSelected(true);
		chkAutomaticTrading.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chkAutomaticTrading.setBounds(272, 275, 181, 23);
		contentPane.add(chkAutomaticTrading);
		
		//Trading hours label
		JLabel lblTradingHours = new JLabel("Hours");
		lblTradingHours.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTradingHours.setBounds(272, 309, 46, 14);
		contentPane.add(lblTradingHours);
		
		//Trading hours text box <-- Stores how many hours there will be in between the house automatically trading electricity
		txtTradingHours = new JTextField();
		txtTradingHours.setToolTipText("The number of hours before the house will automatically trade with vendors");
		txtTradingHours.setBounds(272, 331, 86, 20);
		contentPane.add(txtTradingHours);
		txtTradingHours.setColumns(10);
		
		//Trading minutes label
		JLabel lblTradingMinutes = new JLabel("Minutes");
		lblTradingMinutes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTradingMinutes.setBounds(403, 311, 56, 14);
		contentPane.add(lblTradingMinutes);
		
		//Trading minutes text box <-- Stores how many minutes there will be in between the house automatically trading electricity
		txtTradingMinutes = new JTextField();
		txtTradingMinutes.setToolTipText("The number of minutes before the house will automatically trade with vendors");
		txtTradingMinutes.setBounds(403, 331, 86, 20);
		contentPane.add(txtTradingMinutes);
		txtTradingMinutes.setColumns(10);
		
		//Termination condition label
		JLabel lblTerminationCondition = new JLabel("Termination Conditions");
		lblTerminationCondition.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTerminationCondition.setBounds(10, 250, 198, 14);
		contentPane.add(lblTerminationCondition);
		
		//Maximum number of offers label
		JLabel lblMaxOffers = new JLabel("Maximum offers");
		lblMaxOffers.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaxOffers.setBounds(10, 273, 127, 23);
		contentPane.add(lblMaxOffers);
		
		//Maximum number of offers text box <-- stores how many offers a vendor can make before the house rejects their offers
		txtMaxOffers = new JTextField();
		txtMaxOffers.setToolTipText("How many times a vender can make an offer before the house rejects their offers completly");
		txtMaxOffers.setBounds(122, 276, 86, 20);
		contentPane.add(txtMaxOffers);
		txtMaxOffers.setColumns(10);
		
		//Time before rejection label
		JLabel lblOfferWaitInterval = new JLabel("Time before rejection");
		lblOfferWaitInterval.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOfferWaitInterval.setBounds(10, 307, 198, 14);
		contentPane.add(lblOfferWaitInterval);
		
		//Time before rejection text box <-- stores the number of seconds the house will wait for a vendors offer before rejecting it
		txtRejectionSeconds = new JTextField();
		txtRejectionSeconds.setToolTipText("The number of seconds the house will wait for a vendors response before rejecting their offer.");
		txtRejectionSeconds.setBounds(10, 334, 86, 20);
		contentPane.add(txtRejectionSeconds);
		txtRejectionSeconds.setColumns(10);
		
		//Seconds label
		JLabel lblRejectionSeconds = new JLabel("Seconds");
		lblRejectionSeconds.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRejectionSeconds.setBounds(106, 337, 86, 14);
		contentPane.add(lblRejectionSeconds);
		
		//Appliance list, this list will store the appliances and whether or not they are enabled
		JScrollPane scrlPneApplianceList = new JScrollPane();
		scrlPneApplianceList.setBounds(20, 55, 181, 173);
		contentPane.add(scrlPneApplianceList);
		
		tblApplianceList = new JTable();
		tblApplianceList.setToolTipText("This list contains all of the appliances that the house knows about. Here you can enable or disable them, causing them to not consume or generate electricity.");
		scrlPneApplianceList.setViewportView(tblApplianceList);
		tblApplianceList.setModel(new DefaultTableModel(
			new Object[][] {
				{null, Boolean.TRUE},
			},
			new String[] {
				"Appliance Name", "Enabled?"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tblApplianceList.getColumnModel().getColumn(0).setPreferredWidth(92);
		tblApplianceList.getColumnModel().getColumn(1).setPreferredWidth(65);
		tblApplianceList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblPerUnit = new JLabel("($ per unit)");
		lblPerUnit.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPerUnit.setBounds(377, 30, 72, 14);
		contentPane.add(lblPerUnit);
		
		JLabel label = new JLabel("($ per unit)");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(377, 112, 72, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("$");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(258, 167, 14, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("$");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(389, 168, 14, 14);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("$");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_3.setBounds(258, 78, 14, 14);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("$");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_4.setBounds(389, 78, 14, 14);
		contentPane.add(label_4);
	}
}
