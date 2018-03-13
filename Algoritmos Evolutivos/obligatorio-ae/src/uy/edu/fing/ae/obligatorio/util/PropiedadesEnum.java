package uy.edu.fing.ae.obligatorio.util;

public enum PropiedadesEnum {
    //https://github.com/Alevsk/Java-Genetic-Algorithm/blob/master/src/com/alevsk/genetico/GeneticsAlgorithm.java
    //La idea de los operadores geneticos es que tengamos varias impl y segun propertis optemos por uno u otro
    WORKSPACE("workspace", "instancias"),
    PROPERTIES("properties", "properties/instancia1.prop"),
    
    DEBUG("debug.eneable", "false"),
    CORRIDAS("corridas", "2"),
    SEED("seed", ""),
    
    GENERACIONES("ae.cant.generaciones", "100"),
    GENERACIONES_INVARIANTES("ae.cant.generaciones.invariantes", "10"),
    
    INICIALIZACION("inicializacion", "1"),
    INICIALIZACION_INTENTOS("inicializacion.try", "100"),
    INICIALIZACION_TAMANIO_POBLACION("inicializacion.pop.size", "1"),
    
    SELECCION("seleccion", "1"),
    SELECCION_ELITE("seleccion.elitista", "10"),
    SELECCION_TORNEO("seleccion.torneo", "2"),
    
    MUTACION("mutacion", "1"),
    MUTACION_PROPABILIDAD("mutacion.prob", "1"),
    MUTACION_RUIDO("mutacion.ruido", "100"),
    
    CRUZAMIENTO("cruzamiento", "1"),
    CRUZAMIENTO_PROBABILIDAD("cruzamiento.prob", "50"),
    CRUZAMIENTO_CANTIDAD("cruzamiento.cant", "25"),
    CRUZAMIENTO_ALPHA("cruzamiento.alpha", "0.5"),
    
    PIXEL_REPRESENTACION("pixel.representacion", "10"),
    CAMARA_CANTIDAD("camara.cantidad", "20"),
    CAMARA_RANGO("camara.rango", "100"),
    CAMARA_AMPLITUD("camara.amplitud", "35"),
    
    INTERES_NORMAL("interes.mayor", "7"),
    INTERES_MAYOR("interes.normal", "3"),
    ;

    private final String nombre;
    private final String valorDefecto;

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
