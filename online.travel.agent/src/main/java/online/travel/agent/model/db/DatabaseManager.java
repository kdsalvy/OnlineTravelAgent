package online.travel.agent.model.db;

import java.util.List;

public interface DatabaseManager<T> {
	public T insert(T t);

	public T read(long id, Class<T> clazz);

	public boolean delete(long id, Class<T> clazz);

	public List<T> search(String key, String value, Class<T> clazz);
}
