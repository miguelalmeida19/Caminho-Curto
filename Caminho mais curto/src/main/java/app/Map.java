package app;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Map {
    private int start;
    private int end;
    private int size;
    private int[][] arr;
    private List<String> possiblePaths;
    private int cost;
    private String shortestPath;

    public Map(){
        this.arr = createMatrix();
    }

    public int[][] getArr() {
        return arr;
    }

    public int getEnd() {
        return arr[arr.length-1][arr.length-1];
    }

    public int getSize() {
        return arr.length;
    }

    public int getCost() {
        return cost;
    }

    public int getStart() {
        return arr[0][0];
    }

    public String getShortestPath() {
        return shortestPath;
    }

    public List<String> getPossiblePaths() {
        return possiblePaths;
    }

    public void setPossiblePaths(List<String> possiblePaths) {
        this.possiblePaths = possiblePaths;
    }

    public void setShortestPath(String shortestPath) {
        this.shortestPath = shortestPath;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public static int[][] createMatrix(){

        int randomNum = ThreadLocalRandom.current().nextInt(4, 6);


        int[][] arr = new int[randomNum][randomNum];
        fillTheRestOfTheMatrix(arr);
        fillMainDiagonal(arr);
        return arr;
    }

    public static void fillMainDiagonal(int[][] arr){
        arr[0][arr.length-1] = 0;
        arr[arr.length-1][0] = 0;
        for (int i=0; i<arr.length; i++){
            arr[i][i] = 0;
        }
    }

    public static void fillTheRestOfTheMatrix(int[][] arr){
        for (int i=0;i<arr.length;i++){
            for (int f=0; f<arr[0].length; f++){
                if (i!=f){
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 20);
                    arr[i][f] = randomNum;
                    arr[f][i] = randomNum;
                }
            }
        }
    }
}
