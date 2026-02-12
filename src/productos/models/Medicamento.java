package productos.models;

import enums.CategoriaMercaderia;
import enums.Marca;

// Representa un medicamento
public class Medicamento extends Mercaderia {

    private String tipo;           
    private int cantidadPorPaquete; 
    private boolean paraMayoresDeEdad;

    // Constructor completo
    public Medicamento(int id, String nombre, Marca marca, double precio,
                     int stock, String tipo, int cantidadPorPaquete, boolean conFiltro) {

        super(id, nombre, marca, precio, stock, CategoriaMercaderia.MEDICAMENTO);

        this.tipo = tipo;
        this.cantidadPorPaquete = cantidadPorPaquete;
        this.paraMayoresDeEdad = conFiltro;
    }

    // Constructor con valores por defecto
    public Medicamento(int id, String nombre, Marca marca, double precio) {
        super(id, nombre, marca, precio, 10, CategoriaMercaderia.MEDICAMENTO);

        this.tipo = "tableta";
        this.cantidadPorPaquete = 10;
        this.paraMayoresDeEdad = true;
    }

    // Getters y setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getCantidadPorPaquete() { return cantidadPorPaquete; }
    public void setCantidadPorPaquete(int cantidadPorPaquete) { this.cantidadPorPaquete = cantidadPorPaquete; }

    public boolean isConFiltroEdad() { return paraMayoresDeEdad; }
    public void setConFiltroEdad(boolean conFiltro) { this.paraMayoresDeEdad = conFiltro; }

    // Representación en listas
    @Override
    public String toString() {
        return nombre + " | $" + precio +
               " | Stock: " + stock +
               " | " + tipo +
               " | " + cantidadPorPaquete + "u" +
               " | Filtro: " + (paraMayoresDeEdad ? "Sí" : "No");
    }
}
