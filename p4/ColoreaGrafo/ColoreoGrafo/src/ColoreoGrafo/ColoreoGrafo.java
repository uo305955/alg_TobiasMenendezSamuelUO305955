package ColoreoGrafo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColoreoGrafo {

	public static Map<String, String> realizarVoraz(Map<String, List<String>> grafo) {
		List<String> colores = Arrays.asList("red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta",
				"lime");
		Map<String, String> solucion = new HashMap<>();
		for (String nodo : grafo.keySet()) {
			for (String color : colores) {
				boolean disponible = true;
				for (Object vecino : grafo.get(nodo)) {
					String vecinoStr = vecino.toString();
					if (solucion.containsKey(vecinoStr) && solucion.get(vecinoStr).equals(color)) {
						disponible = false;
						break;
					}
				}
				if (disponible) {
					solucion.put(nodo, color);
					break;
				}
			}
		}
		return solucion;
	}

	// Definir estructura de datos para guardar la representación del mapa
	// y la paleta de colores

	// realizarVoraz devuelve un Map<String, String>

	// Recorrer nodos, para cada uno visitar sus vecinos

	// Si algún vecino ya tiene color, ese color no está disponible

	// Una vez identificados los colores de los vecinos, seleccionar el
	// primer color que no esté usado (el menor)

	// Repetir hasta colorear todos los nodos

	// El método keySet() devuelve todas las claves del mapa

	// Bucle: recorrer claves e ir consultando vecinos
}
