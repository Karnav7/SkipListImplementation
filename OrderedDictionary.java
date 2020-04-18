public interface OrderedDictionary<E> extends Iterable<E> {
    boolean insertElement(E data);
    void removeElement(E data);
    void findElement(E data);
    // void closestKeyAfter();
}