
abstract class Poder {
    protected String nombre;
    public Poder(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
}

class PoderLuz1 extends Poder { public PoderLuz1() { super("Luz Curativa"); } }
class PoderLuz2 extends Poder { public PoderLuz2() { super("Llama Purificadora"); } }
class PoderLuz3 extends Poder { public PoderLuz3() { super("Escudo de Luz"); } }

class PoderOscuro1 extends Poder { public PoderOscuro1() { super("Sombra Voraz"); } }
class PoderOscuro2 extends Poder { public PoderOscuro2() { super("Toque Helado"); } }
class PoderOscuro3 extends Poder { public PoderOscuro3() { super("Corriente Negra"); } }
