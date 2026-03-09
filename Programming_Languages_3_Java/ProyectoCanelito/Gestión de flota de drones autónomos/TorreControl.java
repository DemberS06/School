import java.util.ArrayList;

public class TorreControl {
    private ArrayList<Drone> flota;

    public TorreControl() {
        flota = new ArrayList<>();
    }

    public void agregarDrone(Drone d) {
        flota.add(d);
        System.out.println("Agregado drone " + d.getId());
    }

    public void iniciarOperaciones() {
        boolean seguir = true;
        while (seguir) {
            for (Drone d : flota) {
                d.ejecutarMision();
                if (d.getBateria() <= 0.0) {
                    System.out.println("ALERTA: " + d.getId() + " se quedó sin batería. Deteniendo operaciones.");
                    seguir = false;
                    break;
                }
            }
            // pequeña pausa
            try { Thread.sleep(400); } catch (InterruptedException e) { }
            System.out.println("Ciclo terminado. Comprobando baterías...");
        }
    }
}