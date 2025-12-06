import java.util.ArrayList;
import java.util.List;

interface ObservadorCafe{
    void notificar(String mensaje);
}

abstract class Cafe{
    protected String descpription;
    protected double precio;

    public String getDescripcion(){
        return descpription;
    }

    public double getPrecio(){
        return precio;
    }

    public abstract void preparar();
}

interface FabricaCafe {
    Cafe crearCafe();
}

class FabricaExpresso implements FabricaCafe {
    @Override
    public Cafe crearCafe() {
        return new Expresso();
    }
}

class FabricaCapuchino implements FabricaCafe {
    @Override
    public Cafe crearCafe() {
        return new Capuchino();
    }
}

class FabricaLatte implements FabricaCafe {
    @Override
    public Cafe crearCafe() {
        return new Latte();
    }
}

class Expresso extends Cafe {
    public Expresso() {
        descpription = "Expresso";
        precio = 2.50;
    }

    @Override
    public void preparar() {
        System.out.println("Preparando un expresso...");
        System.out.println("Moliendo granos finos");
        System.out.println("Extrayendo con alta presion");
    }
}

class Capuchino extends Cafe {
    public Capuchino() {
        descpription = "Capuchino";
        precio = 3.00;
    }

    @Override
    public void preparar() {
        System.out.println("Preparando un capuchino...");
        System.out.println("Preparando expresso base");
        System.out.println("Espumando leche");
        System.out.println("Mezclando en proporcion 1:1");
    }
}

class Latte extends Cafe {
    public Latte() {
        descpription = "Latte";
        precio = 3.50;
    }

    @Override
    public void preparar() {
        System.out.println("Preparando un latte...");
        System.out.println("Preparando expresso suave");
        System.out.println("Calentando leche al vapor");
        System.out.println("Agregando mas leche que espuma");
    }
}

abstract class DecoradorCafe extends Cafe {
    protected Cafe cafe;

    public DecoradorCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    @Override
    public String getDescripcion() {
        return cafe.getDescripcion();
    }

    @Override
    public double getPrecio() {
        return cafe.getPrecio();
    }

    @Override
    public void preparar() {
        cafe.preparar();
    }
}

class Leche extends DecoradorCafe {
    public Leche(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescripcion() {
        return cafe.getDescripcion() + " con Leche";
    }

    @Override
    public double getPrecio() {
        return cafe.getPrecio() + 0.50;
    }

    @Override
    public void preparar() {
        cafe.preparar();
        System.out.println("Agregando leche caliente");
    }
}

class Azucar extends DecoradorCafe {
    private int cucharadas;

    public Azucar(Cafe cafe, int cucharadas) {
        super(cafe);
        this.cucharadas = cucharadas;
    }

    @Override
    public String getDescripcion() {
        return cafe.getDescripcion() + " con " + cucharadas + " cucharadas de Azucar";
    }

    @Override
    public double getPrecio() {
        return cafe.getPrecio() + (0.10 * cucharadas);
    }

    @Override
    public void preparar() {
        cafe.preparar();
        System.out.println("Agregando " + cucharadas + " cucharadas de Azucar");
    }
}

class Crema extends DecoradorCafe {
    public Crema(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescripcion() {
        return cafe.getDescripcion() + " con Crema Batida";
    }

    @Override
    public double getPrecio() {
        return cafe.getPrecio() + 0.70;
    }

    @Override
    public void preparar() {
        super.preparar();
        System.out.println("Agregando crema batida por encima");
    }
}

class Canela extends DecoradorCafe {
    public Canela(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescripcion() {
        return cafe.getDescripcion() + " con Canela";
    }

    @Override
    public double getPrecio() {
        return cafe.getPrecio() + 0.30;
    }

    @Override
    public void preparar() {
        super.preparar();
        System.out.println("Espolvoreando canela por encima");
    }
}

class Barista {
    private List<ObservadorCafe> observadores;
    private String nombre;

    public Barista(String nombre) {
        this.nombre = nombre;
        this.observadores = new ArrayList<>();
    }

