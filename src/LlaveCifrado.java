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

	public byte[] getK(String archivoE) {
		Vector[] array = null;
		byte[] array1 = null;
		LinkedList<Vector> list = new LinkedList<Vector>();
       	try {
            String strLine;
            FileInputStream fstream = new FileInputStream(archivoE);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    System.out.println("Reading "+evalDecrypt+"...");
            while((strLine=br.readLine()) != null) {
		list.add(new Vector(2));
		((Vector)list.getLast()).add(0,new BigInteger(strLine.substring(0,strLine.indexOf(','))));
		((Vector)list.getLast()).add(1,new BigInteger(strLine.substring(strLine.indexOf(',')+1,strLine.length())));
            }
	    in.close();
	    array = new Vector[list.size()];
	    for(int i = 0; i < array.length; i++)
		array[i] = (Vector)list.get(i);
	    System.out.println("Evaluating Lagrange interpolating polynomial...");
	    return zp.lagrange(new BigInteger("0"),array).toByteArray();
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
	}
	return null;
    }

	public static void main(String[] args) {
		char[] contraseña = LlaveCifrado.obtenerContraseña(); 
		BigInteger k = new BigInteger(LlaveCifrado.hashContraseña(contraseña));
		System.out.println(k.mod(primo));
		Vector[] evaluaciones = PolinomioShamir.evaluacionesPolinomio(k,3,5);
		PolinomioShamir.archivoEvaluaciones(evaluaciones, "evals");
		
		k = getLlave("evals.frg");
		
	}

	
}