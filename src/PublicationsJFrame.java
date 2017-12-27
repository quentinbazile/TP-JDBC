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

public class PublicationsJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField jTextFieldIdPub;
	private JTextField jTextFieldTitre;
	private JTable jTablePublications;
	private JTextField jTextFieldType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublicationsJFrame frame = new PublicationsJFrame();
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
	public PublicationsJFrame() {
		setTitle("Table Publication");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 701, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblIdPub = new JLabel("Identifiant de la publication");
		lblIdPub.setBounds(25, 36, 173, 16);
		panel.add(lblIdPub);
		
		JLabel lblTitre = new JLabel("Titre de la publication");
		lblTitre.setBounds(35, 86, 141, 16);
		panel.add(lblTitre);
		
		JLabel lblType = new JLabel("Type de la publication");
		lblType.setBounds(35, 133, 133, 16);
		panel.add(lblType);
		
		jTextFieldIdPub = new JTextField();
		jTextFieldIdPub.setColumns(10);
		jTextFieldIdPub.setBounds(188, 33, 56, 22);
		panel.add(jTextFieldIdPub);
		
		jTextFieldTitre = new JTextField();
		jTextFieldTitre.setColumns(10);
		jTextFieldTitre.setBounds(188, 83, 116, 22);
		panel.add(jTextFieldTitre);
		
		jTextFieldType = new JTextField();
		jTextFieldType.setColumns(10);
		jTextFieldType.setBounds(188, 130, 116, 22);
		panel.add(jTextFieldType);
		
		JButton jButtonInsert = new JButton("Insert");
		jButtonInsert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String type = jTextFieldType.getText().toUpperCase();
				String titre = jTextFieldTitre.getText();
	        	if ((type.equals("REVUE") || type.equals("LIVRE") || type.equals("RAPPORT")) && (titre != "")) {
					String req = "INSERT INTO Publication(id_pub, titre, type) VALUES("
							+ jTextFieldIdPub.getText() +",'"
					 		+ jTextFieldTitre.getText() +"','" 
					 		+ jTextFieldType.getText() + "')";  
					executeSQLQuery(req, "Ajout");
	        	}
	        	else
	        		System.err.println("Type non valide ou titre non renseigné.");
			}
		});
		jButtonInsert.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Ajouter.png"));
		jButtonInsert.setBounds(12, 186, 110, 45);
		panel.add(jButtonInsert);
		
		JButton jButtonUpdate = new JButton("Update");
		jButtonUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = jTextFieldType.getText().toUpperCase();
				String titre = jTextFieldTitre.getText();
	        	if ((type.equals("REVUE") || type.equals("LIVRE") || type.equals("RAPPORT")) && (titre != "")) {
					String req = "UPDATE Publication SET titre ='" + jTextFieldTitre.getText()
							+ "', type ='" + jTextFieldType.getText() 
							+"' WHERE id_pub="+jTextFieldIdPub.getText(); 
					executeSQLQuery(req, "Modification"); 
	        	}
	        	else
	        		System.err.println("Type non valide ou titre non renseigné.");
			}
		});
		jButtonUpdate.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Modifier.png"));
		jButtonUpdate.setBounds(134, 186, 110, 45);
		panel.add(jButtonUpdate);
		
		JButton jButtonDelete = new JButton("Delete");
		jButtonDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String req = "DELETE FROM Publication WHERE id_pub = " + jTextFieldIdPub.getText(); 
				executeSQLQuery(req, "Suppression");
			}
		});
		jButtonDelete.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Supprimer.png"));
		jButtonDelete.setBounds(256, 186, 110, 45);
		panel.add(jButtonDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(323, 8, 338, 165);
		panel.add(scrollPane);
		
		jTablePublications = new JTable();
		jTablePublications.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = jTablePublications.getSelectedRow(); 
				TableModel model = jTablePublications.getModel(); 
				jTextFieldIdPub.setText(model.getValueAt(i,0).toString()); 
				jTextFieldTitre.setText(model.getValueAt(i,1).toString()); 
				jTextFieldType.setText(model.getValueAt(i,2).toString());  
			}
		});
		scrollPane.setViewportView(jTablePublications);
		jTablePublications.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_pub", "titre", "type"
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
	
	public ArrayList<Publication> getList() { 
        ArrayList<Publication> list = new ArrayList<Publication>(); 
        Connection c =  getConnection(); 
        String req = "SELECT * FROM Publication"; 
        Statement st; 
        ResultSet rs; 
        Publication Publication; 
        try { 
            st = c.createStatement();
            rs = st.executeQuery(req); 
            while (rs.next()) { 
            	Publication = new Publication(rs.getInt("id_pub"),rs.getString("titre"),rs.getString("type")); 
                list.add(Publication); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
       return list; 
	}
	 
    public void afficherJTable() { 
        ArrayList<Publication> List = getList(); 
        DefaultTableModel model; 
        model = (DefaultTableModel)jTablePublications.getModel(); 
        Object[] row = new Object[3];  
        for (int i=0; i < List.size(); i++) { 
            row[0] = List.get(i).getId_pub(); 
            row[1] = List.get(i).getTitre(); 
            row[2] = List.get(i).getType(); 
            model.addRow(row); 
        } 
    }
    
    public void executeSQLQuery(String req, String message) { 
        try { 
            Connection c = getConnection(); 
            Statement st = c.createStatement(); 
            if ((st.executeUpdate(req))==1) { 
                DefaultTableModel model = (DefaultTableModel)jTablePublications.getModel(); 
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
