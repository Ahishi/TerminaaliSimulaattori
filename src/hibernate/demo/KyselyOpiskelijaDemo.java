package hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import hibernate.demo.entity.Opiskelija;

public class KyselyOpiskelijaDemo {

	public static void main(String[] args) {

		//create session factory
		SessionFactory istuntoTehdas = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Opiskelija.class).buildSessionFactory();

		// create session
		Session istunto = istuntoTehdas.openSession();

		try {
			
			//start a transaction
			istunto.beginTransaction();

			//commit transaction
			istunto.getTransaction().commit();
			
			// query students
			@SuppressWarnings("unchecked")
			List<Opiskelija> theOpiskelijat = istunto.createQuery("from Opiskelija").getResultList();
			
			// display the students
			for (Opiskelija tempOpiskelija : theOpiskelijat) {
				System.out.println(tempOpiskelija);
			}
			
			// commit the transaction
			istunto.getTransaction().commit();
			
			System.out.println("Done!");

			
		} finally {
			istuntoTehdas.close();
		}
	}
}
