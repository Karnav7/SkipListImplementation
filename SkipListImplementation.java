import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

class SkipListImplementation<E extends Comparable<? super E>> extends AbstractSkipList<E> {

    static final int Possible_Levels = 35;
    private int size, maxLevel;
    private Random rand; 
    private Node<E>[] last;
    private int[] distTraversed;

    public SkipListImplementation() {
        head = new Node<E>(null, Possible_Levels);
        tail = new Node<E>(null, Possible_Levels);

        size = 0;
        maxLevel = 1;

        last = new Node[Possible_Levels];
        distTraversed = new int[Possible_Levels];

        rand = new Random();

        // // Each entry in head.next[] points to tail
		// for (int i = 0; i < Possible_Levels; i++) {
		// 	head.next[i] = tail;
		// 	head.traverse[i] = 1; // tail 1 distance away
		// }
		
		// // When list is empty, head is previous of tail
		// tail.prev = head;
    }

    
    private void find(E data) {
        Node<E> c = head;
        distTraversed = new int[Possible_Levels];

        for( int i = 0; i < maxLevel; i++ ) {
            int k = maxLevel - 1 - i;

            while ( c.next[k] != null && c.next[k].data != null && c.next[k].data.compareTo((E) data) < 0) {
                distTraversed[k] += c.traverse[k];
                c = c.next[k];
            }

            last[k] = c;
            System.out.println("last[" + k + "] = " + c);
        }
    }

    private boolean consistsOf(E data) {
        if ( data == null ) {
            return false;
        }

        find(data);
        System.out.println("last1[0]: " + last[0].next[0]);
        if ( last[0].next[0] != null ) {
            if ( (last[0].next[0].data != null) && (last[0].next[0].data.compareTo((E) data) == 0)) {
                return true;
            } 
        }
        
        // else {
            return false;
        // }
    }

    private int selectLevel() {
        int lvl = 1 + Integer.numberOfLeadingZeros(rand.nextInt());

        lvl = Math.min(lvl, maxLevel + 1);

        if ( maxLevel < lvl ) {
            maxLevel = lvl;
        }

        return lvl;
    }

    @Override
    public boolean insertElement(E data) {
        System.out.println("check: " + head);
        if ( head != null ) {
            if ( consistsOf(data) ) {
                return false;
            }
        }
        

        int level = selectLevel();
        System.out.println("level: " + level);

        Node<E> newNode = new Node<E>(data, level);
        System.out.println("new Node: " + newNode.data);
        int prevPos = 0, newPos = 0;

        for ( int i = 0; i < distTraversed.length; i++ ) {
            prevPos += distTraversed[i];
        }

        newPos = prevPos + 1;
        int ctr = 0;

        // each level of newly created node
        for (int i = 0; i < level; i++) {

            if (last[i] == null) {
                break;
            }
            System.out.println("last["+i+"].next["+i+"]: " + last[i].next[i]);
            // newNode.next[i] = last[i].next[i];
            newNode.next[i] = tail;
            last[i].next[i] = newNode;
            System.out.println("newNode.traverse[" + i + "]: " + newNode.traverse[i]);
            newNode.traverse[i] = prevPos + last[i].traverse[i] - newPos + 1;
            last[i].traverse[i] = newPos - prevPos;

            prevPos = prevPos - distTraversed[i];

            ctr++;
        }

        newNode.level = ctr;

        // set previous links
        System.out.println("newNode.next[0]: " + newNode.next[0]);
        System.out.println("last[0]: " + last[0].data);
        if ( newNode.next[0] != null )
            newNode.next[0].prev = newNode;
        newNode.prev = last[0];
        tail.prev = newNode;

        // set next links


        while ( ctr < last.length ) {
            if ( last[ctr] == null ) 
                break;

            last[ctr].traverse[ctr]++;
            ctr++;
        }

        size++;

        for( int i = 0; i < Possible_Levels; i++ ) {
            if( head.next[i] == tail ) {
                head.traverse[i] = size + 1;
            }
        }

        return true;
    }

    private class SkipListIterator implements Iterator<E> {
        Node<E> currNode, prevNode;
        boolean ready;

        SkipListIterator() {
            currNode = head;
            prevNode = null;
            ready = false;
        }

        public boolean hasNext() {
            return ( currNode.next[0] != null && currNode.next[0].data != null );
        }

        public E next() {
            if( !hasNext() )
                throw new NoSuchElementException("No next element.");
            
            prevNode = currNode;
            currNode = currNode.next[0];
            ready = true;

            return currNode.data;
        }

