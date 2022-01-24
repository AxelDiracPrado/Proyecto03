import java.math.BigInteger;
import java.util.Random;
import java.util.Vector;
import java.io.*;


public class PolinomioShamir {
	
	private static BigInteger primo = new BigInteger("208351617316091241234326746312124448251235562226470491514186331217050270460481");

	/**
	 * Método  que genera el polinomio donde se escondera la llave de cifrado.
	 * @param grado grado del polinomio.
	 * @param llave de cifrado.
	 */
	public static BigInteger[] construirPolinomio(int t, BigInteger llave) {
		BigInteger[] coeficientes = new BigInteger[t];
		coeficientes[0] = llave;
		for(int i = 1; i < coeficientes.length; i++) {
			BigInteger coeficient = new BigInteger(primo.bitLength(),new Random());
			while(coeficient.compareTo(primo) >= 0 || coeficient.compareTo(BigInteger.ZERO)==0) {
				coeficient = new BigInteger(primo.bitLength(),new Random());
			}
			coeficientes[i] = coeficient;
		}
		return coeficientes;
	}

	/**
	 * Método que evalua un número en el polinomio.
	 * @param x BigInteger que será evaluado en el polinomio.
	 * @return evaluación del valor en el polinomio.
	 */
	public static BigInteger evaluarValor(BigInteger x, BigInteger[] polinomio) {
		BigInteger value = new BigInteger("0");
		for(int i = 0; i < polinomio.length; i++) {
			BigInteger coef = polinomio[i];
			value = value.add(coef.multiply(x.modPow(BigInteger.valueOf((long) i), primo)).mod(primo)).mod(primo);
		}
		return value;
	}

	

	/**
	 * Método que obtiene una lista de n evaluaciones.
	 * @param n número de evaluaciones
	 * @return lista de n parejas con el valor a evaluar y su evaluación.
	 */
	public static Vector[] evaluarNValores(int n, BigInteger[] polinomio) {
		Vector[] evaluaciones = new Vector[n];
		for(int i = 0; i < n; i++) {
			BigInteger x = new BigInteger(primo.bitLength(),new Random()); 
			while(x.compareTo(primo) >= 0 || x.compareTo(BigInteger.ZERO)==0) {
				x = new BigInteger(primo.bitLength(),new Random());
			}
			evaluaciones[i] = new Vector(2);
			evaluaciones[i].add(0,x);
			BigInteger val = evaluarValor(x, polinomio);
			evaluaciones[i].add(1,val);
		}
		return evaluaciones;
	}

	public static Vector[] evaluacionesPolinomio(BigInteger k, int t, int n){
		return evaluarNValores(n, construirPolinomio(t,k.mod(primo)));
	}

	public static void archivoEvaluaciones(Vector[] evaluaciones, String archivoE) {
		Writer wr;
		try {
			wr = new FileWriter(archivoE + ".frg", false);
			for(int i = 0; i < evaluaciones.length; i++){
				wr.write(evaluaciones[i].elementAt(0).toString()+","+evaluaciones[i].elementAt(1).toString()+"\n");
			}
			wr.close();
			System.out.println("Las evaluaciones se guardaron en: " + archivoE + ".frg");
		} catch(Exception e) {
			System.err.println(e);
	    	System.exit(1);
		}
	}
}