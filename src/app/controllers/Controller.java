package app.controllers;

import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.framework.Trace;

import java.util.List;

import app.hibernate.InfoFromDatabase;
import app.hibernate.SimDataEntity;
import app.model.OmaMoottori;
import app.model.SimulationData;
import app.view.ISimulationUI;
import javafx.application.Platform;

public class Controller implements IController{

	private IMoottori moottori; 
	private ISimulationUI ui;
	
	private long amountOfClientsServed = 0;
	private long amountOfClients = 0;
	
	public Controller(ISimulationUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void startSimulation() {
		//Käynnistä simulointi.
		moottori = new OmaMoottori(this);
		moottori.setSimulointiaika(ui.getTime());
		moottori.setViive(ui.getDelay());
		ui.setCurrentDelay(moottori.getViive());
		((Thread)moottori).start();
	}
	
	@Override
	public void endSimulation(double time) {
		Platform.runLater(() -> ui.setTime(time, true));
		updateProgress();
		SimulationData.getInstance().setAsiakkaitaPalveltu(amountOfClientsServed);
		Trace.out(Trace.Level.INFO, "\nController: ", "Simulaatio päätetty!");
	}

	@Override
	public void visualizeServedClient() {
		updateProgress();
		this.amountOfClientsServed++;
		Platform.runLater(() -> ui.setAmountOfServedClients(amountOfClientsServed));
	}
	
	@Override
	public void visualizeClient() {
		this.amountOfClients++;
		Platform.runLater(() -> ui.setAmountOfClients(amountOfClients));
	}
	
	@Override
	public void visualizeServices(int[][] sList) {
		Platform.runLater(() -> ui.drawService(sList));
	}

	private void updateProgress() {
		double clockTime = Kello.getInstance().getAika();
		double uiTime = ui.getTime();
		if(clockTime <= uiTime) {
			ui.setLoadBarValue(clockTime/uiTime);
		} else {
			ui.setLoadBarValue(1);
		}
	}
	
	@Override
	public void getHistory() {
		InfoFromDatabase ifd = new InfoFromDatabase();
		List<SimDataEntity> asiakkaat = ifd.getDataFromDatabase();
		for (SimDataEntity tempAsiakas : asiakkaat) {
            ui.setHistory(tempAsiakas);
        }
		ui.historyIsDone();
	}
	
	@Override 
	public long getAmountOfClientsServed() {
		return amountOfClientsServed;
	}
	
	@Override
	public void updateTimer(double time) {
		Platform.runLater(() -> ui.setTime(time, false));
	}

	@Override
	public void speedUpSim() {
		moottori.setViive((long)(moottori.getViive()*0.9));
		ui.setCurrentDelay(moottori.getViive());
		
	}

	@Override
	public void slowDownSim() {
		moottori.setViive((long)(moottori.getViive()*1.10));
		ui.setCurrentDelay(moottori.getViive());
		
	}

}
