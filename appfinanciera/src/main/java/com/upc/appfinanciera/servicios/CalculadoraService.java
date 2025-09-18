package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.CalculadoraDTO;
import com.upc.appfinanciera.entidades.Calculadora;
import com.upc.appfinanciera.interfaces.ICalculadoraService;
import com.upc.appfinanciera.repositorios.CalculadoraRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraService implements ICalculadoraService {
    @Autowired
    private CalculadoraRepositorio calculadoraRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CalculadoraDTO insertar(CalculadoraDTO calculadoraDto) {
        Calculadora calculadoraEntidad = modelMapper.map(calculadoraDto, Calculadora.class);
        Calculadora guardado = calculadoraRepositorio.save(calculadoraEntidad);
        return modelMapper.map(guardado, CalculadoraDTO.class); // Convertir a DTO
    }

    @Override
    public String calcular(long monto, long cuotas, long tasa) {
        double tasainteres = tasa / 100.0;
        double capitalPorCuota = monto / cuotas;
        double interesPorCuota = (monto * tasainteres) / cuotas;
        double montoPorCuota = capitalPorCuota + interesPorCuota;

        double interesTotal = monto * tasainteres;
        double totalPagar = monto + interesTotal;

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