    public void agregarObservador(ObservadorCafe observador) {
        observadores.add(observador);
    }

    public void notificarATodos(String mensaje) {
        for (ObservadorCafe observador : observadores) {
            observador.notificar("Barista " + nombre + ":" + mensaje);
        }
    }

    public Cafe prepararPedido(FabricaCafe fabrica, List<DecoradorCafe> decoradores) {
        notificarATodos("Iniciando preparacion de cafe");

        // Aqui creamos el cafe base usando Fabrica (Factory)
        Cafe cafe = fabrica.crearCafe();

        // Aplicamos los decoradores (Decorator)
        for (DecoradorCafe decorador : decoradores) {
            cafe = decorador;
        }

        notificarATodos("Cafe preparado:" + cafe.getDescripcion() + " Precio: $" + cafe.getPrecio());
        return cafe;
    }
}

class Cliente implements ObservadorCafe {
    private String nombre;

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void notificar(String mensaje) {
        System.out.println("Cliente " + nombre + " recibe: " + mensaje);
    }
}

class SistemaDeFacturacion implements ObservadorCafe {
    @Override
    public void notificar (String mensaje) {
        if (mensaje.contains("Precio")) {
            System.out.println("[SISTEMA FACTURACION] Registrando venta...");
        }
    }
}

class SistemaDeInventario implements ObservadorCafe {
    @Override
    public void notificar(String mensaje) {
        String m = mensaje.toLowerCase();
        if (m.contains("iniciando") || m.contains("preparacion") || m.contains("preparando")) {
            System.out.println("[SISTEMA INVENTARIO] Actualizando stock...");
        }
    }
}

public class SistemaCafeteria {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE CAFETERIA ===\n");

        Barista barista = new Barista("Andres");

        barista.agregarObservador(new Cliente("Sophia"));
        barista.agregarObservador(new SistemaDeFacturacion());
        barista.agregarObservador(new SistemaDeInventario());

        // EJEMPLO 1: Expresso Simple
        System.out.println("--- PEDIDO 1: Expresso Simple ---");
        FabricaCafe fabricaExpresso = new FabricaExpresso();
        List<DecoradorCafe> sinDecoradores = new ArrayList<>();
        
        Cafe pedido1 = barista.prepararPedido(fabricaExpresso, sinDecoradores);
        pedido1.preparar();
        System.out.println("Descripcion:" + pedido1.getDescripcion());
        System.out.println("Precio: $" + pedido1.getPrecio());

        // EJEMPLO 2: Latte con varias decoraciones
        System.out.println("\n--- PEDIDO 2: Latte con Leche, Azucar ---");
        FabricaCafe fabricaLatte = new FabricaLatte();
        List<DecoradorCafe> decoradoresLatte = new ArrayList<>();

        Cafe latteBase = fabricaLatte.crearCafe();
        decoradoresLatte.add(new Leche(latteBase));
        decoradoresLatte.add(new Azucar(decoradoresLatte.get(0), 2));
        decoradoresLatte.add(new Crema(decoradoresLatte.get(1)));

        Cafe pedido2 = barista.prepararPedido(fabricaLatte, decoradoresLatte);
        pedido2.preparar();
        System.out.println("Descripcion:" + pedido2.getDescripcion());
        System.out.println("Precio: $" + pedido2.getPrecio());

        // EJEMPLO 3: Capuchino con Canela
        System.out.println("\n--- PEDIDO 3: Capuchino Personalizado ---");
        FabricaCafe fabricaCapuchino = new FabricaCapuchino();
        List<DecoradorCafe> decoradoresCapuchino = new ArrayList<>();

        Cafe capuchinoBase = fabricaCapuchino.crearCafe();
        decoradoresCapuchino.add(new Leche(capuchinoBase));
        decoradoresCapuchino.add(new Canela(decoradoresCapuchino.get(0)));
        Cafe pedido3 = barista.prepararPedido(fabricaCapuchino, decoradoresCapuchino);
        pedido3.preparar();
        System.out.println("Descripcion:" + pedido3.getDescripcion());
        System.out.println("Precio: $" + pedido3.getPrecio());
    }
}