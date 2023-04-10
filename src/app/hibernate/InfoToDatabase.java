package app.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class InfoToDatabase {
	
	public InfoToDatabase() {
		
	}

	public void setHibernateData(){
		
		SessionFactory istuntoTehdas = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SimDataEntity.class).buildSessionFactory();

		Session istunto = istuntoTehdas.openSession();
		
		try {
			SimDataEntity sDE = new SimDataEntity();
			
			Transaction tx = istunto.beginTransaction();

			istunto.save(sDE);

			tx.commit();

			System.out.println(sDE);
			
			System.out.println("Done!");
			
		} finally {
			istuntoTehdas.close();
		}

	}

}
