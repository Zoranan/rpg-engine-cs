package main;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import util.Handler;
import window.Display;

public class Launcher{
	private static Display MainEditor;
	private static MainPanel mainPanel;
	private static String[] fNames;
	private static String[] fRoots;
	
	public static void main(String[] args){
		//Initialize file names
		fNames = new String[] {"sprites.xml", 
				"environmentalObjects.xml"};
		
		fRoots = new String[] {"sprites", 
								"objects"};
		
		//Attempt to load preferences
		Handler.loadPrefs();
		
		//Make sure a valid root folder is selected
		while (isRootDirValid() == false)
		{
			//First attempt to load our saved directory from last time?
			int i = selRootOptionPane();
			
			//Select existing root
			if (i == 0)
			{
				Handler.setRootDirectory(".");
				Handler.selectRootFolder();
				
				//Check if the choice was valid
				if (isRootDirValid() == false)
					JOptionPane.showMessageDialog(null, Handler.getRootDirectory() + "\nThat folder is not a valid root", "Invalid Root", JOptionPane.ERROR_MESSAGE);
			}
			//Create a new root
			else if (i == 1)
			{
				createRootFolder();
			}
			//Only one other option: Exit
			else
			{
				System.exit(0);
			}
		}
		
		MainEditor = new Display("Main Editor", 1000, 600);
		MainEditor.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create common objects
		
		//Add the main panel to the window
		mainPanel = new MainPanel();
		MainEditor.setPanel(mainPanel);

	}
	
	
	//Checks is the root folder is valid
	public static boolean isRootDirValid()
	{		
		boolean b = true;
		
		for (int i=0; i<fNames.length; i++)
		{
			File file = new File(Handler.getRootDirectory() + "/" + fNames[i]);
			
			if (file.exists() == false || file.isDirectory())
				b = false;
		}
		
		
		return b;
	}
	
	//Shows an option pane prompting the user to select a root folder
	private static int selRootOptionPane()
	{
		String message = "You must select a root folder, or create a new one.";
		String[] options = {"Select Root Folder", "Create New Root", "Exit"}; 
		int i = JOptionPane.showOptionDialog(null, message, "Root folder needed", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		return i;
	}
	
	//Make a new root directory
	private static void createRootFolder()
	{
		
	}
}