package com.restexample.restcalculator.dao;

import com.interware.restapi.dto.Usuario;
import java.util.List;

/**
 *
 * @author Alfredo Estrada
 */
public interface UsuarioDAO {
    public List<Usuario> getAll();
    public Usuario insertOrUpdate(Usuario u);
    public boolean remove(Usuario u);
}
