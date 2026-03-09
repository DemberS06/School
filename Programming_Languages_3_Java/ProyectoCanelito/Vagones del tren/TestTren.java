public class TestTren {
    public static void main(String[] args) {
        Tren tren = new Tren(5); 
        tren.anadirVagon(new VagonCaja(10, 2.5, 3.0)); // longitud, alto, ancho
        tren.anadirVagon(new VagonTanque(8, 1.2)); // longitud, radio
        tren.anadirVagon(new VagonCaja(12, 3.0, 2.5));
        System.out.println("Vagones del tren:");
        tren.mostrarVagones();
        System.out.printf("Volumen total del tren: %.3f m3%n", tren.volumenTotal());
    }
}