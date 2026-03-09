abstract class Arma {
    protected String nombre;
    public Arma(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
}

// Armas de fuego
abstract class ArmaDeFuego extends Arma {
    protected int capacidad; // capacidad máxima
    protected int cartucho;  // balas actuales

    public ArmaDeFuego(String nombre, int capacidad) {
        super(nombre);
        this.capacidad = capacidad;
        this.cartucho = capacidad;
    }

    public boolean disparar() {
        if (cartucho > 0) {
            cartucho--;
            return true;
        } else {
            return false;
        }
    }

    public void recargar() {
        cartucho = capacidad;
    }

    public int getCartucho() {
        return cartucho;
    }
}

class Pistola extends ArmaDeFuego {
    public Pistola() { super("Pistola", 6); }
}

class Ametralladora extends ArmaDeFuego {
    public Ametralladora() { super("Ametralladora", 30); }
}

class Revolver extends ArmaDeFuego {
    public Revolver() { super("Revolver", 8); }
}

// Armas blancas
abstract class ArmaBlanca extends Arma {
    protected double filo;
    protected final double porcentajeDeDesgaste;

    public ArmaBlanca(String nombre, double porcentajeDeDesgaste) {
        super(nombre);
        this.porcentajeDeDesgaste = porcentajeDeDesgaste;
        this.filo = 100.0;
    }

    public void desgastar() {
        filo = filo - (filo * (porcentajeDeDesgaste / 100.0));
        if (filo < 0) filo = 0;
    }

    public double getFilo() {
        return filo;
    }
}

class Daga extends ArmaBlanca {
    public Daga() { super("Daga", 5.0); } // 5% desgaste
}

class EspadaCorta extends ArmaBlanca {
    public EspadaCorta() { super("EspadaCorta", 3.0); }
}

class EspadaLarga extends ArmaBlanca {
    public EspadaLarga() { super("EspadaLarga", 2.0); }
}
