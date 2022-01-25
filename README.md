# Proyecto 03.
Los siguiente comandos se ejecutan desde la carpeta Proyecto02:
	## Compilar:
		### javac -d target/ src/shamir/Main.java src/shamir/utilidades/* src/shamir/cifrado/*
	
	## Ejecutar:
		### java -cp target/ shamir.Main <opcion> arg1 arg2 arg3 arg4
			<opcion>:
				c: Cifrar texto.
					arg1: Ruta y nombre del archivo donde se guardaran las n evaluaciones de polinomio.
					arg2: El número total de evaluaciones requeridas (n > 2).
					arg3: El número mínimo de puntos necesarios para decifrar la llave (1<= t <= n).
					arg4: Ruta y nombre del archivo que contiene el texto claro para decifrar.

				
				d: Decifrar texto.	
					arg1: La ruta y nombre del archivo ( extensión .frg) con al menos t de las n evaluaciones del polinomio.
					arg2: El nombre y ruta del texto cifrado (extensión .aes)
# Proyecto03
