package com.alessio.coc;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

	private JTextField textField;
	private JLabel label;

	Controller controller;

	public MyFrame(Controller controller) {
		super("CoCAPI");
		setLayout(new GridLayout(2, 2, 0, 20));

		this.controller = controller;
		setupFrame();
		setupListener();
		pack();
		setVisible(true);
	}

	private void setupFrame() {
		add(new JLabel("Name:"));
		this.textField = new JTextField("", 15);
		add(this.textField);
		add(new JLabel());
		this.label = new JLabel("");
		add(this.label);
	}

	private void setupListener() {
		TextFieldListener listener = new TextFieldListener(this);
		this.textField.getDocument().addDocumentListener(listener);
	}

	public void keyTrigger() {
		setLabel(this.controller.searchMemberByName(this.textField.getText()));
	}

	public void setLabel(String text) {
		this.label.setText(text);
	}
}
