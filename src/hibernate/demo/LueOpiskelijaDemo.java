package hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import hibernate.demo.entity.Opiskelija;

public class LueOpiskelijaDemo {

	public static void main(String[] args) {
		
		
		
		SessionFactory istuntoTehdas = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Opiskelija.class).buildSessionFactory();

		Session istunto = istuntoTehdas.getCurrentSession();
		
		try {
			Opiskelija tempOpiskelija = new Opiskelija("Sukka", "na", "sisaan@jeah.com");
			
			Transaction tx = istunto.beginTransaction();

			istunto.save(tempOpiskelija);

			tx.commit();

			System.out.println(tempOpiskelija);
			
			System.out.println("Done!");
			
		} finally {
			istuntoTehdas.close();
		}

		

	}

}
