public class MatrixThread extends Thread {
    private int[][] matrix;
    private int[][] resultMatrix;
    private int size;
    private int from;
    private int to;

    public MatrixThread(int[][] matrix, int size, int from, int to) {
        this.matrix = matrix;
        this.size = size;
        this.from = from;
        this.to = to;
        resultMatrix = new int[size][to - from];
    }

    public int[][] getResultMatrix() {
        return resultMatrix;
    }

    @Override
    public void run() {
        int rowCenter = size % 2 == 0 ? size / 2 - 1 : (size + 1) / 2;
        for (int i=0; i<size; i++) {
            for (int j=from; j<to; j++) {
                if (i>rowCenter) {
                    resultMatrix[i][j-from] = matrix[size - 1 - i][size - 1 - j];
                } else {
                    resultMatrix[i][j-from] = matrix[i][j];
                }
            }
        }
    }
}