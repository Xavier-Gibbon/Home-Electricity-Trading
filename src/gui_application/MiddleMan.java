package gui_application;

import gui_application.MainMenu;
import home_electricity_agents.HomeAgent;
import jade.tools.sniffer.*;

public final class MiddleMan {
	private MiddleMan() {}
	private static MainMenu theWindow = null;
	private static HomeAgent theHomeAgent = null;
	
	public static void SetHomeAgent(HomeAgent givenAgent)
	{
		theHomeAgent = givenAgent;
	}
	
	public static HomeAgent GetHomeAgent()
	{
		return theHomeAgent;
	}
	
	public static void SetMenu(MainMenu theMenu)
	{
		theWindow = theMenu;
		SendMessageToMenu("Menu Textbox is ready!");
	}
	
	//Any agent that wants to send its messages to the console can do so by calling this method
	public static void SendMessageToMenu(String theMessage)
	{
		if (theWindow != null)
		{
			theWindow.RecieveMessage(theMessage);
		}
		else
		{
			System.out.println(theMessage);
		}
	}
}
