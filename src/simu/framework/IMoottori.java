package simu.framework;

import app.model.Asiakas;

public interface IMoottori { // UUSI
		
	// Kontrolleri käyttää tätä rajapintaa
	
	public void setSimulointiaika(double aika);
	public void setViive(long aika);
	public long getViive();
	public void goBack(Asiakas a, int palvelu);
}
