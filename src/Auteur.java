
public class Auteur {
	
	private int id_au; 
    private int id_pub;
    
	public Auteur(int id_au, int id_pub) {
		this.id_au = id_au;
		this.id_pub = id_pub;
	}

	public int getId_au() {
		return id_au;
	}

	public void setId_au(int id_au) {
		this.id_au = id_au;
	}

	public int getId_pub() {
		return id_pub;
	}

	public void setId_pub(int id_pub) {
		this.id_pub = id_pub;
	}   
}
