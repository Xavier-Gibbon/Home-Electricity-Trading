package gui_application;

import gui_application.MainMenu;
import jade.tools.sniffer.*;

public final class MiddleMan {
	private MiddleMan() {}
	private static MainMenu theWindow = null;
	
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
