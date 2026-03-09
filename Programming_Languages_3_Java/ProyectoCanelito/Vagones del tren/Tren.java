import java.util.ArrayList;

public class Tren {
    private ArrayList<Vagon> vagones;
    private int limite;

    public Tren(int limite) {
        this.limite = limite;
        this.vagones = new ArrayList<>();
    }

    public boolean anadirVagon(Vagon v) {
        if (vagones.size() < limite) {
            vagones.add(v);
            return true;
        } else {
            return false;
        }
    }

    public double volumenTotal() {
        double total = 0.0;
        for (Vagon v : vagones) {
            total += v.volumen();
        }
        return total;
    }

    public void mostrarVagones() {
        for (Vagon v : vagones) {
            System.out.println(v);
        }
    }
}