import java.util.Scanner;

public class Operaciones_Aritmeticas {

    public static void main(String[] args) {
//        Crear un programa que solicite dos números
//enteros y un tercer parámetro que indique la
//operación aritmética a efectuar, la respuesta debe
//proporcionarse de la siguiente forma:
// “El resultado de la <Operación> es: <Resultado>
//(<Valor1> <Operador> <Valor2)”
        Scanner entrada = new Scanner(System.in);
        int primerNumero = 0, segundoNumero = 0, resultado = 0;
        String operador = "", operacion ="";

        System.out.println("Ingrese el Primer Numero: " );
        primerNumero = entrada.nextInt();
        System.out.println("Ingrese el Segundo Numero: " );
        segundoNumero = entrada.nextInt();

        System.out.println("Ingrese el operador aritmetico (+, -, *, /)");
        operador = entrada.next();

        switch (operador) {
            case "+": resultado = primerNumero + segundoNumero;
            operacion = "Suma";
            break;
            case "-": resultado = primerNumero - segundoNumero;
            operacion = "Resta";
            break;
            case "*": resultado = primerNumero * segundoNumero;
            operacion = "Multiplicacion";
            break;
            case "/": resultado = primerNumero / segundoNumero;
            operacion = "Division";
            break;
            default: System.out.println("No es una opcion valida");
            break;
        }

        System.out.println("El resultado de la " + operacion + " es: " + resultado +
                "\n" + "(" + primerNumero + " " + operador + " " + segundoNumero + ")");
    }
}
