import Run.Space;
import Thing.Ball;
import Thing.Force;
import edu.princeton.cs.algs4.In;

public class NBody {

    public static void main(String[] args) {
        try {
            String path = args[0];
            In in = new In(path);

            int length = in.readInt();
            int BallNumber = in.readInt();
            Ball[] balls = new Ball[BallNumber];
            for (int i = 0;i < BallNumber; i++){
                balls[i]  = new Ball(in.readDouble(), in.readDouble(),
                        in.readDouble(), in.readDouble(),
                        in.readDouble(), in.readDouble(),
                        in.readInt(), in.readInt(), in.readInt());
            }
            Force[][] forces = new Force[BallNumber][BallNumber];
            for (int i = 0;i < BallNumber;i++){
                for (int j = 0;j < BallNumber;j++){
                    if (i == j)    continue;
                    forces[i][j] = new Force(balls[i], balls[j]);
                }
            }
            //Space runing = new Space(balls, forces);



        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
