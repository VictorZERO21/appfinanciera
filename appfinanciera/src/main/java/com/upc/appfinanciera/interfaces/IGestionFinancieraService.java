package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.GestionFinanciera;
import java.util.List;

public interface IGestionFinancieraService {
    public GestionFinanciera insertar(GestionFinanciera gestionFinanciera);
    public GestionFinanciera actualizar(GestionFinanciera gestionFinanciera);
    public void eliminar(Long id);
    public List<GestionFinanciera> buscarPorCliente(String dniCliente);
    public List<GestionFinanciera> buscarTodos();
}
