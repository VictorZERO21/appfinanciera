package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.dto.GestionFinancieraDTO;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.GestionFinanciera;
import com.upc.appfinanciera.excepciones.CustomExceptions;
import com.upc.appfinanciera.excepciones.CustomExceptions.GestionFinancieraNotFoundException;
import com.upc.appfinanciera.interfaces.IGestionFinancieraService;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import com.upc.appfinanciera.repositorios.GestionFinancieraRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestionFinancieraService implements IGestionFinancieraService {
    @Autowired
    private GestionFinancieraRepositorio gestionFinancieraRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GestionFinancieraDTO insertar(GestionFinancieraDTO gestionFinancieraDTO) {
        String tipo = gestionFinancieraDTO.getTipo();
        if (!tipo.equalsIgnoreCase("ingreso") && !tipo.equalsIgnoreCase("egreso")) {
            throw new CustomExceptions.ValidationException("El tipo solo puede ser 'ingreso' o 'egreso'");
        }
        Cliente cliente = clienteRepositorio.findById(gestionFinancieraDTO.getCliente().getIdCliente())
                .orElseThrow(() -> new CustomExceptions.ClienteNotFoundException(
                        "Cliente no encontrado con ID: " + gestionFinancieraDTO.getCliente().getIdCliente()));

        GestionFinanciera gestionFinanciera = new GestionFinanciera();
        gestionFinanciera.setTitulo(gestionFinancieraDTO.getTitulo());
        gestionFinanciera.setTipo(gestionFinancieraDTO.getTipo());
        gestionFinanciera.setMonto(gestionFinancieraDTO.getMonto());
        gestionFinanciera.setFecha(gestionFinancieraDTO.getFecha());
        gestionFinanciera.setCliente(cliente);

        GestionFinanciera guardado = gestionFinancieraRepositorio.save(gestionFinanciera);
        return modelMapper.map(guardado, GestionFinancieraDTO.class);
    }

    @Override
    public List<GestionFinancieraDTO> buscarPorCliente(String dniCliente) {
        List<GestionFinanciera> gestiones = gestionFinancieraRepositorio.findByClienteDni(dniCliente);

        if (gestiones.isEmpty()) {
            throw new CustomExceptions.GestionFinancieraNotFoundException(
                    "No se encontraron registros financieros para el cliente con DNI: " + dniCliente);
        }

        return gestiones.stream()
                .map(gestion -> modelMapper.map(gestion, GestionFinancieraDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> reportePorTipo(String tipo) {
        List<Object[]> reporte = gestionFinancieraRepositorio.reportePorTipo(tipo);
        if (reporte.isEmpty()) {
            throw new CustomExceptions.GestionFinancieraNotFoundException(
                    "No se encontraron registros de gestión financiera para el tipo: " + tipo);
        }
        return reporte;
    }

    @Override
    public List<Object[]> reportePorFecha(LocalDate fecha) {
        List<Object[]> reporte = gestionFinancieraRepositorio.reportePorFecha(fecha);
        if (reporte.isEmpty()) {
            throw new CustomExceptions.GestionFinancieraNotFoundException(
                    "No se encontraron registros financieros en la fecha: " + fecha);
        }
        return reporte;
    }

}
