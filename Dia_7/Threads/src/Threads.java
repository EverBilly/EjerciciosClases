public class Threads extends Thread {

    private int numero;
    private int secs;

    // Constructor
    public Threads(int numero, int secs) {
        this.numero = numero;
        this.secs = secs;
    }

    public void run() {
        System.out.println("Inicio de Thread");

        for (int a1 = numero; a1 < numero + 20; a1++) {
            System.out.println(a1);
            try {
                Thread.sleep(secs * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Threads a1 = new Threads(100, 1);
        Threads a2 = new Threads(100, 1);

        a1.start();
        a2.start();

        System.out.println("Principal");
    }

}
