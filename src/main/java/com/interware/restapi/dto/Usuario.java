package com.interware.restapi.dto;

import java.io.Serializable;

/**
 *
 * @author Alfredo Estrada
 */

public class Usuario implements Serializable{

    private String usuario; // "usuario":"Si viene se actualiza, si no viene se crea un nuevo usuario"
    private String nombre;  // ":"Requerido 30 caracteres máximo",
    private String paterno; // "paterno":"Requerido 30 caracteres máximo",
    private String materno; // ":"Opcional y 30 caracteres máximo",
    private String sexo;    // "sexo":"Sólo H o M",
    private String telefono; // "telefono":"10 0 12 caracteres numéricos, opcional",
    private String email;   // "email":"opcional y tiene se que ser un email válido"    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario{usuario=").append(usuario);
        sb.append(", nombre=").append(nombre);
        sb.append(", paterno=").append(paterno);
        sb.append(", materno=").append(materno);
        sb.append(", sexo=").append(sexo);
        sb.append(", telefono=").append(telefono);
        sb.append(", email=").append(email);
        sb.append('}');
        return sb.toString();
    }
    
}