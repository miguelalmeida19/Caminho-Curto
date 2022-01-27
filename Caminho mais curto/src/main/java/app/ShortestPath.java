package app;

public class ShortestPath {

    private static final int NO_PARENT = -1;

    // Function that implements Dijkstra's
    // single source shortest path
    // algorithm for a graph represented
    // using adjacency matrix
    // representation
    static String dijkstra(int[][] adjacencyMatrix,
                           int startVertex, int end)
    {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        int[] shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++)
        {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <
                                shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }

        String solution = printSolution(startVertex, shortestDistances, parents, end);
        return solution;
    }

    // A utility function to print
    // the constructed distances
    // array and shortest paths
    private static String printSolution(int startVertex,
                                        int[] distances,
                                        int[] parents, int end)
    {
        int nVertices = distances.length;

        for (int vertexIndex = 0;
             vertexIndex < nVertices;
             vertexIndex++)
        {
            if (vertexIndex != startVertex && vertexIndex==end)
            {
                String string = "";
                string+= startVertex + " -> ";
                //System.out.print(startVertex + " -> ");
                string+=vertexIndex + "\t\t";
                //System.out.print(vertexIndex + "\t\t");
                string+=distances[vertexIndex] + "\t\t";
                //System.out.print(distances[vertexIndex] + "\t\t");
                StringBuilder line = new StringBuilder();
                printPath(vertexIndex, parents, line);
                string+= line;
                return string;
            }
        }
        return null;
    }

    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    private static void printPath(int currentVertex,
                                  int[] parents, StringBuilder line)
    {
        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT)
        {
            return;
        }
        printPath(parents[currentVertex], parents, line);
        //System.out.print(currentVertex + " ");
        line.append(currentVertex + " ");
        //System.out.println(line);
    }

    // Driver Code
    public static void main(String[] args)
    {
        int[][] adjacencyMatrix = { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
                { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
                { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
                { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
                { 0, 0, 4, 0, 10, 0, 2, 0, 0 },
                { 0, 0, 0, 14, 0, 2, 0, 1, 6 },
                { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
                { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
        dijkstra(adjacencyMatrix, 0, 3);
    }
}