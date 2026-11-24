package Modelo;

public class Nave {
    private int tamano;
    private boolean hundida;
    private int cantidadDeAtaques;

    public Nave(int unTamano){
        //hacer los controles de tamaño de las posibles naves
        if(unTamano < 1 || unTamano > 5){
            throw new IllegalArgumentException("La nave debe estar dentro de los limites permitidos: 1 - 5");
        }
        tamano = unTamano;
        hundida = false;
    }

    public void registrarImpacto(){
        //hacer la logica para que si el ataque es igual al tamaño se hunda
        //¿se puede mejorar este metodo?
        if(cantidadDeAtaques < tamano){
            cantidadDeAtaques++;
            if(cantidadDeAtaques == tamano){
                hundida = true;
            }
        }


    }

    public boolean isHundida(){
        return this.hundida;
    }

    private String asignarNombre(int tamanio) {
        return switch (tamanio) {
            case 1 -> "Lancha";
            case 2 -> "Crucero";
            case 3 -> "Submarino";
            case 4 -> "Buque";
            case 5 -> "Portaaviones";
            default -> throw new IllegalArgumentException("Tamaño de nave inválido.");
        };
    }

    public String getNombre(){
        return asignarNombre(this.tamano);
    }
    public int getTamano(){return this.tamano;}
}
