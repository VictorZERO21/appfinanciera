package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.entidades.Reserva;
import com.upc.appfinanciera.repositorios.ReservaRepositorio;
import com.upc.appfinanciera.interfaces.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService implements IReservaService {
    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Override
    public Reserva insertar(Reserva reserva) {
        return reservaRepositorio.save(reserva);
    }

    @Override
    public Reserva actualizar(Reserva reserva) {
        return reservaRepositorio.save(reserva);
    }

    @Override
    public void eliminar(Long id) {
        reservaRepositorio.deleteById(id);
    }

    @Override
    public List<Reserva> buscarPorCliente(String dniCliente) {
        return reservaRepositorio.findByDniCliente(dniCliente);
    }

    @Override
    public List<Reserva> buscarTodos() {
        return reservaRepositorio.findAll();
    }
}