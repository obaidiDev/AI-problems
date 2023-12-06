package ICS381.HW1and2;

public class Stack<T> {
    private java.util.ArrayList<T> pool = new java.util.ArrayList<T>();
    public int size;
    public Stack() {
        size = 0;
    }
    public Stack(int n) {
        size = n;
        pool.ensureCapacity(n);
    }
    public void clear() {
        size = 0;
        pool.clear();
    }
    public boolean isEmpty() {
        return pool.isEmpty();
    }
    public T topEl() {
        if (isEmpty())
            throw new java.util.EmptyStackException();
        return pool.get(pool.size()-1);
    }
    public T remove() {
        if (isEmpty())
            throw new java.util.EmptyStackException();
        size--;
        return pool.remove(pool.size()-1);
    }
    public void add(T el) {
        size++;
        pool.add(el);
    }
    public boolean contains(T el){
        return pool.contains(el);
    }
    public String toString() {
        return pool.toString();
    }
}