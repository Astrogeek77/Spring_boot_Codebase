package com.gautam.concurra.controller;

import com.gautam.concurra.model.Product;
import com.gautam.concurra.service.FlashSaleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flashsale")
public class FlashSaleController {

    private final FlashSaleService flashSaleService;

    public FlashSaleController(FlashSaleService flashSaleService) {
        this.flashSaleService = flashSaleService;
    }

    // READ (Uses Cache)
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return flashSaleService.getProductDetails(id);
    }

    // WRITE (Evicts Cache)
    @PostMapping("/{id}/purchase")
    public String purchase(@PathVariable Long id) {
        flashSaleService.purchase(id);
        return "Purchase request processed.";
    }
}
