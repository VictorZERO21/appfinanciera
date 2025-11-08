package com.upc.appfinanciera.servicios;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraService {

    public static class Resultado {   // <- PUBLIC
        public double capitalCuota, interesCuota, cuota, capitalTotal, interesTotal, total;
        public Resultado(double capitalCuota, double interesCuota, double cuota,
                         double capitalTotal, double interesTotal, double total) {
            this.capitalCuota = capitalCuota;
            this.interesCuota = interesCuota;
            this.cuota = cuota;
            this.capitalTotal = capitalTotal;
            this.interesTotal = interesTotal;
            this.total = total;
        }
    }

    public Resultado calcular(long monto, long cuotas, long tasa) {
        double tasainteres = tasa / 100.0;
        double capitalPorCuota = monto / (double) cuotas;
        double interesPorCuota = (monto * tasainteres) / cuotas;
        double montoPorCuota = capitalPorCuota + interesPorCuota;
        double interesTotal = monto * tasainteres;
        double totalPagar = monto + interesTotal;

        return new Resultado(
                capitalPorCuota,
                interesPorCuota,
                montoPorCuota,
                monto,
                interesTotal,
                totalPagar
        );
    }}