        public void remove() {
            if (!ready) 
				throw new NoSuchElementException("Not ready.");
			
            find(currNode.data);
            int i = 0;
            for ( i = 0; i < currNode.level; i++ ) {
                last[i].next[i] = currNode.next[i];
                last[i].traverse[i] = last[i].traverse[i] + currNode.traverse[i] - 1; 
            }

            while ( last[i] != null ) {
                last[i].traverse[i]--;
                i++;
            }

            size--;

            currNode = prevNode;
            ready = false;

        }
    }

    public Iterator<E> iterator() {
        return new SkipListIterator();
    }

    private void printLevel() {
        System.out.println("maxLevel: " + maxLevel);
        System.out.print("level \tElements");

        for (int i = 0; i < maxLevel; i++ ) {
            int x = maxLevel - 1 - i;

            System.out.print(( x + 1 ) + ":\t");

            Node<E> n = head;

            while ( n.next[x] != null ) {
                System.out.print(n.data + " ");
                n = n.next[x];
            }
            System.out.println();
        }
    }

    public void printList() {
		
		System.out.println("printList(): maxLevel = "+maxLevel);
		System.out.println("Index\tData\tnext[]");
		Node<E> n = head;
		int i=0;
		System.out.println("head: " + head);
		// for all entries + head and tail
		while (i < size + 2) {
			System.out.print((i-1)+"\t"+n.data+": \t[");
			
			// Printing elements in next[]
			for (int j = 0; j < n.level; j++) {
				
				// In the beginning of each next[] building
				if (j == 0) {
					
					if (n.next[0] == null) {
						System.out.print("]");
						break;
					}
				}
				
				// When you reach at the last, i.e. p is the last
				if (j == n.level - 1 || j == maxLevel - 1) {
					// When n is not null
					if (n.next[j] != null)
						System.out.print(n.next[j].data);
					System.out.print("]");
					break; 
				} else {
					// When Entry after p is null -> just print p 
					// [NO ArrayOutOfBoundException :) 
					// as we already checked if 'j' is the last]
					if (n.next[j+1] == null) {
						System.out.print(n.next[j].data);
						System.out.print("]");
						break; 
					} else {      // When the Entry after p is not null -> print p+", "
						System.out.print(n.next[j].data+", ");
					}
				}	
			}
			
			System.out.println();
			n = n.next[0]; // next entry in the Skip list
			i++;
		}		
	}

    @Override
    public void removeElement(E data) {
        // TODO Auto-generated method stub
        Node<E> currNode;
        currNode = findElementP(data);
        if ( currNode != null ) {
            int i = 0;
            for ( i = 0; i < currNode.level; i++ ) {
                last[i].next[i] = currNode.next[i];
                last[i].traverse[i] = last[i].traverse[i] + currNode.traverse[i] - 1; 
            }
    
            while ( last[i] != null ) {
                last[i].traverse[i]--;
                i++;
            }
    
            size--;
    
            // currNode = prevNode;
            // System.out.println("prevData" + currNode.);
            System.out.println("Data " + data + " was removed successfully!");
        } else {
            System.out.println("Data " + data + " does not exist in list!");
        }
        
    }

    @Override
    public void findElement(E data) {
        // TODO Auto-generated method stub
        Node<E> c = head;
        distTraversed = new int[Possible_Levels];

        for( int i = 0; i < maxLevel; i++ ) {
            int k = maxLevel - 1 - i;

            while ( c.next[k] != null && c.next[k].data != null && c.next[k].data.compareTo((E) data) < 0) {
                distTraversed[k] += c.traverse[k];
                c = c.next[k];
            }

            last[k] = c;
            
        }

        System.out.println("find: " + c.next[0].data);
        if ( c.next[0].data != null && c.next[0].data == data) {
            System.out.println("Data " + data + " was found!");
        } else {
            System.out.println("Data " + data + " was not found!");
        }
    }

    private Node<E> findElementP(E data) {
        Node<E> c = head;
        distTraversed = new int[Possible_Levels];

        for( int i = 0; i < maxLevel; i++ ) {
            int k = maxLevel - 1 - i;

            while ( c.next[k] != null && c.next[k].data != null && c.next[k].data.compareTo((E) data) < 0) {
                distTraversed[k] += c.traverse[k];
                c = c.next[k];
            }

            last[k] = c;
            
        }

        if ( c.next[0].data != null && c.next[0].data == data)
            return c.next[0];
        else 
            return null;
    }

    // @Override
    // public void closestKeyAfter(E data) {
    //     // TODO Auto-generated method stub
        
    // }
}