public class DroneMensajeria extends Drone {
    private double pesoCarga; // kg

    public DroneMensajeria(String id, String modelo, double pesoCarga) {
        super(id, modelo);
        this.pesoCarga = pesoCarga;
    }

    @Override
    public void ejecutarMision() {
        if (bateria > 20.0) {
            despegar();
            moverseA(Math.random()*100, Math.random()*100);
            // consumo depende de peso
            double consumo = 10.0 + (pesoCarga * 0.5);
            bateria -= consumo;
            if (bateria < 0) bateria = 0;
            System.out.println(id + " entrega paquete. Peso=" + pesoCarga + "kg. Batería ahora: " + String.format("%.2f", bateria));
            aterrizar();
        } else {
            System.out.println(id + " batería baja (" + String.format("%.2f", bateria) + "%). No ejecuta misión.");
        }
    }
}