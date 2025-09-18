package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.CalculadoraDTO;

public interface ICalculadoraService {
    public CalculadoraDTO insertar(CalculadoraDTO calculadoraDto);
    public String calcular(long monto, long cuotas, long tasa);
}
