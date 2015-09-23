package simplebouncingball.input;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

public class InputHandler implements KeyListener, MouseListener {

	public InputHandler(Component k, Component m) {
		// works better to attach a keylistener to a frame and a mouselistener
		// to a panel
		k.addKeyListener(this);
		m.addMouseListener(this);
	}

	private Point lastClick;
	public Set<Inputs> activeInputs = new HashSet<>();

	public boolean hasMouseInput() {
		return lastClick != null;
	}

	public Point getLastClick() {
		Point temp = lastClick;
		lastClick = null;
		return temp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			activeInputs.add(Inputs.ESC);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_UP:
			activeInputs.add(Inputs.UP);
			break;
		case KeyEvent.VK_DOWN:
			activeInputs.add(Inputs.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			activeInputs.add(Inputs.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			activeInputs.add(Inputs.RIGHT);
			break;
		case KeyEvent.VK_ESCAPE:
			activeInputs.add(Inputs.ESC);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_UP:
			activeInputs.remove(Inputs.UP);
			break;
		case KeyEvent.VK_DOWN:
			activeInputs.remove(Inputs.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			activeInputs.remove(Inputs.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			activeInputs.remove(Inputs.RIGHT);
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
