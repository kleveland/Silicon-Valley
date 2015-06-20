package a8;

import java.awt.BorderLayout;

import javax.swing.*;

import supplies.Burrito;
import supplies.Food;
import supplies.Ramen;
import supplies.Sushi;
import view.AdventureConsole;
import view.AdventureView;
import model.Adventure;
import model.Squad;
import model.SquadImpl;


public class Main {

	public static void main(String[] args) {
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Silicon Valley Trail");
		main_frame.setResizable(false);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Squad s = SquadImpl.generateSquad(5);		
		Adventure a = new Adventure(Adventure.Difficulty.HARD, s);
		AdventureConsole c = new AdventureConsole(a);
		c.start();

		AdventureView v = new AdventureView(a);
		main_frame.setContentPane(v);

		main_frame.pack();
		main_frame.setVisible(true);	
		
		try {
			c.join();
		} catch (InterruptedException e) {
		}
		//System.exit(0);
	}
}
