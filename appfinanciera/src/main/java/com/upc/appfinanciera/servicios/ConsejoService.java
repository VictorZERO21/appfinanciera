package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.entidades.Consejo;
import com.upc.appfinanciera.repositorios.ConsejoRepositorio;
import com.upc.appfinanciera.interfaces.IConsejoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsejoService implements IConsejoService {
    @Autowired
    private ConsejoRepositorio consejoRepositorio;

    @Override
    public Consejo insertar(Consejo consejo) {
        return consejoRepositorio.save(consejo);
    }

    @Override
    public Consejo actualizar(Consejo consejo) {
        return consejoRepositorio.save(consejo);
    }

    @Override
    public void eliminar(Long id) {
        consejoRepositorio.deleteById(id);
    }

    @Override
    public List<Consejo> buscarPorCliente(String dniCliente) {
        return consejoRepositorio.findByDniCliente(dniCliente);
    }

    @Override
    public List<Consejo> buscarTodos() {
        return consejoRepositorio.findAll();
    }
}