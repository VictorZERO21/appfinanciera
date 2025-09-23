package com.upc.appfinanciera.servicios;
import com.upc.appfinanciera.repositorios.GestionFinancieraRepositorio;
import org.knowm.xchart.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    private final GestionFinancieraRepositorio repo;

    public ReporteService(GestionFinancieraRepositorio repo) {
        this.repo = repo;
    }

    public byte[] graficoPiePorTipo(String tipo) throws Exception {
        // Obtener los datos de la consulta
        List<Object[]> rows = repo.reportePorTipo(tipo); // [titulo, total]

        // Crear el gráfico de tipo PieChart
        PieChart chart = new PieChartBuilder()
                .width(900).height(620)
                .title("Distribución por título — Tipo: " + tipo)
                .build();

        // Si no hay datos, agregar una serie vacía
        if (rows.isEmpty()) {
            chart.addSeries("Sin datos", 1);
            chart.getStyler().setLegendVisible(false);
            return toPng(chart);
        }

        // Agregar las series al gráfico
        for (Object[] r : rows) {
            String titulo = String.valueOf(r[0]);
            double monto = ((Number) r[1]).doubleValue();
            if (monto > 0) {
                // Depuración de los datos
                System.out.println("Titulo: " + titulo + " - Monto: " + monto);
                chart.addSeries(titulo, monto);  // Agregar la serie con el monto
            }
        }

        // Convertir el gráfico a imagen PNG y devolverlo
        return toPng(chart);
    }

    public byte[] graficoPiePorFecha(LocalDate fecha) throws Exception {
        List<Object[]> rows = repo.reportePorFecha(fecha);
        PieChart chart = new PieChartBuilder().width(800).height(600)
                .title("Montos en fecha: " + fecha).build();
        for (Object[] r : rows) {
            chart.addSeries(r[0].toString(), ((Number) r[1]).doubleValue());
        }
        return toPng(chart);
    }

    private byte[] toPng(PieChart chart) throws Exception {
        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        return out.toByteArray();
    }
}
