import java.math.BigInteger;
import java.util.Vector;

public class Lagrange {

	private static BigInteger primo = new BigInteger("208351617316091241234326746312124448251235562226470491514186331217050270460481");
	public static BigInteger lagrangeInterpolacion(BigInteger x, Vector[] valuaciones) {
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


	public static BigInteger numeradorLagrange(BigInteger x, Vector[] valuaciones, int i) {
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

	public static BigInteger denominadorLagrange(Vector[] valuaciones, int i) {
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
}