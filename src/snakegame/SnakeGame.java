package snakegame;

import javax.swing.*;

public class SnakeGame extends JFrame
{
    SnakeGame()
    {
        super("Snake Game");
        add(new Board());
        pack();//(refresh the page)reflect the changes on the frame
        
        setResizable(false);
        setLocationRelativeTo(null);
//        setBounds(400,100,400,400);
        setVisible(true);
    }

    public static void main(String[] args) 
    {
        new SnakeGame();
    }
    
}
