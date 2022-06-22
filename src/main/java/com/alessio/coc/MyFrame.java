package com.alessio.coc;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

	private JTextField textField;
	private JLabel label;

	public MyFrame() throws HeadlessException {
		super("CoCAPI");
		setLayout(new GridLayout(2, 2, 0, 20));

		setupFrame();
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
}
