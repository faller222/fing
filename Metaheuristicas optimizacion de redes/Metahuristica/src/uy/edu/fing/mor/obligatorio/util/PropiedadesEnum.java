package uy.edu.fing.mor.obligatorio.util;

public enum PropiedadesEnum {

    //La idea de los operadores geneticos es que tengamos varias impl y segun propertis optemos por uno u otro
    WORKSPACE("workspace", "../datos"),
    PROPERTIES("properties", "properties/instancia1.prop"),
    DEBUG("debug.eneable", "false"),
    SEED("seed", ""),
    RUIDO("ruido", "0"),
    GENERACIONES("ae.cant.generaciones", "50"),
    GENERACIONES_INVARIANTES("ae.cant.generaciones.invariantes", "10"),
    INICIALIZACION("inicializacion", "1"),
    INICIALIZACION_INTENTOS("inicializacion.try", "10"),
    INICIALIZACION_TAMANIO_POBLACION("inicializacion.pop.size", "50"),
    SELECCION("seleccion", "1"),
    SELECCION_FACTOR("seleccion.factor", "1000"),
    MUTACION("mutacion", "1"),
    MUTACION_PROPABILIDAD("mutacion.prob", "1"),
    MUTACION_RUIDO("mutacion.ruido", "100"),
    CRUZAMIENTO("cruzamiento", "1"),
    CRUZAMIENTO_PROBABILIDAD("cruzamiento.prob", "50"),
    CRUZAMIENTO_CANTIDAD("cruzamiento.cant", "25"),
    CANT_SUMIDEROS("cantSumideros", "3"),
    CANT_FUENTES("cantFuentes", "4"),
    ESPECIALES("especiales", "0;0;0;2;2;3;2"),
    DELAYS("delays", "0;0;0;1263;1234;946;1107"),;

    private String nombre;
    private String valorDefecto;

    private PropiedadesEnum(String nombre, String valorDefecto) {
        this.nombre = nombre;
        this.valorDefecto = valorDefecto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getValorDefecto() {
        return valorDefecto;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
