import java.sql.*;

public class GestionDesEquipes {

	public static void main(String[] args) throws SQLException {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Pilote chargé.");
        } 
        catch (java.lang.ClassNotFoundException e){
            System.err.println("ERREUR : Driver manquant."+e.getMessage());
        }
        Connection conn=null;
        try {	
            String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
            String user = "root"; 
            String passwd = "root";
            conn = DriverManager.getConnection(url, user, passwd); 
            if (conn != null)
                System.out.println("Connexion à la base de données [" + user + "] réussie."); 
            else 
                System.out.println("Connexion impossible."); 
        } 
        catch (SQLException ex) { 
            System.err.println("ERREUR: BD manquante ou connexion invalide."); 
        }
        
        //insertionEquipe(1, 1, "ISIS", conn);
        //insertionPersonne(1, 1, "BAZILE", "Quentin", "MC", conn);
        //insertionPublication(1, "Stage 3A", "Rapport", conn);
        //insertionAuteur(1, 1, conn);
        //AfficheTableEquipe(conn);
        //listeChercheurs("MC", conn);
        
        if (conn != null) { 
        	try { 
        		conn.close(); 
                System.out.println("Déconnexion réussie."); 
            } 
        	catch (Exception e) { 
                System.err.println("ERREUR: Déconnexion impossible."); 
            } 
        }    
    }
	
	public static void insertionPersonne(int id_per, int id_eq, String nom_per, String prenom, String grade, Connection conn) throws SQLException {   	
    	String RequeteParametree = "INSERT INTO Personne(id_per, id_eq, nom_per, prenom, grade) VALUES(?, ?, ?, ?, ?)"; 
        try { 
        	grade = grade.toUpperCase();
        	if (grade.equals("MC") || grade.equals("PROF") || grade.equals("CR") || grade.equals("DR")) {
	        	PreparedStatement pstmt = conn.prepareStatement(RequeteParametree); 
	        	pstmt.setInt(1, id_per); 
	        	pstmt.setInt(2, id_eq);
	        	pstmt.setString(3, nom_per); 
	        	pstmt.setString(4, prenom);
	        	pstmt.setString(5, grade);
	        	int count = pstmt.executeUpdate();
	        	System.out.println("La personne [" + nom_per + " " + prenom + "] a bien été rajoutée dans la base de données.");
	        	if(count == 0)
	        		throw new SQLException ("ERREUR: count = 0."); 
	            pstmt.close();
        	}
        	else
        		System.err.println("Grade non valide.");
        }
        catch (SQLException e) { 
           System.err.println("Erreur lors de l'exécution de la requète SQL.");
        }
    }
	
	public static void insertionEquipe(int id_eq, int id_per, String nom_eq, Connection conn) throws SQLException {   	
    	String RequeteParametree = "INSERT INTO Equipe(id_eq, id_per, nom_eq) VALUES(?, ?, ?)"; 
        try { 
        	PreparedStatement pstmt = conn.prepareStatement(RequeteParametree); 
        	pstmt.setInt(1, id_eq); 
        	pstmt.setInt(2, id_per);
        	pstmt.setString(3, nom_eq); 
        	int count = pstmt.executeUpdate();
        	System.out.println("L'équipe [" + nom_eq + "] a bien été rajoutée dans la base de données.");
        	if(count == 0)
        		throw new SQLException ("ERREUR: count = 0."); 
            pstmt.close();
        }
        catch (SQLException e) { 
           System.err.println("Erreur lors de l'exécution de la requète SQL.");
        }
    }
	
	public static void insertionPublication(int id_pub, String titre, String type, Connection conn) throws SQLException {   	
    	String RequeteParametree = "INSERT INTO Publication(id_pub, titre, type) VALUES(?, ?, ?)"; 
        try { 
        	type = type.toUpperCase();
        	if ((type.equals("REVUE") || type.equals("LIVRE") || type.equals("RAPPORT")) && (titre != "")) {
	        	PreparedStatement pstmt = conn.prepareStatement(RequeteParametree); 
	        	pstmt.setInt(1, id_pub); 
	        	pstmt.setString(2, titre);
	        	pstmt.setString(3, type); 
	        	int count = pstmt.executeUpdate();
	        	System.out.println("La publication [" + titre + "] a bien été rajoutée dans la base de données.");
	        	if(count == 0)
	        		throw new SQLException ("ERREUR: count = 0."); 
	            pstmt.close();
        	}
        	else
        		System.err.println("Type non valide ou titre non renseigné.");        	
        }
        catch (SQLException e) { 
           System.err.println("Erreur lors de l'exécution de la requète SQL.");
        }
    }
	
	public static void insertionAuteur(int id_per, int id_pub, Connection conn) throws SQLException {   	
    	String RequeteParametree = "INSERT INTO Auteur(id_per, id_pub) VALUES(?, ?)"; 
        try { 
        	PreparedStatement pstmt = conn.prepareStatement(RequeteParametree); 
        	pstmt.setInt(1, id_per); 
        	pstmt.setInt(2, id_pub); 
        	int count = pstmt.executeUpdate();
        	System.out.println("La correspondance entre l'auteur n° [" + id_per + "] et la publication "
        			+ "n° [" + id_pub + "] a bien été rajoutée dans la base de données.");
        	if(count == 0)
        		throw new SQLException ("ERREUR: count = 0."); 
            pstmt.close();
        }
        catch (SQLException e) { 
           System.err.println("Erreur lors de l'exécution de la requète SQL.");
        }
    }
	
	public static void AfficheTableEquipe(Connection con) throws SQLException {   
    	String requete = "SELECT * FROM Equipe INNER JOIN Personne USING(id_per)"; 
    	try { 
	        Statement stmt = null; 
	        stmt = con.createStatement(); 
	        ResultSet rset = stmt.executeQuery(requete); 
	        if (rset != null) { 
	        	while (rset.next()) { 
	        		System.out.println("\t Identifiant : " + rset.getInt("id_eq") + "\t"); 
			        System.out.println("\t Nom : " + rset.getString("nom_eq") + "\t"); 
			        System.out.println("\t Chef : " + rset.getString("nom_per") + " " + rset.getString("prenom") + "\t"); 
			        System.out.println("\n---------------------------------"); 
		        } 
	        	System.out.println(); 
            } 
	        else
	        	throw new SQLException("ERREUR: ResultSet = null.");
            rset.close();
            stmt.close();
    	} 
    	catch (SQLException e) {
           	System.err.println("Erreur lors de l'exécution de la requète SQL."); 
    	}
    }
	
	public static void listeChercheurs(String grade, Connection conn) throws SQLException {   	
    	String RequeteParametree = "SELECT * FROM Personne INNER JOIN Equipe USING(id_eq) WHERE grade = ?";
    	try { 
        	PreparedStatement pstmt = conn.prepareStatement(RequeteParametree); 
        	pstmt.setString(1, grade); 
        	ResultSet rset = pstmt.executeQuery();
        	if (rset != null) { 
	        	while (rset.next()) { 
			        System.out.println("\t Identifiant : " + rset.getInt("id_per") + "\t"); 
			        System.out.println("\t Chercheur : " + rset.getString("nom_per") + " " + rset.getString("prenom") + "\t"); 
			        System.out.println("\t Grade : " + rset.getString("grade") + "\t"); 
			        System.out.println("\t Equipe : " + rset.getString("nom_eq") + "\t"); 
			        System.out.println("\n---------------------------------"); 
	        	} 
	        	System.out.println(); 
        	}
	        else
        		throw new SQLException ("ERREUR: ResultSet = null."); 
	        rset.close();
            pstmt.close();
        }
        catch (SQLException e) { 
           System.err.println("Erreur lors de l'exécution de la requète SQL.");
        }
    }
	
	public static void infosDirecteur(String nom_eq, Connection conn) throws SQLException {   	
    	String RequeteParametree = "SELECT * FROM Personne INNER JOIN Equipe USING(id_eq) WHERE nom_eq = ?";
    	try { 
        	PreparedStatement pstmt = conn.prepareStatement(RequeteParametree); 
        	pstmt.setString(1, nom_eq); 
        	ResultSet rset = pstmt.executeQuery();
        	if (rset != null) { 
	        	while (rset.next()) { 
			        System.out.println("\t Identifiant : " + rset.getInt("id_per") + "\t"); 
			        System.out.println("\t Directeur : " + rset.getString("nom_per") + " " + rset.getString("prenom") + "\t"); 
			        System.out.println("\t Grade : " + rset.getString("grade") + "\t"); 
			        System.out.println("\t Equipe : " + rset.getString("nom_eq") + "\t"); 
			        System.out.println("\n---------------------------------"); 
	        	} 
	        	System.out.println(); 
        	}
	        else
        		throw new SQLException ("ERREUR: ResultSet = null."); 
	        rset.close();
            pstmt.close();
        }
        catch (SQLException e) { 
           System.err.println("Erreur lors de l'exécution de la requète SQL.");
        }
    }
}
