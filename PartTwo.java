
// package src.skiplist;
import java.util.Random;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.*;
import javax.swing.JFrame;

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
        System.out.println("Size: " + list.get(0).size());
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
            Set<Map.Entry<String, Long>> st1 = s.getInsertMap();
            for ( Map.Entry<String, Long> me:st1 ) {
                time = time + me.getValue();
                System.out.println("key: " + me.getKey());
                totalInsertList.set(Integer.parseInt(me.getKey()), time);
            }

        }

        for ( int i = 1; i < totalInsertList.size(); i++ ) {
            Long t1 = totalInsertList.get(i);
            t1 = t1 / list.size();
            avgInsertList.set(i, t1);
        }

        return avgInsertList;
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
                
            }
            //129 to 512
            for ( int i = 129; i <= 512; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                
            }
            //513 to 1024
            for ( int i = 513; i <= 1024; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                
            }
            //1025 to 2048
            for ( int i = 1025; i <= 2048; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                
            }
            //2049 to 4096
            for ( int i = 2049; i <= 4096; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                
            }
            //4097 to 8192
            for ( int i = 4097; i <= 8192; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                
            }
            //8193 to 16384
            for ( int i = 8193; i <= 16384; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                
            }
            //16385 to 32768
            for ( int i = 16385; i <= 32768; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                
            }
            //32767 to 65536
            for ( int i = 32767; i <= 65536; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                
            }
            //65537 to 131072
            for ( int i = 65537; i <= 131072; i++ ) {
                skipList.insertElement(i, String.valueOf(i));
                
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

        System.out.println("FirstList: ");
        // s1.newNewPrint();
        Set<Map.Entry<String, Long>> st1 = s1.getInsertMap();
        // for ( Map.Entry<String, Long> me:st1 ) {
        //     System.out.print("Node: " + me.getKey() + "-->");
        //     System.out.println("Time: " + me.getValue());
        // }

        System.out.println("SecondList: ");
        // s2.newNewPrint();
        Set<Map.Entry<String, Long>> st2 = s2.getInsertMap();
        // for ( Map.Entry<String, Long> me:st2 ) {
        //     System.out.print("Node: " + me.getKey() + "-->");
        //     System.out.println("Time: " + me.getValue());
        // }

        System.out.println("Third List: ");
        // s3.newNewPrint();
        Set<Map.Entry<String, Long>> st3 = s3.getInsertMap();
        // for ( Map.Entry<String, Long> me:st3 ) {
        //     System.out.print("Node: " + me.getKey() + "-->");
        //     System.out.println("Time: " + me.getValue());
        // }


        System.out.println("Avg Insert Time: ");
        List<Long> avgInsert = p.calulateAvgTimeInsertOp();
        for ( int i = 0; i < avgInsert.size(); i++ ) {
            System.out.println("Node: " + i + " --> Time: " + avgInsert.get(i));
        }

        GraphData g = new GraphData();
        g.setData(avgInsert);
        JFrame f = new JFrame();
        f.setTitle("insert");
        f.setName("name");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(g);
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);


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