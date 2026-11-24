package Modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BatallaNaval {
    private List<Jugador> misJugadores;
    private int jugadorActual;
    private boolean partidaTerminada;

    public BatallaNaval() {
        misJugadores = new ArrayList<Jugador>();
        jugadorActual = 0;
        partidaTerminada = false;
    }

    public void agregarJugador(String nombre) {
        if(misJugadores.size() == 2){
            throw new IllegalArgumentException("La cantidad de jugadores no puede ser mayor a 2");
        }
        Jugador unJugador = new Jugador(nombre);
        if (!misJugadores.isEmpty() && misJugadores.get(0).getNombre().equalsIgnoreCase(unJugador.getNombre())) {
            throw new IllegalArgumentException("Los jugadores no pueden tener el mismo nombre.");
        }
        misJugadores.add(unJugador);
    }

    public void cargarNaves(int cantidadNaves, int[] tamanos) {
        if(misJugadores.size() != 2){
            throw new IllegalArgumentException("La cantida de jugadores es invalida.");
        }

        validarTamanos(tamanos);
        for(Jugador j : misJugadores){
            //le cargo a los dos lo mismo porque es reglaaa
            j.cargarTablero(cantidadNaves, tamanos);
        }


    }

    public void validarTamanoNave(int tamano) {
        if (tamano < 1 || tamano > 5) {
            throw new IllegalArgumentException("Los tamanios de las naves deben ir de 1 a 5");
        }
    }
    private void validarTamanos(int[] tamanos) {
        HashSet<Integer> controladorDeTamanios = new HashSet<>();
        for (int t : tamanos) {
            validarTamanoNave(t);
            if (!controladorDeTamanios.add(t)) {
                throw new IllegalStateException("No pueden existir tamanios repetidos.");
            }
        }
    }



    public void posicionarMisNaves(String fila, int columna, String direccionDeLaNave){
        misJugadores.get(jugadorActual).posicionarMisNaves(fila, columna, direccionDeLaNave);
    }

    public void validarCantidadNaves(int cantidadNaves) {
        Jugador.validarCantidadNaves(cantidadNaves);
    }

    public boolean jugadorActualTerminoPosicionamiento(){
        return misJugadores.get(jugadorActual).todasMisNavesPosicionadas();
    }

    public void finalizarPosicionamientoCambioDeTurno(){
        if(misJugadores.get(jugadorActual).todasMisNavesPosicionadas()){
            cambiarTurno();
        }
    }

    public estadoEnLaCasilla realizarAtaque(String fila, int columna){
        if(partidaTerminada){throw new IllegalStateException("La partida ya finalizó.");}
        Jugador jugadorContrario = obtenerContrario();
        estadoEnLaCasilla resultado = jugadorContrario.recibirAtaque(fila, columna);
        switch (resultado){
            case AGUA -> {
                cambiarTurno();
            }
            case IMPACTO -> {
                //El mismo jugador mantiene el turno
            }
            case HUNDIDO -> {
                partidaTerminada = verificarSiHayGanador();
            }
        }
        return resultado;
    }

    private Jugador obtenerContrario() {
        int indiceContrario = (jugadorActual == 0) ? 1 : 0;
        return misJugadores.get(indiceContrario);
    }

    private void cambiarTurno(){
        jugadorActual = 1 - jugadorActual;
    }

    public boolean verificarSiHayGanador(){
        //devuelvo el booleano para informar al exterior
        return obtenerContrario().todasLasNavesHundidas();
    }

    public String getNombreJugadorActual(){
        return misJugadores.get(jugadorActual).getNombre();
    }

    public String getNombreGanador() {
        if (!partidaTerminada) {
            return null;
        }
        return misJugadores.get(jugadorActual).getNombre();
    }

    public boolean fasePosicionamientoTerminada(){
        if (misJugadores.size() != 2) {
            return false;
        }
        boolean resultado = true;
        for(Jugador j : misJugadores){
            if(!j.todasMisNavesPosicionadas()){
                resultado = false;
            }
        }
        return resultado;
    }


    public String getNombreNave(){
        return  misJugadores.get(jugadorActual).getNombreNave();
    }

    public int getTamanoNave(){
        return misJugadores.get(jugadorActual).getTamanoNave();
    }

    public estadoDelTablero[][] obtenerEstadoDelTableroDelJugadorActual(){
        return misJugadores.get(jugadorActual).estadoDeMiTablero(false);
    }

    public estadoDelTablero[][] obtenerEstadoDelTableroDelContrincante(){
        return obtenerContrario().estadoDeMiTablero(true);
    }
}
