

/**
 * Implementaremos en esta clase una resolución para el Problema de las N-Reinas utilizando un algoritmo de Backtracking.
 * El objetivo es colocar N reinas en un tablero de ajedrez de NxN sin que ninguna se ataque entre sí; es decir, que no compartan fila, columna ni diagonal.
 * Vamos a evitar recorrer el tablero iterativamente para validar posiciones. 
 * En su lugar, vamos a registrar las columnas y diagonales que ya están bajo ataque utilizando tres arreglos booleanos: 
 * uno para las columnas, otro para las diagonales principales (de arriba a abajo, izquierda a derecha) y otro para las diagonales secundarias (de arriba a abajo, derecha a izquierda).
 */
public class NReinasTiempos {
    
    public static void main(String arg[]) {

        long t1, t2;
        NReinas reinas = new NReinas();

        for (int n = 4; n <= 16; n += 1) {
            t1 = System.currentTimeMillis();

            reinas.resolverNReinas(n);

            t2 = System.currentTimeMillis();
            long tiempo = t2 - t1;
            System.out.println("n=" + n + " **TIEMPO=" + tiempo);
        }
    }

}