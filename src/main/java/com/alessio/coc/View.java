package com.alessio.coc;

import com.alessio.coc.models.Clan;
import com.alessio.coc.models.War;

import javax.swing.*;
import java.util.ArrayList;

public class View extends JFrame {

	private final Controller controller;
	private Clan clanInfo;
	private ArrayList<War> wars;

	MyFrame frame;

	public View(Constants constants) {

		ClashOfClansAPI api = new ClashOfClansAPI(constants);
		this.controller = new Controller(api);

		this.frame = new MyFrame();
//		this.clanInfo = File.readClanInfoFromFile();
//		this.wars = File.readWarsInfoFromFile();
//		clanInfo = this.dataElaboration.fetchAndSaveClanMembersInfo();
//		wars = this.dataElaboration.fetchAndSaveWarInfo();

		/*
		TODO implementation for keyTyped method
		 https://www.educative.io/answers/how-to-listen-to-and-take-action-on-keyboard-strokes-in-java
		 https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
		 https://stackoverflow.com/questions/52550613/how-to-call-function-keypress-in-the-main-java
		 https://www.google.com/search?client=safari&rls=en&q=java+execute+function+every+keystroke&ie=UTF-8&oe=UTF-8
		    */
	}


}
