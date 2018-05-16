package online.travel.agent.model.db;

import java.util.List;

import javax.persistence.EntityManager;

public interface DatabaseManager<T> {
	public T insert(T t, EntityManager entityManager);

	public T read(long id, Class<T> clazz, EntityManager entityManager);

	public boolean delete(long id, Class<T> clazz, EntityManager entityManager);

	public List<T> search(String key, String value, Class<T> clazz, EntityManager entityManager);
}
