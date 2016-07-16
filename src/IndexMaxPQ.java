import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

public class IndexMaxPQ<Key extends Comparable<Key>> {

    private int[] pq;
    private Key[] keys;
    private int N;

    public IndexMaxPQ(int sz) {
        N = 0;
        pq = new int[sz + 1];
        keys = (Key[]) new Comparable[sz + 1];
        for (int i = 0; i <= sz; i++) {
            pq[i] = -1;
        }
    }

    /**
     * Additional function for comparing 2 elements from keys array.
     *
     * @param v left predicate
     * @param w right predicate
     * @return result of comparing keys[v] and keys[w]
     */
    private boolean less(int v, int w) {
        return keys[v].compareTo(keys[w]) < 0;
    }

    /**
     * Additional function for exchanging 2 elements in keys array.
     *
     * @param i left predicate
     * @param j right predicate
     */
    private void exch(int i, int j) {
        Key tmp = keys[j];
        keys[j] = keys[i];
        keys[i] = tmp;
    }

    private void swim(int i) {
        while (i > 1 && less(i / 2, i)) {
            exch(i/2, i);
            i = i / 2;
        }
    }

    private void sink(int i) {
        while (2 * i < N) {
            int j = 2 * i;
            if (j < N && less(j, j + 1)) j++;
            if (less(j, i)) break;
            exch(i,j);
            i = j;
        }
    }

    public boolean contains(int i) {
        return pq[i] != -1;
    }

    public void insert(int i, Key k) {
        if (contains(i)) throw new NoSuchElementException("Index " + i + " is already in priority queue.");
        N++;
        pq[i] = N;
        keys[i] = k;
        swim(i);
    }

    public Key maxKey() {
        return keys[1];
    }

    public int delMax() {
        int max = pq[1];
        exch(1, N--);
        sink(1);

        keys[N] = null;
        pq[N + 1] = -1;
        return max;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public Key keyOf(int i) {
        return keys[i];
    }
    public void delete(int i) {
        pq[i] = -1;
        keys[i] = null;
        N--;
    }

    public static void main(String[] args) {
        // insert a bunch of strings
        String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };

        IndexMaxPQ<String> pq = new IndexMaxPQ<String>(strings.length);
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        StdOut.println();

        // delete and print each key
        while (!pq.isEmpty()) {
            String key = pq.maxKey();
            int i = pq.delMax();
            StdOut.println(i + " " + key);
        }
        StdOut.println();

        // reinsert the same strings
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // delete them in random order
        int[] perm = new int[strings.length];
        for (int i = 0; i < strings.length; i++)
            perm[i] = i;
        StdRandom.shuffle(perm);
        for (int i = 0; i < perm.length; i++) {
            String key = pq.keyOf(perm[i]);
            pq.delete(perm[i]);
            StdOut.println(perm[i] + " " + key);
        }

    }
}
