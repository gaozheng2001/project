import Run.Space;
import Thing.Ball;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class NBody {

    public static void main(String[] args) {
        try {
            String path = args[0];
            //String path = "C:/Users/11520/Desktop/testdata (2)/body3-ter.txt";
            //String path = "C:/Users/11520/Desktop/testdata (2)/chain-ter.txt";
            In in = new In(path);

            String s = in.readLine();
            boolean showwindoe;
            showwindoe = s.equalsIgnoreCase("gui");


            int length = in.readInt();

            int BallNumber = in.readInt();
            Ball[] balls = new Ball[BallNumber];
            for (int i = 0;i < BallNumber; i++){
                balls[i]  = new Ball(in.readDouble()/length, in.readDouble()/length,
                        in.readDouble()/length, in.readDouble()/length,
                        in.readDouble(), in.readDouble()/length,
                        in.readInt(), in.readInt(), in.readInt());
            }

            //打印标准输出
            int questnumber = in.readInt();
            double[][] questions = new double[questnumber][2];
            for (int i = 0;i < questnumber; i++){
                questions[i][0] = in.readDouble();
                questions[i][1] = in.readInt();
            }
            Space runing = new Space(balls, length, questions, showwindoe);
            runing.run();
        }catch (Exception e){
            e.printStackTrace();
        }

    }




}
