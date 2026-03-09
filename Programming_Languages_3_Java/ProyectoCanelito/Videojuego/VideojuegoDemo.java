import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class VideojuegoDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();
        ArrayList<Personaje> lista = new ArrayList<>();

        System.out.print("¿Cuántos guerreros? ");
        int nGuerreros = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < nGuerreros; i++) {
            System.out.println("Guerrero " + (i+1));
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.println("Seleccione arma (número): 1) Pistola 2) Ametralladora 3) Revolver 4) Daga 5) EspadaCorta 6) EspadaLarga");
            int opcion = Integer.parseInt(sc.nextLine().trim());
            Arma arma;
            switch (opcion) {
                case 1: arma = new Pistola(); break;
                case 2: arma = new Ametralladora(); break;
                case 3: arma = new Revolver(); break;
                case 4: arma = new Daga(); break;
                case 5: arma = new EspadaCorta(); break;
                case 6: arma = new EspadaLarga(); break;
                default: arma = new Pistola(); break;
            }
            System.out.print("Energía inicial (ej. 100): ");
            int energia = Integer.parseInt(sc.nextLine().trim());
            Guerrero g = new Guerrero(nombre, arma, energia);
            lista.add(g);
        }

        System.out.print("¿Cuántos magos? ");
        int nMagos = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < nMagos; i++) {
            System.out.println("Mago " + (i+1));
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();
            System.out.println("Tipo de poder (1) Luz (2) Oscuro");
            int t = Integer.parseInt(sc.nextLine().trim());
            Poder poder;
            if (t == 1) {
                int p = rnd.nextInt(3);
                if (p == 0) poder = new PoderLuz1();
                else if (p == 1) poder = new PoderLuz2();
                else poder = new PoderLuz3();
            } else {
                int p = rnd.nextInt(3);
                if (p == 0) poder = new PoderOscuro1();
                else if (p == 1) poder = new PoderOscuro2();
                else poder = new PoderOscuro3();
            }
            Mago m = new Mago(nombre, poder);
            lista.add(m);
        }

        System.out.println("\n--- Simulación: 10 ataques por personaje y alimentarse aleatorio al menos 5 veces ---");
        for (Personaje p : lista) {
            System.out.println("\nPersonaje: " + p);
            // 10 iteraciones
            int alimentaciones = 0;
            for (int k = 0; k < 10; k++) {
                // aleatoriamente decidir si alimentarse (aseguramos al menos 5)
                if ((rnd.nextBoolean() && alimentaciones < 5) || (alimentaciones < 5 && k > 4)) {
                    int energiaAdd = rnd.nextInt(15) + 1; // 1..15
                    p.alimentarse(energiaAdd);
                    alimentaciones++;
                }

                if (p instanceof Guerrero) {
                    Guerrero g = (Guerrero) p;
                    int gasto = rnd.nextInt(20) + 1; // 1..20
                    String res = g.combatir(gasto);
                    System.out.println(res);
                } else if                            (p instanceof Mago) {
                    Mago m = (Mago) p;
                    // usan encantar (gasta 2)  
                    System.out.println(m.encantar());
                    // (curar solo si aplicable)
                    if (rnd.nextInt(10) < 3) { // prob 30% de intentar curar
                        m.curar();
                    }
                }
            }
            System.out.println("ToString sobrescrito: " + p.toString());
        }

        // prueba: colección heterogénea permite atacar con cada uno? Explicar:
        System.out.println("\nColección heterogénea: ¿Se puede llamar combatir() a todos?");
        System.out.println("No directamente: combatir() está solo en Guerrero; magos tienen encantar().");
        System.out.println("Por eso usamos polimorfismo: recorremos la colección y usamos instanceof para llamar al método correspondiente.");

        sc.close();
    }
}