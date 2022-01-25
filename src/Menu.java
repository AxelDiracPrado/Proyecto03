import java.util.*;
public class Menu{
    /** public static void main(String[] args) {
        CifradoShamir c= new CifradoShamir("evals","claro.txt");
        Scanner sc = new Scanner(System.in);
        int particiones,minimo,temp=1;
        while(temp!=0){
        System.out.println ("Escoja c para cifrar o d para descifrar");
        char opcion = sc.next().charAt(0);
        if(opcion == 'c' ){
            System.out.print("Introduce el número de particiones: ");
            particiones=sc.nextInt();
            sc.nextLine();
            System.out.print("Introduce el número mínimo  de particiones para descrifrar: ");
            minimo=sc.nextInt();
            sc.nextLine();
            c.cifrarTexto(particiones,minimo);
        }else if(opcion == 'd'){
            CifradoShamir ci = new CifradoShamir("evals.frg", "claro.aes");
            ci.decifrarTexto();
         
            System.out.println("Desea continuar?: 1 Si 0 No");
            temp= sc.nextInt();
            sc.nextLine();
        }
            }
            }**/
    public static void main(String args[]) throws IOException {
        if (args[0].equals("c")) {
            CifradoShamir c= new CifradoShamir(args[1],args[4]+".txt");
            c.cifrarTexto(args[2],args[3]);
        } else if (args[0].equals("d")) {
            CifradoShamir ci = new CifradoShamir(args[1]+".frg",args[4]+ ".aes");
            ci.decifrarTexto();
        }
        else {
            System.out.println("Opción equivocada.");
        }
    }
}
