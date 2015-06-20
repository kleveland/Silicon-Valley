package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

import model.Adventure;
import model.City;
import model.InTransitException;
import model.InsufficientFundsException;
import model.ItemNotForSaleException;
import model.Store;
import model.TravelObserver;
import supplies.Food;
import supplies.Supplies;

public class StoreView extends JPanel implements MouseListener, TravelObserver {

	private Adventure a;
	private JList<String> store_list; //knapsack contents
	private Map<String, String> itemname; //gets base item name from store_list
	private DefaultListModel<String> list;
	private JLabel label;
	private JScrollPane scroll;
	private JPanel balance;

	public StoreView(Adventure a) {
		this.a = a;
		this.itemname = new HashMap<String, String>();
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(400, 200));
		this.list = new DefaultListModel<>();
		this.store_list = new JList<String>(list);
		this.store_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.store_list.addMouseListener(this);
		this.label = new JLabel("Chapel Hill Store: (Double click on a item to open a buy window)");
		this.balance = new BalanceView(a);
		this.scroll = new JScrollPane();
		this.scroll.setViewportView(store_list);
		a.addTravelObserver(this);
		try {
			updateStoreContainer(a.getCurrentCity().getStore());
		} catch (ItemNotForSaleException | InTransitException e) {
			System.out.println("Error in buying item!");
		}
		this.add(label, BorderLayout.NORTH);
		this.add(scroll);
	}

	public void updateStoreContainer(Store store) throws ItemNotForSaleException, InTransitException {
		String[] supplies = store.getItemNames();
		for(int i = 0; i<supplies.length; i++) {
			String temp = "- " + supplies[i] + " ($" + store.getPrice(supplies[i]) + ")";
			list.addElement(temp);
			itemname.put(temp, supplies[i]);
		}
	}

	@Override
	public void mouseClicked(MouseEvent c) {
		int x = c.getX();
		int y = c.getY();
		int selected = store_list.getSelectedIndex();
		Rectangle bounds = store_list.getCellBounds(selected, selected);
		if (null != bounds && bounds.contains(x, y) && c.getClickCount() == 2) {
			String d = store_list.getSelectedValue();
			try {
				openBuyWindow(d);
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

	public void openBuyWindow(String name) throws ItemNotForSaleException, InTransitException {
		JFrame buywindow = new JFrame("Buying " + name);
		buywindow.setResizable(false);
		JButton buybutton = new JButton("Buy");
		buywindow.setPreferredSize(new Dimension(500,160));

		JTextField amount = new JTextField();
		PlainDocument doc = (PlainDocument) amount.getDocument();
		doc.setDocumentFilter(new IntFilter());
		amount.setMaximumSize(new Dimension(500,30));

		ActionListener click = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					int input = Integer.parseInt(amount.getText());
					a.getSquad().purchaseSupply(itemname.get(name), input, a.getCurrentCity().getStore());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(buywindow,
							"You didn't give a valid input! Only integers are allowed.",
							"Error!",
							JOptionPane.ERROR_MESSAGE);
				} catch(ItemNotForSaleException e) {
					JOptionPane.showMessageDialog(buywindow,
							"That item is not for sale in the current store!",
							"Error!",
							JOptionPane.ERROR_MESSAGE);
				} catch(InsufficientFundsException e) {
					JOptionPane.showMessageDialog(buywindow,
							"You don't have enough money!",
							"Error!",
							JOptionPane.ERROR_MESSAGE);
				} catch(InTransitException e) {
					JOptionPane.showMessageDialog(buywindow,
							"You are currently traveling; there is no shop available!",
							"Error!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};

		buybutton.addActionListener(click);
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		panel.add(new JLabel("Amount of " + itemname.get(name) + " to buy: "));
		panel.add(amount);
		panel.add(buybutton);
		panel2.add(balance);
		panel2.add(panel);
		buywindow.add(panel2);
		buywindow.pack();
		buywindow.setVisible(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override //doesn't work
	public void travelUpdate(Adventure adventure, int distance_to_destination,
			City destination) {
		if(distance_to_destination != 0) {
			list.clear();
			itemname.clear();
		}
		else {
			list.clear();
			Store store = destination.getStore();
			String[] s = store.getItemNames();
			itemname.clear();
			label.setText(destination.getName() + " Store: (Double click on a item to open a buy window)");
			for(int q=0; q<s.length; q++) {
				String temp;
				try {
					temp = "- " + s[q] + " ($" + store.getPrice(s[q]) + ")";
					list.addElement(temp);
					itemname.put(temp, s[q]);
				} catch (ItemNotForSaleException e) {
				}
			}
			if(list.isEmpty()) {
				travelUpdate(adventure, distance_to_destination, destination);
			}
			store_list.repaint();
			store_list.validate();
			scroll.repaint();
			scroll.validate();
		}
	}

}


