import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class AlmacenajeContenedoresRyPTiempos {

    public static void main(String args[]) {
        long t1, t2;
        int nVeces = Integer.parseInt(args[0]);
        String[] ficheros = {
            "test00.txt", "test01.txt", "test02.txt", "test03.txt",
             "test04.txt", "test05.txt", "test06.txt", "test07.txt",
              "test08.txt", "test09.txt"};

        for (String fichero: ficheros) {
            Scanner sc;
            try {
                sc = new Scanner(new FileReader(fichero));
                int c = sc.nextInt();
                sc.nextLine();
                String[] parts = sc.nextLine().split(" ");
                Integer[] toS = new Integer[parts.length];
                int i = 0;
                for(String s: parts){
                    toS[i] = Integer.parseInt(s);
                    i++;
                }
                t1 = System.currentTimeMillis();
                for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++) {
                Integer[] copia = Arrays.copyOf(toS, toS.length);
                new AlmacenajeContenedoresRyP(c, copia).resolverSinSalida();
                }
                t2 = System.currentTimeMillis();
                long tiempo = t2 - t1;
                System.out.println(fichero + " **TIEMPO=" + tiempo + "** nVeces=" + nVeces);
                
            } catch (Exception e) {
                System.out.println("Error con el fichero: " + fichero);
            }
        }
    }
}
