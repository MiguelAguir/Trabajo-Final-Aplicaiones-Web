package com.example.store.repositories;
import com.example.store.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByNombreIgnoreCase(String nombre);
    
}