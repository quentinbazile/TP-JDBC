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

public class PersonnesJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField jTextFieldIdEq;
	private JTextField jTextFieldIdPer;
	private JTextField jTextFieldNomPer;
	private JTable jTablePersonnes;
	private JTextField jTextFieldPrenom;
	private JTextField jTextFieldGrade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonnesJFrame frame = new PersonnesJFrame();
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
	public PersonnesJFrame() {
		setTitle("Table Personne");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 701, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblIdPer = new JLabel("Identifiant de la personne");
		lblIdPer.setBounds(25, 16, 151, 16);
		panel.add(lblIdPer);
		
		JLabel lblNomPer = new JLabel("Nom de la personne");
		lblNomPer.setBounds(35, 51, 116, 16);
		panel.add(lblNomPer);
		
		JLabel lblPrenom = new JLabel("Pr\u00E9nom de la personne");
		lblPrenom.setBounds(25, 86, 133, 16);
		panel.add(lblPrenom);
		
		JLabel lblGrade = new JLabel("Grade de la personne");
		lblGrade.setBounds(35, 122, 134, 16);
		panel.add(lblGrade);
		
		JLabel lblIdEq = new JLabel("Identifiant de l'\u00E9quipe");
		lblIdEq.setBounds(38, 157, 133, 16);
		panel.add(lblIdEq);
		
		jTextFieldIdPer = new JTextField();
		jTextFieldIdPer.setColumns(10);
		jTextFieldIdPer.setBounds(188, 13, 56, 22);
		panel.add(jTextFieldIdPer);
		
		jTextFieldNomPer = new JTextField();
		jTextFieldNomPer.setColumns(10);
		jTextFieldNomPer.setBounds(188, 48, 116, 22);
		panel.add(jTextFieldNomPer);
		
		jTextFieldPrenom = new JTextField();
		jTextFieldPrenom.setColumns(10);
		jTextFieldPrenom.setBounds(188, 83, 116, 22);
		panel.add(jTextFieldPrenom);
		
		jTextFieldGrade = new JTextField();
		jTextFieldGrade.setColumns(10);
		jTextFieldGrade.setBounds(188, 119, 116, 22);
		panel.add(jTextFieldGrade);
		
		jTextFieldIdEq = new JTextField();
		jTextFieldIdEq.setColumns(10);
		jTextFieldIdEq.setBounds(188, 154, 56, 22);
		panel.add(jTextFieldIdEq);
		
		JButton jButtonInsert = new JButton("Insert");
		jButtonInsert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String grade = jTextFieldGrade.getText().toUpperCase();
	        	if (grade.equals("MC") || grade.equals("PROF") || grade.equals("CR") || grade.equals("DR")) {
					String req = "INSERT INTO Personne(id_per, nom_per, prenom, grade, id_eq) VALUES("
							+ jTextFieldIdPer.getText() +",'"
					 		+ jTextFieldNomPer.getText() +"','" 
					 		+ jTextFieldPrenom.getText() +"','" 
					 		+ jTextFieldGrade.getText() +"','" 
					 		+ jTextFieldIdEq.getText() + "')"; 
					executeSQLQuery(req, "Ajout");
	        	}
	        	else
	        		System.err.println("Grade non valide.");
			}
		});
		jButtonInsert.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Ajouter.png"));
		jButtonInsert.setBounds(12, 186, 110, 45);
		panel.add(jButtonInsert);
		
		JButton jButtonUpdate = new JButton("Update");
		jButtonUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String grade = jTextFieldGrade.getText().toUpperCase();
	        	if (grade.equals("MC") || grade.equals("PROF") || grade.equals("CR") || grade.equals("DR")) {
					String req = "UPDATE Personne SET nom_per ='" + jTextFieldNomPer.getText()
							+ "', prenom ='" + jTextFieldPrenom.getText()
							+ "', grade ='" + jTextFieldGrade.getText()
							+ "', id_eq ='" + jTextFieldIdEq.getText()  
							+"' WHERE id_per="+jTextFieldIdPer.getText(); 
					executeSQLQuery(req, "Modification"); 
	        	}
	        	else
	        		System.err.println("Grade non valide.");
			}
		});
		jButtonUpdate.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Modifier.png"));
		jButtonUpdate.setBounds(134, 186, 110, 45);
		panel.add(jButtonUpdate);
		
		JButton jButtonDelete = new JButton("Delete");
		jButtonDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String req = "DELETE FROM Personne WHERE id_per = " + jTextFieldIdPer.getText(); 
				executeSQLQuery(req, "Suppression");
			}
		});
		jButtonDelete.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Supprimer.png"));
		jButtonDelete.setBounds(256, 186, 110, 45);
		panel.add(jButtonDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(323, 8, 338, 165);
		panel.add(scrollPane);
		
		jTablePersonnes = new JTable();
		jTablePersonnes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = jTablePersonnes.getSelectedRow(); 
				TableModel model = jTablePersonnes.getModel(); 
				jTextFieldIdPer.setText(model.getValueAt(i,0).toString()); 
				jTextFieldNomPer.setText(model.getValueAt(i,1).toString()); 
				jTextFieldPrenom.setText(model.getValueAt(i,2).toString()); 
				jTextFieldGrade.setText(model.getValueAt(i,3).toString()); 
				jTextFieldIdEq.setText(model.getValueAt(i,4).toString()); 
			}
		});
		scrollPane.setViewportView(jTablePersonnes);
		jTablePersonnes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_per", "nom_per", "prenom", "grade", "id_eq"
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
	
	public ArrayList<Personne> getList() { 
        ArrayList<Personne> list = new ArrayList<Personne>(); 
        Connection c =  getConnection(); 
        String req = "SELECT * FROM Personne"; 
        Statement st; 
        ResultSet rs; 
        Personne Personne; 
        try { 
            st = c.createStatement();
            rs = st.executeQuery(req); 
            while (rs.next()) { 
            	Personne = new Personne(rs.getInt("id_per"),rs.getString("nom_per"),rs.getString("prenom"),rs.getString("grade"),rs.getInt("id_eq")); 
                list.add(Personne); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
       return list; 
	}
	 
    public void afficherJTable() { 
        ArrayList<Personne> List = getList(); 
        DefaultTableModel model; 
        model = (DefaultTableModel)jTablePersonnes.getModel(); 
        Object[] row = new Object[5];  
        for (int i=0; i < List.size(); i++) { 
            row[0] = List.get(i).getId_per(); 
            row[1] = List.get(i).getNom_per(); 
            row[2] = List.get(i).getPrenom();
            row[3] = List.get(i).getGrade();
            row[4] = List.get(i).getId_eq(); 
            model.addRow(row); 
        } 
    }
    
    public void executeSQLQuery(String req, String message) { 
        try { 
            Connection c = getConnection(); 
            Statement st = c.createStatement(); 
            if ((st.executeUpdate(req))==1) { 
                DefaultTableModel model = (DefaultTableModel)jTablePersonnes.getModel(); 
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
