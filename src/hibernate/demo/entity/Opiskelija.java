package hibernate.demo.entity;

import javax.persistence.*;

@Entity
@Table(name="opiskelija")
public class Opiskelija {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="eka_nimi")
	private String ekaNimi;
	
	@Column(name="suku_nimi")
	private String sukuNimi;
	
	@Column(name="sposti")
	private String sposti;
	
	public Opiskelija() {
		
	}

	public Opiskelija(String ekaNimi, String sukuNimi, String sposti) {
		super();
		this.ekaNimi = ekaNimi;
		this.sukuNimi = sukuNimi;
		this.sposti = sposti;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEkaNimi() {
		return ekaNimi;
	}

	public void setEkaNimi(String ekaNimi) {
		this.ekaNimi = ekaNimi;
	}

	public String getSukuNimi() {
		return sukuNimi;
	}

	public void setSukuNimi(String sukuNimi) {
		this.sukuNimi = sukuNimi;
	}

	public String getSposti() {
		return sposti;
	}

	public void setSposti(String sposti) {
		this.sposti = sposti;
	}

	@Override
	public String toString() {
		return "Opiskelija [id=" + id + ", ekaNimi=" + ekaNimi + ", sukuNimi=" + sukuNimi + ", sposti=" + sposti + "]";
	}
	
	
	
}
