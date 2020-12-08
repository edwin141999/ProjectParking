package Parking.Models;


public class EntradaEstacionamiento {
    private final String[] lugares;

    public EntradaEstacionamiento() {
        lugares = new String[Datos.numeroEspacio];
        for(int i = 0; i< Datos.numeroEspacio; i++){
            lugares[i]=("vacio");
        }
    }

    public synchronized void verificarEntrada(boolean estado) {
        if(estado){
            System.out.println(Thread.currentThread().getName() + ": Entro");
            Datos.contadorEntrada++;
            Datos.numeroVehiculosEntrando++;
            Datos.numeroVehiculosSaliendo--;
            entradaEstacionamiento();

        }
        if(!estado){
            System.out.println(Thread.currentThread().getName() + ": Salio");
            salidaEstacionamiento();
            Datos.contadorSalida++;
            Datos.numeroVehiculosEntrando--;
        }
    }

    public void entradaEstacionamiento(){
        for(int i = 0; i< lugares.length; i++){
            if(lugares[i].compareTo("vacio")==0){
                //System.out.println("Se estaciono el " + Thread.currentThread().getName()+" en "+i);
                lugares[i]=Thread.currentThread().getName();
                Datos.numeroEspaciosOcupados++;
                Datos.espacioEstacionamiento =i;
                break;
            }
        }
    }

    public void salidaEstacionamiento(){
        for(int i = 0; i< lugares.length; i++){
            if(lugares[i].compareTo(Thread.currentThread().getName())==0){
                lugares[i]="vacio";
                Datos.numeroEspaciosOcupados--;
                Datos.espacioEstacionamiento =i;
                break;
            }
        }

    }
}
