// package src.skiplist;
import java.util.Random;
import java.util.Scanner;

public class PartOne {
    
    private static int getRandomNo(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        SkipList<Integer, String> skipList = new SkipList<>();
        // int randNo = getRandomNo(50, 10000);
        // for (int i = 1; i <= randNo; i++) {
        //     skipList.insertElement(i, String.valueOf(i));
        // }    
        
        // for (int i = 1; i <= 10; i++) {
        //     skipList.insertElement(i, String.valueOf(i));
        // } 

        // skipList.printList();

        skipList.setInitialSkipList(0, null);

        Scanner sc1 = new Scanner(System.in);

        int check = 1;

        while (check == 1) {
            System.out.println("Choose an operation to perform:\n1 - insert\n2 - remove\n3 - find\n4 - closestKeyAfter\n5 - exit");
            int choice = sc.nextInt();
            switch(choice) {
                case 1:
                    System.out.println("Enter data to be inserted in Skip List: ");
                    int input = sc1.nextInt();
                    skipList.insertElement(input, String.valueOf(input));
                    // skipList.newInsertElement(input, String.valueOf(input));
                    skipList.newNewPrint();
                break;
                case 2:
                    System.out.println("Enter data to remove from Skip List: ");
                    input = sc1.nextInt();
                    skipList.removeElement(input);
                    skipList.newNewPrint();
                break;
                case 3:
                    System.out.println("Enter data to be searched in the Skip List: ");
                    input = sc1.nextInt();
                    
                    skipList.newNewPrint();
                    skipList.findElement(input);
                    // skipList.newFindElement(input);
                break;
                case 4:
                    System.out.println("Enter data to find next closest key after it: ");
                    input = sc1.nextInt();
                    skipList.newNewPrint();
                    skipList.closestKeyAfter(input);
                break;
                // case 5:
                //     System.out.println("Skip List: ");
                //     skipList.printList();
                //     skipList.newNewPrint();
                //     // skipList.newPrint();
                //     // System.out.println("chk: " + skipList.toString());
                // break;
                case 5:
                    check = 0;
                    System.out.println("Bye!");
                break;
                default:
                    check = 0;
                    System.out.println("Bye!");
            }
        }
        
        // for ( int i = 0; i < 50; i++ ) {
        //     skipList.closestKeyAfter(getRandomNo(1, 2 * randNo + 1));
        //     skipList.findElement(getRandomNo(1, 2 * randNo + 1));
        //     skipList.removeElement(getRandomNo(1, randNo));
        // }
        

        
    }
}