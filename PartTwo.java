
// package src.skiplist;
import java.util.Random;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.*;
import javax.swing.JFrame;
import java.util.concurrent.TimeUnit;

public class PartTwo {
    
    private ArrayList<SkipList<Integer, String>> msl;

    public PartTwo () {
        this.msl = new ArrayList<SkipList<Integer, String>>();
    }
    private void addNewSkipList(SkipList<Integer, String> s) {
        this.msl.add(s);
    }

    private SkipList<Integer, String> getSkipListAtIndex(int index) {
        return this.msl.get(index);
    }

    private ArrayList<SkipList<Integer, String>> getAllSkipLists() {
        return this.msl;
    }

    private List<Long> calulateAvgTimeInsertOp() {
        ArrayList<SkipList<Integer, String>> list = getAllSkipLists();
        // System.out.println("Size: " + list.get(0).size());
        int size = list.get(0).size() + 1;
        ArrayList<Long> totalInsertList = new ArrayList<Long>(size);
        List<Long> avgInsertList = new ArrayList<Long>(size);
        for ( int i = 0; i < size; i++ ) {
            totalInsertList.add(0L);
            avgInsertList.add(0L);
        }
        
        Long time = 0L;
        for ( int i = 0; i < list.size(); i++ ) {
            SkipList<Integer, String> s = list.get(i);
            Set<Map.Entry<Integer, Long>> st1 = s.getInsertMap();
            for ( Map.Entry<Integer, Long> me:st1 ) {
                time = time + me.getValue();
                // System.out.println("key: " + me.getKey());
                totalInsertList.set(me.getKey(), time);
            }
        }

        for ( int i = 1; i < totalInsertList.size(); i++ ) {
            Long t1 = totalInsertList.get(i);
            t1 = t1 / list.size();
            avgInsertList.set(i, t1);
        }

        return avgInsertList;
    }

    private List<Long> calculateAvgTimeFindOp() {
        ArrayList<SkipList<Integer, String>> list = getAllSkipLists();
        // System.out.println("Size: " + list.get(0).size());
        int size = list.get(0).size() + 1;
        ArrayList<Long> totalFindList = new ArrayList<Long>(size);
        List<Long> avgFindList = new ArrayList<Long>(size);

        for ( int i = 0; i < size; i++ ) {
            totalFindList.add(0L);
            avgFindList.add(0L);
        }
        Long findTime = 0L;

        for ( int i = 0; i < list.size(); i++ ) {
            SkipList<Integer, String> s = list.get(i);
            Set<Map.Entry<Integer, Long>> st2 = s.getFindMap();
            for ( Map.Entry<Integer, Long> me:st2 ) {
                findTime = findTime + me.getValue();
                // System.out.println("key: " + me.getKey());
                totalFindList.set(me.getKey(), findTime);
            }
        }

        for ( int i = 1; i < totalFindList.size(); i++ ) {
            Long t1 = totalFindList.get(i);
            t1 = t1 / list.size();
            avgFindList.set(i, t1);
        }

        return avgFindList;
    }

    private List<Long> calculateAvgTimeFindClosestOp() {
        ArrayList<SkipList<Integer, String>> list = getAllSkipLists();
        System.out.println("Size: " + list.get(0).size());
        int size = list.get(0).size() + 1;
        ArrayList<Long> totalFindClosestList = new ArrayList<Long>(size);
        List<Long> avgFindClosestList = new ArrayList<Long>(size);

        for ( int i = 0; i < size; i++ ) {
            totalFindClosestList.add(0L);
            avgFindClosestList.add(0L);
        }
        Long findClosestTime = 0L;

        for ( int i = 0; i < list.size(); i++ ) {
            SkipList<Integer, String> s = list.get(i);
            Set<Map.Entry<Integer, Long>> st2 = s.getFindClosestMap();
            for ( Map.Entry<Integer, Long> me:st2 ) {
                findClosestTime = findClosestTime + me.getValue();
                // System.out.println("key: " + me.getKey());
                totalFindClosestList.set(me.getKey(), findClosestTime);
            }
        }

        for ( int i = 1; i < totalFindClosestList.size(); i++ ) {
            Long t1 = totalFindClosestList.get(i);
            t1 = t1 / list.size();
            avgFindClosestList.set(i, t1);
        }

        return avgFindClosestList;
    }

