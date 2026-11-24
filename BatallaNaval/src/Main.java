import Modelo.BatallaNaval;
import Modelo.estadoDelTablero;
import Modelo.estadoEnLaCasilla;
import vista.IVista;
import controlador.Controlador;
import vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        BatallaNaval juego = new BatallaNaval();
        IVista vista = new VistaConsola();
        Controlador controlador = new Controlador(juego, vista);

        controlador.iniciar();
    }
}