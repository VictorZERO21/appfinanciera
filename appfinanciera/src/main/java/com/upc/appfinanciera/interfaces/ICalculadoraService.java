package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.entidades.Calculadora;

public interface ICalculadoraService {
    public Calculadora insertar(Calculadora calculadora);
    public String calcular(long monto, long cuotas, long tasa);
}
