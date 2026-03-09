class Guerrero extends Personaje {
    private Arma arma;
    private String tipo;

    public Guerrero(String nombre, Arma arma, int energiaInicial) {
        super(nombre, energiaInicial);
        this.arma = arma;
        if (arma instanceof ArmaDeFuego) tipo = "Pistolero";
        else tipo = "Espadachín";
    }

    public String combatir(int energiaAtaque) {
        if (energia < energiaAtaque) {
            return nombre + " no tiene energía suficiente para atacar (tiene " + energia + ").";
        }
        energia -= energiaAtaque;
        StringBuilder sb = new StringBuilder();
        sb.append("Atacó con ").append(arma.getNombre()).append(", gasto ").append(energiaAtaque)
          .append(", energía restante ").append(energia).append(". ");

        if (arma instanceof ArmaDeFuego) {
            ArmaDeFuego af = (ArmaDeFuego) arma;
            boolean disparo = af.disparar();
            if (disparo) {
                sb.append("Balas restantes: ").append(af.getCartucho()).append(".");
                if (af.getCartucho() == 0) {
                    sb.append(" --> Se quedó sin balas. Recargando...");
                    af.recargar();
                    sb.append(" Ahora balas: ").append(af.getCartucho()).append(".");
                }
            } else {
                sb.append("No tenía balas. Recargando...");
                af.recargar();
                sb.append(" Balas: ").append(af.getCartucho()).append(".");
            }
        } else if (arma instanceof ArmaBlanca) {
            ArmaBlanca ab = (ArmaBlanca) arma;
            ab.desgastar();
            sb.append("Filo restante: ").append(String.format("%.2f", ab.getFilo())).append("%.");
            if (ab.getFilo() < 30.0) {
                sb.append(" ADVERTENCIA: filo bajo <30%.");
            }
        }

        sb.append(" Tipo: ").append(tipo).append(".");
        return sb.toString();
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ") energia=" + energia;
    }
}
