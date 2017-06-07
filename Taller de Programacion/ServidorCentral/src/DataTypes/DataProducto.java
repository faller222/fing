package DataTypes;

import Extras.UtilImage;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({DataPuntaje.class})
@XmlAccessorType
public class DataProducto implements Comparable<DataProducto> {

    private String Nombre;
    private String Descripcion;
    private String Especificacion;
    private Integer NRef;
    private Float Precio;
    private byte[] Img0;
    private byte[] Img1;
    private byte[] Img2;
    private Integer CantVentas;
    private Float promedio;
    private int punt1 = 0;
    private int punt2 = 0;
    private int punt3 = 0;
    private int punt4 = 0;
    private int punt5 = 0;
    //Links
    private ArrayList<DataComentario> Coments;
    private ArrayList<DataComentario> Todos;
    private String Proveedor;
    private ArrayList<DataCategoria> Categorias = new ArrayList();
    private ArrayList<DataReclamo> Reclamos = new ArrayList<>();
    private ArrayList<DataPuntaje> Puntajes;
    private DataEstadistica estadistic;

    public DataProducto(String Nombre, String Descripcion, String Especificacion, Integer NRef, Float Precio, String Proveedor, Integer CantVentas) {
        this.Todos = new ArrayList<>();
        this.Coments = new ArrayList<>();
        this.Puntajes = new ArrayList<>();
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Especificacion = Especificacion;
        this.NRef = NRef;
        this.Precio = Precio;
        this.Proveedor = Proveedor;
        this.Img0 = null;
        this.Img1 = null;
        this.Img2 = null;
        this.CantVentas = CantVentas;
        this.estadistic = null;
    }

    public DataProducto(String Nombre, Integer NRef, float Precio, String Proveedor) {
        this.Todos = new ArrayList< DataComentario>();
        this.Coments = new ArrayList< DataComentario>();
        this.Nombre = Nombre;
        this.NRef = NRef;
        this.Precio = Precio;
        this.Proveedor = Proveedor;
        this.Img0 = null;
        this.Img1 = null;
        this.Img2 = null;
        this.CantVentas = 0;
        this.estadistic = null;
    }

    public byte[] getImgs(int Pos) throws Exception {//si se va de tema, tiro null
        byte[] select;
        switch (Pos) {
            case 0:
                select = Img0;
                break;
            case 1:
                select = Img1;
                break;
            case 2:
                select = Img2;
                break;
            default:
                select = null;
                break;
        }
        return select;
    }

    public DataComentario getComentario(Integer id) {
        return Todos.get(id);
    }

    public Float getPromedio() {
        return promedio;
    }

    public int getPunt1() {
        return punt1;
    }

    public int getPunt2() {
        return punt2;
    }

    public int getPunt3() {
        return punt3;
    }

    public int getPunt4() {
        return punt4;
    }

    public int getPunt5() {
        return punt5;
    }



    //Setters
    public void addComen(DataComentario c) {
        Coments.add(c);
    }

    public void addPuntaje(DataPuntaje dp) {
        Puntajes.add(dp);
    }

    public void setPromedio(Float promedio) {
        this.promedio = promedio;
    }

    public void addTodos(DataComentario c) {
        Todos.add(c);
    }

    public void setPunt1(int punt1) {
        this.punt1 = punt1;
    }

    public void setPunt2(int punt2) {
        this.punt2 = punt2;
    }

    public void setPunt3(int punt3) {
        this.punt3 = punt3;
    }

    public void setPunt4(int punt4) {
        this.punt4 = punt4;
    }

    public void setPunt5(int punt5) {
        this.punt5 = punt5;
    }

    public void agregarImgs(byte[] Img, int Pos) {//debe Agregar 3 si no, no la guarda
        switch (Pos) {
            case 0:
                Img0 = Img;
                break;
            case 1:
                Img1 = Img;
                break;
            case 2:
                Img2 = Img;
                break;
            default:
                ;
                break;
        }
    }

    public void agregarImgs(String Path, int Pos) throws Exception {//debe Agregar 3 si no, no la guarda
        byte[] hola = UtilImage.toByteArray(Path);
        agregarImgs(UtilImage.toByteArray(Path), Pos);
    }

    public void agregarCategorias(DataCategoria Categoria) {
        this.Categorias.add(Categoria);
    }

    @Override
    public int compareTo(DataProducto o) {
        return (int) (o.Precio - Precio);
    }

    public void agregarReclamo(DataReclamo Rec) {
        this.Reclamos.add(Rec);
    }

    public void agregarPuntajes(DataPuntaje pun) {
        this.Puntajes.add(pun);
    }

    //para el xml
    public DataProducto() {
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getEspecificacion() {
        return Especificacion;
    }

    public Integer getNRef() {
        return NRef;
    }

    public Float getPrecio() {
        return Precio;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public Integer getCantVentas() {
        return CantVentas;
    }

    public DataEstadistica getEstadistic() {
        return estadistic;
    }

    public byte[] getImg0() {
        return Img0;
    }

    public byte[] getImg1() {
        return Img1;
    }

    public byte[] getImg2() {
        return Img2;
    }

    public byte[][] getImgs() {

        byte[][] ret = new byte[3][];
        ret[0] = Img0;
        ret[1] = Img1;
        ret[2] = Img2;
        return ret;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public void setEspecificacion(String Especificacion) {
        this.Especificacion = Especificacion;
    }

    public void setNRef(Integer NRef) {
        this.NRef = NRef;
    }

    public void setPrecio(Float Precio) {
        this.Precio = Precio;
    }

    public void setProveedor(String Proveedor) {
        this.Proveedor = Proveedor;
    }

    public void setCantVentas(Integer CantVentas) {
        this.CantVentas = CantVentas;
    }

    public void setEstadistic(DataEstadistica estadistic) {
        this.estadistic = estadistic;
    }

    public void setImg0(byte[] Img0) {
        this.Img0 = Img0;
    }

    public void setImg1(byte[] Img1) {
        this.Img1 = Img1;
    }

    public void setImg2(byte[] Img2) {
        this.Img2 = Img2;
    }

    public ArrayList<DataComentario> getComents() {
        return Coments;
    }

    public ArrayList<DataComentario> getTodos() {
        return Todos;
    }

    public ArrayList<DataCategoria> getCategorias() {
        return Categorias;
    }

    public ArrayList<DataReclamo> getReclamos() {
        return Reclamos;
    }

    public ArrayList<DataPuntaje> getPuntajes() {
        return Puntajes;
    }

    public void setComents(ArrayList<DataComentario> Coments) {
        this.Coments = Coments;
    }

    public void setTodos(ArrayList<DataComentario> Todos) {
        this.Todos = Todos;
    }

    public void setCategorias(ArrayList<DataCategoria> Categorias) {
        this.Categorias = Categorias;
    }

    public void setReclamos(ArrayList<DataReclamo> Reclamos) {
        this.Reclamos = Reclamos;
    }

    public void setPuntajes(ArrayList<DataPuntaje> Puntajes) {
        this.Puntajes = Puntajes;
    }
}
