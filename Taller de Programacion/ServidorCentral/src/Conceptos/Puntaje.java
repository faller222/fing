package Conceptos;

import DataTypes.DataPuntaje;

public class Puntaje {
    private Cliente Cli;
    private Integer Puntos;
    
    public Puntaje(Cliente cli, Integer pun){
        Cli=cli;
        Puntos=pun;
    }
    
    public DataPuntaje getDataPuntaje(){
        DataPuntaje dp = new DataPuntaje();
        dp.setCli(Cli.getNickname());
        dp.setPuntos(Puntos);
        return dp; 
    }

    public Integer getPuntos() {
        return Puntos;
    }
    
    
}
