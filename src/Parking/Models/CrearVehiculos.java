package Parking.Models;

import java.util.Random;

public class CrearVehiculos extends Thread{
    private Vehiculo[] vehiculos;
    private Random random;

    public CrearVehiculos(Vehiculo[] vehiculos) {
        this.vehiculos = vehiculos;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run(){
        Vehiculo vehiculo;
        for(int i = 0; i< Datos.numeroVehiculos; i++){
            try {
                vehiculo = vehiculos[i];
                new Thread(vehiculo,"Vehiculo "+i).start();
                Thread.sleep(random.nextInt(400)+100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
