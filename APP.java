import javax.swing.*;
import java.awt.event.*;
public class APP
{
    public static void main(String[] args) throws Exception
    {
        int boardWidth=360;
        int boardHeight=640;

        JFrame frame = new JFrame("Flappy Bird");
      
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        

        FlappyBird flappybird =new FlappyBird();
        frame.add(flappybird);
        frame.pack();
        flappybird.requestFocus();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}