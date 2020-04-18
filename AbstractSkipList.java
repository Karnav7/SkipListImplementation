import java.util.Random;

public abstract class AbstractSkipList<E> implements OrderedDictionary<E> {

    public Node<E> head;
    public Node<E> tail;

    public class Node<T> {

        public T data;
        public int level;
        public Node<T>[] next;
        public Node<T> prev;
        public int[] traverse;

        public Node(T data, int level) {
            this.data = data;
            this.level = level;
            this.next = new Node[level];

            this.traverse = new int[level];
        }
    }

}