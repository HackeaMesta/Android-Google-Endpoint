package com.appspot.data_base_1298.backend;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/** Define el contenido del almacenamiento en la base de datos. */
@Entity
@SuppressWarnings("unused")
public class Conocido {
    /**
     * Indica la llave primaria. Cuando es de tipo Long, y vale null al agregar,
     * se genera el valor automáticamente.
     */
    @Id
    Long id;
    /**
     * La anotación Index define un índice de búsqueda en la base de datos. Es
     * requerida para los campos que se usan en las consultas.
     */
    @Index
    String nombre;
    // Los nombres de los campos que se convierten a JSON no llevan acentos.
    String telefono;
    String descripcion;
    String latitude;
    String longitude;
    String foto;
    Float rating;
    String web;
    Boolean wifi;

    public Conocido() {

    }

    public Conocido(String id, String nombre, String telefono, String descripcion, String latitude, String longitude, String foto, Float rating, String web, Boolean wifi) {
        this.id = id == null ? null : Key.valueOf(id).getId();
        this.nombre = nombre;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.latitude = latitude;
        this.longitude = longitude;
        this.foto = foto;
        this.rating = rating;
        this.web = web;
        this.wifi = wifi;
    }

    public String getId() {
        return id == null ? null : Key.create(Conocido.class, id).toWebSafeString();
    }
    public void setId(String id) {
        this.id = id == null ? null : Key.valueOf(id).getId();
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }
}