package com.example.store.service;

import com.example.store.model.Producto;
import com.example.store.model.Usuarios;
import com.example.store.repositories.ProductoRepository;
import com.example.store.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    private final ProductoRepository productoRepository;
    private final UserRepository userRepository;

    public StoreService(ProductoRepository productoRepository, UserRepository userRepository) {
        this.productoRepository = productoRepository;
        this.userRepository = userRepository;
    }

    public List<Producto> getAvailableProducts() {
        return productoRepository.findAll();
    }

    public Producto getProductoByName(String productName) {
        return productoRepository.findByNombreIgnoreCase(productName);
    }

    public Usuarios getUser(String email) {
        Optional<Usuarios> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("Usuario con correo " + email + " no encontrado.");
        }
    }

    @Transactional
    public void addProduct(Producto producto) {
        productoRepository.save(producto);
    }

    @Transactional
    public String processPurchase(Usuarios user, String productName, int quantity) {
        Producto product = productoRepository.findByNombreIgnoreCase(productName);
        if (product == null) {
            return "Producto no encontrado.";
        }
        if (product.getCantidad() < quantity) {
            return "Cantidad insuficiente. Disponibles: " + product.getCantidad();
        }
        BigDecimal totalCost = BigDecimal.valueOf(product.getPrecio()).multiply(BigDecimal.valueOf(quantity));
        if (user.getSaldo().compareTo(totalCost) >= 0) {
            product.setCantidad(product.getCantidad() - quantity);
            productoRepository.save(product);
            user.setSaldo(user.getSaldo().subtract(totalCost));
            userRepository.save(user);

            return "Compra realizada con éxito. Balance restante: " + user.getSaldo();
        } else {
            return "Fondos insuficientes. Balance actual: " + user.getSaldo();
        }
    }
}
