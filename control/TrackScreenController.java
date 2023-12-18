/* ***************************************************************
* Autor............: Joao Vitor Cangussu Bernardes Oliveira
* Matricula........: 202210559
* Inicio...........: 19/08/2023
* Ultima alteracao.: 07/10/2023
* Nome.............: Controlador da Tela de Percurso dos Trens
* Funcao...........: Controla os elementos gráficos do JavaFX
na tela de percursos do trem, como os controles de velocidade
dos trens, as imagens utilizadas, entre outros
*************************************************************** */
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ThreadTrain1;
import model.ThreadTrain2;

public class TrackScreenController implements Initializable{
  //atributos do FXML
  @FXML
  private ImageView resetButton;  
  @FXML
  private ImageView train1;
  @FXML
  private Slider train1SpeedSlider;
  @FXML
  private ImageView train2;  
  @FXML
  private Slider train2SpeedSlider;

  //instancia dos trens ja definidos como objetos da classe Train
  private static ThreadTrain1 brownTrain;
  private static ThreadTrain2 blueTrain;

  //Getters e setters
  double getTrain1Speed(){//obtem o valor do slider da velocidade do trem 1
    return train1SpeedSlider.getValue();
  }  
  double getTrain2Speed(){//obtem o valor do slider da velocidade do trem 2
    return train2SpeedSlider.getValue();
  }
  public ThreadTrain1 getBrownTrain(){//obtem o objeto da primeira thread
    return brownTrain;
  }
  public ThreadTrain2 getBlueTrain(){//obtem o objeto da segunda thread
    return blueTrain;
  }

  //Variavel de Travamento
  public static int lockVar1 = 0;
  public static int lockVar2 = 0;
  //Estrita Alternancia
  public static int turn1 = 0;
  public static int turn2 = 0;
  //Solucao de Peterson
  public static int turnP1;
  public static boolean[] interesse1 = {false, false};
  public static int turnP2;
  public static boolean[] interesse2 = {false, false};
  
  
  /* ***************************************************************
  * Metodo: setInitialPositions
  * Funcao: Carrega e abre a janela para que o usuario possa escolher
  * a posicao inicial dos trens
  * Parametros: Nao recebe parâmetros
  * Retorno: void
  *************************************************************** */
  public void setInitialPositions() throws IOException{
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ChoosePositionScreenController.class.getResource("/view/choosePositionScreen.fxml"));//carrega o FXML da tela de escolha de posicao
    AnchorPane page = (AnchorPane) loader.load();//cria uma AnchorPane para receber os atributos FXML carregados
    Stage dialogStage = new Stage();//inicia o stage
    dialogStage.setTitle("Escolha a Posicao Inicial");//define o titulo da pagina
    dialogStage.initStyle(StageStyle.UNDECORATED);//retira a decoracao da pagina para que o usuario nao feche a janela sem escolher uma opcao
    dialogStage.initModality(Modality.APPLICATION_MODAL);//nao deixa que o usuario interaja com outra tela enquanto essa estiver aberta
    Scene scene = new Scene(page);//cria uma cena com o FXML atribuido a AnchorPane
    dialogStage.setScene(scene);//atribui a cena ao stage
    ChoosePositionScreenController controller = loader.getController();//carrega o controller do FXML carregado
    controller.setDialogStage(dialogStage);//seta o stage no controller da tela
    dialogStage.showAndWait();//mostra o stage
  }//fim do metodo setInitialPositions 

  /* ***************************************************************
  * Metodo: resetTrains
  * Funcao: Configura o botao de resetar para escolher uma nova
  * posicao dos trens quando acionado
  * Parametros: MouseEvent event - evento de quando o botao eh pressionado
  * Retorno: void
  *************************************************************** */
  @FXML
  void resetTrains(MouseEvent event) {
    brownTrain.getMovimentationTimer().stop();
    blueTrain.getMovimentationTimer().stop();
    try{
      setInitialPositions();//funcao para ir para a tela de escolha de posicao inicial
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
    ChoosePositionScreenController cPSC = new ChoosePositionScreenController();//cPSC eh uma instancia do controller da tela de escolha de posicao
    //instancia os trens com as novas posicoes escolhidas pelo usuario
    brownTrain = new ThreadTrain1(cPSC.getInitialPosition1(), train1, train1SpeedSlider, cPSC.getSolucao());
    blueTrain = new ThreadTrain2(cPSC.getInitialPosition2(), train2, train2SpeedSlider, cPSC.getSolucao());
    //retorna a velocidade dos trens para 20 por cento do slider
    train1SpeedSlider.setValue(20);
    train2SpeedSlider.setValue(20);
    //inicia as threads
    brownTrain.start();
    blueTrain.start();
    //resetam todas as variaveis utilizadas nas solucoes
    lockVar1 = 0;
    lockVar2 = 0;
    turn1 = 0;
    turn2 = 0;
    interesse1[0] = false;
    interesse1[1] = false;
    interesse2[0] = false;
    interesse2[1] = false;

  }//fim do metodo resetTrains

  //metodo initialize para definir configuracoes iniciais da tela
  @Override
  public void initialize(URL location, ResourceBundle resources){
    try{
      setInitialPositions();//funcao para ir para a tela de escolha de posicao inicial
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
    ChoosePositionScreenController cPSC = new ChoosePositionScreenController();//cPSC eh uma instancia do controller da tela de escolha de posicao
    //define uma velocidade inicial de 20 por cento do slider
    train1SpeedSlider.setValue(20);
    train2SpeedSlider.setValue(20);
    //instancia os trens passando a posicao inicial e a imagem de cada um
    brownTrain = new ThreadTrain1(cPSC.getInitialPosition1(), train1, train1SpeedSlider, cPSC.getSolucao());
    blueTrain = new ThreadTrain2(cPSC.getInitialPosition2(), train2, train2SpeedSlider, cPSC.getSolucao());
    //inicia as threads
    brownTrain.start();
    blueTrain.start();
  }//fim metodo initialize
}//fim da classe TrackScreenController