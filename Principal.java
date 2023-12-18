/* ***************************************************************
* Autor............: Joao Vitor Cangussu B Oliveira
* Matricula........: 202210559
* Inicio...........: 19/08/2023
* Ultima alteracao.: 01/09/2023
* Nome.............: Principal do Programa de Simulacao de Trens
* Funcao...........: Realiza as importacoes necessarias carrega o
fxml da primeira tela e inicia o programa
*************************************************************** */

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import control.TrackScreenController;//importando o controlador da tela principal para poder compilar

public class Principal extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/view/trackScreen.fxml"));//Carrega o fxml da tela primaria salvando na variavel root
    Scene scene = new Scene(root);//Cria uma nova cena definindo nela o fxml carregado

    stage.setTitle("Train Simulator 2023");//Define o titulo da janela da aplicacao
    stage.getIcons().add(new Image("/img/icone.png"));//Define o icone da aplicacao
    stage.setScene(scene);//Seta a cena com o fxml carregado no stage
    stage.setResizable(false);//Define a tela como nao redimensionavel
    stage.show();//Mostra o stage
  }

  public static void main(String[] args) throws Exception {
    launch(args);
  }
}//fim classe Principal