    private List<Long> calculateAvgTimeDeleteOp() {
        ArrayList<SkipList<Integer, String>> list = getAllSkipLists();
        // System.out.println("Size: " + list.get(0).size());
        int size = list.get(0).size() + 1;
        ArrayList<Long> totalDeleteList = new ArrayList<Long>(size);
        List<Long> avgDeleteList = new ArrayList<Long>(size);

        for ( int i = 0; i < size; i++ ) {
            totalDeleteList.add(0L);
            avgDeleteList.add(0L);
        }
        Long deleteTime = 0L;

        for ( int i = 0; i < list.size(); i++ ) {
            SkipList<Integer, String> s = list.get(i);
            Set<Map.Entry<Integer, Long>> st2 = s.getDeleteMap();
            for ( Map.Entry<Integer, Long> me:st2 ) {
                deleteTime = deleteTime + me.getValue();
                // System.out.println("key: " + me.getKey());
                totalDeleteList.set(me.getKey(), deleteTime);
            }

            // Collections.sort(st2, Collections.reverseOrder());
        }

        for ( int i = 1; i < totalDeleteList.size(); i++ ) {
            Long t1 = totalDeleteList.get(i);
            t1 = t1 / list.size();
            avgDeleteList.set(i, t1);
        }

        return avgDeleteList;
    }

