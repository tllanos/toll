package co.edu.eafit.dis.dijkstra;

/**
 * Clase encargada de proveer la estructura de datos necesaria para almacenar el
 * resultado producido por la implementación del algoritmo de Dijkstra (Ver
 * clase Dijkstra para más información).
 * 
 * @author tllanos, ccorre20, icardena
 */
public class Vertex implements Comparable<Vertex> {
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(String argName) {
        name = argName;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
