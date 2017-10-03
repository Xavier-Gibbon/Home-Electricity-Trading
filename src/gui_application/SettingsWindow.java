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
		setTitle("Home Agent Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(585, 367, 89, 23);
		contentPane.add(btnBack);
		
		JLabel lblAppliance = new JLabel("Appliances");
		lblAppliance.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAppliance.setBounds(10, 11, 198, 33);
		contentPane.add(lblAppliance);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(585, 333, 89, 23);
		contentPane.add(btnSave);
		
		JLabel lblBuyRange = new JLabel("Buy Range");
		lblBuyRange.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblBuyRange.setBounds(272, 23, 112, 23);
		contentPane.add(lblBuyRange);
		
		JLabel lblMinBuyRange = new JLabel("Min");
		lblMinBuyRange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMinBuyRange.setBounds(272, 55, 46, 14);
		contentPane.add(lblMinBuyRange);
		
		txtMinBuyRange = new JTextField();
		txtMinBuyRange.setToolTipText("The minimum price that the house will buy electricity at");
		txtMinBuyRange.setBounds(272, 75, 86, 20);
		contentPane.add(txtMinBuyRange);
		txtMinBuyRange.setColumns(10);
		
		JLabel lblMaxBuyRange = new JLabel("Max");
		lblMaxBuyRange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMaxBuyRange.setBounds(403, 55, 46, 14);
		contentPane.add(lblMaxBuyRange);
		
		txtMaxBuyRange = new JTextField();
		txtMaxBuyRange.setToolTipText("The Maximum price that the house will buy electricity at");
		txtMaxBuyRange.setBounds(403, 75, 86, 20);
		contentPane.add(txtMaxBuyRange);
		txtMaxBuyRange.setColumns(10);
		
		JLabel lblSellRange = new JLabel("Sell Range");
		lblSellRange.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSellRange.setBounds(272, 106, 112, 23);
		contentPane.add(lblSellRange);
		
		JLabel lblMinSellRange = new JLabel("Min");
		lblMinSellRange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMinSellRange.setBounds(272, 140, 46, 14);
		contentPane.add(lblMinSellRange);
		
		txtMinSellRange = new JTextField();
		txtMinSellRange.setToolTipText("The Minimum the house will sell electricity at");
		txtMinSellRange.setBounds(272, 165, 86, 20);
		contentPane.add(txtMinSellRange);
		txtMinSellRange.setColumns(10);
		
		JLabel lblMaxSellRange = new JLabel("Max");
		lblMaxSellRange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMaxSellRange.setBounds(403, 140, 46, 14);
		contentPane.add(lblMaxSellRange);
		
		txtMaxSellRange = new JTextField();
		txtMaxSellRange.setToolTipText("The Maximum price the house will sell electricity at");
		txtMaxSellRange.setBounds(403, 165, 86, 20);
		contentPane.add(txtMaxSellRange);
		txtMaxSellRange.setColumns(10);
		
		JLabel lblTimeInterval = new JLabel("Automatic Buy/Sell time interval");
		lblTimeInterval.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTimeInterval.setBounds(272, 235, 278, 41);
		contentPane.add(lblTimeInterval);
		
		txtTradingHours = new JTextField();
		txtTradingHours.setToolTipText("The number of hours before the house will automatically trade with vendors");
		txtTradingHours.setBounds(272, 331, 86, 20);
		contentPane.add(txtTradingHours);
		txtTradingHours.setColumns(10);
		
		txtTradingMinutes = new JTextField();
		txtTradingMinutes.setToolTipText("The number of minutes before the house will automatically trade with vendors");
		txtTradingMinutes.setBounds(403, 331, 86, 20);
		contentPane.add(txtTradingMinutes);
		txtTradingMinutes.setColumns(10);
		
		JLabel lblTradingHours = new JLabel("Hours");
		lblTradingHours.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTradingHours.setBounds(272, 309, 46, 14);
		contentPane.add(lblTradingHours);
		
		JLabel lblTerminationCondition = new JLabel("Termination Conditions");
		lblTerminationCondition.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTerminationCondition.setBounds(10, 250, 198, 14);
		contentPane.add(lblTerminationCondition);
		
		JLabel lblTradingMinutes = new JLabel("Minutes");
		lblTradingMinutes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTradingMinutes.setBounds(403, 311, 56, 14);
		contentPane.add(lblTradingMinutes);
		
		JLabel lblMaxOffers = new JLabel("Maximum offers");
		lblMaxOffers.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaxOffers.setBounds(10, 273, 127, 23);
		contentPane.add(lblMaxOffers);
		
		txtMaxOffers = new JTextField();
		txtMaxOffers.setToolTipText("How many times a vender can make an offer before the house rejects their offers completly");
		txtMaxOffers.setBounds(122, 276, 86, 20);
		contentPane.add(txtMaxOffers);
		txtMaxOffers.setColumns(10);
		
		JLabel lblOfferWaitInterval = new JLabel("Time before rejection");
		lblOfferWaitInterval.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOfferWaitInterval.setBounds(10, 307, 198, 14);
		contentPane.add(lblOfferWaitInterval);
		
		txtRejectionSeconds = new JTextField();
		txtRejectionSeconds.setBounds(10, 334, 86, 20);
		contentPane.add(txtRejectionSeconds);
		txtRejectionSeconds.setColumns(10);
		
		JLabel lblRejectionSeconds = new JLabel("Seconds");
		lblRejectionSeconds.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRejectionSeconds.setBounds(106, 337, 86, 14);
		contentPane.add(lblRejectionSeconds);
		
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
		
		JScrollPane scrlPneApplianceList = new JScrollPane();
		scrlPneApplianceList.setBounds(20, 55, 181, 173);
		contentPane.add(scrlPneApplianceList);
		
		tblApplianceList = new JTable();
		scrlPneApplianceList.setViewportView(tblApplianceList);
		tblApplianceList.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
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
	}
}
