package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.entidades.Calculadora;
import com.upc.appfinanciera.interfaces.ICalculadoraService;
import com.upc.appfinanciera.repositorios.CalculadoraRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraService implements ICalculadoraService {
    @Autowired
    private CalculadoraRepositorio calculadoraRepositorio;

    @Override
    public Calculadora insertar(Calculadora calculadora) {
        return calculadoraRepositorio.save(calculadora);
    }
    @Override
    public String calcular(long monto, long cuotas, long tasa) {
        double tasainteres = tasa / 100.0;     // a proporción
        // Modelo simple que estabas usando (interés prorrateado):
        double capitalPorCuota = monto / cuotas; // Cálculo de capital por cuota
        double interesPorCuota = (monto * (tasainteres) / cuotas); // Cálculo de interés por cuota
        double montoPorCuota = capitalPorCuota + interesPorCuota; // Monto a pagar por cuota

        double interesTotal = monto * (tasainteres); // Interés total
        double totalPagar = monto + interesTotal; // Monto total a pagar

        return String.format(
                "Capital por cuota: %.2f%n" +
                        "Interés por cuota: %.2f%n" +
                        "Monto por cuota: %.2f%n" +
                        "Capital total: %.2f%n" +
                        "Interés total: %.2f%n" +
                        "Total a pagar: %.2f%n",
                capitalPorCuota, interesPorCuota, montoPorCuota,
                (double) monto, interesTotal, totalPagar
        );
    }
}