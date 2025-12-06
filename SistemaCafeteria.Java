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

class FabricarLatte implements FabricaCafe {
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