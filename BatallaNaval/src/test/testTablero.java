package test;

import Modelo.Tablero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testTablero {

    private Tablero tablero;

    @BeforeEach
    void setUp() {
        int[] tamanios = {3, 2};
        tablero = new Tablero(2, tamanios);
    }


    @Test
    void comprobarQueLaCantidadDeNavesSeaMenorIgualACinco(){
        int[] tamanios = {3, 1, 2, 5, 2, 4};
        assertThrows(IllegalArgumentException.class, () -> {new Tablero(6, tamanios);
        });
    }

    @Test
    void comprobarQueLaCantidadDeNavesSeaMayorIgualACero(){
        int[] tamanios = {};
        assertThrows(IllegalArgumentException.class, () -> {new Tablero(0, tamanios);
        });
    }

    @Test
    void controlarQueLaColumnaSeaValida(){
        //tablero.registrarNuevaNave(unaNave1);
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            tablero.posicionarMisNaves("A", 0, "H");
        });
        assertEquals("La columna debe ser un valor entre 1 y 10", excepcion.getMessage());
    }

    @Test
    void controlarQueLaFilaSeaValida(){
        //tablero.registrarNuevaNave(unaNave1);
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            tablero.posicionarMisNaves("x", 1, "H");
        });
        assertEquals("La fila debe estar dentro del rango de A/a - J/j", excepcion.getMessage());
    }

    @Test
    void validarQueDosNavesNoEstenSuperpuestas(){
        tablero.posicionarMisNaves("a", 1, "v");
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            tablero.posicionarMisNaves("a", 1, "v");
        });
        assertEquals("La casilla ya fue utilizada.", excepcion.getMessage());
    }

    @Test
    void validarQueDosNavesNoEstenJuntasVertical(){
        //tablero.registrarNuevaNave(unaNave1);
        //tablero.registrarNuevaNave(unaNave2);

        tablero.posicionarMisNaves("a", 1, "v");
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            tablero.posicionarMisNaves("a", 2, "v");
        });
        //assertEquals("No pueden haber dos naves juntas.", excepcion.getMessage());
    }

    @Test
    void validarQueDosNavesNoEstenJuntasHorizontal(){
        tablero.posicionarMisNaves("a", 1, "h");
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            tablero.posicionarMisNaves("b", 2, "h");
        });
        //assertEquals("No pueden haber dos naves juntas.", excepcion.getMessage());
    }

    @Test
    void validarQueLaNaveNoSalgaDelMapaHorizontal(){
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            tablero.posicionarMisNaves("a", 9, "h");
        });
        //assertEquals("No pueden haber dos naves juntas.", excepcion.getMessage());
    }

    @Test
    void validarQueLaNaveNoSalgaDelMapaVertical(){
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            tablero.posicionarMisNaves("i", 1, "v");
        });
        //assertEquals("No pueden haber dos naves juntas.", excepcion.getMessage());
    }

    @Test
    void verificarQueLasPuntasNoSeToquenHorizontal(){
        tablero.posicionarMisNaves("a", 1, "h");
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            tablero.posicionarMisNaves("a", 4, "h");
        });
        //assertEquals("No pueden haber dos naves juntas.", excepcion.getMessage());
    }

    @Test
    void verificarQueLasPuntasNoSeToquenVertical(){
        tablero.posicionarMisNaves("a", 1, "v");
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            tablero.posicionarMisNaves("d", 1, "v");
        });
        //assertEquals("No pueden haber dos naves juntas.", excepcion.getMessage());
    }

    @Test
    void verificarQueLasDiagonalesNoSeToquenHorizontal(){
        tablero.posicionarMisNaves("a", 1, "h");
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            tablero.posicionarMisNaves("b", 2, "h");
        });
        //assertEquals("No pueden haber dos naves juntas.", excepcion.getMessage());
    }

    @Test
    void verificarQueLasDiagonalesNoSeToquenVertical(){
        tablero.posicionarMisNaves("a", 1, "v");
        IllegalStateException excepcion = assertThrows(IllegalStateException.class, () -> {
            tablero.posicionarMisNaves("d", 1, "v");
        });
        //assertEquals("No pueden haber dos naves juntas.", excepcion.getMessage());
    }
}
