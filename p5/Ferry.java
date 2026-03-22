import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ferry {

    private int boatLength;//Longitud de los carriles del barco
    private List<Integer> vehicles; 
    private boolean[][] dp; //Matriz con las posibles soluciones;
    private int[] sumatorio; //Suma acumulada de las longitudes de los vehiculos


    public Ferry(int boatLength, List<Integer> vehicles){
        this.boatLength = boatLength;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size()+1][boatLength+1];

        this.sumatorio = new int[vehicles.size()+1];
        this.sumatorio[0] = 0;

        for(int i = 1;i<=vehicles.size();i++){
            this.sumatorio[i] = sumatorio[i-1] + vehicles.get(i-1);
        }
    }


    public void run(){
        int si = 0;
        int pi = 0;
        int vi = 0;

        dp[0][0] = true;

        for(int i=1;i<=vehicles.size();i++){
            for(int j= boatLength; j >= 0; j--){
                if(!dp[i-1][j]){ //Si con el coche anterior no entra metiendo uno mas tampoco va entrar
                    continue;                    
                }
                si = sumatorio[i];
                pi = j;
                vi = vehicles.get(i-1);

                if((pi + vi)<=boatLength){
                    dp[i][pi+vi] = true;
                }
                if((si-pi)<=boatLength){
                    dp[i][pi] = true;
                }
            }
        }
    }

    private int getNumVehicles(){
        int k = 0;
        for(int i = 0;i < vehicles.size();i++){
            boolean posible = false;
            for(int j = 0;j <= boatLength;j++){
                if(dp[i][j]){
                    posible = true;
                    break;
                }
            }
            if(posible)
                k = i;
            else
                break;
        }
        return k;
    }

    private List<String> solToPrint(int k){
        List<String> resultado = new ArrayList<>();

        int j = 0;
        for(int i = 0; i <= boatLength;i++){
            if(dp[k][i]){
                j = i;
                break;
            }
        }

        for(int i = k; i > 0;i--){
            int vehi = vehicles.get(i-1);
            if(j >= vehi && dp[i-1][j-vehi]){
                resultado.add("babor");
                j = j - vehi;
            } else{
                resultado.add("estribor");
            }
        }

        Collections.reverse(resultado);
        return resultado;
    }

    public void printSolution(){
        int k = getNumVehicles();

        System.out.println("Han llegado un total de " + vehicles.size() + " vehículos (" + k + " viajarán).");
        System.out.println("Tabla con los cálculos realizados:");
        System.out.print("V/L ");
        for(int j = 0; j <= boatLength;j++){
            System.out.print(j + " ");
        }
        System.out.println();

        for(int i = 0; i <= k;i++){
            System.out.print(i + "   ");
            for(int j = 0; j <= boatLength;j++){
                System.out.print((dp[i][j] ? "T ":"F "));
            }
            System.err.println();
        }

        List<String> solucion = solToPrint(k);
        System.out.println("Posible asignación:");
        int sumaBabor = 0;
        int sumaEstribor = 0;

        for(int i = 0; i < k; i++){
            int vehi = vehicles.get(i);

            if(solucion.get(i).equals("babor")){
                sumaBabor += vehi;
            }else{
                sumaEstribor += vehi;
            }
            System.out.println("Vehículo " + (i+1) + " (longitud " + vehi + ") a " + solucion.get(i));
        }

        System.out.println("Ocupación final: Babor " + sumaBabor + "m / Estribor " + sumaEstribor +"m (válido <= " + boatLength + ").");
    }


    public void loadFilePuntos(String nombreFicheroEntrada, List<Integer> listaCoches){
    String linea;
	try {
		BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
		this.boatLength = Integer.parseInt(fichero.readLine().trim());
		while((linea = fichero.readLine()) != null){
            linea = linea.trim();
            if(linea.isEmpty())
                continue;
			String[] datosCoches = linea.split(" ");
            for(String coche: datosCoches){
                if(!coche.isEmpty())
			        listaCoches.add(Integer.parseInt(coche));
            }
		}
		fichero.close();
	} catch (FileNotFoundException fnfe) {
		System.out.println("Archivo no encontrado");
	}catch (IOException ioe){
		new RuntimeException("Error de entrada/salida");
	}
    }


    public int getBoatLenght() {
        return boatLength;
    } 

}
