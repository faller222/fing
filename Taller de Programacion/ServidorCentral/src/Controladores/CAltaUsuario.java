package Controladores;

import DataTypes.DataProveedor;
import DataTypes.DataUsuario;
import Interfaces.IAltaUsuario;
import Conceptos.Cliente;
import Manejadores.MUsuario;
import Conceptos.Proveedor;
import Conceptos.Usuario;
import Extras.Utils;

public class CAltaUsuario implements IAltaUsuario {
    
    private MUsuario mUsuario;
    private DataUsuario dataU;
    
    public CAltaUsuario() {
        dataU = null;
        mUsuario = MUsuario.getInstance();
    }
    
    @Override
    public boolean verificarMail(String mail) {//True si esta disponible
        if (mail == null) {
            return true;
        } else {
            return mUsuario.disponibleMail(mail.toLowerCase());
        }
    }
    
    @Override
    public boolean verificarNickname(String Nickname) {//True si esta disponible
        return mUsuario.disponibleNick(Nickname);
    }
    
    @Override
    public void ingresarDataUsuario(DataUsuario dataU) throws Exception {
        
        if (!mUsuario.disponibleMail(dataU.getMail())) {
            throw new Exception("El mail " + dataU.getMail() + " ya esta registrado");
        }
        
        if (!mUsuario.disponibleNick(dataU.getNickname())) {
            throw new Exception("El Nickname " + dataU.getNickname() + " ya esta tomado, elija otro");
        }
        String passEnc = Utils.enc(dataU.getPass());
        dataU.setPass(passEnc);
        this.dataU = dataU;
    }
    
    @Override
    public void altaUsuario() throws Exception {
        Usuario u;
        if (this.dataU != null) {
            if (this.dataU instanceof DataProveedor) {
                u = new Proveedor(this.dataU);
            } else {
                u = new Cliente(this.dataU);
            }
        } else {
            throw new Exception("No se seteo la memoria");
        }
        mUsuario.addUsuario(u);
        dataU = null;
    }
}
