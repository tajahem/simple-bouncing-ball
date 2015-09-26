package simplebouncingball.input;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Listens for input and stores the last mouse input and an array of select
 * active keys
 * 
 * @author tajahem
 *
 */
public class InputListener implements KeyListener, MouseListener {

	/*
	 * The KeyListener doesn't work on a panel and adjusting the mouse input for
	 * window decorations on the frame is a pain; hence the two component
	 * constructor
	 */
	public InputListener(Component k, Component m) {
		k.addKeyListener(this);
		m.addMouseListener(this);
	}

	private Point lastClick;
	private InputType lastTyped;
	public Set<InputType> activeInputs = new HashSet<>();

	public boolean hasMouseInput() {
		return lastClick != null;
	}

	public boolean hasTypedInput() {
		return lastTyped != null;
	}

	public Point getLastClick() {
		Point temp = lastClick;
		lastClick = null;
		return temp;
	}

	public InputType getLastTyped() {
		InputType temp = lastTyped;
		lastTyped = null;
		return temp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char code = e.getKeyChar();
		if (code == '\u001B') {
			lastTyped = InputType.ESC;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_UP:
			activeInputs.add(InputType.UP);
			break;
		case KeyEvent.VK_DOWN:
			activeInputs.add(InputType.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			activeInputs.add(InputType.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			activeInputs.add(InputType.RIGHT);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_UP:
			activeInputs.remove(InputType.UP);
			break;
		case KeyEvent.VK_DOWN:
			activeInputs.remove(InputType.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			activeInputs.remove(InputType.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			activeInputs.remove(InputType.RIGHT);
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		lastClick = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		lastClick = e.getPoint();
	}

	// unused interface methods

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

}
