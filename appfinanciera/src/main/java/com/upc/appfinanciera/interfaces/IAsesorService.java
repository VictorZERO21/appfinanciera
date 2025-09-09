package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.entidades.AsesorFinanciero;
import java.util.List;

public interface IAsesorService {
    public AsesorFinanciero insertar(AsesorFinanciero asesor);
    public AsesorFinanciero actualizar(AsesorFinanciero asesor);
    public void eliminar(String dni);
    public AsesorFinanciero buscarPorDni(String dni);
    public List<AsesorFinanciero> buscarTodos();
}
