package p3.p3p;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PuntosDyV
{
public static DistanciaMinima calculaDistanciaMin (List<Punto> listaPuntos){
	listaPuntos.sort(Comparator.comparingDouble(Punto::getCoordX));
	return calculaDistanciaMinRec(listaPuntos,0,listaPuntos.size()-1);
}

private static DistanciaMinima calculaDistanciaMinRec(List<Punto> listaPuntos, int inicio, int fin) {
    if(fin - inicio <= 3)
		return resolverBase(listaPuntos,inicio,fin);

	int medio = (inicio+fin)/2;
	DistanciaMinima izq = calculaDistanciaMinRec(listaPuntos,inicio,medio);
	DistanciaMinima der = calculaDistanciaMinRec(listaPuntos, medio+1, fin);

	DistanciaMinima mejor = (izq.getDist() < der.getDist())?izq:der;
	double linea = listaPuntos.get(medio).getCoordX();
	List<Punto> franja = new ArrayList<>();

	for(int i=inicio;i<=fin;i++){
		if(Math.abs(listaPuntos.get(i).getCoordX()-linea)<mejor.getDist()){
			franja.add(listaPuntos.get(i));
		}
	}

	for(int i=0;i<franja.size();i++){
		for(int j=i+1;j<franja.size();j++){
			double d = franja.get(i).calculaDistancia(franja.get(j));
			
			if(d < mejor.getDist()){
				mejor = new DistanciaMinima(franja.get(i), franja.get(j), d);
			}
		}
	}
	return mejor;
}

private static DistanciaMinima resolverBase(List<Punto> listaPuntos, int izq, int der) {
    double min = Double.MAX_VALUE;
	Punto p1 = null;
	Punto p2 = null;

	for(int i = izq; i<der; i++){
		for(int j=i+1; j<=der; j++){
			double d = listaPuntos.get(i).calculaDistancia(listaPuntos.get(j));

			if(d < min){
				min = d;
				p1 = listaPuntos.get(i);
				p2 = listaPuntos.get(j);
			}
		}
	}
	return new DistanciaMinima(p1, p2, min);
}

public static void loadFilePuntos(String nombreFicheroEntrada, List<Punto> listaPuntos){
	String linea;
	String[] datosPunto = null;

	try {
		BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
		Integer.parseInt(fichero.readLine());
		while(fichero.ready()){
			linea = fichero.readLine();
			datosPunto = linea.split(",");
			listaPuntos.add(new Punto(Double.parseDouble(datosPunto[0]),Double.parseDouble(datosPunto[1])));
		}
		fichero.close();
	} catch (FileNotFoundException fnfe) {
		System.out.println("Archivo no encontrado");
	}catch (IOException ioe){
		new RuntimeException("Error de entrada/salida");
	}
} 

public static void main (String arg []) 
{
	List<Punto> puntos = new ArrayList<>();
	loadFilePuntos(arg[0], puntos);
	DistanciaMinima dist = calculaDistanciaMin(puntos);
	
	System.out.println("Puntos mas cercanos: " + dist.getP1() + " " + dist.getP2());
	System.out.println("Distancia minima: " + dist.getDist());
}

}

