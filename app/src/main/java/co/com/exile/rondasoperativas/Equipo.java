package co.com.exile.rondasoperativas;

/**
 * Created by pico on 1/08/2016.
 */
public class Equipo {
    private int id;
    private String nombre;
    private String unidad;
    private String planta;
    public boolean checked = false;

    public Equipo(int id, String nombre, String unidad, String planta, boolean checked) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.planta = planta;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipo equipo = (Equipo) o;

        return id == equipo.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
