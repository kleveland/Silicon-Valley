package view;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import model.Adventure;

public class BalanceView extends JPanel implements Observer {
	private JLabel balance;
	private Adventure a;
	private TitledBorder border;
	
	BalanceView(Adventure a) {
		this.a = a;
		this.balance = new JLabel("$" + this.a.getSquad().getBalance());
		Font f = new Font("Serif", Font.BOLD,20);
		this.balance.setFont(f);
		this.a.getSquad().getKnapsack().addObserver(this);
		this.add(balance);
		border = BorderFactory.createTitledBorder("Current Balance");
		this.setBorder(border);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				balance.setText("$" + a.getSquad().getBalance());
			};
		});
	}

}
