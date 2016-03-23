import java.util.Arrays;

public class Main {
    public static final int SIZE = 10000;
    public static final int THREADS_COUNT = 4;
    public static final int RANDOM_RANGE = 10;

    public static void main(String[] args) throws InterruptedException {
        long start, end;
        long parallelTimeElapsed, successivelyTimeElapsed;
        int[][] matrix = createRandomMatrix(SIZE, RANDOM_RANGE);

        System.out.println("Threads: " + THREADS_COUNT);
        System.out.println("Matrix size: " + SIZE);

        start = System.nanoTime();
        int[][] resultMatrixParallel = getResultMatrix(SIZE, THREADS_COUNT, matrix);
        end = System.nanoTime();
        parallelTimeElapsed = (end - start) / 1000000L;
        System.out.println("Time elapsed on the implementation of parallel computing: " + parallelTimeElapsed);

        start = System.nanoTime();
        int[][] resultMatrixSuccessively = getResultMatrix(SIZE, matrix);
        end = System.nanoTime();
        successivelyTimeElapsed = (end - start) / 1000000L;
        System.out.println("Time elapsed on the implementation of successively computing: " + successivelyTimeElapsed);

        if(parallelTimeElapsed < successivelyTimeElapsed) {
            System.out.println("Parallel computing is faster");
        } else {
            System.out.println("Successively computing is faster");
        }

        System.out.println("\nInitial matrix:");
        printMatrix(matrix);
        System.out.println("Parallel matrix:");
        printMatrix(resultMatrixParallel);
        System.out.println("Successively matrix:");
        printMatrix(resultMatrixSuccessively);
    }

    public static int[][] createRandomMatrix(int size, int randomRange) {
        int[][] matrix = new int[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                matrix[i][j] = (int) Math.floor(Math.random() * randomRange);
            }
        }
        return matrix;
    }

    public static int[][] getResultMatrix(int size, int threadCount, int[][] matrix) throws InterruptedException {
        int[][] resultMatrix = new int[size][size];
        MatrixThread[] matrixThreads = new MatrixThread[threadCount];

        for (int t=0; t<threadCount; t++) {
            matrixThreads[t] = new MatrixThread(matrix, size, t * size / threadCount, (t + 1) * size / threadCount);
            matrixThreads[t].start();
        }

        for (int t=0; t<threadCount; t++) {
            matrixThreads[t].join();
            int[][] resultMatrixThread = matrixThreads[t].getResultMatrix();
            for (int i=0; i<size; i++) {
                for (int j=t * size / threadCount; j < (t + 1) * size / threadCount; j++) {
                    resultMatrix[i][j] = resultMatrixThread[i][j - t * size / threadCount];
                }
            }
        }

        return resultMatrix;
    }

    public static int[][] getResultMatrix(int size, int[][] matrix) {
        int[][] resultMatrix = new int[size][size];
        int rowCenter = size % 2 == 0 ? size / 2 - 1 : (size + 1) / 2;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (i>rowCenter) {
                    resultMatrix[i][j] = (int) Math.sin(matrix[size - 1 - i][size - 1 - j]);
                } else {
                    resultMatrix[i][j] = (int) Math.sin(matrix[i][j]);
                }
            }
        }
        return resultMatrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("");
    }
}
