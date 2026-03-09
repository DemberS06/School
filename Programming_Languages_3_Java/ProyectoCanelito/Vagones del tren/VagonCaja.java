public class VagonCaja extends Vagon {
    private double h;
    private double w;

    public VagonCaja(double l, double h, double w) {
        super(l);
        this.h = h;
        this.w = w;
    }

    @Override
    public double volumen() {
        return h * w * l;
    }

    @Override
    public String toString() {
        return "VagonCaja(l=" + l + ", a=" + h + ", an=" + w + ", vol=" + volumen() + ")";
    }
}