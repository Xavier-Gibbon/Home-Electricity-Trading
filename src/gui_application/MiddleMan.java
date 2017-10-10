package gui_application;

import gui_application.MainMenu;

public final class MiddleMan {
	private MiddleMan() {}
	private static MainMenu theWindow = null;
	
	public static void SetMenu(MainMenu theMenu)
	{
		theWindow = theMenu;
		SendMessageToMenu("I have done it!\nEverything is alright.");
	}
	
	//Any agent that wants to send its messages to the console can do so by calling this method
	public static void SendMessageToMenu(String theMessage)
	{
		theWindow.RecieveMessage(theMessage);
	}
}
