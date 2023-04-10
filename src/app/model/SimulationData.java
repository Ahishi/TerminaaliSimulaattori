package app.model;

import java.util.ArrayList;
import java.util.List;

public class SimulationData {
	
	private static SimulationData INSTANCE;
	private SimulationData() {}
	
	private List<Integer> means = new ArrayList<>();
	private List<Integer> variances = new ArrayList<>();
	
	private List<Double> arrivalsTimeList = new ArrayList<>();
	private List<Double> departingTimeList = new ArrayList<>();
	private List<Double> stayTimeList = new ArrayList<>();
	private List<Double> throughTimeList = new ArrayList<>();
	
	private int[][] usedServices = new int[5][5];
	
	private long asiakkaidenMaara;
	
	public static SimulationData getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SimulationData();
		}
		
		return INSTANCE;
	}
	
	public void setAktiivinenPiste(TapahtumanTyyppi tyyppi, int pos2, int queueSize) {

        if(tyyppi != TapahtumanTyyppi.CHECKIN1) {
            int pos1 = getIndexOfService(tyyppi);
            this.usedServices[pos1][pos2] = queueSize;
        }
    }

    public int getIndexOfService(TapahtumanTyyppi tyyppi) {
        switch(tyyppi) {
        case TURVATARKASTUS2:
            return 1;
        case ODOTUSAULA3:
            return 2;
        case PORTTI4:
            return 3;
        case LENTOKONE5:
            return 4;
        default:
            return 0;
        }
    }
	
	public void setAsiakkaitaPalveltu(long asiakkaidenMaara) {
		this.asiakkaidenMaara = asiakkaidenMaara;
	}
	
	//Listaan lisääjät
	public void lisaaSaapuminen(double time) {
		arrivalsTimeList.add(time);
	}
	
	public void lisaaPoistuminen(double time) {
		departingTimeList.add(time);
	}
	
	public void lisaaViipyminen(double time) {
		stayTimeList.add(time);
	}
	
	public void lisaalapimenoKeskiarvo(double time) {
		throughTimeList.add(time);
	}
	
	//getterit
	public int[][] getAktiivisetPisteet() {
		return usedServices;
	}
	
	//Lista getterit
	public List<Double> getSaapumiset() {
		return arrivalsTimeList;
	}
	
	public List<Double> getPoistumiset() {
		return departingTimeList;
	}
	
	public List<Double> getViipymiset() {
		return stayTimeList;
	}
	
	public List<Double> getLapimenemiset() {
		return throughTimeList;
	}
	
	public long getAsiakkaitaPalveltu() {
		return asiakkaidenMaara;
	}
	
	//Keskiarvo getterit
	public double getSaapumisKeskiarvo() {
		return averager(arrivalsTimeList);
	}
	
	public double getPoistumisKeskiarvo() {
		return averager(departingTimeList);
	}
	
	public double getViipymisKeskiarvo() {
		return averager(stayTimeList);
	}
	
	public double getLapimenoKeskiarvo() {
		return averager(throughTimeList);
	}
	
	//Aloitusdata
	public void setStartData(List<Integer> means, List<Integer> variances){
		this.means = means;
		this.variances = variances;
	}
	
	public List<Integer> getMeans() {
		return means;
	}
	
	public List<Integer> getVariances() {
		return variances;
	}
	
	//Keskiarvofunktio
	private double averager(List<Double> list) {
		double sum = 0;
		for(int i = 0; i < list.size(); i++) {
			sum += list.get(i);
		}
		return (sum/list.size());
	}
	
}
