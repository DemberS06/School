public class DroneVigilancia extends Drone {
    private int resolucionCamara; // 1080

    public DroneVigilancia(String id, String modelo, int resolucionCamara) {
        super(id, modelo);
        this.resolucionCamara = resolucionCamara;
    }

    @Override
    public void ejecutarMision() {
        if (bateria > 20.0) {
            despegar();
            moverseA(Math.random()*100, Math.random()*100);
            // consumo constante
            double consumo = 8.0;
            bateria -= consumo;
            if (bateria < 0) bateria = 0;
            System.out.println(id + " patrulla. Resolución=" + resolucionCamara + ". Batería ahora: " + String.format("%.2f", bateria));
            aterrizar();
        } else {
            System.out.println(id + " batería baja (" + String.format("%.2f", bateria) + "%). No ejecuta misión.");
        }
    }
}