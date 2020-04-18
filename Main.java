class Main {

    public static void main(String[] args) {
        SkipListImplementation<Integer> ski = new SkipListImplementation<>();
        for ( int i = 1; i < 128; i++ ) {
            ski.insertElement(i);
        }
        // System.out.println("Original Skip list: ");
        ski.printList();
        ski.findElement(101);
        ski.removeElement(7);
        ski.findElement(7);
        ski.findElement(112);
    }
}