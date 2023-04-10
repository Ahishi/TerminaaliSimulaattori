package app.model;

import simu.framework.Kello;
import simu.framework.Trace;


// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	
	public Asiakas(){
	    id = i++;
	    
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Asiakas: ", "Uusi asiakas:" + id + ":"+saapumisaika);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void raportti(){
		Trace.out(Trace.Level.INFO, "Asiakas: ", "Asiakas "+id+ " saapui:" +saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas: ", "Asiakas "+id+ " poistui:" +poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas: ", "Asiakas "+id+ " viipyi:" +(poistumisaika-saapumisaika));
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo "+ keskiarvo);
		
		//Lisää data listaan muiden asiakkaiden datan kanssa
		SimulationData simData = SimulationData.getInstance();
		
		simData.lisaaPoistuminen(poistumisaika);
		simData.lisaaSaapuminen(saapumisaika);
		simData.lisaaViipyminen(poistumisaika-saapumisaika);
		simData.lisaalapimenoKeskiarvo(keskiarvo);
	}

}
