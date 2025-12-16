package productos.models;

import java.util.List;

// Operaciones básicas de CRUD para cualquier tipo de entidad
public interface ICRUD<T, K> {

    boolean crear(T entidad);     // Agrega una nueva entidad
    T leerPorId(K id);            // Busca por ID o null si no existee
    List<T> leerTodos();          // Devuelve todas las entidades
    boolean actualizar(T entidad); // Reemplaza una existente
    boolean eliminarPorId(K id);   // Elimina por ID
}
