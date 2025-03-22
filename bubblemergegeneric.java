import java.io.*;
import java.util.*;


public class bubblemergegeneric {

public static void main(String[] args) {
    String filename = "storearray.txt";
    String filename2 = "sortarray.txt";
    boolean run = true;
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("\nWelcome to the Bubble-Merge-Sort Machine!");

    System.out.println("\nAn array will be randomly generated using the integer you entered as the number of elements in the array.");
    System.out.println("For example, entering '9' (without the quotation marks) generates an array of 9 random numbers).");
    System.out.println("\nThese numbers will then be sorted from smallest to largest using Bubblesort and Mergesort.");
    System.out.println("The time it took each array to be sorted will appear below each array in nanoseconds.");
    System.out.println("Note that your unsorted array will be stored to the 'storearray.txt' file and the sorted one will be stored to 'sortarray.txt'");

    while (run) {

    System.out.println("\nEnter any integer between 0 and 100 (inclusive) to generate your array.");
   
    System.out.println("\nEnter '-1' to exit.\n");

    Integer arraySize = scanner.nextInt();

    if ((arraySize < 0 || arraySize > 100) && (arraySize != -1)) {
        System.out.println("\nPlease enter a valid input between 0 and 100 (inclusive).\n");
    }
    else if (arraySize == -1) {
        System.out.println("\nExit successful.\n");
        run = false;
    }
    else if ((arraySize >= 0 && arraySize <= 100)) {

    List<Integer> randomArray = createRandomArray(arraySize);

    writeArrayToFile(randomArray, filename);
        System.out.println("\nHere is your unsorted array with " + arraySize + " numbers in it:\n");
        for (Integer i : randomArray) {
            System.out.println(i);
        }

    List<Integer> inputToArray = readFileToArray(filename);

    long beginBubbleSort = System.nanoTime();
    bubbleSort(inputToArray);
    long stopBubbleSort = System.nanoTime();
    long bubbleSortTime = stopBubbleSort - beginBubbleSort;

    writeArrayToFile2(inputToArray, filename2, "Bubblesort Time: " + bubbleSortTime + " nanoseconds.\n", true);
    System.out.println("\nHere is your bubblesorted array:\n");
        for (Integer i : inputToArray) {
            System.out.println(i);
        }

        System.out.println("\nBubblesort time in nanoseconds: " + bubbleSortTime);
        System.out.println();

    inputToArray = new ArrayList<>(randomArray);

    long beginMergeSort = System.nanoTime();
    mergeSort(inputToArray, 0, inputToArray.size() - 1);
    long stopMergeSort = System.nanoTime();
    long mergeSortTime = stopMergeSort - beginMergeSort;

    writeArrayToFile2(inputToArray, filename2, "Mergesort Time: " + mergeSortTime + " nanoseconds.\n", false);
    System.out.println("\nHere is your mergesorted array:\n");
        for (Integer i : inputToArray) {
            System.out.println(i);
    }

    System.out.println("\nMergesort time in nanoseconds: " + mergeSortTime);
    System.out.println();

    }
    }

        scanner.close();

}

public static List<Integer> createRandomArray(Integer size) {
    Random random = new Random();
    List<Integer> myList = new ArrayList<>();
    for (Integer i = 0; i < size; i++) {
        myList.add(random.nextInt(101));
    }

    return myList;
}

public static void writeArrayToFile(List<Integer> myList, String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (Integer listElement : myList) {
        writer.write(listElement.toString());
        writer.newLine();
        }
    }
    catch (IOException e) {
        System.out.println(e.getMessage());
    }

}

public static void writeArrayToFile2(List<Integer> myList, String filename, String time, boolean blank) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
        if (blank) {
            new FileWriter(filename, false).close();
        }
        writer.write(time);
        writer.newLine();
        for (Integer listElement : myList) {
            writer.write(listElement.toString());
            writer.newLine();
    }
            writer.newLine();
    }
    catch (IOException e) {
        System.out.println(e.getMessage());
    }
}


public static List<Integer> readFileToArray(String filename) {
    List<Integer> myList = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String forLine;
        while ((forLine = reader.readLine()) != null) {
            myList.add(Integer.parseInt(forLine));
        }
        }
    catch (IOException e) {
        System.out.println(e.getMessage());
        return new ArrayList<>();

    }
        return myList;
    }

public static <T extends Comparable <T>> void bubbleSort(List<T> myList) {
    Integer m = myList.size();
    for (Integer i = 0; i < m - 1; i++) {
        for (Integer j = 0; j < m - 1 - i; j++) {
            if (myList.get(j).compareTo(myList.get(j + 1)) > 0) {
                T swap = myList.get(j);
                myList.set(j, myList.get(j + 1));
                myList.set(j+1, swap);
            }
        }
        boolean ordered = true;
        for (Integer n = 0; n < m - 1 - i; n++) {
            if (myList.get(n).compareTo(myList.get(n + 1)) > 0) {
                ordered = false;
                break;
            }
        }
        if (ordered) break;

    }

}

public static <T extends Comparable <T>> void mergeSort(List<T> myList, Integer left, Integer right) {
    if (left < right) {
        Integer middle = (((right - left) / 2) + left);

        mergeSort(myList, left, middle);
        mergeSort(myList, middle + 1, right);

        mergeSort2(myList, left, middle, right);

    }
}

public static <T extends Comparable<T>> void mergeSort2(List<T> myList, Integer left, Integer middle, Integer right) {
    Integer a = middle - left + 1;
    Integer b = right - middle;

    List<T> L = new ArrayList<>(myList.subList(left, middle + 1));
    List<T> R = new ArrayList<>(myList.subList(middle + 1, right + 1));

    Integer i = 0;
    Integer j = 0;
    Integer k = left;

    while (i < a && j < b) {
        if (L.get(i).compareTo(R.get(j)) <= 0) {
        myList.set(k, L.get(i));
        i++;
    }
    else {
        myList.set(k, R.get(j));
        j++;
    }
        k++;
    }

    while (i < a) {
        myList.set(k, L.get(i));
        i++;
        k++;
    }
    while (j < b) {
        myList.set(k, R.get(j));
        j++;
        k++;
    }

}

}