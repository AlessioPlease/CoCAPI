package com.alessio.coc;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

	private JTextField textField;
	private JLabel label;

	Controller controller;

	public View(Controller controller) {
		super("CoCAPI");
		setLayout(new GridLayout(2, 2, 0, 20));
		this.controller = controller;

		setupView();
		setupListener();
		pack();
		setSize(800, 900);
		setVisible(true);
	}

	private void setupView() {
		// Create and add a label and a text field
		add(new JLabel("Name:"));
		this.textField = new JTextField("", 15);
		add(this.textField);
		add(new JLabel());
		// Create a label with central alignment
		// Put said label in a JScrollPane and add the pane
		this.label = new JLabel("");
		this.label.setHorizontalAlignment(SwingConstants.CENTER);
		JScrollPane scrollView = new JScrollPane(this.label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollView.getVerticalScrollBar().setUnitIncrement(3);
		scrollView.getHorizontalScrollBar().setUnitIncrement(2);
		add(scrollView);
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
