package Parking.Models;

import javafx.scene.image.Image;

import java.util.Observable;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Vehiculo extends Observable implements Runnable{
    private Semaphore semaforoChecarEntrada;
    private EntradaEstacionamiento entrada;
    private Random random;
    private Image vehiculo;

    public Vehiculo(Semaphore semaforoChecarEntrada, EntradaEstacionamiento entrada, Image vehiculo) {
        this.semaforoChecarEntrada = semaforoChecarEntrada;
        this.entrada = entrada;
        this.vehiculo = vehiculo;
        random = new Random(System.currentTimeMillis());
    }

    public Image getVehiculo() {
        return vehiculo;
    }

    @Override
    public void run() {
            try {
                setChanged();
                notifyObservers("en cola");
                semaforoChecarEntrada.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Datos.numeroVehiculosEntrando < Datos.numeroEspacio) {
                entrada.verificarEntrada(true);
                setChanged();
                notifyObservers("entro");
                if (Datos.numeroEspaciosOcupados == Datos.numeroEspacio) {
                    System.out.println("Estacionamiento lleno");
                    semaforoChecarEntrada.drainPermits(); //buscar que es
                }else{
                    semaforoChecarEntrada.release();
                }
                try {
                    Thread.sleep(random.nextInt(4000) + 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                entrada.verificarEntrada(false);
                setChanged();
                notifyObservers("salio");

                //Sirve para cuando se lleno el estacionamiento y dejo sin permisos;
                if(semaforoChecarEntrada.availablePermits()==0) {
                    semaforoChecarEntrada.release();
                }
            }
        }
}
