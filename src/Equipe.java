
public class Equipe {
	
	private int id_eq; 
    private String nom_eq;
    private int id_per;
    
	public Equipe(int id_eq, String nom_eq, int id_per) {
		this.id_eq = id_eq;
		this.nom_eq = nom_eq;
		this.id_per = id_per;
	}

	public int getId_eq() {
		return id_eq;
	}

	public void setId_eq(int id_eq) {
		this.id_eq = id_eq;
	}

	public String getNom_eq() {
		return nom_eq;
	}

	public void setNom_eq(String nom_eq) {
		this.nom_eq = nom_eq;
	}

	public int getId_per() {
		return id_per;
	}

	public void setId_per(int id_per) {
		this.id_per = id_per;
	}
}
