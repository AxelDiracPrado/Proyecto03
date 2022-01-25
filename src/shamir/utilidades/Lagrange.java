package shamir.utilidades;

import java.math.BigInteger;
import java.util.*;

/**
 * Clase que implementa el algoritmo para construir un polinomio mediante
 * el método de interpolación de lagrange.
 * Los coeficientes del polinomio se encuentran en el campo finito Zp con p primo.
 */
public class Lagrange {

	/**
	 * El número primo más pequeño que se puede expresar como 
	 * la suma de las primeras once potencias primas de un número más uno.
	 */
	private static BigInteger primo = new BigInteger("208351617316091241234326746312124448251235562226470491514186331217050270460481");
	
	/**
	 * Método que realiza la construcción del polinomio dados t puntos usando el algortimo de
	 * interpolación de Lagrange.
	 * @param x Valor que será evaluado en el polinomio construido por interpolación de lagrange.
	 * @param valuaciones es el arreglo de puntos que permiten construir el polinomio.
	 * @return evaluación del valor x en el polinomio.
	 */
	public static BigInteger lagrangeInterpolacion(BigInteger x, Vector[] valuaciones) {
		BigInteger k = new BigInteger("0");
		BigInteger denominador = new BigInteger("1");
		BigInteger numerador = new BigInteger("1");
		BigInteger x_j = null, x_i = null, cociente = null;
		for(int i = 0; i < valuaciones.length; i++) {
			numerador = numeradorLagrange(x, valuaciones,i);
	   		denominador = denominadorLagrange(valuaciones,i);
			cociente = numerador.multiply(denominador.modInverse(primo)).mod(primo);
			k = k.add(((BigInteger)valuaciones[i].elementAt(1)).multiply(cociente).mod(primo)).mod(primo);
		}
		return k;
	}

	/**
	 * Método que realiza la construcción del numerador del termino Li en el algoritmo de Lagrange.
	 * @param x Valor que será evaluado en el polinomio construido por interpolación de lagrange.
	 * @param valuaciones es el arreglo de puntos que permiten construir el polinomio.
	 * @param i indice del termino Li de la interpolación de Lagramge.
	 * @return evaluación del valor x en el numerador del termino Li.
	 */
	public static BigInteger numeradorLagrange(BigInteger x, Vector[] valuaciones, int i) {
		BigInteger numerador = new BigInteger("1");
		for(int j = 0; j < valuaciones.length; j++){
			if(j != i) {
				BigInteger x_j = (BigInteger) valuaciones[j].elementAt(0);
				numerador = numerador.multiply(x.subtract(x_j).mod(primo)).mod(primo);
			}
		}
		return numerador;
	}

	/**
	 * Método que realiza la construcción del denominador del termino Li en el algoritmo de Lagrange.
	 * @param valuaciones es el arreglo de puntos que permiten construir el polinomio.
	 * @param i indice del termino Li de la interpolación de Lagramge.
	 * @return evaluación del valor x en el denominador del termino Li.
	 */
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