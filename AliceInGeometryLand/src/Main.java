import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader( new InputStreamReader(System.in));
        int nrVertices = Integer.parseInt(in.readLine());
        String[] token = in.readLine().split(" ");
        String[] signals = new String[nrVertices];
        int[] vertices = new int[nrVertices];
        for (int i = 0; i < 2*nrVertices; i++) {
            if (i%2==0) {
                signals[i/2]=token[i];
            } else {
                vertices[i/2]=Integer.parseInt(token[i]);
            }
        }
        GeometryLandSystem system = new GeometryLandSystem(nrVertices,vertices,signals);
        System.out.println(system.getMax());
        System.out.println(system.getGoodStarts());
    }
}