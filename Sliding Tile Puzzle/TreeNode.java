package ICS381.HW1and2;

public class TreeNode<T> {
    public T data;
    public TreeNode<T> firstChild, nextSibling, parent;
    public int[] hole;
    public String origin = "";
    public int depth;
    public int cost;
    public TreeNode(){
        firstChild = null;
        nextSibling = null;
        parent = null;
        hole = null;
    }
    public TreeNode(T data){
        super();
        this.data = data;
        hole = new int[2];
    }
    public TreeNode(T data, int[] hole){
        super();
        this.data = data;
        this.hole = hole;
    }

    public TreeNode(T data, int[] hole, TreeNode<T> parent, String exclude){
        this.data = data;
        firstChild = null;
        nextSibling = null;
        this.parent = parent;
        this.hole = hole;
        this.origin = exclude;
    }
    public TreeNode(T data, int[] hole, TreeNode<T> parent, String exclude, int depth){
        this.data = data;
        firstChild = null;
        nextSibling = null;
        this.parent = parent;
        this.hole = hole;
        this.origin = exclude;
        this.depth = depth;
    }
    public TreeNode(T data, int[] hole, TreeNode<T> parent, String exclude, int depth, int cost){
        this.data = data;
        firstChild = null;
        nextSibling = null;
        this.parent = parent;
        this.hole = hole;
        this.origin = exclude;
        this.depth = depth;
        this.cost = cost;
    }
    public TreeNode(T data, TreeNode<T> firstChild, TreeNode<T> nextSibling){
        this.data = data;
        this.firstChild = firstChild;
        this.nextSibling = nextSibling;
    }
    public TreeNode(T data, TreeNode<T> firstChild, TreeNode<T> nextSibling, TreeNode<T> parent, int[] hole){
        this.data = data;
        this.firstChild = firstChild;
        this.nextSibling = nextSibling;
        this.parent = parent;
        this.hole = hole;
    }
}
