package com.interware.restapi.service;

import com.interware.restapi.service.exception.ValidationDataException;
import com.interware.restapi.dto.Usuario;

/**
 *
 * @author Alfredo Estrada
 */
public interface UsuarioService {
    public Usuario createOrUpdateUsuario(Usuario usuario);
    public void validateUsuario(Usuario usuario) throws ValidationDataException;
    
}
