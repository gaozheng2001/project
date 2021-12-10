package Thing;

public class Force {
    public double rate = 0.001;
    public static final double G = 6.67E-11;
    private Ball ball1;
    private Ball ball2;
    private double force_x;
    private double force_y;
    private double dx;
    private double dy;

    public double getForce_x() {
        return force_x;
    }

    public double getForce_y() {
        return force_y;
    }

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
        return G*this.ball1.getMass()*this.ball2.getMass()/(this.R*this.R*this.R);
    }

    private double ForceY(){
        return G*this.ball1.getMass()*this.ball2.getMass()/(this.R*this.R*this.R);
    }


    public void ChangeVelocityX(){
        double ball1_mess = ball1.getMass();
        double ball2_mess = ball2.getMass();
        double ball1_x = ball1.getBallRX();
        double ball2_x = ball2.getBallRX();
        double location = ball2_x - ball1_x;//计算相对位置
        double ball1_v = ball1.getVelocityX();
        double ball2_v = ball2.getVelocityX();
        if(location<0) {
            ball1_v = ball1_v - rate * force_x / ball1_mess;
            ball2_v = ball2_v + rate * force_x / ball2_mess;
        }
        if(location>0){
            ball1_v = ball1_v + rate * force_x / ball1_mess;
            ball2_v = ball2_v - rate * force_x / ball2_mess;
        }
        ball1.setVelocityX(ball1_v);
        ball2.setVelocityX(ball2_v);
    }

    public void ChangeVelocityY(){
        double ball1_mess = ball1.getMass();
        double ball2_mess = ball2.getMass();
        double ball1_y = ball1.getBallRY();
        double ball2_y = ball2.getBallRY();
        double location = ball2_y - ball1_y;
        double ball1_v = ball1.getVelocityY();
        double ball2_v = ball2.getVelocityY();
        if(location<0) {
            ball1_v = ball1_v - rate * force_y / ball1_mess;
            ball2_v = ball2_v + rate * force_y / ball2_mess;
        }
        if(location>0){
            ball1_v = ball1_v + rate * force_y / ball1_mess;
            ball2_v = ball2_v - rate * force_y / ball2_mess;
        }

        ball1.setVelocityY(ball1_v);
        ball2.setVelocityY(ball2_v);
    }


    public static void main(String[] args) {
    }

}
