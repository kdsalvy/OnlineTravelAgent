package online.travel.agent.model.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class DatabaseManagerOperation<T> implements DatabaseManager<T> {

	private static final Object _LOCK = new Object();
	private static Logger LOG = Log.getLogger(DatabaseManagerOperation.class);

	@Override
	public T insert(T t) {
		EntityManager entityManager = DatabaseUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			synchronized (_LOCK) {
				entityManager.persist(t);
				entityManager.flush();
			}
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();
		}
		return t;
	}

	@Override
	public T read(long id, Class<T> clazz) {
		EntityManager entityManager = DatabaseUtil.getEntityManager();
		T t = null;
		try {
			t = (T) entityManager.find(clazz, id);
		} finally {
			entityManager.close();
			if (t == null)
				LOG.warn("No records records were found with given id value!");
		}
		return t;
	}

	@Override
	public boolean delete(long id, Class<T> clazz) {
		EntityManager entityManager = DatabaseUtil.getEntityManager();
		boolean result = false;
		try {
			entityManager.getTransaction().begin();
			T t = null;
			synchronized (_LOCK) {
				t = (T) entityManager.find(clazz, id);
				if (t != null) {
					entityManager.remove(t);
					entityManager.getTransaction().commit();
					result = true;
				} else {
					LOG.warn("No records records were found with given id value !!");
					result = false;
				}
			}
		} catch (Exception e) {
			result = false;
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();
		}
		return result;
	}

	@Override
	public List<T> search(String key, String value, Class<T> clazz) {
		EntityManager entityManager = DatabaseUtil.getEntityManager();
		try {
		    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		    Root<T> root = criteriaQuery.from(clazz);
		    criteriaQuery.select(root);
		    Predicate where = criteriaBuilder.equal(root.get(key), value);
		    criteriaQuery.where(where);
		    return entityManager.createQuery(criteriaQuery).getResultList();
		} finally {
		    entityManager.close();
		}
	}

}
