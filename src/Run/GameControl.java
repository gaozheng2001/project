package Run;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControl implements KeyListener {

    public static int state = 5;

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {

            case KeyEvent.VK_LEFT:
                state = 4;
                break;
            case KeyEvent.VK_RIGHT:
                state = 6;
                break;

            default:

                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        state = 5;
    }



}