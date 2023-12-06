package ICS381.HW1and2;

public class SLLNode<T> {
    public T info;
    public SLLNode<T> next;

    public SLLNode() {
        this(null, null);
    }

    public SLLNode(T el) {
        this(el, null);
    }

    public SLLNode(T el, SLLNode<T> ptr) {
        info = el;
        next = ptr;
    }
}
