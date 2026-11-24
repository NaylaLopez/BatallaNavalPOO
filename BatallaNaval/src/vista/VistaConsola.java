package vista;

import Modelo.estadoDelTablero;
import Modelo.estadoEnLaCasilla;

import java.util.Scanner;

public class VistaConsola implements IVista{
    private final Scanner scanner;
    public VistaConsola(){
        this.scanner = new Scanner(System.in);
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(this.scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("[ERROR] Ingresa un numero entero valido: ");
            }
        }
    }

    @Override
    public String pedirNombre(int unJugador){
        System.out.println("Ingrese el nombre del jugador " + unJugador + ": ");
        return this.scanner.nextLine().trim();
    }

    @Override
    public String pedirDireccion(){
        System.out.println("Ingrese la direccion de la nave (H/V): ");
        return this.scanner.nextLine().trim();
    }

    @Override
    public String pedirFila(){
        System.out.println("Ingrese la fila (A-J): ");
        return this.scanner.nextLine().trim();
    }

    @Override
    public int pedirColumna(){
        System.out.println("Ingrese la columna (1-10): ");
        return this.leerEntero();
    }

    @Override
    public int pedirTamanoNave(int indiceNave){
        System.out.println("Ingresa el tamanio de la nave n°"+ indiceNave + " (tamanios 1-5)");
        return this.leerEntero();
    }

    @Override
    public void mostrarNaveAUbicar(String nombreNave, int tamano) {
        System.out.println("Posiciona tu " + nombreNave + " (ocupa " + tamano + " casillas)");
    }

    @Override
    public int pedirCantidadNaves(){
        System.out.println("Ingresa la cantidad de Naves que van a jugar: ");
        return this.leerEntero();
    }

    @Override
        public void mostrarResultadoAtaque(estadoEnLaCasilla resultado) {
            switch (resultado) {
                case AGUA -> System.out.println("\n~ ¡AGUA! No le diste a nada. Fin de tu turno.");
                case IMPACTO -> System.out.println("\n* ¡IMPACTO! Le diste a una nave enemiga. Vuelve a disparar.");
                case HUNDIDO -> System.out.println("\n# ¡IMPACTO Y HUNDIDO! Destruiste una nave completa. Vuelve a disparar.");
            }
        }

    @Override
    public void mostrarAviso(String mensaje) {
        System.out.println("\n" + mensaje);
    }

    @Override
    public void mostrarError(String mensaje) {
        System.out.println("\n[ERROR] " + mensaje);
    }

    @Override
    public void mostrarGanador(String nombre) {
        System.out.println("\n==========================================");
        System.out.println("  ¡FELICITACIONES, " + nombre.toUpperCase() + "!");
        System.out.println("  Ganaste la partida de Batalla Naval.");
        System.out.println("==========================================\n");
    }

    @Override
    public void limpiarPantalla() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    @Override
    public void esperarEnter() {
        System.out.print("\nPresione Enter para continuar...");
        this.scanner.nextLine();
    }

    @Override
    public void dibujarTablero(estadoDelTablero[][] tablero, String titulo) {
        System.out.println("\n=== " + titulo + " ===");

        System.out.print("   ");
        for (int col = 1; col <= tablero[0].length; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();

        for (int i = 0; i < tablero.length; i++) {
            char letraFila = (char) ('A' + i);
            System.out.printf("%c  ", letraFila);

            for (int j = 0; j < tablero[i].length; j++) {
                char simbolo = obtenerSimbolo(tablero[i][j]);
                System.out.printf(" %c ", simbolo);
            }
            System.out.println();
        }

        System.out.println("Referencias: [.] Vacio  [N] Nave  [~] Agua  [*] Impacto  [#] Hundido");
    }

    private char obtenerSimbolo(estadoDelTablero estado) {
        return switch (estado) {
            case VACIO -> '.';
            case NAVE -> 'N';
            case AGUA -> '~';
            case IMPACTO -> '*';
            case HUNDIDO -> '#';
        };
    }

}


