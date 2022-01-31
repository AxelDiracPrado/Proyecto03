package shamir;

import java.util.*;
import 	java.io.*;
import shamir.cifrado.CifradoShamir;

/**
 * Clase principal que ejecuta el cifrado.
 */
public class Main{
    
    public static void main(String args[]) throws IOException {
        if (args[0].equals("c")) {
            if (args.length == 5) {
                CifradoShamir c= new CifradoShamir(args[1],args[4]);
                if(Integer.parseInt(args[2])>2)
                c.cifrarTexto(Integer.parseInt(args[2]),Integer.parseInt(args[3]));
                else    System.out.println("Número pequeño de particiones.");
            } else {
                System.out.println("Número incorrecto de argumentos.");
            }
            
        } else if (args[0].equals("d")) {
            if (args.length == 3) {
                CifradoShamir ci = new CifradoShamir(args[1],args[2]);
                ci.decifrarTexto();
            } else {
                System.out.println("Número incorrecto de argumentos.");
            }
        }
        else {
            System.out.println("Opción equivocada.");
        }
    }


}
