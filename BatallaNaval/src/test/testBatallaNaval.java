package test;

import Modelo.BatallaNaval;
import Modelo.Jugador;
import Modelo.Tablero;
import Modelo.estadoEnLaCasilla;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class testBatallaNaval {
    private BatallaNaval miJuego;
    @BeforeEach
    void setUp(){
        miJuego = new BatallaNaval();
        int[] tamanios = {3, 2};

        miJuego.agregarJugador("Facundo");
        miJuego.agregarJugador("Nayla");

        miJuego.cargarNaves(2, tamanios);


        // Naves del jugador 1
        miJuego.posicionarMisNaves("a", 1, "h");
        miJuego.posicionarMisNaves("c", 2, "h");
        miJuego.finalizarPosicionamientoCambioDeTurno();

        // Naves del jugador 2
        miJuego.posicionarMisNaves("a", 4, "v");
        miJuego.posicionarMisNaves("b", 1, "h");
        miJuego.finalizarPosicionamientoCambioDeTurno();
    }

    @Test
    void controlarDisparoAlAguaAlternarTurnos(){
        //Arranca Facundo
        estadoEnLaCasilla resultado1 = miJuego.realizarAtaque("d",1);
        assertEquals(estadoEnLaCasilla.AGUA, resultado1);
        assertEquals("Nayla", miJuego.getNombreJugadorActual());

        //Me toca a mi
        estadoEnLaCasilla resultado2 = miJuego.realizarAtaque("d",1);
        assertEquals(estadoEnLaCasilla.AGUA, resultado2);
        assertEquals("Facundo", miJuego.getNombreJugadorActual());
    }

    @Test
    void controlarDisparoAcertadoYMantenerTurno(){
        //Arranca Facundo
        estadoEnLaCasilla resultado1 = miJuego.realizarAtaque("a",4);
        assertEquals(estadoEnLaCasilla.IMPACTO, resultado1);
        assertEquals("Facundo", miJuego.getNombreJugadorActual());
    }

    @Test
    void controlarNaveHundidaYMantenerTurno(){
        //Sigue Facundo
        miJuego.realizarAtaque("a",4);
        miJuego.realizarAtaque("b",4);
        estadoEnLaCasilla resultado1 = miJuego.realizarAtaque("c",4);
        assertEquals(estadoEnLaCasilla.HUNDIDO, resultado1);
        assertEquals("Facundo", miJuego.getNombreJugadorActual());
    }

    @Test
    void controlarQueHayUnGanador(){
        //Sigue Facundo
        miJuego.realizarAtaque("a",4);
        miJuego.realizarAtaque("b",4);
        miJuego.realizarAtaque("c",4);

        miJuego.realizarAtaque("b",1);
        miJuego.realizarAtaque("b",2);

        assertThrows(IllegalStateException.class, () -> {miJuego.realizarAtaque("f",1);});
        assertEquals("Facundo", miJuego.getNombreJugadorActual());
    }

    @Test
    void controlarNoPoderDispararDosVecesEnLaMismaCasilla(){
        miJuego.realizarAtaque("a",4);
        assertThrows(IllegalStateException.class, () -> {miJuego.realizarAtaque("a",4);});
        assertEquals("Facundo", miJuego.getNombreJugadorActual());
    }




}
