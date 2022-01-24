import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import java.math.BigInteger;



public class LlaveCifrado {
		private static BigInteger primo = new BigInteger("208351617316091241234326746312124448251235562226470491514186331217050270460481");


	/**
	 * Método que pide el password al usuario.
	 * @return cadena con el password.
	 */
	public static char[] obtenerContraseña() {
		Console console = System.console();
		char[] contraseña =console.readPassword("Password: ");
		return contraseña;
	}

	/**
	 * Método que aplica el hashing SHA-256 en una contraseña.
	 * @param password la contraseña del usuario.
	 * @return cadena de bytes que representan la llave de cifrado.
	 */
	public static byte[] hashContraseña(char[] password) {
		byte[] hashContraseña = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			String passwordStr = new String(password);
			hashContraseña = md.digest(passwordStr.getBytes());
			Arrays.fill(password, ' ');
		} catch(Exception e) {
			System.err.println(e);
	    	System.exit(1);
		}
		return hashContraseña;
	}

	public static Vector[] vectorEvaluaciones(String archivoE) {
		Vector[] array = null;
		LinkedList<Vector> lista = new LinkedList<Vector>();
       	try {
            String parEvals;
            FileReader filer = new FileReader(archivoE);
            BufferedReader br = new BufferedReader(filer);
            while((parEvals=br.readLine()) != null) {
            	Vector punto = new Vector(2);
            	String[] valuacion = parEvals.split(",");
            	punto.add(0,new BigInteger(valuacion[0]));
            	punto.add(1,new BigInteger(valuacion[1]));
				lista.add(punto);
            }
	    	br.close();
	    	array = listaAVectorArray(lista);
	    	return array;
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
		}
		return null;
    }

    public static Vector[] listaAVectorArray(LinkedList<Vector> lista) {
    	Vector[] array = new Vector[lista.size()];
    	for(int i = 0; i < array.length; i++){
			array[i] = (Vector)lista.get(i);
		}
		return array;
    }

    public static byte[] getLlave(String archivoE) {
    	Vector[] array = vectorEvaluaciones(archivoE);
    	return Lagrange.lagrangeInterpolacion(new BigInteger("0"),array).toByteArray();
    }

	public static void main(String[] args) {
		char[] contraseña = LlaveCifrado.obtenerContraseña(); 
		BigInteger k = new BigInteger(LlaveCifrado.hashContraseña(contraseña));
		System.out.println(k.mod(primo));
		PolinomioShamir poli = new PolinomioShamir(2,k);
		poli.archivoEvaluaciones(5,"evals");
		
		k = new BigInteger(getLlave("evals.frg"));
		System.out.println(k.mod(primo));
		
	}

	
}