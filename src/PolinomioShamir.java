import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import java.io.*;


public class PolinomioShamir {
	private BigInteger[] coeficientes;
	private int grado;
	private BigInteger primo = new BigInteger("208351617316091241234326746312124448251235562226470491514186331217050270460481");

	/**
	 * Método constructor que genera el polinomio donde se escondera la llave de cifrado.
	 * @param grado grado del polinomio.
	 * @param llave de cifrado.
	 */
	public PolinomioShamir(int grado) {
		char[] contraseña = LlaveCifrado.obtenerContraseña(); 
		BigInteger k = new BigInteger(LlaveCifrado.hashContraseña(contraseña));
		this.coeficientes = new BigInteger[grado + 1];
		this.grado = grado;
		this.coeficientes[0] = k;
		for(int i = 1; i <= grado; i++) {
			BigInteger coeficient = new BigInteger(primo.bitLength(),new SecureRandom());
			while(coeficient.compareTo(primo) >= 0 || coeficient.compareTo(BigInteger.ZERO)==0) {
				coeficient = new BigInteger(primo.bitLength(),new SecureRandom());
			}
			this.coeficientes[i] = coeficient;
		}
	}

	/**
	 * Método que evalua un número en el polinomio.
	 * @param x BigInteger que será evaluado en el polinomio.
	 * @return evaluación del valor en el polinomio.
	 */
	public BigInteger evaluarValor(BigInteger x) {
		BigInteger value = new BigInteger("0");
		for(int i = 0; i <= this.grado; i++) {
			BigInteger coef = this.coeficientes[i];
			value = value.add(coef.multiply(x.modPow(BigInteger.valueOf((long) i), primo)).mod(primo)).mod(primo);
		}
		return value;
	}

	/**
	 * Método que obtiene la apreja (x,P(x)) en forma de cadena.
	 * @param x valor a evaluar en el polinomio.
	 * @return pareja x,P(x)
	 */
	public String parajeEvaluacion(BigInteger x) {
		return x.toString() + "," + evaluarValor(x).toString() + "\n";
	}

	/**
	 * Método que obtiene una lista en forma de cadena de n evaluaciones
	 * del polinómio.
	 * @param n número de evaluaciones
	 * @return lista de n parejas con el valor a evaluar y su evaluación.
	 */
	public String evaluarNValores(int n) {
		String lista = "";
		for(int i = 0; i < n; i++) {
			BigInteger x = new BigInteger(primo.bitLength(),new SecureRandom()); 
			while(x.compareTo(primo) >= 0 || x.compareTo(BigInteger.ZERO)==0) {
				x = new BigInteger(primo.bitLength(),new SecureRandom());
			}
			lista += parajeEvaluacion(x);
		}
		return lista;
	}

	public void archivoEvaluaciones(int n, String archivoE) {
		String evaluaciones = this.evaluarNValores(n);
		Writer wr;
		try {
			wr = new FileWriter(archivoE + ".frg", true);
			wr.write(evaluaciones);
			wr.close();
			System.out.println("Las evaluaciones se guardaron en: " + archivoE + ".frg");
		} catch(Exception e) {
			System.err.println(e);
	    	System.exit(1);
		}
	}

	

	public static void main(String[] args) {
		PolinomioShamir p = new PolinomioShamir(3);
		p.archivoEvaluaciones(5,"evas");

	}
}