import java.util.Arrays; // for use of .toString() only

public class Merge {

    /**
     * Method accepts two arrays that are already sorted in ascending order and
     * merges them in an so sorted output array.
     * 
     * @param A int[] sorted array
     * @param B int[] sorted array
     * @return int[] that contains the elements from A and B also sorted.
     */
    static int[] merge(int[] A, int[] B) {
        int[] C = new int[A.length + B.length];
        // Initialize points to active leftmost elements for the three arrays
        int i = 0; // index for input array A
        int j = 0; // index for input array B
        int k = 0; // index for output array C
        /*
         * While both arrays have active leftmost elements to consider, select the
         * smallest of the two to add to the output array and advance the corresponding
         * input array's leftmost index by 1.
         */
        while (i < A.length && j < B.length) {
            if (A[i] < B[j]) {
                C[k++] = A[i++];
            } else {
                C[k++] = B[j++];
            }
        }
        // In case array A has elements to process
        while (i < A.length) {
            C[k++] = A[i++];
        }
        // In case array B has elements to process
        while (j < B.length) {
            C[k++] = B[j++];
        }
        return C;
    } // method merge

    /**
     * Recursively sorts a given array by decomposing it to single element arrays,
     * then passing them to the merge method. 
     * 
     * @param array int[] array to sort
     * @returns sorted int[]
     */

    static int[] mergeSort(int[] array) {
        // Initialize the output
        int[] result;
        if (array.length == 1) {
            // Base case - input array has one element. By definition
            // it's already sorted.
            result = new int[1];
            result[0] = array[0];
        } else {
            // Split input array into two halves
            int mid = array.length / 2;
            int[] left = new int[mid];
            int[] right = new int[mid];
            // populate left half
            for (int i = 0; i < mid; i++) {
                left[i] = array[i];
            }
            // populate right half ... index i-mid for 0, 1, ... for right array
            for (int i = mid; i < array.length; i++) {
                right[i - mid] = array[i];
            }
            // Feed the halves to this method in case they need to be halved again
            int[] sortedLeft = mergeSort(left);
            int[] sortedRight = mergeSort(right);
            // Recursion ultimately returns control here and we can begin 
            // merging the sorted halves. Note that the first sorted halves
            // we'll see here will have length 1.
            result = merge(sortedLeft, sortedRight);
        }
        // Done
        return result;
    } // method mergeSort

    /**
     * finds the maximum value with in a section of a given array.
     *
     * @param array with indices left and right indicating where in the array to look for a maximum value.
     * @return the maximum value in the wanted bounds of the array.
     */
    public static int findMax(int[] array, int left, int right) {
        // initializing return variable
        int max;
        // if the boundary is only one element long - return that element
        if (left == right) {
            max = array[left];
        // if not:
        } else {
            // create a new array with the length of the bounds
            int[] tempArr = new int[right - left + 1];
            // assign it the values from the original array
            for (int i = left; i <= right; i++) {
                tempArr[i] = array[left + i];
            }
            // sort the list (recursive step)
            tempArr = mergeSort(tempArr);
            // take the max to be the last value, as the last value will be the largest in the list.
            max = tempArr[tempArr.length - 1];
        }
        // return the max value
        return max;
    } // method findMax

    /**
     * reverses the given section of the array; the last element becomes the first, second to last becomes
     * second, etc.
     * @param array with left and right indices indicating boundaries.
     */
    public static void reverseArray(int[] array, int left, int right) {
        // initialize a container variable
        int temp;
        // failsafe - if either boundary goes outside of the array, it will default to the entire array
        if (right < 0 || left < 0 || right > array.length || left > array.length) {
            left = 0;
            right = array.length - 1;
        }
        // recursive loop
        if (left <= right) {
            // container variable records one of the switched element's value
            temp = array[right];
            // assigns the other value to the opposite position
            array[right] = array[left];
            // does the same with the first value
            array[left] = temp;
            // recursive step - calls the function again for the values in between the boundary,
            // until the array is completely reversed
            reverseArray(array, left + 1, right - 1);
        }

    } // method reverseArray

    /**
     * counts how many times a target value occurs within a given section in an array.
     * @param array with a starting index and target value
     * @return count of target value occurances
     */
    public static int countOccurances(int[] array, int target, int index) {
        // initialize a counter variable
        int count = 0;
        // loop starts at index; count is increased by one if target is found
        if  (array[index] == target) {
            count += 1;
        }
        // moves on to the next index
        index++;
        // recursive step - so long as the index is no larger than the length of the array,
        // calls itself again and adds another to count if it's found again.
        if (index < array.length) {
            count += countOccurances(array, target, index);
        }
        // after loop finishes, count is returned
        return count;
    } // method countOccurances

    /**
     * finds the targeted value within a section of an array, and returns it's index.
     * @param array with left and right indices indicating the target section, and a target value
     * @return the index of the target value
     */
    public static int binarySearch(int[] array, int target, int left, int right) {
        // initializing return value with default index
        int index = -1;
        // uses boolean to track if target was found
        boolean found = (array[left] == target);
        // if found, assigns index to that position and ends loop
        if  (found) {
            index = left;
        // recursive step - if not, the calls itself again with the next element's position
        // so long as it does not exceed the bounds.
        } else if (left < right && !found) {
            index = binarySearch(array, target, left+1, right);
        }
        // returns the index
        return index;
    } // method binarySearch


    /** Demo code */
    public static void main(String[] args) {
        int[] test = { 4, 3, 2, 6 };
        int[] sortedTest = mergeSort(test);
        reverseArray(sortedTest, 0, 3);
        System.out.print(binarySearch(sortedTest, 3, 0, sortedTest.length-1) + "\n");
        System.out.print(countOccurances(sortedTest, 6, 0) + "\n");
        System.out.print(findMax(test, 0, sortedTest.length - 1));
        System.out.printf("\n%s\n\n",Arrays.toString(sortedTest));
    }
} // class Merge