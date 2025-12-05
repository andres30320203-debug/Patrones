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