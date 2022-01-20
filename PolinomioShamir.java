import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;


public class PolinomioShamir {
	private BigInteger[] coeficientes;
	private int grado;
	private BigInteger primo = new BigInteger("208351617316091241234326746312124448251235562226470491514186331217050270460481");

	/**
	 * Método constructor que genera el polinomio donde se escondera la llave de cifrado.
	 * @param grado grado del polinomio.
	 * @param llave de cifrado.
	 */
	public PolinomioShamir(int grado, String llave) {
		BigInteger k = new BigInteger(llave);
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


	public BigInteger lagrangeInterpolacion(BigInteger x, Vector[] valuaciones) {
		BigInteger k = new BigInteger("0");
		for(int i = 0; i < valuaciones.length; i++) {
			BigInteger numerador = numeradorLagrange(x,valuaciones,i);
			BigInteger denominador = denominadorLagrange(valuaciones,i);
			BigInteger cociente = numerador.multiply(denominador.modInverse(primo)).mod(primo);
			BigInteger coef = (BigInteger) valuaciones[i].elementAt(1);
			k = k.add(coef.multiply(cociente).mod(primo)).mod(primo);
		}
		return k;
	}


	public BigInteger numeradorLagrange(BigInteger x, Vector[] valuaciones, int i) {
		BigInteger numerador = new BigInteger("1");
		for(int j = 0; j < valuaciones.length; j++){
			if(j != i) {
				BigInteger x_i = (BigInteger) valuaciones[i].elementAt(0);
				BigInteger x_j = (BigInteger) valuaciones[j].elementAt(0);
				numerador = numerador.multiply(x.subtract(x_j).mod(primo)).mod(primo);
			}
		}
		return numerador;
	}

	public BigInteger denominadorLagrange(Vector[] valuaciones, int i) {
		BigInteger denominador = new BigInteger("1");
		for(int j = 0; j < valuaciones.length; j++){
			if(j != i) {
				BigInteger x_i = (BigInteger) valuaciones[i].elementAt(0);
				BigInteger x_j = (BigInteger) valuaciones[j].elementAt(0);
				denominador = denominador.multiply(x_i.subtract(x_j).mod(primo)).mod(primo);
			}
		}
		return denominador;
	}

	public static void main(String[] args) {
		String k = "123456789123456789";
		PolinomioShamir p = new PolinomioShamir(2,k);
		System.out.println(p.evaluarNValores(10));

	}
}