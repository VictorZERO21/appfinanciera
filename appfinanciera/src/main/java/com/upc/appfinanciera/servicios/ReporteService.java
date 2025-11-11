package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.excepciones.CustomExceptions;
import com.upc.appfinanciera.repositorios.GestionFinancieraRepositorio;
import org.knowm.xchart.*;
import org.knowm.xchart.style.PieStyler;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteService {

    private final GestionFinancieraRepositorio repo;

    public ReporteService(GestionFinancieraRepositorio repo) {
        this.repo = repo;
    }

    public byte[] graficoPiePorTipo(String tipo) throws Exception {
        List<Object[]> rows = repo.reportePorTipo(tipo);

        PieChart chart = new PieChartBuilder()
                .width(900)
                .height(620)
                .title("Distribución de gastos por categoría (" + tipo + ")")
                .build();

        aplicarEstiloModerno(chart);

        if (rows.isEmpty()) {
            throw new CustomExceptions.NoDataAvailableException("No hay datos para generar el gráfico.");
        } else {
            double total = rows.stream().mapToDouble(r -> ((Number) r[1]).doubleValue()).sum();
            for (Object[] r : rows) {
                String titulo = String.valueOf(r[0]);
                double monto = ((Number) r[1]).doubleValue();
                double porcentaje = (monto / total) * 100;
                chart.addSeries(titulo, porcentaje);
            }
        }

        return toPng(chart);
    }

    public byte[] graficoPiePorFecha(LocalDate fecha) throws Exception {
        List<Object[]> rows = repo.reportePorFecha(fecha);

        if (rows.isEmpty()) {
            throw new CustomExceptions.NoDataAvailableException("No hay datos para generar el gráfico.");
        }

        PieChart chart = new PieChartBuilder()
                .width(900)
                .height(620)
                .title("Montos en fecha: " + fecha)
                .build();

        aplicarEstiloModerno(chart);

        double total = rows.stream().mapToDouble(r -> ((Number) r[1]).doubleValue()).sum();
        for (Object[] r : rows) {
            double monto = ((Number) r[1]).doubleValue();
            double porcentaje = (monto / total) * 100;
            chart.addSeries(r[0].toString(), porcentaje);
        }

        return toPng(chart);
    }

    private void aplicarEstiloModerno(PieChart chart) {
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setPlotBackgroundColor(Color.WHITE);

        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setLegendPosition(PieStyler.LegendPosition.OutsideE);
        chart.getStyler().setLegendFont(new Font("SansSerif", Font.PLAIN, 14));
        chart.getStyler().setLegendPadding(8);

        chart.getStyler().setCircular(true);
        chart.getStyler().setPlotContentSize(0.70);
        chart.getStyler().setStartAngleInDegrees(90); 

        chart.getStyler().setLabelType(PieStyler.LabelType.Value);     // muestra el value (que ya pasamos como %)
        chart.getStyler().setLabelsFont(new Font("SansSerif", Font.BOLD, 14));
        chart.getStyler().setLabelsDistance(0.55);                      // empuja el texto hacia dentro
        chart.getStyler().setDecimalPattern("#.#'%'");                  // 1 decimal en porcentaje

        chart.getStyler().setSeriesColors(new Color[]{
                new Color(126, 182, 255), // azul pastel
                new Color(255, 177, 153), // durazno
                new Color(255, 226, 154), // crema
                new Color(158, 232, 195), // menta
                new Color(200, 165, 255), // lavanda
                new Color(255, 165, 216)  // rosa
        });

        chart.getStyler().setChartTitleFont(new Font("SansSerif", Font.BOLD, 18));
        chart.getStyler().setChartTitlePadding(15);
    }

    private byte[] toPng(PieChart chart) throws Exception {
        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        return out.toByteArray();
    }
}
