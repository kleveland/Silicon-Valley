package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;

import person.Person;
import model.Adventure;
import model.InTransitException;
import model.InsufficientFundsException;
import model.ItemNotForSaleException;
import supplies.Food;
import supplies.Supplies;

public class KnapsackView extends JPanel implements MouseListener, Observer {

	private Adventure a;
	private DefaultListModel<String> list;
	private JList<String> supplies_list; //knapsack contents
	private JScrollPane scroll;
	private Map<String, String> itemname;

	public KnapsackView(Adventure a) {
		this.a = a;
		this.itemname = new HashMap<String, String>();
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(700, 200));
		a.getSquad().getKnapsack().addObserver(this);
		this.list  = new DefaultListModel<>();
		this.supplies_list = new JList<String>(list);
		this.supplies_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.supplies_list.addMouseListener(this);
		this.scroll = new JScrollPane();
		this.scroll.setViewportView(supplies_list);

		this.add(new JLabel("Knapsack Contents: (Double click on a food to open a feed window)"), BorderLayout.NORTH);
		this.add(scroll);
	}

	@Override
	public void update(Observable o, Object arg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				list.clear();
				Supplies[] supplies = a.getSquad().getKnapsack().getSupplies();
				for(Supplies i: supplies) {
					if(i instanceof Food) {
						Food item = (Food) i;
						String temp = "You have " + item.getAmount() + " of " + item.getName() + " left. (Expires in " + item.getDaysTillExpiration() + ")";
						list.addElement(temp);
						itemname.put(temp, item.getName());
					}
					else {
						list.addElement("You have " + i.getAmount() + " of " + i.getName() + " left.");
					}
				}
			}
		});

	}
	public void openFeedWindow(String name) throws ItemNotForSaleException, InTransitException {
		JFrame feedwindow = new JFrame("Feed: " + name);
		feedwindow.setResizable(false);
		feedwindow.setMaximumSize(new Dimension(400,80));
		String[] players = new String[a.getSquad().getNumPlayers()];
		JButton feedbutton = new JButton("Feed");
		for(int i = 0; i<players.length; i++) {
			players[i] = a.getSquad().getPlayer(i).getName();
		}
		JComboBox<String> playerlist = new JComboBox<String>(players);
		ActionListener click = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Food[] foods = a.getSquad().getKnapsack().getEdibleSupplies();
				for(int i=0; i<players.length; i++) {
					if(playerlist.getSelectedItem().toString().equals(players[i])) {
						for(int q=0; q<foods.length; q++) {
							if(foods[q].getName().equals(name)) {
								a.getSquad().feed(foods[q], a.getSquad().getPlayer(i));
								if(foods[q].getAmount() == 0) {
									JOptionPane.showMessageDialog(supplies_list,
											"You are out of " + foods[q].getName() + "!",
											"Error!",
											JOptionPane.ERROR_MESSAGE);		
								}
								break;
							}
						}
						break;
					}
				}
			}
		};
		feedbutton.addActionListener(click);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(playerlist);
		panel.add(feedbutton);
		feedwindow.add(panel);
		feedwindow.pack();
		feedwindow.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent c) {
		int x = c.getX();
		int y = c.getY();
		int selected = supplies_list.getSelectedIndex();
		Rectangle bounds = supplies_list.getCellBounds(selected, selected);
		if (null != bounds && bounds.contains(x, y) && c.getClickCount() == 2) {
			String d = supplies_list.getSelectedValue();
			if(itemname.containsKey(d)) {
				try {
					openFeedWindow(itemname.get(d));
				} catch (InTransitException e) {
					JOptionPane.showMessageDialog(this,
							"You are currently travelling!",
							"Error!",
							JOptionPane.ERROR_MESSAGE);				
				} catch(ItemNotForSaleException e) {
					JOptionPane.showMessageDialog(this,
							"That item is not for sale!",
							"Error!",
							JOptionPane.ERROR_MESSAGE);	
				}
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
