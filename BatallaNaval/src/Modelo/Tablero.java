package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Casilla [][] miTablero;
    private int cantidadDeNavesAJugar;
    private List<Nave> misNaves;
    private int indiceNave;

    public Tablero(int unaCantidadDeNaves, int tamanios[]){
        validarCantidadNaves(unaCantidadDeNaves);

        cantidadDeNavesAJugar = unaCantidadDeNaves;
        indiceNave = 0;
        if (tamanios == null || tamanios.length != cantidadDeNavesAJugar) {
            throw new IllegalArgumentException("La cantidad de naves no coincide con los tamaños ingresados.");
        }

        misNaves = new ArrayList<>();
        for(int i = 0; i < cantidadDeNavesAJugar; i++){
            int tamanio = tamanios[i];
            Nave unaNave = new Nave(tamanio);
            misNaves.add(unaNave);
        }

        miTablero = new Casilla[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                miTablero[i][j] = new Casilla();
            }
        }
    }

    public static void validarCantidadNaves(int unaCantidadDeNaves) {
        if (unaCantidadDeNaves > 5 || unaCantidadDeNaves < 1) {
            throw new IllegalArgumentException("La cantidad de naves para jugar debe estar en un rango de 1 - 5");
        }
    }

    //vamo a hacer un helper
    public static int convertirFilaAIndice(String fila){
        char letra = fila.toUpperCase().charAt(0);
        return letra - 'A';
    }

    public void posicionarMisNaves(String fila, int columna, String direccionDeLaNave){
        if(indiceNave >= misNaves.size()){
            throw new IllegalStateException("Todas las naves ya fueron posicionadas.");
        }
        if (direccionDeLaNave == null || direccionDeLaNave.isBlank()) {
            throw new IllegalArgumentException("La direccion no puede estar vacia.");
        }
        String direccionLimpia = direccionDeLaNave.trim();
        if (!direccionLimpia.equalsIgnoreCase("H") && !direccionLimpia.equalsIgnoreCase("V")) {
            throw new IllegalArgumentException("Direccion invalida. Debe ser H o V.");
        }

        posicionarNave(fila, columna, direccionLimpia, misNaves.get(indiceNave));

        indiceNave++;
    }

    public boolean todasLasNavesPosicionadas(){
        return indiceNave >= misNaves.size();
    }

    private void posicionarNave(String fila, int columna, String direccionDeLaNave, Nave unaNave){
        validarFormatoCoordenadas(fila, columna);
        int indiceFila = convertirFilaAIndice(fila);
        if(hayEspacioLibreParaLaNave(indiceFila, columna, direccionDeLaNave, unaNave)){
            //posiciono toda mi nave
            if(direccionDeLaNave.equalsIgnoreCase("H")){
                for(int i = 0; i < unaNave.getTamano(); i++){
                    miTablero[indiceFila][(columna-1)+i].ubicarNave(unaNave);
                }
            } else if (direccionDeLaNave.equalsIgnoreCase("V")) {
                for(int i = 0; i < unaNave.getTamano(); i++){
                    miTablero[indiceFila+i][columna-1].ubicarNave(unaNave);
                }
            }
        }else{
            throw new IllegalStateException("La posicion ingresada no es valida.");
        }
    }

    private void validarFormatoCoordenadas(String fila, int columna){
        if(columna > 10 || columna <= 0){
            throw new IllegalArgumentException("La columna debe ser un valor entre 1 y 10");
        }
        if(fila == null || fila.isBlank()){
            throw new IllegalArgumentException("La fila no puede estar vacia.");
        }
        if(!fila.matches("(?i)[a-j]")){
            throw new IllegalArgumentException("La fila debe estar dentro del rango de A/a - J/j");
        }
    }


    private boolean hayEspacioLibreParaLaNave(int fila, int columna, String direccionDeLaNave, Nave unaNave){
        int posicionesValidasHORIZONTAL = (columna-1) + unaNave.getTamano();
        int posicionesValidasVERTICAL = fila + unaNave.getTamano();

        if(miTablero[fila][columna - 1].isOcupada()){
            throw new IllegalStateException("La casilla ya fue utilizada.");
        }

        if(direccionDeLaNave.equalsIgnoreCase("H")){
            if(posicionesValidasHORIZONTAL <= 10){
                hayEspacioHorizontal(fila, columna, unaNave);
            }else{
                throw new IllegalStateException("La nave no puede sobresalir del tablero. Intenta nuevamente.");
            }
        }

        if(direccionDeLaNave.equalsIgnoreCase("V")){
            if(posicionesValidasVERTICAL <= 10){
                hayEspacioVertical(fila, columna, unaNave);
            }else{
                throw new IllegalStateException("La nave no puede sobresalir del tablero. Intenta nuevamente.");
            }
        }
        return true; //porque se supone que sino hay espacio salta antes
    }

    private void validarCasillaLibre(int fila, int columna, String mensajeError) {
        if (fila >= 0 && fila < 10 && columna >= 0 && columna < 10) {
            if (miTablero[fila][columna].isOcupada()) {
                throw new IllegalStateException(mensajeError);
            }
        }
    }
    private void hayEspacioHorizontal(int fila, int columna, Nave unaNave){
        //la nave debe ser posicionada de manera horizontal
        //Usara una sola fila de letras y las columnas numericas que le corresponda
        //Caso horizontal, debo tener mi tamano mas los extremos libres
        int columnaActual = columna - 1;

        // Punta izquierda
        validarCasillaLibre(fila, columnaActual - 1, "No pueden haber dos naves juntas.");
        validarCasillaLibre(fila - 1, columnaActual - 1, "No pueden haber dos naves juntas.");
        validarCasillaLibre(fila - 1, columnaActual + 1, "No pueden haber dos naves juntas.");
        // Punta derecha
        int puntaDerecha = columnaActual + unaNave.getTamano();
        validarCasillaLibre(fila, puntaDerecha, "No pueden haber dos naves juntas.");
        validarCasillaLibre(fila - 1, puntaDerecha, "No pueden haber dos naves juntas.");
        validarCasillaLibre(fila + 1, puntaDerecha, "No pueden haber dos naves juntas.");

        for (int i = 0; i <= unaNave.getTamano(); i++) {
            validarCasillaLibre(fila, columnaActual, "No puede haber superposicion de naves.");
            validarCasillaLibre(fila + 1, columnaActual, "No pueden haber dos naves juntas.");
            validarCasillaLibre(fila - 1, columnaActual, "No pueden haber dos naves juntas.");
            columnaActual++;
        }
    }

    private void hayEspacioVertical(int fila, int columna, Nave unaNave){
        //la nave debe ser posicionada de manera vertical
        //Usara una sola columna numerica y las filas de numeros que le corresponda

        //Caso vertical, debo tener mi tamano mas los extremos libres
        int filaActual = fila;
        int columnaActual = columna - 1;

        // Punta izquierda
        int puntaIzquierda = fila - 1;
        validarCasillaLibre(puntaIzquierda, columnaActual, "No pueden haber dos naves juntas.");
        validarCasillaLibre(puntaIzquierda, columnaActual - 1, "No pueden haber dos naves juntas.");
        validarCasillaLibre(puntaIzquierda, columnaActual + 1, "No pueden haber dos naves juntas.");

        // Punta derecha
        int puntaDerecha = fila + unaNave.getTamano();
        validarCasillaLibre(puntaDerecha, columnaActual, "No pueden haber dos naves juntas.");
        validarCasillaLibre(puntaDerecha, columnaActual - 1, "No pueden haber dos naves juntas.");
        validarCasillaLibre(puntaDerecha, columnaActual + 1, "No pueden haber dos naves juntas.");

        for (int i = 0; i <= unaNave.getTamano(); i++) {
            validarCasillaLibre(filaActual, columnaActual, "No puede haber superposicion de naves.");
            validarCasillaLibre(filaActual, columnaActual + 1, "No pueden haber dos naves juntas.");
            validarCasillaLibre(filaActual, columnaActual - 1, "No pueden haber dos naves juntas.");
            filaActual++;
        }
    }


    public estadoEnLaCasilla registrarAtaque(String fila, int columna){
        validarFormatoCoordenadas(fila, columna);
        int indiceFila = convertirFilaAIndice(fila);

        if(miTablero[indiceFila][columna - 1].isAtacada()){
            throw new IllegalStateException("La casilla ya fue utilizada.");
        }else{
            return miTablero[indiceFila][columna - 1].ataque();
        }
    }

    //public List<Nave> getMisNaves(){return misNaves;}

    public boolean todasLasNavesHundidas(){
        boolean resultado = true;
        for(Nave n : misNaves){
            if(!n.isHundida()){
                resultado = false;
            }
        }
        return resultado;
    }

    private Nave obtenerProximaNave() {
        if (todasLasNavesPosicionadas()) {
            throw new IllegalStateException("Ya se posicionaron todas las naves.");
        }
        return misNaves.get(indiceNave);
    }

    public int getTamanoProximaNave() {
        return obtenerProximaNave().getTamano();
    }

    public String getNombreProximaNave() {
        return obtenerProximaNave().getNombre();
    }

    public estadoDelTablero[][] obtenerEstadoDelTablero(boolean ocultarInformacion){
        estadoDelTablero[][] nuevoTablero = new estadoDelTablero[10][10];
        Casilla unaCasilla;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                unaCasilla = this.miTablero[i][j];

                if (unaCasilla.isAtacada()) {
                    if (unaCasilla.isNaveHundida()) {
                        nuevoTablero[i][j] = estadoDelTablero.HUNDIDO;
                    } else if (unaCasilla.isOcupada()) {
                        nuevoTablero[i][j] = estadoDelTablero.IMPACTO;
                    } else {
                        nuevoTablero[i][j] = estadoDelTablero.AGUA;
                    }
                } else {
                    if (unaCasilla.isOcupada() && !ocultarInformacion) {
                        nuevoTablero[i][j] = estadoDelTablero.NAVE;
                    } else {
                        nuevoTablero[i][j] = estadoDelTablero.VACIO;
                    }
                }

            }
        }
        return nuevoTablero;
    }
}
