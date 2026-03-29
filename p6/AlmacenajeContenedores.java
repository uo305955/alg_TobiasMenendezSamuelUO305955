import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedores{

    private int capacidadC;
    private Integer[] conjuntoS;
    private int mejorK; //Numero min contenedores
    private List<List<Integer>> mejorDistribucion;
    private int llamadas = 0;


    public static void main(String[] args) {
        Scanner sc;
        try {
            sc = new Scanner(new FileReader(args[0]));
            int c = sc.nextInt();
            sc.nextLine();
            String[] parts = sc.nextLine().split(" ");
            Integer[] toS = new Integer[parts.length];
            int i = 0;
            for(String s: parts){
                toS[i] = Integer.parseInt(s);
                i++;
            }
            new AlmacenajeContenedores(c,toS).resolver(args[1]);
        } catch (FileNotFoundException ex) {
            System.getLogger("Fichero no encontrado");
        }
    }

    AlmacenajeContenedores(int capacidadC, Integer[] conjuntoS) {
        //Inicializar en el peor caso posible (1 contenedor por objeto)
        this.capacidadC = capacidadC;
        this.conjuntoS = conjuntoS;
        Arrays.sort(this.conjuntoS, Collections.reverseOrder());
        this.mejorK = conjuntoS.length;
        this.mejorDistribucion = new ArrayList<>();
    }

    public void resolver(String solucion) {
        llamadas = 0;
        List<List<Integer>> contenedores = new ArrayList<>();
        backtracking(0, contenedores); 
        printSol(solucion);    
    }

    public void resolverSinSalida(){
        llamadas = 0;
        List<List<Integer>> contenedores = new ArrayList<>();
        backtracking(0, contenedores);
    }

    private void backtracking(int indexObject, List<List<Integer>> contenedores){
        llamadas++;
        //Caso base
        if(indexObject == conjuntoS.length){
            if(contenedores.size()<mejorK){
                mejorK = contenedores.size();
                mejorDistribucion = copiar(contenedores);
            }
            return;
        }
        //PODA: si size de contenedores > mejor encontrado, paramos
        if(contenedores.size() >= mejorK){
            return;
        }



        //Probar a meter en contenedores existentes
        for(int i = 0; i < contenedores.size(); i++){
            if(sum(contenedores.get(i)) + conjuntoS[indexObject] <= capacidadC){
                //Avanzar
                contenedores.get(i).add(conjuntoS[indexObject]);
                backtracking(indexObject + 1, contenedores);
                //Retroceder
                contenedores.get(i).remove(contenedores.get(i).size() - 1);
            }
        }

        //Intentar meterlo en un nuevo contenedor 
        List<Integer> nuevoContenedor = new ArrayList<>();
        nuevoContenedor.add(conjuntoS[indexObject]);
        contenedores.add(nuevoContenedor);
        //Avanzo
        backtracking(indexObject + 1, contenedores);
        //Retrocedo
        contenedores.remove(contenedores.size() - 1);
    }

    private int sum(List<Integer> list) {
        int suma = 0;
        for(int i = 0; i < list.size();i++){
            suma += list.get(i);
        }
        return suma;
    }

    private void printSol(String nombreSolucion) {
    try{
        FileWriter wr = new FileWriter(nombreSolucion);
        wr.write("Lista de contenedores y objetos contenidos:\n");
        for(int i = 0; i < mejorDistribucion.size();i++){
            wr.write("\tContenedor " + (i+1) + ": ");
            for(int j = 0;j < mejorDistribucion.get(i).size();j++){
                wr.write(mejorDistribucion.get(i).get(j) + " ");
            }
            wr.write("\n");
        }
        wr.write("\n");
        wr.write("El número de contenedores necesario es " + mejorK+"\n");
        wr.write("Número de llamadas: " + llamadas);
        wr.close();
    }catch(IOException e){
        System.out.println("Error al escribir");
    }
    }

    private List<List<Integer>> copiar(List<List<Integer>> contenedores){
        ArrayList<List<Integer>> copia = new ArrayList<>();
        for(List<Integer> i: contenedores){
            copia.add(new ArrayList<>(i));
        }
        return copia;
    }
}