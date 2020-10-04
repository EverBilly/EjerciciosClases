import java.util.Scanner;

public class Mayor_Tres_Numeros {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        int numeros[] = new int[3];
        int numeroMayor = numeros[0];

        for (int i = 0; i < numeros.length; i++) {
            System.out.println("Ingrese un numero: ");
            numeros[i] = entrada.nextInt();
        }

        for (int i = 0; i < numeros.length; i++) {
            int numeroActual = numeros[i];

            if(numeroActual > numeroMayor) {
                numeroMayor = numeroActual;
            }
        }

        System.out.print("El numero mayor Es: " + numeroMayor + "\n");
    }
}
