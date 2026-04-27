import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LaberintoTodas{

    List<int[][]> soluciones;
    private int mejorMovs;
    private int[][] tablero;
    private int[][] mejorTablero;
    private int llamadas = 0;
    int[] dx = {0, 0, -1, 1};
    int[] dy = {-1, 1, 0, 0};


    LaberintoTodas(int[][] tablero) {
        this.tablero = tablero;
        mejorMovs = Integer.MAX_VALUE;
        soluciones = new ArrayList<>();
        mejorTablero = new int[7][7];
    }

    public void resolver() {
        llamadas = 0;
        tablero[0][0]=2;
        backtracking(0,0,0); 
        printSol();    
    }

    private void backtracking(int posI, int posJ,int movs){
        llamadas++;
        //Caso base
        if(posI == 6 && posJ == 6){
            System.out.println("SOLUCIÓN ENCONTRADA CON " + movs + " PASOS");
            imprimirTablero(tablero);
            if(movs<mejorMovs){
                mejorMovs = movs;
                mejorTablero = copiarTablero(tablero);
            }
            soluciones.add(copiarTablero(tablero));
            return;
        }

        for(int i = 0; i < 4; i++){
            if(esValido(posI+dx[i], posJ+dy[i])){
                //Avanzar
                tablero[posI+dx[i]][posJ+dy[i]] = 2;
                backtracking(posI+dx[i],posJ+dy[i],movs+1);
                //Retroceder
                tablero[posI+dx[i]][posJ+dy[i]] = 0;
            }
        }
    }

    private int[][] copiarTablero(int[][] tablero){
        int[][] nuevo = new int[tablero.length][tablero[0].length];
        for(int i = 0; i < nuevo.length; i++){
            for(int j = 0; j < nuevo[i].length;j++){
                nuevo[i][j]=tablero[i][j];
            }
        }
        return nuevo;
    }

    private boolean esValido(int i,int j){
        return (i>=0 && j>=0 && i<tablero.length && j<tablero[0].length && tablero[i][j]==0);
    }

    private void imprimirTablero(int[][] t){
        for(int i = 0; i < t.length; i++){
            for(int j = 0; j < t[i].length; j++){
                char c;
                if(t[i][j] == 0) c = '·';
                else if(t[i][j] == 1) c = 'H';
                else c = '*';
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printSol() {
        System.out.println("LA MEJOR SOLUCIÓN TIENE " + mejorMovs + 
            " PASOS, HABIENDO UN TOTAL DE " + soluciones.size() + " SOLUCIONES");
        imprimirTablero(mejorTablero);
    }


     public static void main(String[] args) {
    Scanner sc;
    try {
        sc = new Scanner(new FileReader(args[0]));

        List<int[]> filas = new ArrayList<>();

        while (sc.hasNextLine()) {
            String linea = sc.nextLine().trim();

            if (linea.isEmpty()) continue;

            String[] partes = linea.split("\\s+");
            int[] fila = new int[partes.length];

            for (int i = 0; i < partes.length; i++) {
                fila[i] = Integer.parseInt(partes[i]);
            }

            filas.add(fila);
        }

        int numFilas = filas.size();
        int numColumnas = filas.get(0).length;

        int[][] matriz = new int[numFilas][numColumnas];

        for (int i = 0; i < numFilas; i++) {
            matriz[i] = filas.get(i);
        }
        new LaberintoTodas(matriz).resolver();


    } catch (FileNotFoundException e) {
        System.out.println("Error: fichero no encontrado");
    }
    }
}