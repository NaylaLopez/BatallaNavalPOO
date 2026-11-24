package vista;

import Modelo.estadoDelTablero;
import Modelo.estadoEnLaCasilla;

public interface IVista {
    String pedirNombre(int unJugador);
    String pedirDireccion();
    String pedirFila();
    int pedirColumna();
    int pedirTamanoNave(int indiceNave);
    void mostrarNaveAUbicar(String nombreNave, int tamano);
    int pedirCantidadNaves();
    void mostrarResultadoAtaque(estadoEnLaCasilla resultado);
    void mostrarAviso(String mensaje);
    void mostrarError(String mensaje);
    void mostrarGanador(String nombre);
    void limpiarPantalla();
    void esperarEnter();
    void dibujarTablero(estadoDelTablero[][] tablero, String titulo);


}
