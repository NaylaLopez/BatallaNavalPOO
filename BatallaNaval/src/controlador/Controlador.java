package controlador;

import Modelo.BatallaNaval;
import Modelo.estadoEnLaCasilla;
import org.junit.platform.commons.function.Try;
import vista.IVista;

import java.util.ArrayList;
import java.util.List;

public class Controlador {
    private BatallaNaval juego;
    private IVista vista;

    public Controlador(BatallaNaval unJuego, IVista unaVista){
        this.juego = unJuego;
        this.vista = unaVista;
    }

    public void iniciar(){
        configurarPartida();
        fasePosicionamiento();
        faseAtaque();
    }

    private void configurarPartida(){
        for(int i = 1; i <= 2; i++){
            boolean jugadorAgregado = false;
            while(!jugadorAgregado){
                try{
                    String nombre = vista.pedirNombre(i);
                    juego.agregarJugador(nombre);
                    jugadorAgregado = true;
                }catch (IllegalArgumentException e){
                    vista.mostrarError(e.getMessage());
                }
            }

        }
        boolean naveCargada = false;
        while(!naveCargada){
            try{
                int cantidadDeNaves = vista.pedirCantidadNaves();
                juego.validarCantidadNaves(cantidadDeNaves);
                //si no salta, todo ok salgo del while
                int[] tamanios = new int[cantidadDeNaves];
                for(int i = 0; i < cantidadDeNaves; i++){
                    boolean tamanoValido = false;
                    while (!tamanoValido) {
                        try {
                            int tamano = vista.pedirTamanoNave(i + 1);
                            juego.validarTamanoNave(tamano);
                            //si no salta, todo ok salgo del while
                            tamanios[i] = tamano;
                            tamanoValido = true;
                        } catch (IllegalArgumentException e) {
                            vista.mostrarError(e.getMessage());
                        }
                    }
                }
                juego.cargarNaves(cantidadDeNaves, tamanios);
                naveCargada = true;

            }catch (NegativeArraySizeException e){
                vista.mostrarError("La cantidad de naves no puede ser negativa");
            }catch (IllegalArgumentException | IllegalStateException e){
                vista.mostrarError(e.getMessage());
            }
        }
    }

    private void fasePosicionamiento(){
        while(!juego.fasePosicionamientoTerminada()){
            vista.mostrarAviso("\nTurno de "+ juego.getNombreJugadorActual());
            vista.esperarEnter();
            while(!juego.jugadorActualTerminoPosicionamiento()){
                vista.dibujarTablero(juego.obtenerEstadoDelTableroDelJugadorActual(), "MI TABLERO");
                vista.mostrarNaveAUbicar(juego.getNombreNave(), juego.getTamanoNave());
                //tengo que ver si mejoro esto para que no tenga que reingresar todo completo
                String fila = vista.pedirFila();
                int columna = vista.pedirColumna();
                String direccion = vista.pedirDireccion();

                try {
                    juego.posicionarMisNaves(fila, columna, direccion);
                }catch (IllegalArgumentException | IllegalStateException e){
                    vista.mostrarError(e.getMessage());
                }

            }
            vista.dibujarTablero(juego.obtenerEstadoDelTableroDelJugadorActual(), "TABLERO FINAL - ¡¡LISTO PARA EL COMBATE!!");
            vista.esperarEnter();
            vista.limpiarPantalla();
            juego.finalizarPosicionamientoCambioDeTurno();
        }
    }

    private void faseAtaque(){
        while(!juego.verificarSiHayGanador()){
            vista.mostrarAviso("\n--- Turno de " + juego.getNombreJugadorActual() + " ---");
            vista.dibujarTablero(juego.obtenerEstadoDelTableroDelContrincante(), "CONTRINCANTE");
            vista.dibujarTablero(juego.obtenerEstadoDelTableroDelJugadorActual(), "MI TABLERO");
            String fila = vista.pedirFila();
            int columna = vista.pedirColumna();
            try{
                estadoEnLaCasilla resultado = juego.realizarAtaque(fila,columna);
                vista.mostrarResultadoAtaque(resultado);
                if(resultado == estadoEnLaCasilla.AGUA){
                    vista.esperarEnter();
                    vista.limpiarPantalla();
                    vista.mostrarAviso("Turno de " + juego.getNombreJugadorActual());
                    vista.esperarEnter();
                }

            }catch (IllegalArgumentException | IllegalStateException e){
                vista.mostrarError(e.getMessage());
            }

        }
        vista.mostrarGanador(juego.getNombreGanador());
    }
}
