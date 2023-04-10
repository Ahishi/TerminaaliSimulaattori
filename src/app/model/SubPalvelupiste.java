package app.model;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

public class SubPalvelupiste {
	private LinkedList<Asiakas> queue = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	
	private ContinuousGenerator generator;
	private Tapahtumalista eventList;
	private TapahtumanTyyppi typeOfSceduledEvent; 
	private int indexOfService;
	private double servicetime;
	private int maxQueueLenght = 0;
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean occupied = false;

	public SubPalvelupiste(ContinuousGenerator generator, Tapahtumalista eventlist, TapahtumanTyyppi type, int queueLenght , int indexOfService){
		this.eventList = eventlist;
		this.generator = generator;
		this.typeOfSceduledEvent = type;
		this.maxQueueLenght = queueLenght;
		this.indexOfService = indexOfService;
				
	}

	public boolean addToQueue(Asiakas a) {
		if(queue.size() < maxQueueLenght) {
			queue.add(a);
			Trace.out(Trace.Level.INFO, "SubPalvelupiste: ", "Lisätty asiakas: " + a.getId() + ", kohteessa: " + getCurrentType(typeOfSceduledEvent) + ": (" + (indexOfService+1) + ")");
			SimulationData.getInstance().setAktiivinenPiste(typeOfSceduledEvent, (indexOfService+1), getQueueLenght());
			return true;
		} else {
			return false;
		}
	}

	public Asiakas takeFromQueue(){  // Poistetaan palvelussa ollut
		occupied = false;
		Asiakas a = queue.poll();
		SimulationData.getInstance().setAktiivinenPiste(typeOfSceduledEvent, (indexOfService+1), getQueueLenght());
		return a;
	}

	public void startService(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "SubPalvelupiste: ", "Käynnistettään " + getCurrentType(typeOfSceduledEvent) + ": (" + (indexOfService+1) + ")");
		occupied = true;
		servicetime = Kello.getInstance().getAika() + generator.sample();
		eventList.lisaa(new Tapahtuma(typeOfSceduledEvent,servicetime));
	}
	
	public int getQueueLenght() {
		return queue.size();
	}
	
	public double getComparableTime() {
		return servicetime;
	}

	public boolean isOccupied(){
		return occupied;
	}

	public boolean clientInQueue(){
		return queue.size() != 0;
	}
	
	private String getCurrentType(TapahtumanTyyppi TT) {
		switch(TT) {
		case CHECKIN1:
			return "ARR";
		case TURVATARKASTUS2:
			return "CHECK-IN";
		case ODOTUSAULA3:
			return "TURVATARKASTUS";
		case PORTTI4:
			return "ODOTUSAULA";
		case LENTOKONE5:
			return "PORTTI";
		default:
			return "";
		}
	}
}