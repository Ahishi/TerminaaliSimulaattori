package hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import hibernate.demo.entity.Opiskelija;
import simu.framework.Trace;

public class PrimaryKeyDemo {

	public static void main(String[] args) {
		
		//create session factory
		try {
			SessionFactory istuntoTehdas = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Opiskelija.class).buildSessionFactory();
			
			// create session
			Session istunto = istuntoTehdas.openSession();

			try {
				// create 3 student objects
				System.out.println("Creating 3 student objects...");
				Opiskelija tempOpiskelija1 = new Opiskelija("Nana", "nono", "asdfasf.fg@jeah.com");
				Opiskelija tempOpiskelija2 = new Opiskelija("Saippa", "hehe", "asdfasf@jeah.com");
				Opiskelija tempOpiskelija3 = new Opiskelija("Hirmu", "afdsf", "asdfsf@jeah.com");
	
				//start a transaction
				istunto.beginTransaction();
				
				//save the student object
				System.out.println("Saving the student...");
				istunto.save(tempOpiskelija1);
				istunto.save(tempOpiskelija2);
				istunto.save(tempOpiskelija3);
				
				//commit transaction
				istunto.getTransaction().commit();
				
				System.out.println("Done!");
	
			} finally {
				istuntoTehdas.close();
			}
		
		} catch(Exception e) {
			Trace.out(Trace.Level.ERR, "PrimaryKeyDemo: ", "Virhe tietokannassa.");
		}

	}

}
