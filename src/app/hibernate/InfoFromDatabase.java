package app.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class InfoFromDatabase {
	
	private List<SimDataEntity> asiakkaat;

	public InfoFromDatabase() {}
	
    public List<SimDataEntity> getDataFromDatabase() {
    	getHibernateData();
    	return asiakkaat;
    }

    @SuppressWarnings("unchecked")
	private void getHibernateData() {

        SessionFactory istuntoTehdas = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(SimDataEntity.class).buildSessionFactory();

        Session istunto = istuntoTehdas.openSession();

        try {

            Transaction tx = istunto.beginTransaction();

			this.asiakkaat = istunto.createQuery("from SimDataEntity").getResultList();

            for (SimDataEntity tempAsiakas : asiakkaat) {
                System.out.println(tempAsiakas);
            }

            tx.commit();

            System.out.println("Done!");

        } finally {
            istuntoTehdas.close();
        }
    }
    
}