
package publisher;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataProducto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataProducto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cantVentas" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="categorias" type="{http://Publisher/}dataCategoria" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="coments" type="{http://Publisher/}dataComentario" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="especificacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estadistic" type="{http://Publisher/}dataEstadistica" minOccurs="0"/>
 *         &lt;element name="img0" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="img1" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="img2" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="NRef" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="precio" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="promedio" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="punt1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="punt2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="punt3" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="punt4" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="punt5" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="puntajes" type="{http://Publisher/}dataPuntaje" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reclamos" type="{http://Publisher/}dataReclamo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="todos" type="{http://Publisher/}dataComentario" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataProducto", propOrder = {
    "cantVentas",
    "categorias",
    "coments",
    "descripcion",
    "especificacion",
    "estadistic",
    "img0",
    "img1",
    "img2",
    "nRef",
    "nombre",
    "precio",
    "promedio",
    "proveedor",
    "punt1",
    "punt2",
    "punt3",
    "punt4",
    "punt5",
    "puntajes",
    "reclamos",
    "todos"
})
public class DataProducto {

    protected Integer cantVentas;
    @XmlElement(nillable = true)
    protected List<DataCategoria> categorias;
    @XmlElement(nillable = true)
    protected List<DataComentario> coments;
    protected String descripcion;
    protected String especificacion;
    protected DataEstadistica estadistic;
    protected byte[] img0;
    protected byte[] img1;
    protected byte[] img2;
    @XmlElement(name = "NRef")
    protected Integer nRef;
    protected String nombre;
    protected Float precio;
    protected Float promedio;
    protected String proveedor;
    protected int punt1;
    protected int punt2;
    protected int punt3;
    protected int punt4;
    protected int punt5;
    @XmlElement(nillable = true)
    protected List<DataPuntaje> puntajes;
    @XmlElement(nillable = true)
    protected List<DataReclamo> reclamos;
    @XmlElement(nillable = true)
    protected List<DataComentario> todos;

    /**
     * Gets the value of the cantVentas property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCantVentas() {
        return cantVentas;
    }

    /**
     * Sets the value of the cantVentas property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantVentas(Integer value) {
        this.cantVentas = value;
    }

    /**
     * Gets the value of the categorias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categorias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategorias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataCategoria }
     * 
     * 
     */
    public List<DataCategoria> getCategorias() {
        if (categorias == null) {
            categorias = new ArrayList<DataCategoria>();
        }
        return this.categorias;
    }

    /**
     * Gets the value of the coments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataComentario }
     * 
     * 
     */
    public List<DataComentario> getComents() {
        if (coments == null) {
            coments = new ArrayList<DataComentario>();
        }
        return this.coments;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the especificacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEspecificacion() {
        return especificacion;
    }

    /**
     * Sets the value of the especificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEspecificacion(String value) {
        this.especificacion = value;
    }

    /**
     * Gets the value of the estadistic property.
     * 
     * @return
     *     possible object is
     *     {@link DataEstadistica }
     *     
     */
    public DataEstadistica getEstadistic() {
        return estadistic;
    }

    /**
     * Sets the value of the estadistic property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataEstadistica }
     *     
     */
    public void setEstadistic(DataEstadistica value) {
        this.estadistic = value;
    }

    /**
     * Gets the value of the img0 property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImg0() {
        return img0;
    }

    /**
     * Sets the value of the img0 property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImg0(byte[] value) {
        this.img0 = value;
    }

    /**
     * Gets the value of the img1 property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImg1() {
        return img1;
    }

    /**
     * Sets the value of the img1 property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImg1(byte[] value) {
        this.img1 = value;
    }

    /**
     * Gets the value of the img2 property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImg2() {
        return img2;
    }

    /**
     * Sets the value of the img2 property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImg2(byte[] value) {
        this.img2 = value;
    }

    /**
     * Gets the value of the nRef property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNRef() {
        return nRef;
    }

    /**
     * Sets the value of the nRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNRef(Integer value) {
        this.nRef = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the precio property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Sets the value of the precio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPrecio(Float value) {
        this.precio = value;
    }

    /**
     * Gets the value of the promedio property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getPromedio() {
        return promedio;
    }

    /**
     * Sets the value of the promedio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setPromedio(Float value) {
        this.promedio = value;
    }

    /**
     * Gets the value of the proveedor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProveedor() {
        return proveedor;
    }

    /**
     * Sets the value of the proveedor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProveedor(String value) {
        this.proveedor = value;
    }

    /**
     * Gets the value of the punt1 property.
     * 
     */
    public int getPunt1() {
        return punt1;
    }

    /**
     * Sets the value of the punt1 property.
     * 
     */
    public void setPunt1(int value) {
        this.punt1 = value;
    }

    /**
     * Gets the value of the punt2 property.
     * 
     */
    public int getPunt2() {
        return punt2;
    }

    /**
     * Sets the value of the punt2 property.
     * 
     */
    public void setPunt2(int value) {
        this.punt2 = value;
    }

    /**
     * Gets the value of the punt3 property.
     * 
     */
    public int getPunt3() {
        return punt3;
    }

    /**
     * Sets the value of the punt3 property.
     * 
     */
    public void setPunt3(int value) {
        this.punt3 = value;
    }

    /**
     * Gets the value of the punt4 property.
     * 
     */
    public int getPunt4() {
        return punt4;
    }

    /**
     * Sets the value of the punt4 property.
     * 
     */
    public void setPunt4(int value) {
        this.punt4 = value;
    }

    /**
     * Gets the value of the punt5 property.
     * 
     */
    public int getPunt5() {
        return punt5;
    }

    /**
     * Sets the value of the punt5 property.
     * 
     */
    public void setPunt5(int value) {
        this.punt5 = value;
    }

    /**
     * Gets the value of the puntajes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the puntajes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPuntajes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataPuntaje }
     * 
     * 
     */
    public List<DataPuntaje> getPuntajes() {
        if (puntajes == null) {
            puntajes = new ArrayList<DataPuntaje>();
        }
        return this.puntajes;
    }

    /**
     * Gets the value of the reclamos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reclamos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReclamos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataReclamo }
     * 
     * 
     */
    public List<DataReclamo> getReclamos() {
        if (reclamos == null) {
            reclamos = new ArrayList<DataReclamo>();
        }
        return this.reclamos;
    }

    /**
     * Gets the value of the todos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the todos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTodos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataComentario }
     * 
     * 
     */
    public List<DataComentario> getTodos() {
        if (todos == null) {
            todos = new ArrayList<DataComentario>();
        }
        return this.todos;
    }

}
