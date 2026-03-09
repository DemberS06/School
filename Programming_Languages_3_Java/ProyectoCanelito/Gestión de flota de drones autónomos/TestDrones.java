public class TestDrones {
    public static void main(String[] args) {
        TorreControl torre = new TorreControl();
        
        DroneMensajeria dm1 = new DroneMensajeria("DM1", "ModeloA", 5.0);
        DroneMensajeria dm2 = new DroneMensajeria("DM2", "ModeloB", 12.0);
        DroneVigilancia dv1 = new DroneVigilancia("DV1", "VigA", 1080);
        DroneVigilancia dv2 = new DroneVigilancia("DV2", "VigB", 720);

        torre.agregarDrone(dm1);
        torre.agregarDrone(dm2);
        torre.agregarDrone(dv1);
        torre.agregarDrone(dv2);

        torre.iniciarOperaciones();
    }
}