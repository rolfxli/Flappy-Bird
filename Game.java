package main;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game {
	static JFrame frame;
	static DrawPanel panel;
	static Bird bird;
	static int width = 500;
	static int height = 500;
	static int score = 0;
	static int highScore = 0;
	
	static void init() {
		score = 0;
		frame = new JFrame();
		panel = new DrawPanel();
		panel.addKeyListener(panel);
		bird = new Bird(100, 350, 15, 30);
		Pipe.pipes.clear();
		Pipe.generate(350);
		Pipe.generate(700);
		frame.setSize(width, height);
		panel.setFocusable(true);
		frame.add(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	static class DrawPanel extends JPanel implements KeyListener{
		public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.black);
            for(int i = 0; i < Pipe.pipes.size(); i++){
            	Pipe.pipes.get(i);
				g.fillRect(Pipe.pipes.get(i).getX(), Pipe.pipes.get(i).getY(), Pipe.width, Pipe.pipes.get(i).getHeight());
            }
            g.fillRect(bird.getX(), bird.getY(), bird.getWidth(), bird.getWidth());
            g.setColor(Color.blue);
            Font f = new Font ("Monosbirdced", Font.BOLD, 30);
            g.setFont(f);
            g.drawString(String.valueOf(score), 10, 30);
            g.setColor(Color.red);
            f = new Font ("Monosbirdced", Font.BOLD, 30);
            g.setFont(f);
            g.drawString(String.valueOf(highScore), width-45, 30);
        }
		
      	public void keyPressed(KeyEvent e){
      		if(e.getKeyCode() == KeyEvent.VK_SPACE){
      			bird.setYVel(15);
      		} else if(e.getKeyCode() == KeyEvent.VK_R){
      			if(bird.getStatus()){
      				bird = new Bird(100, 350, 15, 30);
					Pipe.pipes.clear();
					Pipe.generate(350);
					Pipe.generate(700);
					score = 0;
      			}
      		} else if(e.getKeyCode() == KeyEvent.VK_X){
      			System.exit(0);
      		}
      	}
	    public void keyReleased(KeyEvent e){}
	    public void keyTyped(KeyEvent e){}
	}
	
	public static void update(){
		bird.update();
		Pipe.update();
		frame.getToolkit().sync();
		panel.repaint();
		bird.collide();
		updateScore();
		highScore = Math.max(highScore, score);
		try{
			Thread.sleep(15);
		}catch(Exception e){}
	}
	
	public static void updateScore(){
		boolean inc = false;
		for(Pipe p : Pipe.pipes){
			if(p.getX() + Pipe.width <= 100 && !p.getCounted()){
				inc = true;
				p.setCounted(true);
			}
		}
		if(inc) {
			score++;
		}
		}
	
	
	public static void main(String args[]){
		init();
		while(true){
			while(!bird.getStatus()){
				update();
			}
			try{
				Thread.sleep(15);
			}catch(Exception e){}
		}
	}

}
