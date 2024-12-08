package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener
{
    private int dots;
    private Image apple;
    private Image dot;
    private Image head;
    
    private final int random_position=30;
    private final int all_dots=1230;
    private final int dot_size=10;
    
    private final int x[]=new int[all_dots];
    private final int y[]=new int[all_dots];
    
    private int apple_x;
    private int apple_y;
    private Timer timer;
    
    private boolean left=false;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;
    private boolean ingame=true;
    
    Board()
    {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400,400));
        setFocusable(true);
        
        loadImages();
        initGame();
    }
    public void loadImages()
    {
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        apple=i1.getImage();
        
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot=i2.getImage();
        
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head=i3.getImage();
    }
    public void initGame()
    {
        dots=3;
        for(int i=0;i<dots;i++)
        {
            y[i]=50;
            x[i]=(50-(i*dot_size));
        }
        locateApple();
        timer=new Timer(150,this);
        timer.start();
    }
    public void locateApple()
    {
        int r=(int)(Math.random()*random_position);
        apple_x=r*dot_size;
        r=(int)(Math.random()*random_position);
        apple_y=r*dot_size;
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(ingame)
        {
            g.drawImage(apple, apple_x, apple_y, this);
        
        for(int i=0;i<dots;i++)
        {
            if(i==0)
            {
                g.drawImage(head, x[i], y[i], this);
            }
            else
            {
                g.drawImage(dot,x[i],y[i],this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        }
        else
        {
            gameOver(g);
        }
    }
    public void gameOver(Graphics g)
    {
        String msg="Game Over!!!";
        Font f=new Font("Comic Sans MS",Font.BOLD,24);
        FontMetrics metrices=getFontMetrics(f);
        g.setColor(Color.red);
        g.setFont(f);
        g.drawString(msg,(400-metrices.stringWidth(msg))/2,400/2);
    }
    public void move()
    {
        for(int i=dots;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(left)
        {
            x[0]=x[0]-dot_size;
        }
        if(right)
        {
            x[0]=x[0]+dot_size;
        }
        if(down)
        {
            y[0]=y[0]+dot_size;
        }
        if(up)
        {
            y[0]=y[0]-dot_size;
        }
    }
    public void actionPerformed(ActionEvent ae)
    {
       if(ingame)
       {
            checkApple();
            checkCollision();
            move(); 
            repaint();
       }
    }
    public void checkCollision()
    {
        for(int i=dots;i>0;i--)
        {
            if((i>4)&&(x[0]==x[i])&&(y[0]==y[i]))
            {
                ingame=false;
            }
            if((y[0]>=340)||(y[0]<=10)||(x[0]>=362)||(x[0]<=10))
            {
                ingame=false;
            }
            if(!ingame)
            {
                timer.stop();
            }
        }
    }
    public void checkApple()
    {
        if((x[0]==apple_x) && (y[0]==apple_y))
        {
            dots++;
            locateApple();
        }
    }
    public class TAdapter extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT && (!right))
            {
                left=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_RIGHT && (!left))
            {
                right=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_UP && (!down))
            {
                left=false;
                up=true;
                right=false;
            }
            if(key==KeyEvent.VK_DOWN && (!up))
            {
                left=false;
                down=true;
                right=false;
            }
        }
    }
}
