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
        /*
        // Criação das matrizes que guardam os valores se formos fazer com os máximos
        // e com os mínimos, tentativa de superar o resultado errado no exemplo 2
        long[][] dpMax = new long[nrVertices][nrVertices];
        long[][] dpMin = new long[nrVertices][nrVertices];

        // Inicialização das matrizes
        for (int i = 0; i < nrVertices; i++) {
            for (int j = 0; j < nrVertices; j++) {
                // com os valores do vertice se for a diagonal, porque é como se já so houvesse um vertice
                if (i==j) {
                    dpMax[i][i] = vertices[i];
                    dpMin[i][i] = vertices[i];
                }
                // no resto da tabela colocar os MIN ou MAX Values, sugestão de gente no grupo do wpp dos gajos do nosso ano
                // porque senão ia iniciar a 0 e isso pode dar asneira
                else {
                    dpMax[i][j] = Long.MAX_VALUE;
                    dpMin[i][j] = Long.MIN_VALUE;
                }
            }
        }

        // indice das subsequencias
        for (int l = 2; l <= nrVertices; l++) {
            // corres os possivies indices iniciais (i)
            for (int i = 0; i < nrVertices - l + 1; i++) {
                int j = i + l - 1;

                dpMax[i][j] = evaluate(dpMax[i][i], dpMax[i + 1][j], operatorsCropped[i]);

                if (operatorsCropped[i].equals("+")) {
                    dpMin[i][j] = Math.min(dpMin[i][i], dpMin[i + 1][j]);
                } else {
                    dpMin[i][j] = Math.max(dpMax[i][i], dpMax[i + 1][j]);
                }

                for (int k = i + 1; k < j; k++) {
                    long leftValMax = evaluate(dpMax[i][k], dpMax[k + 1][j], operatorsCropped[k]);
                    dpMax[i][j] = Math.max(dpMax[i][j], leftValMax);

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
        return Math.max(dpMax[0][nrVertices - 1], dpMin[0][nrVertices - 1]);*/

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

        // Debug para ver as matrizes
        /*
        for (long[] longs : dp) {
            System.out.println(Arrays.toString(longs));
        }
        System.out.println();
        */

        return dp[0][nrVertices - 1];
    }

    public static long evaluate(long a, long b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "*" -> (a * b);
            default -> throw new IllegalStateException("Unexpected value: " + op);
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

