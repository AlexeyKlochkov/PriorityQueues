/**
 * Compilation: javac MaxPQ.java
 * Execution: java MaxPQ
 *
 * Maximum oriented priority queue using a binary heap.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The MaxPQ class represents a priority queue of generic keys.
 *
 * @author Alex Klochkov
 * @param <Key>
 */
public class MaxPQ<Key extends Comparable> implements Iterable<Key> {
    private Key[] pq;
    private int N;

    public Iterator<Key> iterator() {
        return new MaxPQIterator();
    }

    public class MaxPQIterator implements Iterator<Key> {

        private MaxPQ<Key> copy;

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public  MaxPQIterator() {
            copy = new MaxPQ<>(size());
            for (int i = 1; i <= size(); i++) {
                copy.insert(pq[i]);
            }
        }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }

    public MaxPQ(int sz) {
        N = 0;
        pq = (Key[]) new Comparable[sz + 1];
    }

    public MaxPQ(Key[] a) {
        N = a.length;
        pq =(Key[]) new Comparable[N + 1];
        System.arraycopy(a, 0, pq, 1, N);
        for (int i = N / 2; i >=1 ; i--) {
            sink(i);
        }
    }

    public boolean isEmpty(){
        return N==0;
    }

    private void resize(int sz) {
        Key[] newPQ = (Key[]) new Comparable[sz];
        System.arraycopy(pq ,0, newPQ, 0, N + 1);
        pq = newPQ;
    }

    public void insert(Key key) {
        if (pq.length == N - 1) resize(N * 2);
        pq[++N] = key;
        swim(N);
    }

    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("Trying to delete element from empty priority queue");
        Key tmp = pq[1];
        exch(1, N);
        pq[N--] = null;
        sink(1);
        if (N + 1 == pq.length / 4 && N > 0) resize(pq.length / 2);
        return tmp;
    }

    public Key getMax() {
        if (isEmpty()) throw new NoSuchElementException("Trying to get max element from empty priority queue");
        return pq[1];
    }

    public Key getMin() {
        if (isEmpty()) throw new NoSuchElementException("Trying to get min element from empty priority queue");
        int min = N / 2 + 1;
        for (int i = N / 2 + 2; i <= N; i++) {
            if (less(i,min)) min = i;
        }
        return pq[min];
    }

    private void exch(int i, int j) {
        Key tmpK = pq[i];
        pq[i] = pq[j];
        pq[j] = tmpK;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void sink(int k) {
        while (k * 2 <= N) {
         int j = k * 2;
         if (j < N && less(j, j + 1)) j++;
         if (less(j, k)) break;
         exch(k, j);
         k = j;
        }
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k /=2;
        }
    }

    public int size() {
        return N;
    }

    public static void main(String[] args) {
        Integer[] a = {4,1,2,3,5,0,6,-1,8,9,-2};
        MaxPQ<Integer> pq = new MaxPQ<>(a);
        System.out.println("Min key: " + pq.getMin());
        System.out.println("Max key: " + pq.delMax());
        System.out.println("Residual pq: ");
        for (Object i:pq) {
            System.out.print(i+" ");
        }
    }
}
