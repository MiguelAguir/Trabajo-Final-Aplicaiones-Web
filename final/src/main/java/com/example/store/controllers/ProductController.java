package com.example.store.controllers;

import com.example.store.model.Producto;
import com.example.store.model.Usuarios;
import com.example.store.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import java.util.function.BiFunction;

@Controller
public class ProductController {

    private final StoreService storeService;

    public ProductController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/")
    public String showProducts(Model model) {
        List<Producto> products = storeService.getAvailableProducts();
        model.addAttribute("producto", products);
        return "index";
    }
    @PostMapping("/buy")
    public String buyProducts(@RequestParam("email") String email,
                          @RequestParam("productName") List<String> productName,
                          @RequestParam("quantity") List<Integer> quantities,
                          Model model) {
    System.out.println("Producto recibido: " + productName);
    System.out.println("Cantidad recibida: " + quantities);

    Usuarios user = storeService.getUser(email);
    List<Producto> productos = productName.stream().map(storeService::getProductoByName).filter(producto -> producto != null).collect(Collectors.toList());
    BigDecimal totalCost = IntStream.range(0, productos.size())
                            .mapToObj(i -> BigDecimal.valueOf(productos.get(i).getPrecio()).multiply(BigDecimal.valueOf(quantities.get(i))))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BiFunction<Producto, Integer, String> getMessage = (producto, cantidad) -> {
        return storeService.processPurchase(user, producto.getNombre(), cantidad);
    };
    if (user.getSaldo().compareTo(totalCost) >= 0) {
        List<String> messages = IntStream.range(0, productos.size())
        .mapToObj(i -> getMessage.apply(productos.get(i), quantities.get(i)))
        .collect(Collectors.toList());
        model.addAttribute("message", String.join("\n", messages));
    } else {
        model.addAttribute("message", "No tienes suficiente saldo para realizar la compra. Tu saldo actual es : " + user.getSaldo());
    }
    model.addAttribute("productName", productName);
    model.addAttribute("quantity", quantities);
    model.addAttribute("totalCost", totalCost);
    return "checkout";
}
}