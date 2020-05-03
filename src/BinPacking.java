import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BinPacking {

    public static class Bin {

        private final ArrayList<Integer> objects;

        private int capacity;

        Bin(int capacity) {
            this.capacity = capacity;
            objects = new ArrayList<>();
        }

        public void addObject(int o) {
            objects.add(o);
            capacity = -o;
        }

        public boolean isFitting(int o) {
            return capacity >= o;
        }
    }

    /*
     *  objects of different weight/volume/...
     *  that have to be assigned to the bins
     */
    private final Integer[] objects;

    /*
     * fixed capacity of each bin
     */
    private final int capacity;

    /*
     * minimum number of bins required
     */
    private final int minNumberBins;

    /*
     * list of bins whose final number is not yet known
     */
    private final ArrayList<Bin> bins;

    BinPacking(Integer[] objects, int capacity) {
        this.objects = objects;
        this.capacity = capacity;
        minNumberBins = getMinNumberBins();
        bins = new ArrayList<Bin>();
        initBinList();
        Arrays.sort(objects, Collections.reverseOrder());
    }

    /*
     * get the minimum number of bins required
     * by dividing the number of objects by
     * the bin capacity and rounding up the result
     * if necessary
     */
    int getMinNumberBins() {
        return (int) Math.ceil(
                (double) objects.length / capacity);
    }

    /*
     * initializes list of bins with minimum
     * number of bins required each of fixed
     * capacity
     */
    void initBinList() {
        for (int i = 0; i < minNumberBins; i++) {
            bins.add(new Bin(capacity));
        }
    }

    int calculateNumberOfBins() {
        int numberOfBins = minNumberBins;
        Bin currentBin;
        for (int o : objects) {
            for (int j = 0; j < bins.size(); j++) {
                currentBin = bins.get(j);
                if (currentBin.isFitting(o))
                    currentBin.addObject(o);
                else if (j == bins.size()) {
                    numberOfBins++;
                    bins.add(new Bin(capacity));
                }
            }
        }
        return numberOfBins;
    }


    public static void main(String[] args) {


        BinPacking binPacking = new BinPacking(
                new Integer[]{9, 8, 2, 5, 8, 1, 3, 2, 1, 15, 1, 5, 9, 4}, 6);

        System.out.println(binPacking.calculateNumberOfBins());


    }

}
