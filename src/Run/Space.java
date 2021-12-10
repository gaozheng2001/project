package Run;

import Thing.Ball;
import Thing.Force;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import static java.lang.Math.*;

public class Space {
    public Ball[] balls;
    public Force[][] forces;
    public int boardlength;
    public int rate = 1; // GUI刷新频率：1ms
    public double time = 0;//绝对时间
    public double[][] questionslist;
    public boolean show ;


    public Space(Ball[] balls, int BoardLength, double[][] questionslist, boolean show){
        this.balls = balls;
        this.forces = CreateForce();
        this.boardlength = BoardLength;
        this.questionslist = questionslist;
        this.show = show;
    }

    public Force[][] CreateForce(){
        int n = balls.length;
        Force[][] forces = new Force[n][n];
        for(int i =0 ; i < n-1 ; i++){
            for (int j = i+1; j<n ; j++){
                forces[i][j] = new Force(balls[i] , balls[j]);
            }
        }
        return forces;
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

    /**
     * 针对碰撞过程中小球的坐标和速度的更新
     * 坐标变化：小球相互嵌入式零七回退至两球刚好相切
     * 速度更新：将小球速度坐标系变为两小球球心连线为横坐标。
     * @param ball1 第一个小球
     * @param ball2 另一个小球
     */
    public void collide(Ball ball1,Ball ball2){

        //第一步：先判断两个球的位置
        if (sqrt(getDistance(ball1,ball2))==0)
            return;
        if (sqrt(getDistance(ball1,ball2)) > (ball1.getBALL_R() + ball2.getBall_R()))
            return;

        //第二步：计算合速度的大小和方向
        //计算ball1的合速度的值（不包含方向）
        double ball1v = sqrt((ball1.getVelocityX()*ball1.getVelocityX()) + (ball1.getVelocityY()*ball1.getVelocityY()));
        //计算ball1的运行角度（运行方向，【-pi~pi】）
        double ball1_angle = acos(ball1.getVelocityX()/ball1v*1);
        if (ball1.getVelocityY() != 0)
            ball1_angle *= ball1.getVelocityY()/abs(ball1.getVelocityY());

        //计算ball2的合速度的值
        double ball2v = sqrt(ball2.getVelocityX()*ball2.getVelocityX()+ball2.getVelocityY()*ball2.getVelocityY());
        //计算ball2的运行角度
        double ball2_angle = acos(ball2.getVelocityX()/ball2v*1);
        if (ball2.getVelocityY() != 0)
            ball2_angle *= ball2.getVelocityY()/abs(ball2.getVelocityY());
        //第三步：计算共同坐标系s，以及ball1和ball2在s中的速度的大小和方向
        //第三步：第一部分：计算共同坐标系s的参数
        //计算ball1球心相对于ball2球心的坐标（注意y轴为数学上的y轴，与计算机中的y轴相反）
        double sx = ball2.getBallRX()-ball1.getBallRX();
        double sy = ball2.getBallRY()-ball1.getBallRY();
        //计算ball2相对于ball1的速度方向和大小
        double sv = sqrt(pow(sx,2)+pow(sy,2));
        double s_angle = acos(sx/sv);
        if (sy !=0)
            s_angle *=sy/abs(sy);

        //第三步：第二部分：解决粘连问题，或者检测到碰撞时已经相交，让两个球分离
        //此时ball1的半径加ball2的半径等于两球心的距离时算相切
        //两球相交（或两球粘连）
        if (sqrt(getDistance(ball1, ball2))<(ball1.getBALL_R() + ball2.getBall_R())){
            double de = ((ball1.getBALL_R() + ball2.getBall_R())-sqrt(getDistance(ball1, ball2)))/2;
            double dxx = de * cos(s_angle);
            double dyy = de * sin(s_angle);
            double ball1_lx = ball1.getBallRX();
            ball1_lx -= dxx ;
            ball1.setBallRX(ball1_lx);
            double ball1_ly = ball1.getBallRY();
            ball1_ly -= dyy ;
            ball1.setBallRY(ball1_ly);
            double ball2_lx = ball2.getBallRX();
            ball2_lx += dxx ;
            ball2.setBallRX(ball2_lx);
            double ball2_ly = ball2.getBallRY();
            ball2_ly += dyy ;
            ball2.setBallRY(ball2_ly);


        }
        //第三步：第三部分：将ball1,ball2的速度大小和方向由原坐标系转化为在共同坐标系s中的速度大小和方向

        // 在s坐标系中ball1的新运动方向
        double s_ball1_angle = ball1_angle - s_angle;
        // 在s坐标系中ball1的新速度大小
        double s_ball1_v = ball1v;
        // 在s坐标系中ball1的新速度在s的x轴上的投影
        double s_ball1_vx = s_ball1_v * cos(s_ball1_angle);
        // 在s坐标系中ball1的新速度在s的y轴上的投影
        double s_ball1_vy = s_ball1_v * sin(s_ball1_angle);

        // 在s坐标系中ball2的新运动方向
        double s_ball2_angle = ball2_angle - s_angle;
        // 在s坐标系中ball2的新速度大小
        double s_ball2_v = ball2v;
        // 在s坐标系中ball2的新速度在s的x轴上的投影
        double s_ball2_vx = s_ball2_v * cos(s_ball2_angle);
        // 在s坐标系中ball2的新速度在s的y轴上的投影
        double s_ball2_vy = s_ball2_v * sin(s_ball2_angle);

        //第四步：发生完全弹性斜碰时，在s坐标系中，两球y轴速度不变，x轴速度满足完全弹性正碰（由动能定理和动量守恒推导）

        // 碰撞后ball1的s坐标系x轴的分速度
        double s_ball1_vxfinal = ((ball1.getMass() - ball2.getMass()) * s_ball1_vx + 2 * ball2.getMass() * s_ball2_vx) / (ball1.getMass()+ball2.getMass());
        // 碰撞后ball1的s坐标系y轴的分速度
        double s_ball1_vyfinal = s_ball1_vy;
        // 碰撞后ball2的s坐标系x轴的分速度
        double s_ball2_vxfinal = ((ball2.getMass()-ball1.getMass()) * s_ball2_vx + 2 * ball1.getMass() * s_ball1_vx) / (ball1.getMass()+ball2.getMass());
        // 碰撞后ball2的s坐标系y轴的分速度
        double s_ball2_vyfinal = s_ball2_vy;

        //第五步：计算两球发生碰撞后在s中的各自合速度大小和运动方向

        // 碰撞后ball1在s坐标系的合速度大小
        double s_ball1_vfinal = sqrt(pow(s_ball1_vxfinal, 2) + pow(s_ball1_vyfinal, 2));
        // 碰撞后ball1在s坐标系的运动方向
        double s_balll1_anglefinal = acos(s_ball1_vxfinal / s_ball1_vfinal);
        if (s_ball1_vxfinal == 0 && s_ball1_vyfinal == 0)
            s_balll1_anglefinal = 0;
        else if (s_ball1_vyfinal != 0)
            s_balll1_anglefinal *= s_ball1_vyfinal / abs(s_ball1_vyfinal);
        // 碰撞后ball2在s坐标系的合速度大小
        double s_ball2_vfinal = sqrt(pow(s_ball2_vxfinal, 2) + pow(s_ball2_vyfinal, 2));
        // 碰撞后ball2在s坐标系的运动方向
        double s_ball2_anglefinal = acos(s_ball2_vxfinal / s_ball2_vfinal);
        if (s_ball2_vxfinal == 0 && s_ball2_vyfinal == 0)
            s_ball2_anglefinal = 0;
        else if (s_ball2_vyfinal != 0)
            s_ball2_anglefinal *= s_ball2_vyfinal / abs(s_ball2_vyfinal);

        //第六步：将两球速度转化为原坐标系中的速度

        // 碰撞后ball1在原坐标系的合速度大小
        double final_ball1_v = s_ball1_vfinal;
        // 碰撞后ball1在原坐标系的运动方向
        double final_ball1_angle = s_balll1_anglefinal + s_angle;
        if (final_ball1_angle > PI)
            final_ball1_angle -= 2 * PI;
        else if (final_ball1_angle <= -PI)
            final_ball1_angle += 2 * PI;
        // 碰撞后ball1在原坐标系的合速度大小在x轴上的分量
        double final_ball1_vx = final_ball1_v * cos(final_ball1_angle);
        // 碰撞后ball1在原坐标系的合速度大小在y轴上的分量
        double final_ball1_vy = final_ball1_v * sin(final_ball1_angle) ;

        // 碰撞后ball2在原坐标系的合速度大小
        double final_ball2_v = s_ball2_vfinal;
        // 碰撞后ball2在原坐标系的运动方向
        double final_ball2_angle = s_ball2_anglefinal + s_angle;
        if (final_ball2_angle > PI)
            final_ball2_angle -= 2 * PI;
        else if (final_ball2_angle <= -PI)
            final_ball2_angle += 2 * PI;
        // 碰撞后ball2在原坐标系的合速度大小在x轴上的分量
        double final_ball2_vx = final_ball2_v * cos(final_ball2_angle);
        // 碰撞后ball2在原坐标系的合速度大小在y轴上的分量
        double final_ball2_vy = final_ball2_v * sin(final_ball2_angle) ;

        // 第七步：更新
        //ball1的x方向速度 = final_ball1_vx；
        ball1.setVelocityX(final_ball1_vx);
        //ball1的y方向速度 = final_ball1_vy；
        ball1.setVelocityY(final_ball1_vy);
        //ball2的x方向速度 = final_ball2_vx；
        ball2.setVelocityX(final_ball2_vx);
        //ball2的y方向速度 = final_ball2_vy；
        ball2.setVelocityY(final_ball2_vy);
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
                            collide(balls[i], balls[j]);
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

    /**
     *墙壁的碰撞检测与速度更新。
     */
    public void boardcheck(Ball ball) {
        if (checkboardx(ball))
            ball.setVelocityX(-ball.getVelocityX());
        if (checkboardy(ball))
            ball.setVelocityY(-ball.getVelocityY());
    }

    public void checktime(){
        for (double[] doubles : questionslist) {
            if (time - 0.5 * rate / 1000 < doubles[0] && time + 0.5 * rate / 1000 > doubles[0]) {
                int index = (int) doubles[1];
                if (!show) {
                    StdOut.printf("\n" + time + "\n");
                    StdOut.printf(balls[index].getBallRX() * boardlength + " " +
                            balls[index].getBallRY() * boardlength + " " +
                            balls[index].getVelocityX() * boardlength + " " +
                            balls[index].getVelocityY() * boardlength);
                }
            }
        }
    }

    public void repaint(Ball[] balls){
        int n =balls.length;
        for (int i =0; i <n; i++){
            for (int j = i+1; j < n; j++){
                forces[i][j].ChangeVelocityX();
                forces[i][j].ChangeVelocityY();
            }
        }
        checktime();
        if (show){
            StdDraw.clear();
            for (Ball ball : balls) {
                ball.draw(1);
            }
            //显示缓存  在界面上 将所有缓存上用bufferGraphics画完的图形只用一次用之前界面上的画笔g展现处理啊
            StdDraw.show();//0,65为图形左上角坐标     65为了不遮挡鼠标
        }

        for (Ball ball : balls) {
            boardcheck(ball);
            ballcheck(balls);
            ball.ballMove();
        }
        for (int i =0; i <n; i++){
            for (int j = i+1; j < n; j++){
                forces[i][j].setForce_x();
                forces[i][j].setForce_y();
            }
        }
        //每过10ms利用缓存将数组中全部的小球移动+画出+清屏
        try{
            //Thread.sleep(rate);
            time += rate/1000.0;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        if (show){
            StdDraw.setCanvasSize(800, 800);
            StdDraw.enableDoubleBuffering();
        }
        while(true) {
            repaint(balls);
        }
    }

    /**
     *调试
     */
    public static void main(String[] args) {
     Ball ball1 = new Ball(0.1,0.5,0,0,0.01,400000,0,250,0);
     Ball ball2 = new Ball(0.48,0.5,0,0,0.01,400000,250,0,0);
     Ball ball3 = new Ball(0.5,0.5,0,0,0.01,400000,250,0,250);
     Ball ball4 = new Ball(0.52,0.5,0,0,0.01,400000,100,100,0);
     //Ball ball5 = new Ball(0.54,0.5,0,0,0.01,10000,100,0,100);
     Ball[] balls = {ball1, ball2, ball3, ball4};
     //Force[][] forces = null;
     //Space space = new Space(balls, 800);
     //space.run(balls);
     // Ball ball1 = new Ball(0.2,0.2,0,0,0.15,2000000000,50,50,0);
     // Ball ball2 = new Ball(0.8,0.2,0,0,0.10,2000000000,50,0,50);
     //Ball[] balls = {ball1 , ball2};

     Space space = new Space(balls, 100, new double[1][2], true);
     space.run();

    }
}
