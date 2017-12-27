import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AuteursJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField jTextFieldIdPub;
	private JTextField jTextFieldIdAu;
	private JTable jTableAuteurs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuteursJFrame frame = new AuteursJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AuteursJFrame() {
		setTitle("Table Auteur");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 529, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblIdAu = new JLabel("Identifiant de l'auteur");
		lblIdAu.setBounds(45, 55, 151, 16);
		panel.add(lblIdAu);
		
		JLabel lblIdPub = new JLabel("Identifiant de la publication");
		lblIdPub.setBounds(32, 115, 154, 16);
		panel.add(lblIdPub);
		
		jTextFieldIdAu = new JTextField();
		jTextFieldIdAu.setColumns(10);
		jTextFieldIdAu.setBounds(219, 52, 56, 22);
		panel.add(jTextFieldIdAu);
		
		jTextFieldIdPub = new JTextField();
		jTextFieldIdPub.setColumns(10);
		jTextFieldIdPub.setBounds(219, 112, 56, 22);
		panel.add(jTextFieldIdPub);
		
		JButton jButtonInsert = new JButton("Insert");
		jButtonInsert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String req = "INSERT INTO Auteur(id_au, id_pub) VALUES("
						+ jTextFieldIdAu.getText() +",'" 
				 		+ jTextFieldIdPub.getText() + "')"; 
				executeSQLQuery(req, "Ajout");
			}
		});
		jButtonInsert.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Ajouter.png"));
		jButtonInsert.setBounds(12, 186, 110, 45);
		panel.add(jButtonInsert);
		
		JButton jButtonUpdate = new JButton("Update");
		jButtonUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String req = "UPDATE Auteur SET id_pub ='" + jTextFieldIdPub.getText()
						+"' WHERE id_au="+jTextFieldIdAu.getText(); 
		executeSQLQuery(req, "Modification"); 
			}
		});
		jButtonUpdate.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Modifier.png"));
		jButtonUpdate.setBounds(134, 186, 110, 45);
		panel.add(jButtonUpdate);
		
		JButton jButtonDelete = new JButton("Delete");
		jButtonDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String req = "DELETE FROM Auteur WHERE id_au = " + jTextFieldIdAu.getText()
						+"' AND id_pub =" + jTextFieldIdPub.getText(); 
				executeSQLQuery(req, "Suppression");
			}
		});
		jButtonDelete.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Supprimer.png"));
		jButtonDelete.setBounds(256, 186, 110, 45);
		panel.add(jButtonDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(323, 8, 160, 165);
		panel.add(scrollPane);
		
		jTableAuteurs = new JTable();
		jTableAuteurs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = jTableAuteurs.getSelectedRow(); 
				TableModel model = jTableAuteurs.getModel(); 
				jTextFieldIdAu.setText(model.getValueAt(i,0).toString());  
				jTextFieldIdPub.setText(model.getValueAt(i,1).toString()); 
			}
		});
		scrollPane.setViewportView(jTableAuteurs);
		jTableAuteurs.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_au", "id_pub"
			}
		));
		
		afficherJTable();
	}
	
	public Connection getConnection() { 
        try{    
        	Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","root","root");   
            return con; 
        } 
        catch(Exception e) { 
            e.printStackTrace(); 
            return null; 
        } 
	}
	
	public ArrayList<Auteur> getList() { 
        ArrayList<Auteur> list = new ArrayList<Auteur>(); 
        Connection c =  getConnection(); 
        String req = "SELECT * FROM Auteur"; 
        Statement st; 
        ResultSet rs; 
        Auteur Auteur; 
        try { 
            st = c.createStatement();
            rs = st.executeQuery(req); 
            while (rs.next()) { 
            	Auteur = new Auteur(rs.getInt("id_au"),rs.getInt("id_pub")); 
                list.add(Auteur); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
       return list; 
	}
	 
    public void afficherJTable() { 
        ArrayList<Auteur> List = getList(); 
        DefaultTableModel model; 
        model = (DefaultTableModel)jTableAuteurs.getModel(); 
        Object[] row = new Object[2];  
        for (int i=0; i < List.size(); i++) { 
            row[0] = List.get(i).getId_au(); 
            row[1] = List.get(i).getId_pub(); 
            model.addRow(row); 
        } 
    }
    
    public void executeSQLQuery(String req, String message) { 
        try { 
            Connection c = getConnection(); 
            Statement st = c.createStatement(); 
            if ((st.executeUpdate(req))==1) { 
                DefaultTableModel model = (DefaultTableModel)jTableAuteurs.getModel(); 
                model.setNumRows(0); 
                afficherJTable();  
                JOptionPane.showMessageDialog(null, "SUCCÈS: " + message + " des données."); 
            } 
            else 
                JOptionPane.showMessageDialog(null, "ERREUR: " + message + " des données.");  
        } catch(Exception e) { 
            e.printStackTrace(); 
        } 
    }
}
