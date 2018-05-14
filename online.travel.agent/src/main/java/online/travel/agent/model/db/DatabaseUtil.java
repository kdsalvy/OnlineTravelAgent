package online.travel.agent.model.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseUtil {

	private static volatile EntityManagerFactory factory = null;
	private static final Object _LOCK = new Object();

	private DatabaseUtil() {
	}

	private static <T> EntityManagerFactory getEntityManagerFactory() {
		try {
			if (factory == null)
				synchronized (_LOCK) {
					if (factory == null) {
						factory = Persistence.createEntityManagerFactory("InMemH2DB");
					}
				}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return factory;
	}

	public static EntityManager getEntityManager() {
		return DatabaseUtil.getEntityManagerFactory().createEntityManager();
	}
}
