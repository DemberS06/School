interface PoderCurativo {
    void curar();
}

class Mago extends Personaje implements PoderCurativo {
    private Poder poder;
    private int contadorAtaquesQuePuedeCurar;

    public Mago(String nombre, Poder poder) {
        super(nombre, 100); // siempre inicia con 100
        this.poder = poder;
        this.contadorAtaquesQuePuedeCurar = 0;
    }

    public String encantar() {
        if (energia >= 2) {
            energia -= 2;
            contadorAtaquesQuePuedeCurar++;
            return nombre + " usa " + poder.getNombre() + ", energia restante " + energia;
        } else {
            return nombre + " no tiene energía para encantar.";
        }
    }

    // se puede curar solo después de cada 3 ataques
    @Override
    public void curar() {
        if (contadorAtaquesQuePuedeCurar >= 3) {
            int antes = energia;
            energia += 3;
            if (energia > energiaInicial) energia = energiaInicial;
            contadorAtaquesQuePuedeCurar = 0; // reset después de curar
            System.out.println(nombre + " se cura. energía " + antes + " -> " + energia);
        } else {
            System.out.println(nombre + " no puede curar aún (falta usar encantar 3 veces).");
        }
    }

    @Override
    public String toString() {
        return nombre + " (Mago, poder=" + poder.getNombre() + ", energia=" + energia + ")";
    }
}
