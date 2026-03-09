public class VagonTanque extends Vagon {
    private double r;

    public VagonTanque(double l, double r) {
        super(l);
        this.r = r;
    }

    @Override
    public double volumen() {
        return Math.PI * r * r * l;
    }

    @Override
    public String toString() {
        return "VagonTanque(l=" + l + ", r=" + r + ", vol=" + volumen() + ")";
    }
}