package productos.models;

import enums.CategoriaMercaderia;
import enums.Marca;
import java.time.LocalDate;

// Representa un electrodoméstico
public class Electrodomesticos extends Mercaderia {

    private int mesesGarantia;
    private boolean bajoConsumo;
    private LocalDate fechaFabricacion;

    // Constructor completo
    public Electrodomesticos(int id, String nombre, Marca marca, double precio,
                 int stock, int mesesGarantia, boolean bajoConsumo, LocalDate fechaFabricacion) {

        /* NOTA: Recuerda agregar ELECTRODOMESTICO a tu enum CategoriaMercaderia */
        super(id, nombre, marca, precio, stock, CategoriaMercaderia.ELECTRODOMESTICO);

        this.mesesGarantia = mesesGarantia;
        this.bajoConsumo = bajoConsumo;
        this.fechaFabricacion = fechaFabricacion;
    }

    // Constructor con valores por defecto (para cargas rápidas)
    public Electrodomesticos(int id, String nombre, Marca marca, double precio, int stock) {
        super(id, nombre, marca, precio, stock, CategoriaMercaderia.ELECTRODOMESTICO);

        this.mesesGarantia = 12;    // 1 año de garantía por defecto
        this.bajoConsumo = true;    // Asumimos que es eficiente
        this.fechaFabricacion = LocalDate.now().minusMonths(1); // Fabricado recientemente
    }
}