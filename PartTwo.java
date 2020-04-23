// package src.skiplist;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.*;
import javax.swing.JFrame;

public class PartTwo {
    
    private static int getRandomNo(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static void main(String[] args) {
        
        // First List
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm:ss.SSS");

        SkipList<Integer, String> skipList = new SkipList<>();
        int randNo = getRandomNo(0, 100000);
        LocalDateTime[] insertTimeStart = new LocalDateTime[randNo];
        LocalDateTime[] insertTimeEnd = new LocalDateTime[randNo];
        for (int i = 1; i <= randNo; i++) {
            insertTimeStart[i-1] = LocalDateTime.now();
            skipList.insertElement(i-1, String.valueOf(i));
            insertTimeEnd[i-1] = LocalDateTime.now();
        }    
        Long[] insertTD = new Long[randNo];
        for (int i = 0; i < randNo; i++) {
            insertTD[i] = Duration.between(insertTimeStart[i], insertTimeEnd[i]).toMillis();
        }
        double avgInsertT = 0.0;
        for ( int i = 0; i < randNo; i++ ) {
            avgInsertT += insertTD[i];
        }
        int temp1 = randNo;
        skipList.printList();
        LocalDateTime[] findTimeStart = new LocalDateTime[100];
        LocalDateTime[] findTimeEnd = new LocalDateTime[100];
        LocalDateTime[] cKAStart = new LocalDateTime[100];
        LocalDateTime[] cKAEnd = new LocalDateTime[100];
        LocalDateTime[] removeTimeStart = new LocalDateTime[100];
        LocalDateTime[] removeTimeEnd = new LocalDateTime[100];

        for ( int i = 0; i < 100; i++ ) {
            cKAStart[i] = LocalDateTime.now();
            skipList.closestKeyAfter(getRandomNo(1, 2 * randNo + 1));
            cKAEnd[i] = LocalDateTime.now();
            findTimeStart[i] = LocalDateTime.now();
            skipList.findElement(getRandomNo(1, 2 * randNo + 1));
            findTimeEnd[i] = LocalDateTime.now();
            removeTimeStart[i] = LocalDateTime.now();
            skipList.removeElement(getRandomNo(1, randNo));
            removeTimeEnd[i] = LocalDateTime.now();
        }
        
        // JFrame f = new JFrame();
        // f.setTitle("title");
        // f.setName("name");
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // f.add(new GraphData());
        // f.setSize(400,400);
        // f.setLocation(200,200);
        // f.setVisible(true);
        Long[] cKATD = new Long[100];
        Long[] findTD = new Long[100];
        Long[] removeTD = new Long[100];

        for(int i = 0; i < 100; i++) {
            // System.out.println("yo: " + cKAStart[i]);
            cKATD[i] = Duration.between(cKAStart[i], cKAEnd[i]).toMillis();
            findTD[i] = Duration.between(findTimeStart[i], findTimeEnd[i]).toMillis();
            removeTD[i] = Duration.between(removeTimeStart[i], removeTimeEnd[i]).toMillis();
        }

        double avgCKAT = 0.0, avgFindT = 0.0, avgRemoveT = 0.0;
        for(int i = 0; i < 100; i++) {
            // System.out.println("yo1: " + cKATD[i]);
            // System.out.println("yo2: " + findTD[i]);
            // System.out.println("yo3: " + removeTD[i]);
            avgCKAT = avgCKAT + cKATD[i];
            avgFindT = avgFindT + findTD[i];
            avgRemoveT = avgRemoveT + removeTD[i];
        }
        System.out.println("Avg time for Insert Operation: " + avgInsertT / temp1 + " milliseconds.");
        System.out.println("Avg Time for Find Operation: " + avgFindT / 50 + " milliseconds.");
        System.out.println("Avg Time for Remove Operation: " + avgRemoveT / 50 + " milliseconds.");
        System.out.println("Avg Time for Find Closest Next Operation: " + avgCKAT / 50 + " milliseconds.");
    }
}