import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Ferry {

    private int boatLength;//Longitud de los carriles del barco
    private List<Integer> vehicles; 
    private boolean[][] dp; //Matriz con las posibles soluciones;
    private int[] sumatorio; //Suma acumulada de las longitudes de los vehiculosç
    private List<Step> path;


    public Ferry(int boatLength, List<Integer> vehicles){
        this.path = new LinkedList<>();
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

    private int getMaximumNumberOfVehicles(){
        int k = 0;
        for(int i = 0;i <= vehicles.size();i++){
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

    public void printData() {
		System.out.printf("Length of parallel lanes for starboard and port on the ferry: %d\n", boatLength);
		System.out.printf("The vehicles have the following lengths:\n");
		for (int i = 0; i < vehicles.size(); i++) {
			System.out.printf("\tVehicle %d: %d\n", i+1, vehicles.get(i));
		}
	}

    public void printPossibleAssignation(){
        path.clear();
        boolean found = false;
		System.out.printf("\nPossible assignation:\n");
		for (int i = getMaximumNumberOfVehicles(); i > 0; i--) {
            if(found)
                break;
            for(int j = 0; j <= boatLength; j++){
                if(found)
                    break;
                int vehi = vehicles.get(i-1);
                if(j>=vehi){
                    if(dp[i][j]){
                        if(dp[i-1][j-vehi]){
                            found = true;
                            processAssignation(i,j);
                        }
                    }
                }
            }
			//si found es true -> rompo la ejecución
			//para cada p de la longitud del barco
			//		si found es true -> rompo la ejecución
			//		si dp[i][p-v(i)] es true -> found = true; llamo a processAssignation()
		}
	}

    private void processAssignation(int i, int l) {
        if(i==0 && l==0){
            printPath();
            return;
        }

        int vehi = vehicles.get(i-1);

        if(dp[i-1][l]){
            path.addFirst(new Step(i-1, l, i, l, i, "estribor"));
            processAssignation(i-1, l);
            return;
        }

        if(dp[i-1][l-vehicles.get(i-1)]){
            path.addFirst(new Step(i-1, l-vehi, i, l, i, "babor"));
            processAssignation(i-1, l-vehicles.get(i-1));
        }
	// if ((i == 0) && (l == 0)) { // llamo a printPath y acabo la ejecución (return)
		
	//if (dp[i-1][l]) {
	//		añado al path (path.addFirst) un nuevo Step llamado estribor; llamo a processAssignation(i-1, l);

		
	// if (dp[i-1][l-vehicles.get(i-1)]) {
	//		añado al path (path.addFirst) un nuevo Step llamado babor; llamo a processAssignation(i-1, l-vehicles.get(i-1));
	}

    public void printSolutionTable() {
	System.out.printf("\nTable with calculations:\n");
		
	System.out.printf("%4s", "V/L");
	for (int i = 0; i <= boatLength; i++) {
		System.out.printf("%4d", i);	
	}
	System.out.printf("\n");
		
	for (int i = 0; i <= vehicles.size(); i++) {
		System.out.printf("%4d", i);
		for (int l = 0; l <= boatLength; l++) {
			if (dp[i][l]){				
				System.out.printf("%4s", "T");
			}
			else{ 
					System.out.printf("%4s", "F");
				}
			}
			System.out.printf("\n");
		}
	}


    private void printPath() {
	    int portLength = 0;
	    int starboardLength = 0;
	    for (var step : path) {		
		    if (step.movement().equals("babor")){
			    portLength += vehicles.get(step.vehicle()-1);
		    }
		    else{
			    starboardLength += vehicles.get(step.vehicle()-1);
		    }
		    System.out.printf("Vehicle %d (length %d) -- From (%d, %d) -- To (%d, %d) -- Position: %s -- Port lengh: %d -- Starboard length: %d\n", 
			    	step.vehicle(), vehicles.get(step.vehicle()-1),
				    step.previousI(), step.previousL(),
				    step.currentI(), step.currentL(), 
				    step.movement(), portLength, starboardLength);
	    }
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

record Step(int previousI, int previousL, 
		int currentI, int currentL, 
		int vehicle, String movement) {}
