package app.controllers;

public interface IController {
	
	//Moottorista Viewiin
	public void endSimulation(double aika);
	public void visualizeServedClient();
	public void visualizeClient();
	public void visualizeServices(int[][] sList);
	
	//Viewistä moottoriin.
	public void startSimulation();
	public void speedUpSim();
	public void slowDownSim();
	public void updateTimer(double time);
	public long getAmountOfClientsServed();
	public void getHistory();
	
}
