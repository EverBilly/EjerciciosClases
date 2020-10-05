public class ThreadInter implements Runnable {

    Thread hilo;

    private String nombre;
    private int numero;
    private int secs;

    public ThreadInter(String nombre, int numero, int secs) {
        this.nombre = nombre;
        this.numero = numero;
        this.secs = secs;
    }

    public void run() {
        System.out.println("Hilo " + nombre + " ejecutandose");

        for (int a1 = numero; a1 < numero+20; a1++) {
            System.out.println(a1);
            try {
                Thread.sleep(secs * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        hilo.start();
    }

    public static void main(String args[]) {
        ThreadInter b1 = new ThreadInter("b1 ", 100, 1);
        ThreadInter b2 = new ThreadInter("b2 ", 100, 1);

        b1.start();
        b2.start();

        System.out.println("Principal");
    }
}
