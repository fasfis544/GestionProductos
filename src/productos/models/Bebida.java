package productos.models;

import enums.CategoriaMercaderia;
import enums.Marca;
import java.time.LocalDate;

// Representa una bebida del supermercado
public class Bebida extends Mercaderia {

    private int tamanioMl;
    private boolean conAzucar;
    private LocalDate vencimiento;

    // Constructor completo
    public Bebida(int id, String nombre, Marca marca, double precio,
                  int stock, int tamanioMl, boolean conAzucar, LocalDate vencimiento) {

        super(id, nombre, marca, precio, stock, CategoriaMercaderia.BEBIDA);

        this.tamanioMl = tamanioMl;
        this.conAzucar = conAzucar;
        this.vencimiento = vencimiento;
    }

    // Constructor con valores por defecto
    public Bebida(int id, String nombre, Marca marca, double precio) {
        super(id, nombre, marca, precio, 10, CategoriaMercaderia.BEBIDA);

        this.tamanioMl = 500;
        this.conAzucar = true;
        this.vencimiento = LocalDate.now().plusMonths(6);
    }
}
