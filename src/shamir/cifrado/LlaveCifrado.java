package shamir.cifrado;

import shamir.utilidades.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import java.math.BigInteger;


/**
 * Clase que permite manejar el password dado por el usuario y la llave de cifrado 
 * para el cifrado AES.
 */
public class LlaveCifrado {
	/**
	 * El número primo más pequeño que se puede expresar como 
	 * la suma de las primeras once potencias primas de un número más uno.
	 */
	private static BigInteger primo = new BigInteger("208351617316091241234326746312124448251235562226470491514186331217050270460481");


	/**
	 * Método que pide el password al usuario.
	 * @return arreglo de caracteres del password.
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
	public static String hashContraseña(char[] password) {
		String hashContraseña = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			String passwordStr = new String(password);
			md.update(passwordStr.getBytes());
			hashContraseña = bytesToHex(md.digest());
			Arrays.fill(password, ' ');
		} catch(Exception e) {
			System.err.println(e);
	    	System.exit(1);
		}
		return hashContraseña;
	}

	/**
	 * Método que recupera las evaluaciones de una archivo con parejas (x,P(X)).
	 * Obtiene un arreglo de vectores del tipo {x,P(x)}
	 * @param archivoE cadena que representa el nombre del archivo donde estan las evaluaciones.
	 * @return arreglo con todas las parejas x,P(x)
	 */
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

    /**
     * Método que tranforma una lista ligada de vectores en un arreglo de vectores.
     * @param lista lista ligada de vectores.
     * @return arreglo de vectores.
     */
    public static Vector[] listaAVectorArray(LinkedList<Vector> lista) {
    	Vector[] array = new Vector[lista.size()];
    	for(int i = 0; i < array.length; i++){
			array[i] = (Vector)lista.get(i);
		}
		return array;
    }

    /**
     * Método que recupera la llave de cifrado utilizando el archivo de evaluaciones y 
     * evaluando el polinomio construido con la interpolación de Lagrange en cero.
     * @param archivoE nombre del archivo de evaluaciones.
     * @return arreglo de bytes que representan a la llave de cifrado.
     */
    public static byte[] getLlave(String archivoE) {
    	Vector[] array = vectorEvaluaciones(archivoE);
    	return Lagrange.lagrangeInterpolacion(new BigInteger("0"),array).toByteArray();
    }
	

	 /** 
    *Metodo qe convierte un arreglo de bytes a cadena.
    *@param bytes -- arreglo de bytes.
    *@return String con el texto.
    */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            return result.toString();
    }

}