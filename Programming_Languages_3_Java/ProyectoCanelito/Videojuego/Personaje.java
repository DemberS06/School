abstract class Personaje {
    protected String nombre;
    protected int energia; // nivel actual
    protected int energiaInicial;

    public Personaje(String nombre, int energiaInicial) {
        this.nombre = nombre;
        this.energiaInicial = energiaInicial;
        this.energia = energiaInicial;
    }

    public void alimentarse(int cantidad) {
        int nueva = energia + cantidad;
        if (nueva > energiaInicial) {
            System.out.println(nombre + ": no es posible aumentar la energía por encima de " + energiaInicial);
            energia = energiaInicial;
        } else {
            energia = nueva;
            System.out.println(nombre + " se alimenta +" + cantidad + " -> energía " + energia);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getEnergia() {
        return energia;
    }

    @Override
    public String toString() {
        return nombre + " (energia=" + energia + ")";
    }
}