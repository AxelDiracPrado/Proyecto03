import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import java.math.BigInteger;



public class LlaveCifrado {

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

	public static LinkedList<Vector<BigInteger>> obtenerEvaluaciones(String archivoE) {
		LinkedList<Vector<BigInteger>> listaEvaluaciones = new LinkedList<>();
		try {
        	String parEvals;
	        Vector<BigInteger> vectorEvals = new Vector<>(2);

            File evaluciones = new File(archivoE);
            FileReader evasFR = new FileReader(evaluciones);
            BufferedReader br = new BufferedReader(evasFR);
            while((parEvals=br.readLine()) != null) {
            	String[] evals= parEvals.split(","); 
            	vectorEvals.add(0, new BigInteger(evals[0]));
            	vectorEvals.add(1, new BigInteger(evals[1]));
                boolean agrgar = listaEvaluaciones.add(vectorEvals);
            }
            br.close();
		} catch(Exception e) {
			System.err.println(e);
            System.exit(1);
		}
		return listaEvaluaciones;
	}


	
}