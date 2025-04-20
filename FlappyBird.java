
import javax.swing.*;

import java.awt.Dimension;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;



public class FlappyBird extends JPanel implements ActionListener,KeyListener{

    int boardWidth=360;
    int boardHeight=640;
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;


    //bird

    int birdx=boardWidth/8;  
    int birdy=boardHeight/2;

    int birdwidth=34;
    int birdheight=24;


    class Bird{
        int x=birdx;
        int y= birdy;
        int width= birdwidth;
        int height=birdheight;
        Image img;
        Bird(Image img)
        {
            this.img = img;
        }


    }

    //pipes

    int pipeX=boardWidth;
    int pipeY=0;
    int pipwWidth=64;
    int pipeHeight=512;

    class Pipe{
        int x= pipeX;
        int y= pipeY;
        int width=pipwWidth;
        int height=pipeHeight;
        Image img ;
        boolean passed =false;

        Pipe(Image img)
        {
            this.img=img;
        }
    }



    // game logic
    Bird bird;
    int velocityX=-4;
    int velocityY=0;
    int gravity=1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer placepipesTimer;
    Timer gameLoop;
    double score =0;
    boolean gameover = false;

    FlappyBird()
    {
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        //setBackground(Color.blue);

        setFocusable(true);
        addKeyListener(this);

        //load image

        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg= new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg= new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdImg);
        pipes= new ArrayList<Pipe>();

        //place pipes
        placepipesTimer =new Timer(1500,new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                placepipe();
            }
        });

        placepipesTimer.start();

        //game loop
        gameLoop= new Timer(1000/60,this);
        gameLoop.start();
    }


    public void placepipe()
    {
        int randompipeY = (int)(pipeY-pipeHeight/4-Math.random()*(pipeHeight/2));
        int openingspace= boardHeight/4;

        Pipe toppipe = new Pipe(topPipeImg);
        toppipe.y=randompipeY;
        pipes.add(toppipe);

        Pipe bottomPipe= new Pipe(bottomPipeImg);
        bottomPipe.y=toppipe.y+pipeHeight+openingspace;
        pipes.add(bottomPipe);
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
 
    public void draw(Graphics g)
    {
    g.drawImage(backgroundImg,0,0,boardWidth,boardHeight,null);

    g.drawImage(birdImg,bird.x,bird.y,bird.width,bird.height,null);

    for(int i=0;i<pipes.size();i++)
    {
        Pipe pipe=pipes.get(i);
        g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
    }
    g.setColor(Color.white);
    g.setFont(new Font("Arial",Font.PLAIN,32));
    if (gameover)
    {
        g.drawString("Game Over :"+String.valueOf((int) score),10,35);
    }
    else{
        g.drawString(String.valueOf((int)score),10,35);
    }
        
    }

    public void move() 
    {
           //bird
           velocityY += gravity;
            bird.y +=velocityY;
            bird.y = Math.max(bird.y,0);

            for(int i=0;i<pipes.size();i++)
            {
                Pipe pipe =pipes.get(i);
                pipe.x += velocityX;
                if(!pipe.passed&& bird.x>pipe.x+pipe.width){
                    score += 0.5;
                    pipe.passed=true;
                
                }
            
                if(collision(bird,pipe))
                {
                    gameover=true;
                }
             
            }
            if(bird.y>boardHeight)
            {
                gameover=true;
            }
    }
    


    public  boolean collision(Bird a , Pipe b)
    {
            return a.x<b.x + b.width &&
                    a.x+a.width>b.x &&
                    a.y<b.y+b.height &&
                    a.y+a.height>b.y;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
       move();
       repaint();
       if(gameover)
       {
        placepipesTimer.stop();
        gameLoop.stop();
       }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_SPACE)
        {
            velocityY= -9;
        }

        if(gameover)
        {
            bird.y= birdy;
            velocityY=0;
            pipes.clear();
            gameover=false;
            score=0;
            gameLoop.start();
            placepipesTimer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
