package dictionary;

/**
 *
 * Created by Hongyu on 2/1/2017.
 */
public interface Dictionary<T> {
    void insert(T obj);
    void delete(T obj);
    boolean search(T obj);
}
