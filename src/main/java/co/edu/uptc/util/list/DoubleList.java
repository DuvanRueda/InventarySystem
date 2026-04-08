package co.edu.uptc.util.list;

import java.util.ArrayList;
import java.util.List;

public class DoubleList<T> {

    public class Node<T> {
        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
        public T value;
        public Node<T> next;
        public Node<T> previous;
    }

    private Node<T> tail = null;
    private Node<T> head = null;

    private boolean addIfHeadEmpty(T value) {
        if (head == null) {
            head = new Node<>(value, null, null);
            tail = head;
            return true;
        }
        return false;
    }

    public void addFirst(T value) {
        if (addIfHeadEmpty(value)) return;
        Node<T> newNode = new Node<>(value, head, null);
        head.previous = newNode;
        head = newNode;
    }

    public void addLast(T value) {
        if (addIfHeadEmpty(value)) return;
        Node<T> newNode = new Node<>(value, null, tail);
        tail.next = newNode;
        tail = newNode;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public T delete() {
        if (isEmpty()) return null;
        Node<T> temp = head;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        return temp.value;
    }

    public List<T> getAllObjects() {
        Node<T> auxNode = head;
        List<T> auxList = new ArrayList<>();
        while (auxNode != null) {
            auxList.add(auxNode.value);
            auxNode = auxNode.next;
        }
        return auxList;
    }
}
