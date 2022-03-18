public class PriorityQueue<E extends Comparable<E>> {
    private Node<E> head;

    public void enqueue(E value) {
        Node<E> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            merge(head, node);
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
        setNullPathLength(small, -1);
        return small;
    }

    private void setNullPathLength(Node<E> small, int counter) {
        if (small.right == null || small.left == null) {
            small.npl = -1;
            return;
        }
        setNullPathLength(small.right, counter++);
        setNullPathLength(small.left, counter++);
    }

    private void swapKids(Node<E> small) {
        Node<E> holder = small;
        small.right = small.left;
        small.left = holder.right;
    }

    private int getNpl(Node<E> t) {
        if (t == null) return -1;
        return t.npl;
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

        @Override
        public int compareTo(Node<E> o) {
            return value.compareTo(o.value);
        }
    }
}
