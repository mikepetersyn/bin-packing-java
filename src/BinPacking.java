import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * Offline First Fit Decreasing Algorithm
 * for Bin Packing Problem
 */
public class BinPacking {

    public static class Bin {

        private final int binID;

        private final ArrayList<Integer> objects;

        private int capacity;

        Bin(int capacity, int binID) {
            this.capacity = capacity;
            this.binID = binID;
            objects = new ArrayList<>();
        }

        void addObject(int o) {
            objects.add(o);
            capacity -= o;
        }

        boolean isFitting(int o) {
            return capacity >= o;
        }

        void printBin() {
            System.out.format("Bin %d contains the item(s): {", binID);
            for (int o : objects) {
                System.out.format(" %d ", o);
            }
            System.out.print("}\n");
        }
    }

    /*
     *  objects of different weight/volume/...
     *  that have to be assigned to the bins
     */
    private final Integer[] objects;

    /*
     * sum of all the object's
     * weight/volume/...
     */
    private final int sumObjectValues;

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
        sumObjectValues = getSumObjectValues(objects);
        minNumberBins = getMinNumberBins();
        bins = new ArrayList<>();
        initBinList();
    }

    /*
     * get the minimum number of bins required
     * by dividing the number of objects by
     * the bin capacity and rounding up the result
     * if necessary
     */
    int getMinNumberBins() {
        return (int) Math.ceil(
                (double) sumObjectValues / capacity);
    }

    /*
     * returns the sum of all object's values
     */
    int getSumObjectValues(Integer[] objects) {
        return Arrays.stream(objects)
                .mapToInt(Integer::intValue)
                .sum();
    }

    /*
     * initializes list of bins with minimum
     * number of bins required each of fixed
     * capacity
     */
    void initBinList() {
        for (int i = 0; i < minNumberBins; i++) {
            bins.add(new Bin(capacity, i));
        }
    }

    void printAllBins() {
        bins.forEach((bin -> bin.printBin()));
    }

    /*
     * First-Fit-Decreased Algorithm (FFD):
     * intuition behind considering large items first
     * is that large items do not fit into the same
     * bin anyway, so we already use unavoidable bins
     * and try to place small items into the residual
     * space
     */
    int calculateNumberOfBins() {
        // first sort all objects with decreasing order
        Arrays.sort(objects, Collections.reverseOrder());
        int numberOfBins = minNumberBins;
        Bin currentBin;
        /*
         * take each object and put it into the first bin
         * that offers enough space
         */
        for (int o : objects) {
            for (int j = 0; j < bins.size(); j++) {
                currentBin = bins.get(j);
                if (currentBin.isFitting(o)) {
                    currentBin.addObject(o);
                    break;
                }
                /*
                 * only in online mode required when we don't
                 * know the number of bins in advance
                 */
                else if (j == bins.size()) {
                    numberOfBins++;
                    bins.add(new Bin(capacity, numberOfBins));
                    break;
                }
            }
        }
        return numberOfBins;
    }


    public static void main(String[] args) {

        // we can assume that none of the objects has a higher value than the capacity.
        BinPacking binPacking = new BinPacking(
                new Integer[]{9, 8, 2, 5, 8, 1, 3, 2, 1, 2, 10, 4, 3, 1, 5, 9, 4}, 20);

        System.out.format("%d bins in total are required.\n",
                binPacking.calculateNumberOfBins());

        binPacking.printAllBins();
    }

}
