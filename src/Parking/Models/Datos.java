package Parking.Models;

public class Datos {
    //Nunca cambian
    public static int numeroEspacio = 20;
    public static int numeroVehiculos = 100;

    //Cambian constantemente
    public static int numeroVehiculosEntrando = 0;
    public static int numeroEspaciosOcupados = 0;
    public static int numeroVehiculosSaliendo = numeroVehiculos;
    public static int espacioEstacionamiento = 0;

    //Extras
    public static int contadorEntrada = 0;
    public static int contadorSalida = 0;
}
