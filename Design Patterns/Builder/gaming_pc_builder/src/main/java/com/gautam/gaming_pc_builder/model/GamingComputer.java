package com.gautam.gaming_pc_builder.model;


public class GamingComputer {
    // Required parameters
    private String CPU;
    private String RAM;

    // Optional parameters
    private String GPU;
    private boolean hasWaterCooling;
    private boolean hasRGBLights;
    private String storage;
    private boolean isOverclocked;

    // 1. Private Constructor: Only the Builder can call this
    private GamingComputer(ComputerBuilder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.GPU = builder.GPU;
        this.hasWaterCooling = builder.hasWaterCooling;
        this.hasRGBLights = builder.hasRGBLights;
        this.storage = builder.storage;
        this.isOverclocked = builder.isOverclocked;
    }

    // Getters
    public String toString() {
        return "ComputerSpecs [CPU=" + CPU + ", RAM=" + RAM + ", GPU=" + GPU +
                ", WaterCooling=" + hasWaterCooling + ", RGB=" + hasRGBLights +
                ", Overlooked=" + isOverclocked + "]";
    }

    // 2. The Static Builder Class
    public static class ComputerBuilder {
        // Same fields as the parent
        private String CPU;
        private String RAM;
        private String GPU;
        private boolean hasWaterCooling;
        private boolean hasRGBLights;
        private String storage;
        private boolean isOverclocked;

        // Constructor for REQUIRED fields
        public ComputerBuilder(String CPU, String RAM) {
            this.CPU = CPU;
            this.RAM = RAM;
        }

        // Methods for OPTIONAL fields (returning 'this' for chaining)
        public ComputerBuilder setGPU(String GPU) {
            this.GPU = GPU;
            return this; // <--- The magic of method chaining
        }

        public ComputerBuilder enableWaterCooling() {
            this.hasWaterCooling = true;
            return this;
        }

        public ComputerBuilder enableRGB() {
            this.hasRGBLights = true;
            return this;
        }

        public ComputerBuilder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public ComputerBuilder setIsOverlooked(){
            this.isOverclocked = true;
            return this;
        }

        // The Final Build Method
        public GamingComputer build() {
            return new GamingComputer(this);
        }
    }
}
