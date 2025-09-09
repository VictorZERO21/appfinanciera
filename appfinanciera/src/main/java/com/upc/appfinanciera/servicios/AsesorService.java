package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.interfaces.IAsesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsesorService implements IAsesorService {
    @Autowired
    private AsesorRepositorio asesorRepositorio;

    @Override
    public AsesorFinanciero insertar(AsesorFinanciero asesor) {
        return asesorRepositorio.save(asesor);
    }

    @Override
    public AsesorFinanciero actualizar(AsesorFinanciero asesor) {
        return asesorRepositorio.save(asesor);
    }

    @Override
    public void eliminar(String dni) {
        AsesorFinanciero asesor = asesorRepositorio.findByDni(dni);
        if (asesor != null) {
            asesorRepositorio.delete(asesor);
        }
    }

    @Override
    public AsesorFinanciero buscarPorDni(String dni) {
        return asesorRepositorio.findByDni(dni);
    }

    @Override
    public List<AsesorFinanciero> buscarTodos() {
        return asesorRepositorio.findAll();
    }
}
