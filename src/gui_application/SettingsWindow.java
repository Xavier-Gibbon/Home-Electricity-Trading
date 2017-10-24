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

import home_electricity_agents.HomeAgent;

import jade.core.AID;
import java.util.*;

import jade.core.Runtime;

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
	private JTextField txtTradingSeconds;
	private JCheckBox chkAutomaticTrading;

	/**
	 * Launch the application.
	 */
	private static void main(String[] args) {
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
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveForm();
			}
		});
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
		chkAutomaticTrading = new JCheckBox("Enable Automatic Trading");
		chkAutomaticTrading.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//This makes the trading hours and minutes textboxes enabled or disabled
				//Depending on the state of the checkbox
				boolean selected = chkAutomaticTrading.isSelected();
				txtTradingHours.setEnabled(selected);
				txtTradingHours.setEditable(selected);
				
				txtTradingMinutes.setEnabled(selected);
				txtTradingMinutes.setEditable(selected);
				
				txtTradingSeconds.setEnabled(selected);
				txtTradingSeconds.setEnabled(selected);
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
		txtTradingHours.setBounds(272, 331, 64, 20);
		contentPane.add(txtTradingHours);
		txtTradingHours.setColumns(10);
		
		//Trading minutes label
		JLabel lblTradingMinutes = new JLabel("Minutes");
		lblTradingMinutes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTradingMinutes.setBounds(358, 309, 56, 14);
		contentPane.add(lblTradingMinutes);
		
		//Trading minutes text box <-- Stores how many minutes there will be in between the house automatically trading electricity
		txtTradingMinutes = new JTextField();
		txtTradingMinutes.setToolTipText("The number of minutes before the house will automatically trade with vendors");
		txtTradingMinutes.setBounds(346, 331, 64, 20);
		contentPane.add(txtTradingMinutes);
		txtTradingMinutes.setColumns(10);
		
		//Trading seconds label
		JLabel lblTradingSeconds = new JLabel("Seconds");
		lblTradingSeconds.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTradingSeconds.setBounds(444, 309, 56, 14);
		contentPane.add(lblTradingSeconds);
		
		//Trading seconds text box <-- Stores how many seconds there will be in between the house automatically trading electricity
		txtTradingSeconds = new JTextField();
		txtTradingSeconds.setToolTipText("The number of seconds before the house will automatically trade with vendors");
		txtTradingSeconds.setColumns(10);
		txtTradingSeconds.setBounds(443, 331, 64, 20);
		contentPane.add(txtTradingSeconds);
		
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
			},
			new String[] {
				"Appliance Object", "Appliance Name", "Enabled?"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, String.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblApplianceList.getColumnModel().getColumn(0).setResizable(false);
		tblApplianceList.getColumnModel().getColumn(0).setPreferredWidth(15);
		tblApplianceList.getColumnModel().getColumn(1).setPreferredWidth(92);
		tblApplianceList.getColumnModel().getColumn(2).setPreferredWidth(65);
		tblApplianceList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		
		//Dollar dollar bills yo
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
		
		
		this.FillForm();
	}
	
	/*
	 * Gets information from the home agent and fills the form with its info
	 */
	public void FillForm()
	{
		HomeAgent theAgent = MiddleMan.GetHomeAgent();
		
		txtMaxBuyRange.setText(Integer.toString(theAgent.acceptableBuyMax));
		txtMinBuyRange.setText(Integer.toString(theAgent.acceptableBuyMin));
		txtMaxSellRange.setText(Integer.toString(theAgent.acceptableSellMax));
		txtMinSellRange.setText(Integer.toString(theAgent.acceptableSellMin));
		
		chkAutomaticTrading.setSelected(theAgent.doesAutomaticTrade);
		int tradingSeconds = theAgent.timeBetweenTrade;
		int tradingMinutes = 0;
		int tradingHours = 0;
		
		if (tradingSeconds >= 60)
		{
			int i = tradingSeconds % 60;
			tradingSeconds -= i;
			tradingMinutes = tradingSeconds / 60;
			tradingSeconds = i;
			
			if (tradingMinutes >= 60)
			{
				i = tradingMinutes % 60;
				tradingMinutes -= i;
				tradingHours = tradingMinutes / 60;
				tradingMinutes = i;
			}
		}
		
		txtTradingSeconds.setText(Integer.toString(tradingSeconds));
		txtTradingMinutes.setText(Integer.toString(tradingMinutes));
		txtTradingHours.setText(Integer.toString(tradingHours));
		
		txtMaxOffers.setText(Integer.toString(theAgent.maxOffers));
		txtRejectionSeconds.setText(Integer.toString(theAgent.timeBeforeRejection));
		
		Map<AID, Boolean> theAppliances = theAgent.GetAppliances();
		
		DefaultTableModel theTable = (DefaultTableModel)tblApplianceList.getModel();
		Set<AID> theIDs = theAppliances.keySet();
		for (AID id : theIDs)
		{
			theTable.addRow(new Object[] {
					id,
					id.getLocalName(),
					theAppliances.get(id)
			});
		}		
	}
	
	public void SaveForm()
	{
		HomeAgent theAgent = MiddleMan.GetHomeAgent();
		
		theAgent.acceptableBuyMax = Integer.parseInt(txtMaxBuyRange.getText());
		theAgent.acceptableBuyMin = Integer.parseInt(txtMinBuyRange.getText());
		theAgent.acceptableSellMax = Integer.parseInt(txtMaxSellRange.getText());
		theAgent.acceptableSellMin = Integer.parseInt(txtMinSellRange.getText());
		
		theAgent.doesAutomaticTrade = chkAutomaticTrading.isSelected();
		
		int tradingSeconds = Integer.parseInt(txtTradingSeconds.getText()) + Integer.parseInt(txtTradingMinutes.getText()) * 60 + Integer.parseInt(txtTradingHours.getText()) * 3600;
		
		theAgent.timeBetweenTrade = tradingSeconds;
		
		theAgent.maxOffers = Integer.parseInt(txtMaxOffers.getText());
		theAgent.timeBeforeRejection = Integer.parseInt(txtRejectionSeconds.getText());
		
		Map<AID, Boolean> theAppliances = new HashMap<AID, Boolean>();
		DefaultTableModel theTable = (DefaultTableModel)tblApplianceList.getModel();
		int rowCount = theTable.getRowCount();
		for (int i = 0; i < rowCount; i++)
		{
			theAppliances.put((AID)theTable.getValueAt(i, 0), (Boolean)theTable.getValueAt(i, 2));
		}
		
		theAgent.SetAppliancesStatus(theAppliances);
		
		
	}
}


