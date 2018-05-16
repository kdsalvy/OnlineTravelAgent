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
    public T insert(T t, EntityManager entityManager) {

	try {
	    synchronized (_LOCK) {
		entityManager.persist(t);
		entityManager.flush();
	    }
	} catch (Exception e) {
	    throw e;
	}
	return t;
    }

    @Override
    public T read(long id, Class<T> clazz, EntityManager entityManager) {
	T t = null;
	try {
	    t = (T) entityManager.find(clazz, id);
	} finally {
	    if (t == null)
		LOG.warn("No records records were found with given id value!");
	}
	return t;
    }

    @Override
    public boolean delete(long id, Class<T> clazz, EntityManager entityManager) {
	boolean result = false;
	try {
	    T t = null;
	    synchronized (_LOCK) {
		t = (T) entityManager.find(clazz, id);
		if (t != null) {
		    entityManager.remove(t);
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
	}
	return result;
    }

    @Override
    public List<T> search(String key, String value, Class<T> clazz, EntityManager entityManager) {
	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
	Root<T> root = criteriaQuery.from(clazz);
	criteriaQuery.select(root);
	Predicate where = criteriaBuilder.equal(root.get(key.toLowerCase()), value);
	criteriaQuery.where(where);
	return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
