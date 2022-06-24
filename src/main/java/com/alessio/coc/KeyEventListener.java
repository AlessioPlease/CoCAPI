package com.alessio.coc;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyEventListener extends KeyAdapter {

	public KeyEventListener() {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_TYPED) {
			System.out.println("PRESSED \"" + e.getKeyChar() + "\"");
		}
	}
}
