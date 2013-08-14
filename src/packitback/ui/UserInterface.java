package packitback.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import packitback.util.DocHandler;

/**
 * The UserInterface Class. Sets up the User Interface and overal functionality.
 * 
 * @author James Dozier
 *
 */
public class UserInterface extends JFrame{
	
	//FIELDS
	/**
	 *  Relevant fields for the User Interface.
	 */
	private JTextArea results = new JTextArea(1, 20);
	private JComboBox<String> games;
	private JComboBox<String> sets;
	private DocHandler docHand;
	
	//CONSTRUCTOR
	/**
	 * Constructor for the User Interface.
	 * 
	 * @param docHandParam	Reference to Document Handler from PackItBack().
	 */
	public UserInterface(DocHandler docHandParam){
		this.docHand = docHandParam;
		
		init();
	}
	
	//METHODS
	/**
	 *  Initializes the User Interface and sets proper settings.
	 */
	private void init(){
		setTitle("Pack-It-Back");
		setSize(600,300);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		
		//UI Panel
		JPanel uiPanel = new JPanel();
		uiPanel.setLayout(new GridLayout(8, 1));
		
		//1
		uiPanel.add(new JLabel("Games"));
		
		//2
		games = new JComboBox<String>();
		for (String s : docHand.getGames()){
			games.addItem(s);
		}
		games.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshSets();
			}
			
		});
		uiPanel.add(games);
		
		//3
		uiPanel.add(new JLabel("Sets"));
		
		//4
		sets = new JComboBox<String>();
		refreshSets();
		uiPanel.add(sets);
		mainPanel.add(uiPanel);
		
		//5-7
		for (int i = 0; i < 3; i++){
			uiPanel.add(new JLabel(""));
		}
		
		//8
		JButton pullButton = new JButton("Pull Pack");
		pullButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String resultsString = null;
				try {
					resultsString = docHand.getPack((String)games.getSelectedItem(), (String)sets.getSelectedItem());
				} catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					resultsString = e1.toString();
				}
				results.setText(resultsString);
			}
			
		});
		
		uiPanel.add(pullButton);
		
		
		//Results Pane
		results.setEditable(false);
		results.setLineWrap(true);
		JScrollPane resultsPane = new JScrollPane(results);
		mainPanel.add(resultsPane);
		
		add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * This method resets the options in the sets JComboBox to reflect Sets relevant to the game in the games JComboBox.
	 */
	private void refreshSets() {
		// TODO Auto-generated method stub
		sets.removeAllItems();
		
		for (String s : docHand.getSets((String)games.getSelectedItem())){
			sets.addItem(s);
		}
	}
}