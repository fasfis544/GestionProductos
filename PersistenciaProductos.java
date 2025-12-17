package productos.models;

import java.io.*;
import java.util.List;
import enums.Marca;
import enums.CategoriaMercaderia;

// Maneja la lectura y escritura de archivos del sistema
public class PersistenciaProductos {

    // ---- CSV ----

    public static void guardarCsv(List<Mercaderia> lista, String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

            pw.println("id;nombre;marca;categoria;precio;stock");

            for (Mercaderia p : lista) {
                pw.println(p.aCsv());
            }

            System.out.println("CSV guardado en: " + archivo);

        } catch (IOException e) {
            System.out.println("Error al guardar CSV: " + e.getMessage());
        }
    }

    public static void cargarCsv(List<Mercaderia> lista, String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            lista.clear();

            String linea = br.readLine();  // encabezado
            linea = br.readLine();         // primera línea útil

            while (linea != null) {
                String[] partes = linea.split(";");

                if (partes.length >= 6) {
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    Marca marca = Marca.valueOf(partes[2]);
                    CategoriaMercaderia categoria = CategoriaMercaderia.valueOf(partes[3]);
                    double precio = Double.parseDouble(partes[4]);
                    int stock = Integer.parseInt(partes[5]);

                    // Se reconstruye como Snack
                    lista.add(new Comida(id, nombre, marca, precio, stock));
                }

                linea = br.readLine();
            }

            System.out.println("CSV cargado desde: " + archivo);

        } catch (IOException e) {
            System.out.println("Error al cargar CSV: " + e.getMessage());
        }
    }

    // ---- JSON manual ----

    public static void guardarJson(List<Mercaderia> lista, String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

            pw.println("[");
            for (int i = 0; i < lista.size(); i++) {
                Mercaderia p = lista.get(i);

                pw.println("  {");
                pw.println("    \"id\": " + p.getId() + ",");
                pw.println("    \"nombre\": \"" + p.getNombre() + "\",");
                pw.println("    \"marca\": \"" + p.getMarca() + "\",");
                pw.println("    \"categoria\": \"" + p.getCategoria() + "\",");
                pw.println("    \"precio\": " + p.getPrecio() + ",");
                pw.println("    \"stock\": " + p.getStock());
                pw.print("  }");

                if (i < lista.size() - 1) pw.println(",");
                else pw.println();
            }
            pw.println("]");

            System.out.println("JSON guardado en: " + archivo);

        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    public static void cargarJson(List<Mercaderia> lista, String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            lista.clear();
            String linea;
            
           
            while ((linea = br.readLine()) != null) {
                 
                // Si la línea comienza con "{", significa que empieza un objeto JSON de producto
                if (linea.trim().startsWith("{")) {

                    String idLinea = br.readLine().trim();
                    String nombreLinea = br.readLine().trim();
                    String marcaLinea = br.readLine().trim();
                    String categoriaLinea = br.readLine().trim();
                    String precioLinea = br.readLine().trim();
                    String stockLinea = br.readLine().trim();

                    int id = Integer.parseInt(idLinea.split(":")[1].replace(",", "").trim());

                    String nombre = nombreLinea.split(":")[1]
                            .replace(",", "").replace("\"", "").trim();

                    String marcaTxt = marcaLinea.split(":")[1]
                            .replace(",", "").replace("\"", "").trim();
                    Marca marca = Marca.valueOf(marcaTxt);

                    double precio = Double.parseDouble(precioLinea.split(":")[1]
                            .replace(",", "").trim());

                    int stock = Integer.parseInt(stockLinea.split(":")[1]
                            .replace(",", "").trim());

                    lista.add(new Comida(id, nombre, marca, precio, stock));

                    br.readLine(); // Cierra el bloque
                }
            }

            System.out.println("JSON cargado desde: " + archivo);

        } catch (IOException e) {
            System.out.println("Error al cargar JSON: " + e.getMessage());
        }
    }

    // ---- TXT filtrado ----

    public static void exportarTxtFiltrado(List<Mercaderia> lista,
                                           String archivo, double max) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

            pw.println("==============================================");
            pw.println("LISTADO DE PRODUCTOS (precio <= " + max + ")");
            pw.println("==============================================");
            pw.println("ID\tNOMBRE\tMARCA\tCATEGORIA\tPRECIO\tSTOCK");

            for (Mercaderia p : lista) {
                if (p.getPrecio() <= max) {
                    pw.println(p.getId() + "\t" +
                               p.getNombre() + "\t" +
                               p.getMarca() + "\t" +
                               p.getCategoria() + "\t" +
                               p.getPrecio() + "\t" +
                               p.getStock());
                }
            }

            System.out.println("TXT exportado en: " + archivo);

        } catch (IOException e) {
            System.out.println("Error al exportar TXT: " + e.getMessage());
        }
    }
}
