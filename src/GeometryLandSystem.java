import java.util.Arrays;

public class GeometryLandSystem {
    private int nrVertices;
    private String[] operators;
    private int[] vertices;
    private long[] maxes;
    private long max;

    public GeometryLandSystem(int nrVertices, int[] vertices, String[] operators) {
        this.nrVertices=nrVertices;
        this.operators=operators;
        this.vertices=vertices;
        this.maxes = new long[nrVertices];
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < operators.length; i++) {
            String[] croppedArray = new String[nrVertices-1];
            int j = 0;
            for (int k = 0; k < operators.length; k++) {
                if (k != i) {
                    croppedArray[j++] = operators[(i+j)%nrVertices];
                }
            }
            j = 0;
            int[] reorganizedArray = new int[nrVertices];
            for (int k = 0; k < nrVertices; k++) {
                reorganizedArray[j] = vertices[(i+j)%nrVertices];
                j++;
            }

            maxes[i] = solve(croppedArray,reorganizedArray);
        }
    }

    public long solve(String[] operatorsCropped, int[] vertices) {
        long[][] dpMax = new long[nrVertices][nrVertices]; // Table for maximum values
        long[][] dpMin = new long[nrVertices][nrVertices]; // Table for minimum values

        // Initialize base cases (similar to original implementation)
        for (int i = 0; i < nrVertices; i++) {
            for (int j = 0; j < nrVertices; j++) {
                if (i==j) {
                    dpMax[i][i] = vertices[i];
                    dpMin[i][i] = vertices[i];
                }
                else {
                    dpMax[i][j] = Long.MAX_VALUE;
                    dpMin[i][j] = Long.MIN_VALUE;
                }
            }
        }

        for (int l = 2; l <= nrVertices; l++) {
            for (int i = 0; i < nrVertices - l + 1; i++) {
                int j = i + l - 1;

                // Evaluate for max (similar to original implementation)
                dpMax[i][j] = evaluate(dpMax[i][i], dpMax[i + 1][j], operatorsCropped[i]);

                // Evaluate for min based on operator and signs
                if (operatorsCropped[i].equals("+")) {
                    dpMin[i][j] = Math.min(dpMin[i][i], dpMin[i + 1][j]);
                } else { // Assuming multiplication (*)
                    dpMin[i][j] = Math.max(dpMax[i][i], dpMax[i + 1][j]);
                }

                for (int k = i + 1; k < j; k++) {
                    // Recursive evaluation for max (unchanged)
                    long leftValMax = evaluate(dpMax[i][k], dpMax[k + 1][j], operatorsCropped[k]);
                    dpMax[i][j] = Math.max(dpMax[i][j], leftValMax);

                    // Recursive evaluation for min based on operator and signs
                    long leftValMin = evaluate(dpMin[i][k], dpMin[k + 1][j], operatorsCropped[k]);
                    long rightValMin = evaluate(dpMin[i][k - 1], dpMin[k][j], operatorsCropped[k - 1]);
                    if (operatorsCropped[k].equals("+")) {
                        dpMin[i][j] = Math.min(dpMin[i][j], Math.min(leftValMin, rightValMin));
                    } else {
                        dpMin[i][j] = Math.max(dpMin[i][j], Math.max(leftValMin, rightValMin));
                    }
                }
            }
        }
        return Math.max(dpMax[0][nrVertices - 1], dpMin[0][nrVertices - 1]);
    }

    public static long evaluate(long a, long b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "*" -> (a * b);
            default -> throw new IllegalArgumentException("Invalid operator: " + op);
        };
    }

    public long getMax() {
        max = maxes[0];
        for (long num : maxes) {
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
        return result.trim();
    }
}

