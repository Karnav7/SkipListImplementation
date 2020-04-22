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
        int randNo = getRandomNo(50, 10000);
        for (int i = 1; i <= randNo; i++) {
            skipList.insertElement(i, String.valueOf(i));
        }    
        

        skipList.printList();

        Scanner sc1 = new Scanner(System.in);

        int check = 1;

        while (check == 1) {
            System.out.println("Enter 1. to perform insert operation, 2. to perform findElement operation, 3. to perform removeElement operation," +
                "4. to perform findClosestKeyAfter operation, 5. to print skip list, 6. to exit.");
            int choice = sc.nextInt();
            switch(choice) {
                case 1:
                    System.out.println("Enter data to be inserted in Skip List: ");
                    int input = sc1.nextInt();
                    skipList.insertElement(input, String.valueOf(input));
                break;
                case 2:
                    System.out.println("Enter data to be searched in the Skip List: ");
                    input = sc1.nextInt();
                    skipList.findElement(input);
                break;
                case 3:
                    System.out.println("Enter data to remove from Skip List: ");
                    input = sc1.nextInt();
                    skipList.removeElement(input);
                break;
                case 4:
                    System.out.println("Enter data to find next closest key after it: ");
                    input = sc1.nextInt();
                    skipList.closestKeyAfter(input);
                break;
                case 5:
                    System.out.println("Skip List: ");
                    skipList.printList();
                break;
                case 6:
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