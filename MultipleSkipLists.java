import java.util.ArrayList;
import java.util.List;

public class MultipleSkipLists {
    private ArrayList<SkipList> skipListArray;

    public MultipleSkipLists() {
        this.skipListArray = new ArrayList<SkipList>();
    }

    public void addNewSkipList(SkipList s) {
        this.skipListArray.add(s);
    }

    public SkipList getSkipListAtIndex(int index) {
        return this.skipListArray.get(index);
    }

    public ArrayList<SkipList> getAllSkipLists() {
        return this.skipListArray;
    }
}