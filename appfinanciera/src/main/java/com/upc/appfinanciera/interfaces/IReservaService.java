package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.Reserva;
import java.util.List;

public interface IReservaService {
    public Reserva insertar(Reserva reserva);
    public Reserva actualizar(Reserva reserva);
    public void eliminar(Long id);
    public List<Reserva> buscarPorCliente(String dniCliente);
    public List<Reserva> buscarTodos();
}