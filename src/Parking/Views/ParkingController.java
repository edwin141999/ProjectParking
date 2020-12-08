package Parking.Views;

import Parking.Main;
import Parking.Models.Datos;
import Parking.Models.CrearVehiculos;
import Parking.Models.EntradaEstacionamiento;
import Parking.Models.Vehiculo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class ParkingController implements Observer {
    private Main main;
    @FXML private AnchorPane canvas;
    @FXML private Label lbCant;
    @FXML private Label lbCantIn;
    @FXML private Label lbCantOut;
    @FXML private ImageView iVStatus;
    @FXML private ImageView iVNext;

    private ImageView[] espaciosEstacionamiento = new ImageView[Datos.numeroEspacio];
    private Vehiculo[] vehiculos = new Vehiculo[Datos.numeroVehiculos];
    private int numeroImagenes = 9;
    private Image[] imagenes = new Image[numeroImagenes];

    @FXML
    void startSimulation(ActionEvent event){
        CrearVehiculos crearVehiculos = new CrearVehiculos(vehiculos);
        crearVehiculos.start();
    }

    public Image getVehiculo(){
        Random random = new Random();
        Image image = imagenes[random.nextInt(numeroImagenes)];
        return image;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Vehiculo) {
            ImageView valor = espaciosEstacionamiento[Datos.espacioEstacionamiento];
            if(String.valueOf(arg).compareTo("en cola")==0){
                Platform.runLater(()->{
                    iVNext.setImage(((Vehiculo) o).getVehiculo());
                });
            }
            if(String.valueOf(arg).compareTo("entro")==0){

                Platform.runLater(()->{
                    iVStatus.setRotate(90);
                    iVStatus.setLayoutX(0);
                    iVStatus.setLayoutY(496);
                    iVStatus.setImage(((Vehiculo) o).getVehiculo());
                    valor.setImage(((Vehiculo) o).getVehiculo());
                    lbCantIn.setText(String.valueOf(Datos.contadorEntrada));
                    lbCant.setText(String.valueOf(Datos.numeroVehiculosSaliendo));
                });
            }
            if(String.valueOf(arg).compareTo("salio")==0){

                Platform.runLater(()->{
                    lbCantOut.setText(String.valueOf(Datos.contadorSalida));
                    iVStatus.setRotate(335);
                    iVStatus.setLayoutX(12);
                    iVStatus.setLayoutY(539);
                    iVStatus.setImage(((Vehiculo) o).getVehiculo());
                    valor.setImage(null);
                });
            }

        }

    }

    @FXML
    public void initialize(){
        //Se llena el arreglo de Imagenes
        String file = "file:src/Parking/Resources/";
        for(int x = 0; x< numeroImagenes; x++){
            imagenes[x] =  new Image(file+"Coche"+x+".png");
        }

        //Se llena el estacionamiento de ImageViews
        int j=0, k=0, l=0,m=0;
        Random posicion = new Random();

        for(int i = 0; i< Datos.numeroEspacio; i++){
            espaciosEstacionamiento[i]= new ImageView();
            //Columna 1
            if(i<4){
                espaciosEstacionamiento[i].setLayoutX(116);
                espaciosEstacionamiento[i].setLayoutY(247+(i*60));
                canvas.getChildren().add(espaciosEstacionamiento[i]);
                if(posicion.nextBoolean()==true){
                    espaciosEstacionamiento[i].setRotate(180.0);
                }
            }
            //Columna 2
            if(i>3 && i<8) {
                espaciosEstacionamiento[i].setLayoutX(266);
                espaciosEstacionamiento[i].setLayoutY(242+(j*60));
                canvas.getChildren().add(espaciosEstacionamiento[i]);
                if(posicion.nextBoolean()==true){
                    espaciosEstacionamiento[i].setRotate(180.0);
                }

                j++;
            }
            //Columna 3
            if(i>7 && i<12) {
                espaciosEstacionamiento[i].setLayoutX(377);
                espaciosEstacionamiento[i].setLayoutY(242+(k*60));
                canvas.getChildren().add(espaciosEstacionamiento[i]);
                if(posicion.nextBoolean()==true){
                    espaciosEstacionamiento[i].setRotate(180.0);
                }
                k++;
            }
            //Fila 1
            if(i>11 && i<16) {
                espaciosEstacionamiento[i].setLayoutX(535+(l*60));
                espaciosEstacionamiento[i].setLayoutY(258);
                canvas.getChildren().add(espaciosEstacionamiento[i]);
                if(posicion.nextBoolean()==true){
                    espaciosEstacionamiento[i].setRotate(270.0);
                }else{
                    espaciosEstacionamiento[i].setRotate(90);
                }
                l++;
            }
            //Fila 2
            if(i>15 && i<20) {
                espaciosEstacionamiento[i].setLayoutX(540+(m*60));
                espaciosEstacionamiento[i].setLayoutY(409);

                canvas.getChildren().add(espaciosEstacionamiento[i]);
                if(posicion.nextBoolean()==true){
                    espaciosEstacionamiento[i].setRotate(270.0);
                }else{
                    espaciosEstacionamiento[i].setRotate(90);
                }
                m++;
            }
            espaciosEstacionamiento[i].setFitWidth(95);
            espaciosEstacionamiento[i].setFitHeight(47);
        }

        Semaphore checarEntrada = new Semaphore(1);
        EntradaEstacionamiento entradaEstacionamiento = new EntradaEstacionamiento();

        for(int i = 0; i< Datos.numeroVehiculos; i++){
            Vehiculo vehiculo = new Vehiculo(checarEntrada,entradaEstacionamiento, getVehiculo());
            vehiculo.addObserver(this);
            vehiculos[i] = vehiculo;
        }

    }

    public void setMain(Main main) {
        this.main = main;
    }
}
