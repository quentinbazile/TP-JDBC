import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AccueilJFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccueilJFrame window = new AccueilJFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AccueilJFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 472, 359);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblCR = new JLabel("Centre de recherche");
		lblCR.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
		lblCR.setBounds(108, 13, 237, 48);
		panel.add(lblCR);
		
		JLabel lblAccueil = new JLabel("Interface d'accueil");
		lblAccueil.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAccueil.setBounds(160, 74, 132, 20);
		panel.add(lblAccueil);
		
		JButton JButtonEquipe = new JButton("Table Equipe");
		JButtonEquipe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				EquipesJFrame a = new EquipesJFrame();
				a.setVisible(true);
			
			}
		});
		JButtonEquipe.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Team.png"));
		JButtonEquipe.setBounds(36, 132, 178, 51);
		panel.add(JButtonEquipe);
		
		JButton JButtonPersonne = new JButton("Table Personne");
		JButtonPersonne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PersonnesJFrame a = new PersonnesJFrame();
				a.setVisible(true);
			}
		});
		JButtonPersonne.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Person.png"));
		JButtonPersonne.setBounds(250, 130, 178, 54);
		panel.add(JButtonPersonne);
		
		JButton JButtonAuteur = new JButton("Table Auteur");
		JButtonAuteur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AuteursJFrame a = new AuteursJFrame();
				a.setVisible(true);
			}
		});
		JButtonAuteur.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Author.png"));
		JButtonAuteur.setBounds(36, 225, 178, 54);
		panel.add(JButtonAuteur);
		
		JButton JButtonPublication = new JButton("Table Publication");
		JButtonPublication.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PublicationsJFrame a = new PublicationsJFrame();
				a.setVisible(true);
			}
		});
		JButtonPublication.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Book.png"));
		JButtonPublication.setBounds(250, 225, 178, 54);
		panel.add(JButtonPublication);
	}

}
