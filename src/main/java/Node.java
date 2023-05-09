/**
 * The Node class implement standard node for doubly linked list.
 * The Queue class will use this class.
 */
public class Node <T> {
    private T patient;
    private Node next;
    private  Node prev;
    public Node(T patient){
        this.patient = patient;
        this.next = null;
        this.prev = null;
    }

    public Node getNext(){
        return next;
    }
    public Node getPrev(){
        return prev;
    }
    public T getPatient(){
        return patient;
    }
    public void setNext(Node n){
        this.next = n;
    }
    public void setPrev(Node n){
        this.prev = n;
    }
    public void setPatient(T p){
        this.patient = p;
    }
};
