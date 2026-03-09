public abstract class Vagon {
    protected double l;

    public Vagon(double l) {
        this.l = l;
    }

    public double getLongitud() {
        return l;
    }

    public abstract double volumen();
}