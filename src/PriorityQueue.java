import java.lang.reflect.Array;
import java.util.ArrayList;

public class PriorityQueue<E extends Comparable<E>> {
    private Node<E> head;

    public void enqueue(E value) {
        Node<E> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            head = merge(head, node);
        }
    }

    public E dequeue() {
        Node<E> front = head;
        head = null;
        head = merge(front.right, front.left);
        return front.value;
    }

    public boolean isEmpty() {
        return head == null;
    }

    private Node<E> merge(Node<E> t1, Node<E> t2) {
        Node<E> small;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        if (t1.value.compareTo(t2.value) < 0) {
            t1.right = merge(t1.right, t2);
            small = t1;
        }
        else {
            t2.right = merge(t2.right, t1);
            small = t2;
        }
        if (getNpl(small.left) < getNpl(small.right))
            swapKids(small);
        setNullPathLength(small);
        return small;
    }

    // Could be a problem with the += 1
    private void setNullPathLength(Node<E> small) {
        if (small.right == null || small.left == null) {
            small.npl = 0;
        } else {
            small.npl += 1;
        }
    }

    private void swapKids(Node<E> small) {
        Node<E> holder = small.left;
        small.left = small.right;
        small.right = holder;
    }

    private int getNpl(Node<E> t) {
        if (t == null) return -1;
        return t.npl;
    }

    public Node<E> getHead() {
        return head;
    }

    public void printTree(Node<Task> node, StringBuilder sb, int counter) {
        if (node == null) return;
        counter++;
        printTree(node.right, sb, counter);
        String tab = new String(new char[counter]).replace("\0", "  ");
        sb.append(tab).append(node.value).append("\n");
        printTree(node.left, sb, counter);
    }

    private static class Node<E extends Comparable<E>> implements Comparable<Node<E>> {
        public E value;
        public Node<E> left;
        public Node<E> right;
        public int npl;

        public Node(E value) {
            this(value, null, null);
        }

        public Node(E value, Node<E> left, Node<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
            npl = 0;
        }

        // This compareTo method might be wrong
        @Override
        public int compareTo(Node<E> o) {
            return value.compareTo(o.value);
        }
    }
}
