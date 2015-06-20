package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import person.Person;
import supplies.*;
import model.Adventure;
import model.InTransitException;
import model.Squad;

public class AdventureView extends JPanel {

	private Adventure adventure;
	private JComboBox<String> storelist; //current city store contents
	private JList<String> supplies_list; //knapsack contents
	private Person[] p;
	private PersonView[] pv;
	JButton storebutton;

	public AdventureView(Adventure adventure) {
		this.adventure = adventure;
		setLayout(new BorderLayout());

		Squad s = adventure.getSquad();

		pv = new PersonView[5];
		p = new Person[5];

		for(int i = 0; i<p.length; i++) { p[i] = s.getPlayer(i); }
		for(int i = 0; i<pv.length; i++) { pv[i] = new PersonView(p[i]); }
		
		add(East(), BorderLayout.EAST);
		add(West(), BorderLayout.WEST);
	}
	
	public JPanel East() {
		JPanel east_view = new JPanel(new BorderLayout());
		east_view.setLayout(new BoxLayout(east_view, BoxLayout.Y_AXIS));
		east_view.setPreferredSize(new Dimension(500, 400));
		east_view.setMaximumSize(new Dimension(500, 350));
		
		east_view.add(new KnapsackView(adventure));
		east_view.add(new StoreView(adventure));
		return east_view;
	}
	
	public JPanel West() {
		JPanel west_view = new JPanel(new BorderLayout());
		west_view.setLayout(new BoxLayout(west_view, BoxLayout.Y_AXIS));
		west_view.setPreferredSize(new Dimension(400, 350));
		west_view.setMaximumSize(new Dimension(400, 350));
		
		west_view.add(this.addPlayerContainer());
		west_view.add(Box.createVerticalGlue());
		west_view.add(new BalanceView(adventure));
		west_view.add(Box.createVerticalGlue());
		west_view.add(new TravelView(adventure));
		return west_view;
	}

	public JPanel addPlayerContainer() {
		JPanel player_view = new JPanel(new BorderLayout());
		TitledBorder border = BorderFactory.createTitledBorder("Player List");
		player_view.setBorder(border);
		player_view.setLayout(new BoxLayout(player_view, BoxLayout.Y_AXIS));
		player_view.setPreferredSize(new Dimension(400, 200));

		for(int i = 0; i<pv.length; i++) { pv[i].setMaximumSize(pv[i].getPreferredSize()); }
		for(int i = 0; i<pv.length; i++) { 
			player_view.add(pv[i]);
			player_view.add(new JSeparator());
		}
		
		return player_view;
	}
}
