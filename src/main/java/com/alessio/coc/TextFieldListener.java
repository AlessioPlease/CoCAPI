package com.alessio.coc;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextFieldListener implements DocumentListener {

	View view;

	public TextFieldListener(View view) {
		this.view = view;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		this.view.keyTrigger();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.view.keyTrigger();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}
}
