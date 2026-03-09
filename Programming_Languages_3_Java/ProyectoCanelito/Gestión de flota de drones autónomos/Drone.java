public abstract class Drone implements Volable {
    protected String id;
    protected double bateria; // 0-100
    protected String modelo;

    public Drone(String id, String modelo) {
        this.id = id;
        this.modelo = modelo;
        this.bateria = 100.0;
    }

    public abstract void ejecutarMision();

    public void cargarBateria() {
        bateria = 100.0;
        System.out.println(id + " carga batería al 100%");
    }

    @Override
    public void despegar() {
        System.out.println(id + " despega.");
    }

    @Override
    public void aterrizar() {
        System.out.println(id + " aterriza.");
    }

    @Override
    public void moverseA(double x, double y) {
        System.out.println(id + " se mueve a (" + x + ", " + y + ").");
    }

    public double getBateria() {
        return bateria;
    }

    public String getId() {
        return id;
    }
}