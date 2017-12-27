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

public class EquipesJFrame extends JFrame {

	private JPanel contentPane;
	private JTextField jTextFieldIdChef;
	private JTextField jTextFieldIdEq;
	private JTextField jTextFieldNomEq;
	private JTable jTableEquipes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EquipesJFrame frame = new EquipesJFrame();
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
	public EquipesJFrame() {
		setTitle("Table Equipe");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 701, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblIdEq = new JLabel("Identifiant de l'\u00E9quipe");
		lblIdEq.setBounds(25, 16, 130, 16);
		panel.add(lblIdEq);
		
		jTextFieldIdEq = new JTextField();
		jTextFieldIdEq.setColumns(10);
		jTextFieldIdEq.setBounds(167, 13, 56, 22);
		panel.add(jTextFieldIdEq);
		
		jTextFieldNomEq = new JTextField();
		jTextFieldNomEq.setColumns(10);
		jTextFieldNomEq.setBounds(167, 69, 116, 22);
		panel.add(jTextFieldNomEq);
		
		jTextFieldIdChef = new JTextField();
		jTextFieldIdChef.setColumns(10);
		jTextFieldIdChef.setBounds(167, 122, 56, 22);
		panel.add(jTextFieldIdChef);
		
		JLabel lblNomEq = new JLabel("Nom de l'\u00E9quipe");
		lblNomEq.setBounds(37, 72, 97, 16);
		panel.add(lblNomEq);
		
		JLabel lblIdChef = new JLabel("Identifiant du chef");
		lblIdChef.setBounds(32, 125, 116, 16);
		panel.add(lblIdChef);
		
		JButton jButtonInsert = new JButton("Insert");
		jButtonInsert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String req = "INSERT INTO Equipe(id_eq, nom_eq, id_per) VALUES(" + jTextFieldIdEq.getText() +",'"
				 		+ jTextFieldNomEq.getText() +"','" 
				 		+ jTextFieldIdChef.getText() + "')"; 
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
				String req = "UPDATE Equipe SET nom_eq ='" + jTextFieldNomEq.getText() + "', id_per ='"
						+ jTextFieldIdChef.getText()  
						+"' WHERE id_eq="+jTextFieldIdEq.getText(); 
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
				String req = "DELETE FROM Equipe WHERE id_eq = " + jTextFieldIdEq.getText(); 
				executeSQLQuery(req, "Suppression");
			}
		});
		jButtonDelete.setIcon(new ImageIcon("C:\\Users\\quent\\Desktop\\ISIS 3A\\Base de donn\u00E9es\\TP\\Icons\\Supprimer.png"));
		jButtonDelete.setBounds(256, 186, 110, 45);
		panel.add(jButtonDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(312, 8, 338, 165);
		panel.add(scrollPane);
		
		jTableEquipes = new JTable();
		jTableEquipes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = jTableEquipes.getSelectedRow(); 
				TableModel model = jTableEquipes.getModel(); 
				jTextFieldIdEq.setText(model.getValueAt(i,0).toString()); 
				jTextFieldNomEq.setText(model.getValueAt(i,1).toString()); 
				jTextFieldIdChef.setText(model.getValueAt(i,2).toString()); 
			}
		});
		scrollPane.setViewportView(jTableEquipes);
		jTableEquipes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id_eq", "nom_eq", "id_chef"
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
	
	public ArrayList<Equipe> getList() { 
        ArrayList<Equipe> list = new ArrayList<Equipe>(); 
        Connection c =  getConnection(); 
        String req = "SELECT * FROM Equipe"; 
        Statement st; 
        ResultSet rs; 
        Equipe equipe; 
        try { 
            st = c.createStatement();
            rs = st.executeQuery(req); 
            while (rs.next()) { 
            	equipe = new Equipe(rs.getInt("id_eq"),rs.getString("nom_eq"),rs.getInt("id_per")); 
                list.add(equipe); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
       return list; 
	}
	 
    public void afficherJTable() { 
        ArrayList<Equipe> List = getList(); 
        DefaultTableModel model; 
        model = (DefaultTableModel)jTableEquipes.getModel(); 
        Object[] row = new Object[3];  
        for (int i=0; i < List.size(); i++) { 
            row[0] = List.get(i).getId_eq(); 
            row[1] = List.get(i).getNom_eq(); 
            row[2] = List.get(i).getId_per(); 
            model.addRow(row); 
        } 
    }
    
    public void executeSQLQuery(String req, String message) { 
        try { 
            Connection c = getConnection(); 
            Statement st = c.createStatement(); 
            if ((st.executeUpdate(req))==1) { 
                DefaultTableModel model = (DefaultTableModel)jTableEquipes.getModel(); 
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
