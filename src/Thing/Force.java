package Thing;

public class Force {
    public double rate = 1.0/1000;//单位s
    public static final double G = 6.67E-11;
    private Ball ball1;
    private Ball ball2;
    private double force_x;
    private double force_y;
    private double dx;
    private double dy;

    public double R;

    public Force(Ball ball1, Ball ball2){
        this.ball1 = ball1;
        this.ball2 = ball2;
        this.dx = ball1.getBallRX() - ball2.getBallRX();
        this.dy = ball1.getBallRY() - ball2.getBallRY();
        this.R = Math.sqrt(this.dx*this.dx + this.dy*this.dy);
        if (this.R < ball1.getBALL_R() + ball2.getBall_R())
            return;
        this.force_x = ForceX();
        this.force_y = ForceY();
    }

    public void setForce_x() {
        this.force_x = ForceX();
    }

    public void setForce_y() {
        this.force_y = ForceY();
    }

    private double ForceX(){
        return G * ball1.getMass() * ball2.getMass() * dx/(R * R * R);
    }

    private double ForceY(){
        return G * ball1.getMass() * ball2.getMass() * dy/(R * R * R);
    }

    /**
     * 更新小球的x轴向速度
     */
    public void ChangeVelocityX(int boardlength){
        double ball1_mess = ball1.getMass();
        double ball2_mess = ball2.getMass();
        double ball1_v = ball1.getVelocityX();
        double ball2_v = ball2.getVelocityX();

        ball1_v -= 100* rate * force_x / (ball1_mess * boardlength);
        ball2_v += 100* rate * force_x / (ball2_mess * boardlength);

        ball1.setVelocityX(ball1_v);
        ball2.setVelocityX(ball2_v);
    }

    /**
     * 更新小球的y轴向速度
     */
    public void ChangeVelocityY(int boardlength){
        double ball1_mess = ball1.getMass();
        double ball2_mess = ball2.getMass();
        double ball1_v = ball1.getVelocityY();
        double ball2_v = ball2.getVelocityY();

        ball1_v -= rate * force_y / (ball1_mess * boardlength);
        ball2_v += rate * force_y / (ball2_mess * boardlength);

        ball1.setVelocityY(ball1_v);
        ball2.setVelocityY(ball2_v);
    }

    public static void main(String[] args) {
    }
}
