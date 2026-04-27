import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedoresRyP{

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
            new AlmacenajeContenedoresRyP(c,toS).resolver(args[1]);
        } catch (FileNotFoundException ex) {
            System.getLogger("Fichero no encontrado");
        }
    }

    AlmacenajeContenedoresRyP(int capacidadC, Integer[] conjuntoS) {
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
        //calculaMejorK();
        backtracking(0, contenedores, sumaTotal()); 
        printSol(solucion);    
    }

    //Inicializar k
    //El problema de esto esta en que al inicializar la mejor k no se entra en el caso base
    //Lo hice en clase pero no me funciona
    //private void calculaMejorK(){
      //  mejorK = (sumaTotal() + capacidadC + 1)/capacidadC;
    //}

    private int sumaTotal(){
        int suma = 0;
        for(int i = 0; i < conjuntoS.length;i++){
            suma += conjuntoS[i];
        }
        return suma;
    }

    public void resolverSinSalida(){
        llamadas = 0;
        List<List<Integer>> contenedores = new ArrayList<>();
        backtracking(0, contenedores,sumaTotal());
    }

    private void backtracking(int indexObject, List<List<Integer>> contenedores, int sumaRestante){
        llamadas++;

        //LowerBound(cotaMinima)
        //Calcular el numero minimo teorico de contenedores adicionales necesarios
        int lowerBound = (sumaRestante + capacidadC - 1) / capacidadC;


        //PODA: si size de contenedores > mejor encontrado, paramos
        if(contenedores.size() + lowerBound >= mejorK){
            return;
        }

        //Caso base
        if(indexObject == conjuntoS.length){
            if(contenedores.size()<mejorK){
                mejorK = contenedores.size();
                mejorDistribucion = copiar(contenedores);
            }
            return;
        }

        //Probar a meter en contenedores existentes
        for(int i = 0; i < contenedores.size(); i++){
            if(sum(contenedores.get(i)) + conjuntoS[indexObject] <= capacidadC){
                //Avanzar
                contenedores.get(i).add(conjuntoS[indexObject]);
                backtracking(indexObject + 1, contenedores, sumaRestante-conjuntoS[indexObject]);
                //Retroceder
                contenedores.get(i).remove(contenedores.get(i).size() - 1);
            }
        }

        if(contenedores.size() < mejorK){
        //Intentar meterlo en un nuevo contenedor 
        List<Integer> nuevoContenedor = new ArrayList<>();
        nuevoContenedor.add(conjuntoS[indexObject]);
        contenedores.add(nuevoContenedor);
        //Avanzo
        backtracking(indexObject + 1, contenedores, sumaRestante-conjuntoS[indexObject]);
        //Retrocedo
        contenedores.remove(contenedores.size() - 1);
        }

        //Hacer indice y que aparezca en la izquierda para click
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