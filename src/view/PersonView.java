package view;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import person.Person;

public class PersonView extends JPanel implements Observer {

	private Person person;
	private JLabel b;
	
	public PersonView(Person p) {
		person = p;
		b = new JLabel(p.toString());
		b.setFont(new Font(null, Font.BOLD, 12));
		add(b);
		person.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				b.setText(person.toString());
			}
		});
	}
}
