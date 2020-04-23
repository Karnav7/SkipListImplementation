// package src.skiplist;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.time.LocalDateTime;
import java.time.format.*;
import java.util.Random;
import java.time.Duration;

public class SkipList<K extends Comparable<K>, V> implements Iterable<K> {

    protected static final Random randomGenerator = new Random();

    protected static final double DEFAULT_PROBABILITY = 0.5;

    private Node<K, V> head;

    private double probability;

    private int size;
    public static int count = 0;

    public SkipList() {
        this(DEFAULT_PROBABILITY);
    }

    public SkipList(double probability) {
        this.head = new Node<K, V>(null, null, 0);
        this.probability = probability;
        this.size = 0;
    }

    public V get(K key) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        // System.out.println("up " + node.level);
        if (node.getKey().compareTo(key) == 0)
            return node.getValue();
        else
            return null;
    }

    public void insertElement(K key, V value) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node.getKey() != null && node.getKey().compareTo(key) == 0) {
            node.setValue(value);
            System.out.println("Key already exist in the Skip List!");
            return;
        }

        Node<K, V> newNode = new Node<K, V>(key, value, node.getLevel());
        horizontalInsert(node, newNode);
        // Decide level according to the probability function
        int currentLevel = node.getLevel();
        int headLevel = head.getLevel();
        while (isBuildLevel()) {
            // buiding a new level
            if (currentLevel >= headLevel) {
                Node<K, V> newHead = new Node<K, V>(null, null, headLevel + 1);
                verticalLink(newHead, head);
                head = newHead;
                headLevel = head.getLevel();
            }

            count++;
            // Move to the bottom
            while (node.getDown() != null) {
                count++;
                node = node.getDown();
            }
                
            // Because node is on the lowest level so we need remove by down-top
            Node<K, V> prev = null;
            Node<K, V> next = null;

            Node<K, V> tmp = new Node<K, V>(key, value, node.getLevel());
            horizontalInsert(node, tmp);
            verticalLink(tmp, newNode);
            newNode = tmp;
            currentLevel++;
        }
        size++;
        System.out.println("Inserted successfully!");
    }

    public void removeElement(K key) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if (node == null || node.getKey().compareTo(key) != 0)
            System.out.println("The key does not exist!");

        // Move to the bottom
        while (node.getDown() != null) {
            count++;
            node = node.getDown();
        }
            
        // Because node is on the lowest level so we need remove by down-top
        Node<K, V> prev = null;
        Node<K, V> next = null;
        for (; node != null; node = node.getUp()) {
            prev = node.getPrevious();
            next = node.getNext();
            if (prev != null)
                prev.setNext(next);
            if (next != null)
                next.setPrevious(prev);

            count++;
        }

        // Adjust head
        while (head.getNext() == null && head.getDown() != null) {
            head = head.getDown();
            head.setUp(null);
        }
        size--;
        System.out.println("Element " + key + " was removed successfully!");
    }

    public boolean findElement(K key) {
        // System.out.println("YO: " + get(key));
        if ( get(key) != null ) {
            System.out.println("Element " + key + " was found!");
        } else {
            System.out.println("Element " + key + " was not found!");
        } 
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    protected Node<K, V> findNode(K key) {
        Node<K, V> node = head;
        Node<K, V> next = null;
        Node<K, V> down = null;
        K nodeKey = null;

        // System.out.println("head: " + node.getValue());

        while (true) {
            // Searching nearest (less than or equal) node with special key
            next = node.getNext();
            // System.out.println("next check: " + next.getValue());
            count++; // For caclulating no of steps
            while (next != null && lessThanOrEqual(next.getKey(), key)) {
                node = next;
                // System.out.println("next: " + node.getValue());
                next = node.getNext();
                count++;
            }
            nodeKey = node.getKey();
            if (nodeKey != null && nodeKey.compareTo(key) == 0)
                break;
            // Descend to the bottom for continue search
            down = node.getDown();
            // if(node.getDown() != null)
            // System.out.println("down: " + node.getDown().value);
            if (down != null) {
                node = down;
                // System.out.println("down: " + down.value);
            } else {
                break;
            }
        }

        return node;
    }

    protected void checkKeyValidity(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key must be not null!");
    }

    protected boolean lessThanOrEqual(K a, K b) {
        return a.compareTo(b) <= 0;
    }

    protected boolean isBuildLevel() {
        return randomGenerator.nextDouble() < probability;
    }

    protected void horizontalInsert(Node<K, V> x, Node<K, V> y) {
        y.setPrevious(x);
        y.setNext(x.getNext());
        if (x.getNext() != null)
            x.getNext().setPrevious(y);
        x.setNext(y);
    }

    protected void verticalLink(Node<K, V> x, Node<K, V> y) {
        x.setDown(y);
        y.setUp(x);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<K, V> node = head;

        // Move into the lower left bottom
        while (node.getDown() != null)
            node = node.getDown();

        while (node.getPrevious() != null)
            node = node.getPrevious();

        // Head node with each level the key is null
        // so need to move into the next node
        if (node.getNext() != null)
            node = node.getNext();

        while (node != null) {
            sb.append(node.toString()).append("\n");
            node = node.getNext();
        }

        return sb.toString();
    }

    @Override
    public Iterator<K> iterator() {
        return new SkipListIterator<K, V>(head);
    }

    protected static class SkipListIterator<K extends Comparable<K>, V> implements Iterator<K> {

        private Node<K, V> node;

        public SkipListIterator(Node<K, V> node) {
            while (node.getDown() != null)
                node = node.getDown();

            while (node.getPrevious() != null)
                node = node.getPrevious();

            if (node.getNext() != null)
                node = node.getNext();

            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return this.node != null;
        }

        @Override
        public K next() {
            K result = node.getKey();
            node = node.getNext();
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    protected static class Node<K extends Comparable<K>, V> {

        private K key;

        private V value;

        private int level;

        private Node<K, V> up, down, next, previous;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.level = level;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node[")
                    .append("key:");
            if (this.key == null)
                sb.append("None");
            else
                sb.append(this.key.toString());

            sb.append(" value:");
            if (this.value == null)
                sb.append("None");
            else
                sb.append(this.value.toString());
            sb.append("]");
            return sb.toString();
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Node<K, V> getUp() {
            return up;
        }

        public void setUp(Node<K, V> up) {
            this.up = up;
        }

        public Node<K, V> getDown() {
            return down;
        }

        public void setDown(Node<K, V> down) {
            this.down = down;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        public Node<K, V> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<K, V> previous) {
            this.previous = previous;
        }
    }

    public void closestKeyAfter(K key) {
        checkKeyValidity(key);
        Node<K, V> node = findNode(key);
        if ( node != null ) {
            if( node.getNext() != null )
                System.out.println("closest Key After " + key + " is " + node.getNext().getValue());
            else
                System.out.println("closest Key After " + key + " does not exist!");
        }  else {
            System.out.println("Key " + key + " does not exist!");
        }
            
    }

    public void printList() {
        Node<K, V> node = head;
        Node<K, V> next = null;
        Node<K, V> down = null;
        // System.out.println("head: " + node.getValue());
        // Node<K, V> nextNode = head;
        Node<K, V> upNode = null;

        // next = node.getNext();
        System.out.println("Skip List");

        while ( true ) {
            next = node.getNext();
            count++;
            while (next != null) {
                node = next;
                System.out.print(node.getValue() + ": ");
                upNode = node.getUp();
                count++;
                while ( upNode != null ) {
                    System.out.print(" " + upNode.getValue());
                    upNode = upNode.getUp();
                    count++;
                }
                // if ( node.getUp() != null )
                //     System.out.println("up: " + node.getUp().getValue());
                next = node.getNext();
                System.out.println("");
            }

            // if ( next == null ) {
            //     break;
            // }

            // Descend to the bottom for continue search
            down = node.getDown();
            // if(node.getDown() != null)
            // System.out.println("down: " + node.getDown().value);
            if (down != null) {
                node = down;
                // System.out.println("down: " + down.value);
            } else {
                break;
            }
        }

    }

    

    // public static void main(String[] args) {

    //     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm:ss.SSS");
        
    //     // First List
    //     LocalDateTime now1 = LocalDateTime.now();
    //     // System.out.println("Before implementing skip list 1: " + dtf.format(now1));

    //     SkipList<Integer, String> skipList = new SkipList<>();
    //     int randNo = getRandomNo(50, 10000);
    //     for (int i = 1; i <= randNo; i++) {
    //         skipList.insertElement(i, String.valueOf(i));
    //     }    
        

    //     skipList.printList();

    //     skipList.closestKeyAfter(getRandomNo(50, randNo));
    //     skipList.findElement(getRandomNo(50, randNo));
    //     skipList.removeElement(getRandomNo(50, randNo));

    //     LocalDateTime then1 = LocalDateTime.now();
    //     // System.out.println("After implementing skip list 1: " + dtf.format(then1));
    //     // Period timeTaken1 = Period.between(now1.toLocalDate(), then1.toLocalDate());
    //     Duration timeDuration1 = Duration.between(now1, then1);
    //     // LocalDateTime timeTaken1 = then1 - now1;
    //     // System.out.println("Time Taken for implementing skip list 1: " + timeDuration1.toString());
    //     String temp = timeDuration1.toString();
    //     temp = temp.substring(2, temp.length());
    //     double timeDurn1 = Double.parseDouble(temp.substring(0, temp.length() - 1));
    //     // System.out.println("Total Time taken for implementing Skip List 1: " + timeDurn1 + " seconds.");
    //     int count1 = count;
    //     System.out.println("Count1: " + count1);
    //     count = 0;

    //     // Second List
    //     LocalDateTime now2 = LocalDateTime.now();
    //     // System.out.println("Before implementing skip list 2: " + dtf.format(now1));

    //     SkipList<Integer, String> skipList1 = new SkipList<>();
    //     randNo = getRandomNo(50, 10000);
    //     for (int i = 1; i <= randNo; i++) {
    //         skipList1.insertElement(i, String.valueOf(i));
    //     }    
        

    //     skipList1.printList();

    //     skipList1.closestKeyAfter(getRandomNo(50, randNo));
    //     skipList1.findElement(getRandomNo(50, randNo));
    //     skipList1.removeElement(getRandomNo(50, randNo));

    //     LocalDateTime then2 = LocalDateTime.now();
    //     // System.out.println("After implementing skip list 2: " + dtf.format(then2));
    //     // Period timeTaken1 = Period.between(now1.toLocalDate(), then2.toLocalDate());
    //     Duration timeDuration2 = Duration.between(now2, then2);
    //     // LocalDateTime timeTaken1 = then1 - now1;
    //     // System.out.println("Time Taken for implementing skip list 2: " + timeDuration2.toString());
    //     String temp1 = timeDuration2.toString();
    //     temp1 = temp1.substring(2, temp1.length());
    //     double timeDurn2 = Double.parseDouble(temp1.substring(0, temp1.length() - 1));
    //     // System.out.println("Total Time taken for implementing Skip List 2: " + timeDurn2 + " seconds.");
    //     int count2 = count;
    //     System.out.println("Count2: " + count2);
    //     count = 0;

    //     // Third List
    //     LocalDateTime now3 = LocalDateTime.now();
    //     // System.out.println("Before implementing skip list 3: " + dtf.format(now1));

    //     SkipList<Integer, String> skipList2 = new SkipList<>();
    //     randNo = getRandomNo(50, 10000);
    //     for (int i = 1; i <= randNo; i++) {
    //         skipList2.insertElement(i, String.valueOf(i));
    //     }    
        

    //     skipList2.printList();

    //     skipList2.closestKeyAfter(getRandomNo(50, randNo));
    //     skipList2.findElement(getRandomNo(50, randNo));
    //     skipList2.removeElement(getRandomNo(50, randNo));

    //     LocalDateTime then3 = LocalDateTime.now();
    //     // System.out.println("After implementing skip list 2: " + dtf.format(then3));
    //     // Period timeTaken1 = Period.between(now1.toLocalDate(), then3.toLocalDate());
    //     Duration timeDuration3 = Duration.between(now3, then3);
    //     // LocalDateTime timeTaken1 = then1 - now1;
    //     // System.out.println("Time Taken for implementing skip list 2: " + timeDuration3.toString());
    //     String temp2 = timeDuration3.toString();
    //     temp2 = temp2.substring(2, temp2.length());
    //     double timeDurn3 = Double.parseDouble(temp2.substring(0, temp2.length() - 1));
    //     // System.out.println("Total Time taken for implementing Skip List 3: " + timeDurn3 + " seconds.");
    //     int count3 = count;
    //     System.out.println("Count3: " + count3);

    //     double avgTime = (timeDurn1 + timeDurn2 + timeDurn3) / 3;
    //     double avgCount = (count1 + count2 + count3) / 3;
        
    //     System.out.println("Total Time taken for implementing Skip List 1: " + timeDurn1 + " seconds.");
    //     System.out.println("Total Time taken for implementing Skip List 2: " + timeDurn2 + " seconds.");
    //     System.out.println("Total Time taken for implementing Skip List 3: " + timeDurn3 + " seconds.");
    //     System.out.println("Average Time Taken for implementing 3 skip lists: " + avgTime + " seconds.");
    //     System.out.println("Average number of steps executed: " + avgCount);
    // }

}