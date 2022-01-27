package app;
import app.Graph;
import app.Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MainTestes {

    public static void main(String[] args) {
        Map map = new Map();
        System.out.println(Arrays.deepToString(map.getArr()));
        Scanner read = new Scanner(System.in);
        System.out.println("\rOnde está?");
        int start = read.nextInt();
        System.out.println("\rOnde quer ir?");
        int end = read.nextInt();
        String solution = ShortestPath.dijkstra(map.getArr(), start, end);
        System.out.println("Partida/Chegada: " + solution.split("\t\t")[0]);
        System.out.println("Custo: " + solution.split("\t\t")[1]);
        System.out.println("Percurso: " + solution.split("\t\t")[2]);


        Graph g = new Graph(map.getSize());
        for (int i=0; i<map.getArr().length; i++){
            for (int f=0; f<map.getArr().length; f++){
                if (map.getArr()[i][f]!=0){
                    g.addEdge(i, f);
                }
            }
        }
        System.out.println("Todos os caminhos possíveis: ");
        List<String> allPossiblePaths = g.printAllPaths(start, end);
        for (String path: allPossiblePaths){
            System.out.println(path);
        }
    }
}
