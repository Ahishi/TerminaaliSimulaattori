package app.view;

import app.MainApp;
import app.hibernate.SimDataEntity;

public interface ISimulationUI {
	public double getTime();
	public long getDelay();
	public void setMainApp(MainApp mainApp);
	public void setCurrentDelay(long delay);
	public void setTime(double time, boolean isFinal);
	public void setLoadBarValue(double howFull);
	public void updateUI();
	public void setAmountOfClients(long maara);
	public void setAmountOfServedClients(long maara);
	public void drawService(int[][] sList);
	public void setHistory(SimDataEntity sDE);
	public void historyIsDone();
}
