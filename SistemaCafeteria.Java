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