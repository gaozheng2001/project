package Thing;

import java.awt.*;
import edu.princeton.cs.algs4.StdDraw;

public class Ball {


    public double rate = 0.001;
    private double ballRX;
    private double ballRY;
    private double BALL_R;
    private Color color;
    private double mass;

    private double velocityX;
    private double velocityY;

    public Ball(double ballRX, double ballRY, double velocityX, double velocityy, double ball_R, double mass,
                int r, int g, int b) {
        this.setBallRX(ballRX);
        this.setBallRY(ballRY);
        this.setVelocityX(velocityX);
        this.setVelocityY(velocityy);

        this.setBALL_R(ball_R);
        this.setMass(mass);

        this.setBALL_COLOR(r, g, b);

    }

    public void setBALL_R(double ball_r){
        this.BALL_R = ball_r;
    }

    public double getBall_R(){
        return this.BALL_R;
    }

    public void setMass(double mass){
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getBallRX() {
        return ballRX;
    }

    public void setBallRX(double ballRX) {
        this.ballRX = ballRX;
    }

    public double getBallRY() {
        return ballRY;
    }

    public void setBallRY(double ballRY) {
        this.ballRY = ballRY;
    }

    public Color getBALL_COLOR() {
        return color;
    }

    public void setBALL_COLOR(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }

    public double getBALL_R() {
        return BALL_R;
    }

    public double Velocity(){
        return Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
    }

    public void velocityChange(double acceleratedX, double acceleratedY){
        velocityX = velocityX + rate*acceleratedX;
        velocityY = velocityY + rate*acceleratedY;
    }

    public void ballMove() {
            ballRX = ballRX + rate*velocityX;
            ballRY = ballRY + rate*velocityY;
        }
    public void draw(int boardlength) {
        //传入参数待修改
        StdDraw.setPenColor(getBALL_COLOR());
        StdDraw.filledCircle(getBallRX()/boardlength ,getBallRY()/boardlength , getBALL_R()/boardlength);

    }

    }


