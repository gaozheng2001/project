package Run;
import Thing.*;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameView extends Frame{
    int rate = 10; // 10ms
    Graphics g;
    Color color;
    Ball[] balls;
    //构造方法  传参
    public GameView(Color color, Ball[] balls) {
        this.g=g;
        this.color=color;
        this.balls=balls;
    }

    public void run() {
        StdDraw.setCanvasSize(800, 800);
        StdDraw.enableDoubleBuffering();
        while(true) {
            StdDraw.clear();
            int n =balls.length;
            for (Ball ball : balls) {
                //ball.draw();
            }
            //显示缓存  在界面上 将所有缓存上用bufferGraphics画完的图形只用一次用之前界面上的画笔g展现处理啊
            StdDraw.show();//0,65为图形左上角坐标     65为了不遮挡鼠标

            //每过10ms利用缓存将数组中全部的小球移动+画出+清屏
            try{
                Thread.sleep(rate);
            }catch(Exception ef) {};

        }
    }


}
