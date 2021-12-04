package Run;
import Thing.*;
public class GameRun extends Thread{

    private int sleepTime = 3;
    private GameView gameView;

    private Ball ball;

    public GameRun(GameView gameView,  Ball ball) {

        this.gameView = gameView;

        this.ball = ball;
    }

    public void run() {

        while(true) {

                gameView.repaint();
                ball.ballMove();

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }


        }
    }

