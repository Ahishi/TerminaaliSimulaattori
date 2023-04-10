package app.hibernate;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import app.model.SimulationData;
import simu.framework.Kello;

@Entity
@Table(name="asiakastiedot")
public class SimDataEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="asiakasmaara")
    private String asiakasmaara;

    @Column(name="aika")
    private String aika;

    public SimDataEntity() {
        SimulationData simData = SimulationData.getInstance();
        this.asiakasmaara =  Long.toString(simData.getAsiakkaitaPalveltu());
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.aika = formatter.format(Kello.getInstance().getAika());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public String getAsiakasmaara() {
        return asiakasmaara;
    }

    public void setAsiakasmaara(String asiakasmaara) {
        this.asiakasmaara = asiakasmaara;
    }

    @Override
    public String toString() {
        return "Asiakas [id=" + id + ", palveltujen määrä=" + asiakasmaara + ", aika=" + aika + "]";
    }

}
