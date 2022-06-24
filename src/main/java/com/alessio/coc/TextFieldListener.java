package com.alessio.coc;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextFieldListener implements DocumentListener {

	MyFrame frame;

	public TextFieldListener(MyFrame frame) {
		this.frame = frame;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		this.frame.keyTrigger();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.frame.keyTrigger();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}
}
