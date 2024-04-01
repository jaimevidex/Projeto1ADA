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
            // Isto tira um dos operadores e cria o array de operadores
            // com que vamos trabalhar
            String[] croppedArray = new String[nrVertices-1];
            int j = 0;
            for (int k = 0; k < operators.length; k++) {
                if (k != i) {
                    croppedArray[j++] = operators[(i+j)%nrVertices];
                }
            }
            // Isto reorganiza os vertices de modo a que os vertices
            // estejam de acordo com os operadores
            j = 0;
            int[] reorganizedArray = new int[nrVertices];
            for (int k = 0; k < nrVertices; k++) {
                reorganizedArray[j] = vertices[(i+j)%nrVertices];
                j++;
            }

            // Isto chama a função que vai resolver para cada expressão (já tirado um operador)
            maxes[i] = solve(croppedArray,reorganizedArray);
        }
    }

    public long solve(String[] operatorsCropped, int[] vertices) {
        long[][] dp = new long[nrVertices][nrVertices];
        // Inicialização da matriz, preenchendo a diagonal com os casos base (só há um vertice)
        for (int i = 0; i < nrVertices; i++) {
            dp[i][i] = vertices[i];
        }

        // indice das subsequencias
        for (int l = 2; l <= nrVertices; l++) {
            // corres os possivies indices iniciais (i)
            for (int i = 0; i < nrVertices - l + 1; i++) {
                // calcula o indice final (j) para s subsequencia com o indice a iniciar em i
                int j = i + l - 1;

                // calcula valores iniciais para os indices da tabela
                dp[i][j] = evaluate(dp[i][i], dp[i + 1][j], operatorsCropped[i]);

                // ajuda a melhorar o valor assumido a cima com a ajuda do k que é um ponto de divisao
                // para as subsequencias para tentar chegar ao melhor resultado
                for (int k = i + 1; k < j; k++) {
                    long leftVal = evaluate(dp[i][k], dp[k + 1][j], operatorsCropped[k]);
                    long rightVal = evaluate(dp[i][k - 1], dp[k][j], operatorsCropped[k - 1]);
                    dp[i][j] = Math.max(dp[i][j], Math.max(leftVal, rightVal));
                }
            }
        }

        return dp[0][nrVertices - 1];
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

