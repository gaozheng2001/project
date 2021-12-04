package Run;

import Thing.Ball;
import Thing.Force;
import edu.princeton.cs.algs4.StdDraw;

public class Space {
    public Ball[] balls;
    public Force[][] forces;
    public int boardlength;
    public int rate = 10; // 10ms
    public Space(Ball[] balls, Force[][] forces ,  int BoardLength){
        this.balls = balls;
        this.forces = forces;
        this.boardlength = BoardLength;
    }

    public double getDistance(Ball ball1 , Ball ball2){
        double distance = Math.sqrt((ball2.getBallRX() - ball1.getBallRX()) * (ball2.getBallRX() - ball1.getBallRX()) + (ball2.getBallRY() - ball1.getBallRY()) * (ball2.getBallRY() - ball1.getBallRY()));
        return distance;
    }//返回两个球的距离

    public boolean checkCrash(Ball ball1 , Ball ball2) {
        double distance = getDistance(ball1, ball2);
        double r = 0.1; //r为允许误差，用于后期调试
        return (distance <= ball1.getBALL_R() + ball2.getBall_R() + r);
    }

    public boolean checkboardx(Ball ball) {
        return ball.getBallRX() <= ball.getBALL_R() || ball.getBallRX() >= boardlength - ball.getBall_R();
    }

    public boolean checkboardy(Ball ball) {
        return ball.getBallRY() <= ball.getBALL_R() || ball.getBallRY() >= boardlength - ball.getBall_R();
    }

    public void boardcheck(Ball ball) {
        if (checkboardx(ball))
            ball.setVelocityX(-ball.getVelocityX());
        if (checkboardy(ball))
            ball.setVelocityY(-ball.getVelocityY());
    }

    public void repaint(Ball[] balls){
        StdDraw.clear();
        int n =balls.length;
        for (Ball ball : balls) {
            ball.draw();
        }
        //显示缓存  在界面上 将所有缓存上用bufferGraphics画完的图形只用一次用之前界面上的画笔g展现处理啊
        StdDraw.show();//0,65为图形左上角坐标     65为了不遮挡鼠标
        for (Ball ball : balls) {
            ball.ballMove();
        }
        //每过10ms利用缓存将数组中全部的小球移动+画出+清屏
        try{
            Thread.sleep(rate);
        }catch(Exception ef) {};
    }

    public void run(Ball[] balls) {
        StdDraw.setCanvasSize(boardlength, boardlength);
        StdDraw.enableDoubleBuffering();
        while(true) {
            repaint(balls);
        }
    }

    public static void main(String[] args) {
     Ball ball1 = new Ball(0.1,0.5,0,0.1,0.03,1,50,50,50);
     Ball ball2 = new Ball(0.8,0.5,0,0.1,0.01,0.1,50,50,50);
     Ball[] balls = {ball1 , ball2};
     Force[][] forces = null;
     Space space = new Space(balls, forces, 800);
     space.run(balls);

    }
}
