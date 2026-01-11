package com.gautam.gaming_pc_builder.controller;


import com.gautam.gaming_pc_builder.model.GamingComputer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PCController {

    @GetMapping("/api/pc/build")
    public List<String> buildComputers() {

        // Configuration 1: Basic Office PC
        // Notice we only pass the required CPU/RAM
        GamingComputer officePC = new GamingComputer.ComputerBuilder("Intel i5", "8GB")
                .setStorage("512GB SSD")
                .build();

        // Configuration 2: High-End Beast
        // Notice the fluent chaining
        GamingComputer beastPC = new GamingComputer.ComputerBuilder("AMD Ryzen 9", "64GB")
                .setGPU("Nvidia RTX 4090")
                .enableWaterCooling()
                .enableRGB()
                .setIsOverlooked()
                .setStorage("2TB NVMe")
                .build();

        return List.of(
                "Office Build: " + officePC.toString(),
                "Gaming Beast: " + beastPC.toString()
        );
    }
}
