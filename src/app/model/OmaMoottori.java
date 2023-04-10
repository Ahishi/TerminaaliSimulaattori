package app.model;

import java.util.List;

import app.controllers.IController;
import app.hibernate.InfoToDatabase;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.framework.Trace;


public class OmaMoottori extends Moottori{
	
	private Saapumisprosessi saapumisprosessi;
	
	private final int ARR_MAARA = 1;
	private final int CHECKIN_MAARA = 3;
	private final int TURVATARKASTUS_MAARA = 4;
	private final int ODOTUSAULA_MAARA = 1;
	private final int PORTTI_MAARA = 2;
	
	private final int ARR_JONO = 200;
	private final int CHECKIN_JONO = 4;
	private final int TURVATARKASTUS_JONO = 4;
	private final int ODOTUSAULA_JONO = 50;
	private final int PORTTI_JONO = 4;
	
	public OmaMoottori(IController kontrolleri){

		super(kontrolleri);
		
		SimulationData simData = SimulationData.getInstance();
		
		List<Integer> means = simData.getMeans();
		List<Integer> variances = simData.getVariances();
		
		palvelupisteet = new Palvelupiste[5];
	
		//Kehitä tähän loop?
		palvelupisteet[0]=new Palvelupiste(this, new Normal(1, 1), tapahtumalista, TapahtumanTyyppi.CHECKIN1,           ARR_JONO,                ARR_MAARA);
        palvelupisteet[1]=new Palvelupiste(this, new Normal(means.get(1), variances.get(1)), tapahtumalista, TapahtumanTyyppi.TURVATARKASTUS2,  CHECKIN_JONO,            CHECKIN_MAARA);
        palvelupisteet[2]=new Palvelupiste(this, new Normal(means.get(2), variances.get(2)), tapahtumalista, TapahtumanTyyppi.ODOTUSAULA3,       TURVATARKASTUS_JONO,    TURVATARKASTUS_MAARA);
        palvelupisteet[3]=new Palvelupiste(this, new Normal(means.get(3), variances.get(3)), tapahtumalista, TapahtumanTyyppi.PORTTI4,           ODOTUSAULA_JONO,        ODOTUSAULA_MAARA);
        palvelupisteet[4]=new Palvelupiste(this, new Normal(means.get(4), variances.get(4)), tapahtumalista, TapahtumanTyyppi.LENTOKONE5,       PORTTI_JONO,            PORTTI_MAARA);
		
		saapumisprosessi = new Saapumisprosessi(new Negexp(means.get(0), variances.get(0)), tapahtumalista, TapahtumanTyyppi.ARR); //Tähän joku valinta?

	}

	
	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}
	
	
	@Override
	protected void suoritaTapahtuma(Tapahtuma t){  // B-vaiheen tapahtumat
		
		kontrolleri.updateTimer(Kello.getInstance().getAika());
		
		try {
			kontrolleri.visualizeServices(SimulationData.getInstance().getAktiivisetPisteet());
		} catch(Exception e) {
			
		}
		
		Asiakas a;
		switch (t.getTyyppi()){
			
			case ARR: 
						Trace.out(Trace.Level.INFO, "\nOmaMoottori: ", "Vaihe: ARR" );
						kontrolleri.visualizeClient();
						palvelupisteet[0].lisaaJonoon(new Asiakas());	
						saapumisprosessi.generoiSeuraava();	
						
				break;
			case CHECKIN1:
				
						Trace.out(Trace.Level.INFO, "\nOmaMoottori: ", "Vaihe: Check-in" );
						
						a = palvelupisteet[0].otaJonosta();
						palvelupisteet[1].lisaaJonoon(a);

				break;
			case TURVATARKASTUS2: 
				
						Trace.out(Trace.Level.INFO, "\nOmaMoottori: ", "Vaihe: Turvatarkastus" );
						
						a = palvelupisteet[1].otaJonosta();
						  palvelupisteet[2].lisaaJonoon(a);
						  
				break;  
			case ODOTUSAULA3: 
				
						Trace.out(Trace.Level.INFO, "\nOmaMoottori: ", "Vaihe: Odotusaula" );
						
						a = palvelupisteet[2].otaJonosta();
			   		  	palvelupisteet[3].lisaaJonoon(a); 
			   		  	
		   	     break; 
			case PORTTI4: 
				
						Trace.out(Trace.Level.INFO, "\nOmaMoottori: ", "Vaihe: Portti");
						
						a = palvelupisteet[3].otaJonosta();
						palvelupisteet[4].lisaaJonoon(a);
				 break;
						
			case LENTOKONE5:
				
						Trace.out(Trace.Level.INFO, "\nOmaMoottori: ", "Vaihe: Lentokoneeseen poistuminen");
						
						a = palvelupisteet[4].otaJonosta();
						a.setPoistumisaika(Kello.getInstance().getAika());
						kontrolleri.visualizeServedClient();
						a.raportti();
				 break;
		default:
			break; 
		}	
	}
	
	@Override
    public void goBack(Asiakas a, int palvelu) {
        palvelupisteet[palvelu].lisaaJonoon(a);
    }

	
	@Override
	protected void tulokset() {
		
		// VANHAA tekstipohjaista
		// System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		// System.out.println("Tulokset ... puuttuvat vielä");
		
		// UUTTA graafista
		kontrolleri.endSimulation(Kello.getInstance().getAika());
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		InfoToDatabase itd = new InfoToDatabase();
		itd.setHibernateData();
	}
}
