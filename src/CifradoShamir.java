import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.math.BigInteger;

public class CifradoShamir {
	private String nombreArchivoEvals;
	private String nombreArchivoTexto;

	public CifradoShamir(String nEvals, String textoClaro) {
		this.nombreArchivoEvals = nEvals;
		this.nombreArchivoTexto = textoClaro;
	}

	public String archivoAES() {
		String archivo;
		if(this.nombreArchivoTexto.lastIndexOf('.') == -1){
			archivo = this.nombreArchivoTexto + ".aes";
		}
	    else {
	    	archivo = this.nombreArchivoTexto.substring(0,this.nombreArchivoTexto.lastIndexOf('.'))+".aes";
	    }
	    return archivo;
	}

	public void cifradoAES(BigInteger llave) {
		byte[] k = llave.toByteArray();
		String archivo = this.archivoAES();

		try {
			Cipher cipher = Cipher.getInstance("AES");
			FileOutputStream textoCifrado = new FileOutputStream(archivo,true);
			SecretKeySpec kSec = new SecretKeySpec(k,"AES");
			cipher.init(Cipher.ENCRYPT_MODE,kSec);
			CipherInputStream cipherInput = new CipherInputStream(new FileInputStream(this.nombreArchivoTexto),cipher);
			int escribir = cipherInput.read();
			while(escribir != -1) {
				textoCifrado.write(escribir);
				escribir = cipherInput.read();
			}
			textoCifrado.close();
			cipherInput.close();
			System.out.println("El texto cifrado es guardo como: " + archivo);
		} catch(Exception e) {
			System.err.println(e);
	    	System.exit(1);
		}
	}

	public void cifrarTexto(int n, int t) {
		char[] contraseña = LlaveCifrado.obtenerContraseña(); 
		BigInteger k = new BigInteger(LlaveCifrado.hashContraseña(contraseña));
		PolinomioShamir polinomio = new PolinomioShamir(t-1,k);
		polinomio.archivoEvaluaciones(n,this.nombreArchivoEvals);
		this.cifradoAES(k);
	}

}