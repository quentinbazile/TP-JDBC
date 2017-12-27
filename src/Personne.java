
public class Personne {

	private int id_per; 
    private String nom_per;
    private String prenom;
    private String grade;
    private int id_eq;
    
	public Personne(int id_per, String nom_per, String prenom, String grade, int id_eq) {
		this.id_per = id_per;
		this.nom_per = nom_per;
		this.prenom = prenom;
		this.grade = grade;
		this.id_eq = id_eq;
	}

	public int getId_per() {
		return id_per;
	}

	public void setId_per(int id_per) {
		this.id_per = id_per;
	}

	public String getNom_per() {
		return nom_per;
	}

	public void setNom_per(String nom_per) {
		this.nom_per = nom_per;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getId_eq() {
		return id_eq;
	}

	public void setId_eq(int id_eq) {
		this.id_eq = id_eq;
	}   
}
