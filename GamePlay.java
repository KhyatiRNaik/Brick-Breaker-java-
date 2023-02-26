package BrickBreaker;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/* 
 * g is already used for Graphics obj
 */

public class GamePlay extends JPanel implements ActionListener, KeyListener{

    private boolean play = false;
    private Timer timer;
    private int delay = 5;
    private int score = 0;
    private int paddleSpeed = 25;
    private int totalBrick = 21;
    private int ballXpos = 100;
    private int ballYpos = 350;
    private int ballXdir = -3;
    private int ballYdir = -4;
    private int paddleX = 350;

    private MapGenerator map;


    public GamePlay(){
       addKeyListener(this);
       setFocusable(true);
       setFocusTraversalKeysEnabled(true);

       timer = new Timer(delay, this);
       timer.start();

       map = new MapGenerator(3, 7);
    }

    public void paint(Graphics g){
        //bg
        g.setColor(Color.black);
        g.fillRect(0, 0, 700, 600);

        //paddle
        g.setColor(Color.CYAN);
        g.fillRoundRect(paddleX, 520, 100, 20, 25, 25);

        //bricks
        map.draw((Graphics2D)g);

        //ball
        g.setColor(Color.MAGENTA);
        g.fillOval(ballXpos, ballYpos, 20, 20);

        //Score
        g.setColor(Color.YELLOW);
        g.setFont(new Font("monospace", Font.BOLD, 20));
        g.drawString("Score: " + score, 550, 30);

        //GameOver
        if(ballYpos>=570 || totalBrick<=0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setFont(new Font("monospace", Font.BOLD, 40));
            
            if(ballYpos>=570){
                g.setColor(Color.RED);
                g.drawString("GameOver!!!", 230, 300);
            }
            if(totalBrick <= 0){
                g.setColor(Color.GREEN);
                g.drawString("YOU WON!!!", 230, 300);
            }

            g.setFont(new Font("monospace", Font.PLAIN, 30));
            g.drawString("Score: "+score, 280, 350);

            g.setColor(Color.BLUE);
            g.setFont(new Font("monospace", Font.BOLD, 25));
            g.drawString("Press ENTER to restart", 210, 400);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(play){

            if(ballXpos <= 0){
                ballXdir = -ballXdir;
            }
             if(ballXpos >= 660){
                ballXdir = -ballXdir;
            }
             if(ballYpos <= 0){
                ballYdir = -ballYdir;
            }

            Rectangle ballRect =  new Rectangle(ballXpos, ballYpos, 20, 20);
            Rectangle paddleRect = new Rectangle(paddleX, 520, 100, 20);

            if(ballRect.intersects(paddleRect)){
                ballYdir =- ballYdir;
            }

            A:for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {

                    if(map.map[i][j] > 0){
                        int brickHeight = map.brickHeight;
                        int brickWidth = map.brickWidth;
                        
                        int brickXpos = 80+j*brickWidth;
                        int brickYpos = 50+i*brickHeight;

                    
                        Rectangle brickRect = new Rectangle(brickXpos, brickYpos, brickWidth, brickHeight);
                    
                        if(ballRect.intersects(brickRect)){
                        
                            map.setBrick(0, i, j);
                            totalBrick--;
                            score += 10;

                            if(ballXpos+19<=brickXpos || ballXpos+1>=brickXpos+brickWidth){
                                ballXdir = -ballXdir;
                            }
                            else{
                                ballYdir = -ballYdir;
                            }
                        
                            break A;

                        }

                    }

                }
            }

            ballXpos += ballXdir;
            ballYpos += ballYdir;
        }
        
        repaint();

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            play = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(paddleX <=0)
                paddleX = 0;
            else
                moveLeft();
        }
        
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(paddleX >= 580)           
                paddleX = 580;
            else
                moveRight();
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){

            if(!play){
                score = 0;
                totalBrick = 21;
                ballXpos = 100;
                ballYpos = 350;
                ballXdir = -3;
                ballYdir = -4;
                paddleX = 350;

                map = new MapGenerator(3, 7);
            }
        
        }

        repaint();
    }

    private void moveRight(){
        play = true;
        paddleX += paddleSpeed;
    }

    private void moveLeft() {
        play = true;
        paddleX -= paddleSpeed;
    }

    // NOTHING JUST OVERRIDEN
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
