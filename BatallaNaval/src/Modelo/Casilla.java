package Modelo;

public class Casilla {
    private boolean atacada;
    private Nave miNave;
    private boolean ocupada;

    public Casilla(){
        ocupada = false;
        atacada = false;
        miNave = null;
    }

    public boolean isAtacada(){
        return this.atacada;
    }

    public boolean isOcupada(){
        return this.ocupada;
    }

    public boolean isNaveHundida(){
        if(miNave == null){
            return false;
        }
        return this.miNave.isHundida();
    }

    public void ubicarNave(Nave unaNave){
        miNave = unaNave;
        ocupada = true;

    }

    public estadoEnLaCasilla ataque(){
        atacada = true;

        if(miNave != null){
            miNave.registrarImpacto();

            if(miNave.isHundida()){
                return estadoEnLaCasilla.HUNDIDO;
            }else{
                return estadoEnLaCasilla.IMPACTO;
            }

        }else{
            return estadoEnLaCasilla.AGUA;
        }
    }




}

