package DataTypes;

import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType
public class DataEstadistica {
    private Float ganancia;
    private Float[] meses;

    public DataEstadistica() {
        meses = new Float[12];
        for(int i=0; i<12; i++){
            meses[i]= new Float(0);
        }
    }

    public Float getGanancia() {
        return ganancia;
    }

    public void setGanancia(Float ganancia) {
        this.ganancia = ganancia;
    }
    
    public Float[] getMeses() {
        return meses;
    }

    public void setMeses(Float[] meses) {
        this.meses = meses;
    }
      
}
