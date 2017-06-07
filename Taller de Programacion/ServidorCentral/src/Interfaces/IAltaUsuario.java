package Interfaces;

import DataTypes.DataUsuario;

public interface IAltaUsuario {

    public boolean verificarMail(String mail);//True si esta disponible

    public boolean verificarNickname(String Nickname);//True si esta disponible

    public void ingresarDataUsuario(DataUsuario dataU) throws Exception;

    public void altaUsuario() throws Exception;
}
