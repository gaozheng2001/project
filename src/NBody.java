import Run.Space;
import Thing.Ball;
import edu.princeton.cs.algs4.In;

public class NBody {

    public static void main(String[] args) {
        try {
            //String path = args[0];
            String path = "C:/Users/11520/Desktop/testdata (2)/body3-ter.txt";
            //String path = "C:/Users/11520/Desktop/testdata (2)/chain-ter.txt";
            In in = new In(path);

            //读取命令参数控制是否显示GUI及打印问题结果
            String s = in.readLine();
            boolean showwindow;
            showwindow = s.equalsIgnoreCase("gui");

            //需模拟的空间边界长度
            int length = in.readInt();

            int BallNumber = in.readInt();
            Ball[] balls = new Ball[BallNumber];
            //根据输入生成小球对象时对其参数进行归一化
            for (int i = 0;i < BallNumber; i++){
                balls[i]  = new Ball(in.readDouble()/length, in.readDouble()/length,
                        in.readDouble()/length, in.readDouble()/length,
                        in.readDouble(), in.readDouble()/length,
                        in.readInt(), in.readInt(), in.readInt());
            }

            //记录问题列表
            int questnumber = in.readInt();
            double[][] questions = new double[questnumber][2];
            for (int i = 0;i < questnumber; i++){
                questions[i][0] = in.readDouble();
                questions[i][1] = in.readInt();
            }

            //生成模拟空间
            Space runing = new Space(balls, length, questions, showwindow);
            //开始模拟
            runing.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