    private static int getRandomNo(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static void main(String[] args) {
        PartTwo p = new PartTwo();
        for ( int j = 0; j < 3; j++ ) {
            SkipList<Integer, String> skipList = new SkipList<Integer, String>();
            skipList.setInitialSkipList(0, null);
    
            //1 to 128
            for ( int i = 1; i <= 128; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }
            //129 to 512
            for ( int i = 129; i <= 512; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }
            //513 to 1024
            for ( int i = 513; i <= 1024; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }
            //1025 to 2048
            for ( int i = 1025; i <= 2048; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }
            //2049 to 4096
            for ( int i = 2049; i <= 4096; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }
            //4097 to 8192
            for ( int i = 4097; i <= 8192; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }
            //8193 to 16384
            for ( int i = 8193; i <= 16384; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }
            //16385 to 32768
            for ( int i = 16385; i <= 32768; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }
            //32767 to 65536
            for ( int i = 32767; i <= 65536; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }
            //65537 to 131072
            for ( int i = 65537; i <= 131072; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                // skipList.findElement(i);
                // skipList.closestKeyAfter(i);
            }

            
    
            // Set<Map.Entry<String, Long>> st = skipList.getInsertMap();
            // for ( Map.Entry<String, Long> me:st ) {
            //     System.out.print("Node: " + me.getKey() + "-->");
            //     System.out.println("Time: " + me.getValue());
            // }

            p.addNewSkipList(skipList);
        }

        SkipList<Integer, String> s1 = p.getSkipListAtIndex(0);
        SkipList<Integer, String> s2 = p.getSkipListAtIndex(1);
        SkipList<Integer, String> s3 = p.getSkipListAtIndex(2);


        // System.out.println("Avg Insert Time: ");
        List<Long> avgInsert = p.calulateAvgTimeInsertOp();
        // for ( int i = 0; i < avgInsert.size(); i++ ) {
        //     System.out.println("Node: " + i + " --> Time: " + avgInsert.get(i));
        // }

        GraphData g = new GraphData();
        g.setData(avgInsert);
        JFrame f = new JFrame();
        f.setTitle("insert");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(g);
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);

        // try {
        //     Thread.sleep(30000);
        // }   catch(InterruptedException ex) {
        //     Thread.currentThread().interrupt();
        // }
        for ( int j = 0; j < 3; j++ ) {
            SkipList<Integer, String> s = p.getSkipListAtIndex(j);
            for ( int i = 1; i <= 131072; i++ ) {
                s.findElement(i);
                s.closestKeyAfter(i);
            }
        }

        // System.out.println("Avg Find Time: ");
        List<Long> avgFind = p.calculateAvgTimeFindOp();
        // for ( int i = 0; i < avgFind.size(); i++ ) {
        //     System.out.println("Node: " + i + " --> Time: " + avgFind.get(i));
        // }

        GraphData g1 = new GraphData();
        g1.setData(avgFind);
        JFrame f1 = new JFrame();
        f1.setTitle("find");
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.add(g1);
        f1.setSize(400,400);
        f1.setLocation(300,300);
        f1.setVisible(true);

        // try {
        //     Thread.sleep(30000);
        // }   catch(InterruptedException ex) {
        //     Thread.currentThread().interrupt();
        // }

        // System.out.println("Avg Find Closest Time: ");
        List<Long> avgFindClosest = p.calculateAvgTimeFindClosestOp();
        // for ( int i = 0; i < avgFindClosest.size(); i++ ) {
        //     System.out.println("Node: " + i + " --> Time: " + avgFindClosest.get(i));
        // }

        GraphData g2 = new GraphData();
        g2.setData(avgFindClosest);
        JFrame f2 = new JFrame();
        f2.setTitle("find closest");
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.add(g2);
        f2.setSize(400,400);
        f2.setLocation(400,400);
        f2.setVisible(true);

        try {
            Thread.sleep(1000);
        }   catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        for ( int i = 0; i < 3; i++ ) {
            SkipList<Integer, String> s = p.getSkipListAtIndex(i);
            for ( int j = 60000; j > 0; j-- ) {
                s.removeElement(j);
            }
        }

        // System.out.println("Avg Delete Time: ");
        List<Long> avgDelete = p.calculateAvgTimeDeleteOp();
        // for ( int i = 0; i < avgDelete.size(); i++ ) {
        //     System.out.println("Node: " + i + " --> Time: " + avgDelete.get(i));
        // }

        GraphData g3 = new GraphData();
        g3.setData(avgDelete);
        JFrame f3 = new JFrame();
        f3.setTitle("delete");
        f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f3.add(g3);
        f3.setSize(400,400);
        f3.setLocation(500,500);
        f3.setVisible(true);


        // SkipList<Integer, String> skipList = new SkipList<Integer, String>();
        // skipList.setInitialSkipList(0, null);

        // //1 to 128
        // for ( int i = 1; i <= 128; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }
        // //129 to 512
        // for ( int i = 129; i <= 512; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }
        // //513 to 1024
        // for ( int i = 513; i <= 1024; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }
        // //1025 to 2048
        // for ( int i = 1025; i <= 2048; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }
        // //2049 to 4096
        // for ( int i = 2049; i <= 4096; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }
        // //4097 to 8192
        // for ( int i = 4097; i <= 8192; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }
        // //8193 to 16384
        // for ( int i = 8193; i <= 16384; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }
        // //16385 to 32768
        // for ( int i = 16385; i <= 32768; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }
        // //32767 to 65536
        // for ( int i = 32767; i <= 65536; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }
        // //65537 to 131072
        // for ( int i = 65537; i <= 131072; i++ ) {
        //     skipList.insertElement(i, String.valueOf(i));
            
        // }

        // Set<Map.Entry<String, Long>> st = skipList.getInsertMap();
        // for ( Map.Entry<String, Long> me:st ) {
        //     System.out.print("Node: " + me.getKey() + "-->");
        //     System.out.println("Time: " + me.getValue());
        // }
        
    //     // First List
    //     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm:ss.SSS");

    //     SkipList<Integer, String> skipList = new SkipList<>();
    //     int randNo = getRandomNo(0, 100000);
    //     LocalDateTime[] insertTimeStart = new LocalDateTime[randNo];
    //     LocalDateTime[] insertTimeEnd = new LocalDateTime[randNo];
    //     for (int i = 1; i <= randNo; i++) {
    //         insertTimeStart[i-1] = LocalDateTime.now();
    //         skipList.insertElement(i-1, String.valueOf(i));
    //         insertTimeEnd[i-1] = LocalDateTime.now();
    //     }    
    //     Long[] insertTD = new Long[randNo];
    //     for (int i = 0; i < randNo; i++) {
    //         insertTD[i] = Duration.between(insertTimeStart[i], insertTimeEnd[i]).toMillis();
    //     }
    //     double avgInsertT = 0.0;
    //     for ( int i = 0; i < randNo; i++ ) {
    //         avgInsertT += insertTD[i];
    //     }
    //     int temp1 = randNo;
    //     skipList.printList();
    //     LocalDateTime[] findTimeStart = new LocalDateTime[100];
    //     LocalDateTime[] findTimeEnd = new LocalDateTime[100];
    //     LocalDateTime[] cKAStart = new LocalDateTime[100];
    //     LocalDateTime[] cKAEnd = new LocalDateTime[100];
    //     LocalDateTime[] removeTimeStart = new LocalDateTime[100];
    //     LocalDateTime[] removeTimeEnd = new LocalDateTime[100];

    //     for ( int i = 0; i < 100; i++ ) {
    //         cKAStart[i] = LocalDateTime.now();
    //         skipList.closestKeyAfter(getRandomNo(1, 2 * randNo + 1));
    //         cKAEnd[i] = LocalDateTime.now();
    //         findTimeStart[i] = LocalDateTime.now();
    //         skipList.findElement(getRandomNo(1, 2 * randNo + 1));
    //         findTimeEnd[i] = LocalDateTime.now();
    //         removeTimeStart[i] = LocalDateTime.now();
    //         skipList.removeElement(getRandomNo(1, randNo));
    //         removeTimeEnd[i] = LocalDateTime.now();
    //     }
        
    //     // JFrame f = new JFrame();
    //     // f.setTitle("title");
    //     // f.setName("name");
    //     // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     // f.add(new GraphData());
    //     // f.setSize(400,400);
    //     // f.setLocation(200,200);
    //     // f.setVisible(true);
    //     Long[] cKATD = new Long[100];
    //     Long[] findTD = new Long[100];
    //     Long[] removeTD = new Long[100];

    //     for(int i = 0; i < 100; i++) {
    //         // System.out.println("yo: " + cKAStart[i]);
    //         cKATD[i] = Duration.between(cKAStart[i], cKAEnd[i]).toMillis();
    //         findTD[i] = Duration.between(findTimeStart[i], findTimeEnd[i]).toMillis();
    //         removeTD[i] = Duration.between(removeTimeStart[i], removeTimeEnd[i]).toMillis();
    //     }

    //     double avgCKAT = 0.0, avgFindT = 0.0, avgRemoveT = 0.0;
    //     for(int i = 0; i < 100; i++) {
    //         // System.out.println("yo1: " + cKATD[i]);
    //         // System.out.println("yo2: " + findTD[i]);
    //         // System.out.println("yo3: " + removeTD[i]);
    //         avgCKAT = avgCKAT + cKATD[i];
    //         avgFindT = avgFindT + findTD[i];
    //         avgRemoveT = avgRemoveT + removeTD[i];
    //     }
    //     System.out.println("Avg time for Insert Operation: " + avgInsertT / temp1 + " milliseconds.");
    //     System.out.println("Avg Time for Find Operation: " + avgFindT / 50 + " milliseconds.");
    //     System.out.println("Avg Time for Remove Operation: " + avgRemoveT / 50 + " milliseconds.");
    //     System.out.println("Avg Time for Find Closest Next Operation: " + avgCKAT / 50 + " milliseconds.");
    }
}