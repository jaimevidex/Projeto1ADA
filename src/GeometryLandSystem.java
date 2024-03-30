import java.util.Arrays;

public class GeometryLandSystem {
    private int nrVertices;
    private String[] signals;
    private int[] vertices;
    private int[] maxes;
    private int max;

    public GeometryLandSystem(int nrVertices, int[] vertices, String[] signals) {
        this.nrVertices=nrVertices;
        this.signals=signals;
        this.vertices=vertices;
        this.maxes = new int[nrVertices];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < signals.length; i++) {
            String[] croppedArray = new String[nrVertices-1];
            int j = 0;
            for (int k = 0; k < signals.length; k++) {
                if (k != i) { // Skip the element we want to remove
                    croppedArray[j++] = signals[k];
                }
            }
            System.out.println(Arrays.toString(croppedArray));
            maxes[i] = solve(croppedArray,vertices);
        }
    }

    public int solve(String[] signalsCropped, int[] vertices) {
        int[][] matrix = new int[nrVertices][nrVertices];
        return (int) Math.floor(Math.random()*10);
    }

    public int getMax() {
        max = maxes[0];
        for (int num : maxes) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public String getGoodStarts() {
        String result = "";
        for (int i = 0; i < maxes.length; i++) {
            if (maxes[i] == max) {
                result = result + (i+1) + " ";
            }
        }
        System.out.println(Arrays.toString(maxes));
        return result.trim();
    }
}

