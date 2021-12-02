package Run;

import Thing.Ball;
import Thing.Force;

public class Space {
    public Ball[] balls;
    public Force[][] forces;

    public Space(Ball[] balls, Force[][] forces, int BoaldLength){
        this.balls = balls;
        this.forces = forces;

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
}
