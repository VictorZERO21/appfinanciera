package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.Consejo;
import java.util.List;

public interface IConsejoService {
    public Consejo insertar(Consejo consejo);
    public Consejo actualizar(Consejo consejo);
    public void eliminar(Long id);
    public List<Consejo> buscarPorCliente(String dniCliente);
    public List<Consejo> buscarTodos();

}
