import java.util.Random;

public class PartTwo {
    
    private static int getRandomNo(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static void main(String[] args) {
        
        // First List
        

        SkipList<Integer, String> skipList = new SkipList<>();
        int randNo = getRandomNo(50, 10000);
        for (int i = 1; i <= randNo; i++) {
            skipList.insertElement(i, String.valueOf(i));
        }    
        

        skipList.printList();

        for ( int i = 0; i < 50; i++ ) {
            skipList.closestKeyAfter(getRandomNo(1, 2 * randNo + 1));
            skipList.findElement(getRandomNo(1, 2 * randNo + 1));
            skipList.removeElement(getRandomNo(1, randNo));
        }
        

        
    }
}