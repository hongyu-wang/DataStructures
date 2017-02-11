package dictionary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Created by Hongyu on 2/1/2017.
 */
public class MyHashTable<T> implements Dictionary<T>{

    private List<List<T>> lists;
    private int expectedSize;

    public MyHashTable(int expectedSize){
        lists = new ArrayList<>(expectedSize);
        for (int i = 0; i < expectedSize; i ++){
            lists.add(new LinkedList<>());
        }
        this.expectedSize = expectedSize;

    }

    @Override
    public void insert(T obj) {
        int index = obj.hashCode()%expectedSize;
        lists.get(index).add(obj);

    }

    @Override
    public void delete(T obj) {

    }

    @Override
    public boolean search(T obj) {

        return false;
    }
}
