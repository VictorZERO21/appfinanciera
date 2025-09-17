package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.entidades.GestionFinanciera;
import com.upc.appfinanciera.repositorios.GestionFinancieraRepositorio;
import com.upc.appfinanciera.interfaces.IGestionFinancieraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GestionFinancieraService implements IGestionFinancieraService {
    @Autowired
    private GestionFinancieraRepositorio gestionFinancieraRepositorio;

    @Override
    public GestionFinanciera insertar(GestionFinanciera gestionFinanciera) {
        return gestionFinancieraRepositorio.save(gestionFinanciera);
    }

    @Override
    public GestionFinanciera actualizar(GestionFinanciera gestionFinanciera) {
        return gestionFinancieraRepositorio.save(gestionFinanciera);
    }

    @Override
    public void eliminar(Long id) {
        gestionFinancieraRepositorio.deleteById(id);
    }

    @Override
    public List<GestionFinanciera> buscarPorCliente(String dniCliente) {
        return gestionFinancieraRepositorio.findByDniCliente(dniCliente);
    }

    @Override
    public List<Object[]> reportePorTipo(String tipo) {
        return List.of();
    }

    @Override
    public List<Object[]> reportePorFecha(LocalDate fecha) {
        return List.of();
    }

    @Override
    public List<GestionFinanciera> buscarTodos() {
        return gestionFinancieraRepositorio.findAll();
    }
}