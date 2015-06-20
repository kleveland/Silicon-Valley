package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import model.Adventure;
import model.City;
import model.InTransitException;
import model.TravelObserver;

public class TravelView extends JPanel implements ActionListener, TravelObserver {

	private Adventure a;
	private DefaultListModel<String> list;
	private JComboBox<String> city_list; //knapsack contents
	private JLabel location;
	private String[] items;
	private TitledBorder border;

	public TravelView(Adventure a) {
		this.a = a;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		border = BorderFactory.createTitledBorder("Travel Menu");
		this.setBorder(border);
		this.setMaximumSize(new Dimension(400, 60));
		this.list  = new DefaultListModel<>();
		this.location = new JLabel("You are in Chapel Hill");
		a.addTravelObserver(this);

		City[] cities = a.getCities();
		items = new String[cities.length];
		for(int i = 0; i<cities.length; i++) {
			items[i] = cities[i].getName();
		}
		this.city_list = new JComboBox<String>(items);
		this.city_list.setMaximumSize(new Dimension(400, 25));

		JPanel left = new JPanel();
		JPanel right = new JPanel();
		JPanel all = new JPanel();
		JButton travel = new JButton("Travel");
		travel.addActionListener(this);

		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		all.setLayout(new BoxLayout(all, BoxLayout.X_AXIS));

		left.add(location);
		left.add(Box.createRigidArea(new Dimension(0,10)));
		left.add(city_list);
		right.add(Box.createRigidArea(new Dimension(0,28)));
		right.add(travel);
		all.add(left);
		all.add(Box.createRigidArea(new Dimension(10,0)));
		all.add(right);
		this.add(all);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		City[] cities = a.getCities();
		for(int i = 0; i<cities.length; i++) {
			if(cities[i].getName().equals(city_list.getSelectedItem().toString())) {
				if(a.isTravelling()) {
					JOptionPane.showMessageDialog(this,
							"You are currently traveling!",
							"Error!",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
				else {
					a.travel(cities[i], 100);
					break;
				}
			}
		}
	}

	@Override
	public void travelUpdate(Adventure adventure, int distance_to_destination,
			City destination) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(distance_to_destination == 0) {

					location.setText("You are in " + destination.getName());
				}
				else {
					location.setText("You are " + distance_to_destination + " miles from " + destination.getName());
				}
			}
		});
	}

}
