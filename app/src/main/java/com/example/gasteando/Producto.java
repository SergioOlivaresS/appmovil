package com.example.gasteando;

public class Producto {
    private String categoria;
    private String detalle;
    private String fecha;
    private double monto;

    public Producto() {
        // Constructor vacío requerido por Firebase
    }

    public Producto(String categoria, String detalle, String fecha, double monto) {
        this.categoria = categoria;
        this.detalle = detalle;
        this.fecha = fecha;
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
