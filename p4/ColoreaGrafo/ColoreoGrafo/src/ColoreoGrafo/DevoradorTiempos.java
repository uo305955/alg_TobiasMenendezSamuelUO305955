package ColoreoGrafo;

import java.io.FileReader;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DevoradorTiempos {

    public static void main(String arg[]) {
        long t1, t2;
        int nVeces = Integer.parseInt(arg[0]);
        JSONParser parser = new JSONParser();
        int[] tamaños = {8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536};

        for (int n : tamaños) {
            try {
                String ruta = "sols/g" + n + ".json";
                FileReader reader = new FileReader(ruta);
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                
                Map<String, List<String>> grafo = (Map<String, List<String>>) jsonObject.get("grafo");
                t1 = System.currentTimeMillis();
                for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++) {
                    ColoreoGrafo.realizarVoraz(grafo);
                }

                t2 = System.currentTimeMillis();
                long tiempo = t2 - t1;
                System.out.println("n=" + n + " **TIEMPO=" + tiempo + "** nVeces=" + nVeces);
            } catch (Exception e) {
                System.out.println("Error con n=" + n);
                e.printStackTrace();
            }
        }
    }
}