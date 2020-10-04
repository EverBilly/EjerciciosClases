import java.util.Calendar;
import java.util.TimeZone;

public class Main {
    public static void main(String args[]) {

        //siguiente forma: “Guatemala, 14 de Junio de 2012”.
        MostrarFecha();
        MostrarAbecedario();
    }

    public static void MostrarFecha() {

        String dia[] = { "Lunes", "Martes", "Miercoles",
                "Jueves", "Viernes", "Sabado", "Domingo" };

        String mes[] = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        Calendar fechaDefinida = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("America/Guatemala");

        String cadenaPais = timeZone.toZoneId().toString();
//        cadenaPais.substring(8)

        int definirAnio = 2012;
        String definirMes = String.valueOf(mes[5]);
        int definirMes2 = 5;
        int definirDia = 14;

        //Metodos Set
        fechaDefinida.set(Calendar.YEAR, definirAnio);
        fechaDefinida.set(Calendar.MONTH, definirMes2);
        fechaDefinida.set(Calendar.DAY_OF_MONTH, definirDia);

        //Metodos Get
        int anio = fechaDefinida.get(Calendar.YEAR);
        int mes1 = fechaDefinida.get(Calendar.MONTH);
        int dia1 = fechaDefinida.get(Calendar.DAY_OF_MONTH);

        System.out.print(cadenaPais.substring(8) + ", "+ dia1 + " de " + definirMes + " de " + anio +"\n\n");
        System.out.print(cadenaPais.substring(8) + ", "+ dia1 + " de " + mes1 + " de " + anio +"\n\n");

//        fechaDefinida.set(2012, 5, 14);
//        System.out.println(fechaDefinida.getTime());
        /*
        // Instantiate a Date object
        Date date = new Date();
        // display time and date
        System.out.printf("\n"+"%1$s %2$td %2$tB de %2$tY", "Due date:", date);
        */
    }

    public static void MostrarAbecedario() {
        char abecedario[] = new char[26];
//        abecedario[0] = 'a';
        System.out.print("Abecedario Invertido: ");

        int i = 0;

        while(i <= 25) {
            abecedario[i] = (char) ('a' + i);
            i++;
        }

        for(int j = 25; j >= 0; j--) {
            System.out.print(abecedario[j] + " ");
        }
    }
}
