package productos.models;

import enums.CategoriaMercaderia;
import enums.Marca;
import java.time.LocalDate;

// Representa un snack del supermercado
public class Comida extends Mercaderia {

    private int gramos;
    private boolean aptoCeliacos;
    private LocalDate vencimiento;

    // Constructor completo
    public Comida(int id, String nombre, Marca marca, double precio,
                 int stock, int gramos, boolean aptoCeliacos, LocalDate vencimiento) {

        super(id, nombre, marca, precio, stock, CategoriaMercaderia.COMIDA);

        this.gramos = gramos;
        this.aptoCeliacos = aptoCeliacos;
        this.vencimiento = vencimiento;
    }

    // Constructor usado en cargas simples (CSV/JSON)
    public Comida(int id, String nombre, Marca marca, double precio, int stock) {
        super(id, nombre, marca, precio, stock, CategoriaMercaderia.COMIDA);

        this.gramos = 100;
        this.aptoCeliacos = false;
        this.vencimiento = LocalDate.now().plusMonths(6);
    }
}
