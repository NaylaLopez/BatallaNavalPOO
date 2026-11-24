package Modelo;

public class Jugador {
    private String nombre;
    private Tablero miTablero;

    public Jugador(String unNombre){
        if (unNombre == null || unNombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacio.");
        }
        nombre = unNombre;
    }

    public static void validarCantidadNaves(int cantidadNaves) {
        Tablero.validarCantidadNaves(cantidadNaves);
    }

    public void cargarTablero(int cantidadNaves, int[] tamanios){
        miTablero = new Tablero(cantidadNaves, tamanios);
    }

    public void posicionarMisNaves(String fila, int columna, String direccionDeLaNave){
        miTablero.posicionarMisNaves(fila, columna, direccionDeLaNave);
    }

    public String getNombre(){return this.nombre;}

    public estadoEnLaCasilla recibirAtaque(String fila, int columna){
        return miTablero.registrarAtaque(fila, columna);
    }

    public boolean todasLasNavesHundidas(){
        return miTablero.todasLasNavesHundidas();
    }

    public boolean todasMisNavesPosicionadas(){
        if(miTablero == null){
            return false;
        }
        return miTablero.todasLasNavesPosicionadas();
    }

    public String getNombreNave(){
        return  miTablero.getNombreProximaNave();
    }

    public int getTamanoNave(){
        return miTablero.getTamanoProximaNave();
    }

    public estadoDelTablero[][] estadoDeMiTablero(boolean ocultarInformacion){
        return miTablero.obtenerEstadoDelTablero(ocultarInformacion);
    }
}
