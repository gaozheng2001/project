package Run;

import Thing.Ball;
import Thing.Force;
import edu.princeton.cs.algs4.StdDraw;

public class Space {
    public Ball[] balls;
    public Force[][] forces;
    public int boardlength;
    public int rate = 1; // 刷新频率


    public Space(Ball[] balls, Force[][] forces, int BoardLength){
        this.balls = balls;
        this.forces = forces;
        this.boardlength = BoardLength;
    }

    public double getDistance(Ball ball1 , Ball ball2){
        return Math.pow(ball2.getBallRX() - ball1.getBallRX(), 2) +
                Math.pow(ball2.getBallRY() - ball1.getBallRY(), 2);
    }//返回两个球的距离的平方

    public boolean checkCrash(Ball ball1 , Ball ball2) {
        double distance = getDistance(ball1, ball2);
        return distance <= Math.pow(ball1.getBALL_R() + ball2.getBall_R(), 2);
    }

    /**
     *碰撞预测，只有慢速球在前的碰撞有效。
     * @param ball1 待测小球1
     * @param ball2 待测小球2
     * @return true 碰撞合法
     */
    public boolean predictcresh(Ball ball1, Ball ball2){
        double dx = ball1.getBallRX() - ball2.getBallRX();
        double dvx = ball1.getVelocityX() - ball2.getVelocityX();
        double dy = ball1.getBallRY() - ball2.getBallRY();
        double dvy = ball1.getVelocityY() - ball2.getVelocityY();
        return dx*dvx < 0 || dy*dvy < 0;
    }

    public void creshing(Ball ball1, Ball ball2){
        double dr = Math.sqrt(Math.pow(ball1.getBALL_R() + ball2.getBall_R(), 2) - getDistance(ball1, ball2));
        double dx = (ball1.getBallRX() - ball2.getBallRX());
        double dy = (ball1.getBallRY() - ball2.getBallRY());
        //1、2球的球心连线与x轴正方向的夹角，以2球为角的顶点。
        double angle = dx == 0 ? Math.acos(dx/dr) : Math.asin(dy/dr);

        //将嵌入另一个球的坐标回退至两球恰好相切的状态，默认速度大的球嵌入速度小的球
        //该部分仍需优化
        /*
        if (ball1.Velocity() > ball2.Velocity()){
            ball1.setBallRX(ball1.getBallRX() + (dx > 0 ? dx : -dx));
            ball1.setBallRY(ball1.getBallRY() + (dy > 0 ? dy : -dy));
        }
        ball2.setBallRX(ball2.getBallRX() + (dx > 0 ? -dx : dx));
        ball2.setBallRY(ball2.getBallRY() + (dy > 0 ? -dy : dy));
        */
        double v1x1 = ((ball1.getMass() - ball2.getMass())*ball1.getVelocityX() + 2*ball2.getMass()*ball2.getVelocityX())/(ball1.getMass() + ball2.getMass());
        double v1y1 = ((ball1.getMass() - ball2.getMass())*ball1.getVelocityY() + 2*ball2.getMass()*ball2.getVelocityY())/(ball1.getMass() + ball2.getMass());
        double v2x1 = ((ball2.getMass() - ball1.getMass())*ball2.getVelocityX() + 2*ball1.getMass()*ball1.getVelocityX())/(ball1.getMass() + ball2.getMass());
        double v2y1 = ((ball2.getMass() - ball1.getMass())*ball2.getVelocityY() + 2*ball1.getMass()*ball1.getVelocityY())/(ball1.getMass() + ball2.getMass());
        ball1.setVelocityX(v1x1);
        ball1.setVelocityY(v1y1);
        ball2.setVelocityX(v2x1);
        ball2.setVelocityY(v2y1);
    }

    /**
     * 小球间的碰撞检测与速度更新。
     * @param balls 包含所有小球的列表.
     */
    public void ballcheck(Ball[] balls){
        int[][] hascheck = new int[balls.length][balls.length];
        for (int i = 0;i < balls.length;i++){
            for (int j = 0;j < balls.length;j++){
                if (i == j) continue;
                if (checkCrash(balls[i], balls[j])){
                    if (hascheck[Math.min(i, j)][Math.max(i, j)] == 0){
                        if (predictcresh(balls[i], balls[j])){
                            creshing(balls[i], balls[j]);
                            hascheck[Math.min(i, j)][Math.max(i, j)] = 1;
                        }
                    }
                    break;
                }
            }
        }
    }

    public boolean checkboardx(Ball ball) {
        return ball.getBallRX() <= ball.getBALL_R() || ball.getBallRX() >= 1 - ball.getBall_R();
    }

    public boolean checkboardy(Ball ball) {
        return ball.getBallRY() <= ball.getBALL_R() || ball.getBallRY() >= 1 - ball.getBall_R();
    }

    /*
     *墙壁的碰撞检测与速度更新。
     */
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
            ball.draw(1);
        }
        //显示缓存  在界面上 将所有缓存上用bufferGraphics画完的图形只用一次用之前界面上的画笔g展现处理啊
        StdDraw.show();//0,65为图形左上角坐标     65为了不遮挡鼠标
        for (Ball ball : balls) {
            boardcheck(ball);
            ballcheck(balls);
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

    /*
     *调试
     */
    public static void main(String[] args) {
     Ball ball1 = new Ball(0.1,0.5,1,0,0.01,1,0,250,0);
     Ball ball2 = new Ball(0.48,0.5,0,0,0.01,1,250,0,0);
     Ball ball3 = new Ball(0.5,0.5,0,0,0.01,1,250,0,250);
     Ball ball4 = new Ball(0.52,0.5,0,0,0.01,1,100,100,0);
     Ball ball5 = new Ball(0.54,0.5,0,0,0.01,1,100,0,100);
     Ball[] balls = {ball1, ball2, ball3, ball4, ball5};
     Force[][] forces = null;
     Space space = new Space(balls, forces, 800);
     space.run(balls);

    }
}
