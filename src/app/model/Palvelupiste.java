package app.model;

import simu.framework.Kello;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

import java.util.Arrays;
import java.util.Comparator;

import eduni.distributions.ContinuousGenerator;

public class Palvelupiste {

	private SubPalvelupiste[] SBPlista;
	private TapahtumanTyyppi tyyppi;
	private OmaMoottori oMoottori;
	
	public Palvelupiste(OmaMoottori oMoottori, ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, int jononPituus, int SBPMaara) {
		
		this.oMoottori = oMoottori;
		this.tyyppi = tyyppi;
		
		SBPlista = new SubPalvelupiste[SBPMaara];
		for(int i = 0; i < SBPlista.length; i++) {
			SBPlista[i] = new SubPalvelupiste(generator, tapahtumalista, tyyppi, jononPituus, i);
		}
	}
	
	public void aloitaPalvelu() {
		for(int i = 0; i < SBPlista.length; i++) {
			if(!SBPlista[i].isOccupied() && SBPlista[i].clientInQueue()) {
				SBPlista[i].startService();
			}
		}
	}
	
	public void lisaaJonoon(Asiakas a) {
		
		Arrays.sort(SBPlista, new Comparator<SubPalvelupiste>() {
	        @Override
	        public int compare(SubPalvelupiste sp1, SubPalvelupiste sp2) {
	            return Integer.compare(sp1.getQueueLenght(), sp2.getQueueLenght());
	        }
	    });
		
		boolean onAsetettu = false;
		for(int i = 0; i < SBPlista.length && !onAsetettu; i++) {
			if(SBPlista[i].addToQueue(a) && a != null) {
				onAsetettu = true;
			}
		}
		
		if (a == null) {
			Trace.out(Trace.Level.ERR, "Palvelupiste: ", "Asiakas on NULL");
		}
		
		if(!onAsetettu) {
			Trace.out(Trace.Level.ERR, "Palvelupiste: ", tyyppi +": Kaikki täynnä!");
			if(!onAsetettu) {
	            Trace.out(Trace.Level.ERR, "Palvelupiste: ", tyyppi +": Piste täynnä!");
	            int pos = SimulationData.getInstance().getIndexOfService(tyyppi)-1;
	            try {
	            	oMoottori.goBack(a, pos);
	            } catch (Exception e) {
	            	Trace.out(Trace.Level.ERR, "Palvelupiste: ", tyyppi +": Kaikki täynnä, ei voitu lisätä.");
	            }
	        }
		}
	}
	
	public Asiakas otaJonosta() {
		
		for(int i = 0; i < SBPlista.length; i++) {
			if(SBPlista[i].getComparableTime() == Kello.getInstance().getAika()) {
				return SBPlista[i].takeFromQueue();
			}
			
		}
		
		Trace.out(Trace.Level.ERR, "Palvelupiste: ", "Asiakasta ei saatu noudettua!");
		return null;
		
	}
	
	public boolean onJonossa() {
		for(SubPalvelupiste SP: SBPlista) {
			if(SP.clientInQueue()) {
				return true;
			}
		}
		return false;
		
	}
	
	public boolean onVarattu() {
		return false;
	}

}
