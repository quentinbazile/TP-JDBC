
public class Publication {

	private int id_pub; 
    private String titre; 
    private String type;
    
	public Publication(int id_pub, String titre, String type) {
		this.id_pub = id_pub;
		this.titre = titre;
		this.type = type;
	}

	public int getId_pub() {
		return id_pub;
	}

	public void setId_pub(int id_pub) {
		this.id_pub = id_pub;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	} 
}
