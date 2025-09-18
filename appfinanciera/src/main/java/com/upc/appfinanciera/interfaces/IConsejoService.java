package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ConsejoDTO;
import java.util.List;

public interface IConsejoService {
    public ConsejoDTO insertar(ConsejoDTO consejo);
    public ConsejoDTO actualizar(ConsejoDTO consejo);
    public void eliminar(Long id);
    public List<ConsejoDTO> buscarPorCliente(String dniCliente);
    public List<ConsejoDTO> buscarTodos();
}
