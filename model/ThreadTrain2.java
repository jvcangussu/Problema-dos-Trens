/* ***************************************************************
* Autor............: Joao Vitor Cangussu Bernardes Oliveira
* Matricula........: 202210559
* Inicio...........: 20/09/2023
* Ultima alteracao.: 07/10/2023
* Nome.............: Classe ThreadTrem
* Funcao...........: Classe que descreve um trem da simulacao, 
com seus respectivos atributos, construtores, metodos, getters e 
setters, extendendo a classe Thread
*************************************************************** */
package model;

import control.TrackScreenController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class ThreadTrain2 extends Thread{
  //Atributos de um Train
  private ImageView imageView;//imageView eh uma imagem associada ao trem na interface
  private Slider speedSlider; //speedSlider eh o controlador de velocidade do trem
  private int initialPosition;//initialPosition eh a posicao inicial do trem escolhida pelo usuario
  private double speed;//speed eh a velocidade do trem atualizada manipulada pelo usuario
  private final double SPEED_RATE = 0.035;//SPEED_RATE eh a constante de taxa de velocidade
  //Solucao que sera usada
  private int solucao;
  //Numero do processo
  private int processo = 1;
  
  //Variavel de Travamento
  private boolean critZone1 = false;
  private boolean critZone2 = false;

  //Construtor da classe recebendo a posicao inicial e a imagem associada ao Train
  public ThreadTrain2(int initialPosition, ImageView imageView, Slider speedSlider, int solucao){
    this.initialPosition = initialPosition;
    this.imageView = imageView;
    this.speedSlider = speedSlider;
    //Switch para definir a posicao da imagem na interface analisando a posicao inicial do Train
    switch(this.initialPosition){
      case 2:{//Posicao superior direita
        //Define as posicoes X Y e a rotacao da imagem
        this.imageView.setLayoutX(449);
        this.imageView.setLayoutY(-135);
        this.imageView.setRotate(0);
        break;
      }//fim case 2
      case 4:{//Posicao inferior direita
        //Define as posicoes X Y e a rotacao da imagem
        this.imageView.setLayoutX(451);
        this.imageView.setLayoutY(624);
        this.imageView.setRotate(180);
        break;
      }//fim case 4
    }//fim do switch
    //Define a solucao usada
    this.solucao = solucao;
  }//fim do construtor

  private class MyTimer2 extends AnimationTimer {
        private ThreadTrain2 train;

        public MyTimer2(ThreadTrain2 train){
          this.train = train;
        }
        //handle eh o metodo da classe AnimationTimer que eh executado a cada frame    
        @Override
        public void handle(long a) {
          Platform.runLater(() -> {train.moveTrain();});
        }//fim do metodo handle
      }//fim da classe MyTimer
  
  AnimationTimer movimentationTimer = new MyTimer2(this);//instancia da classe MyTimer que controla o timer de animacao
  public AnimationTimer getMovimentationTimer(){
    return movimentationTimer;
  }
  
  @Override
  public void run(){
    movimentationTimer.start();   
  }

  /* ***************************************************************
    * Metodo: moveTrain
    * Funcao: Move um trem pelo trilho de acordo com sua posicao inicial
    * Parametros: Sem parametros
    * Retorno: void
    *************************************************************** */
  public void moveTrain(){
    double inc = SPEED_RATE * this.speedSlider.getValue();//inc eh a quantidade de pixels que sera incrementado nas posicoes X e Y das imagens dos trens a cada frame do timer de movimento proporcional a velocidade do trem definida pelo usuario
    //switch para definir o movimento do trem de acordo com a sua posicao inicial
    switch(this.getInitialPosition()){
      case 2:{//Descreve os movimentos do trem na posicao superior direita
        if(this.getImageView().getLayoutY() <= 6){
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() + inc);
        }
        //INICIO ZONA CRITICA 1
        if(this.getImageView().getLayoutY() > 6 && this.getImageView().getLayoutY() <= 85){
          if(!entraRC1(solucao) && !critZone1){//testa a condicao do metodo selecionado e se o trem ja passou da zona critica
            break;//se entrar na condicao o movimento do trem eh bloqueado nao entrando na zona critica
          }        
          this.getImageView().setRotate(45);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() + inc);
          this.getImageView().setLayoutX(this.getImageView().getLayoutX() - inc);
        }
        if(this.getImageView().getLayoutY() > 85 && this.getImageView().getLayoutY() <= 120){
          this.getImageView().setRotate(0);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() + inc);
        }
        if(this.getImageView().getLayoutY() > 120 && this.getImageView().getLayoutY() <= 187){
          this.getImageView().setRotate(45);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() + inc);
          this.getImageView().setLayoutX(this.getImageView().getLayoutX() - inc);
        }
        //FIM ZONA CRITICA 1
        if(this.getImageView().getLayoutY() > 187 && this.getImageView().getLayoutY() <= 273){
          saiRC1(solucao);//executa os passos de saida da zona critica dos diferentes metodos 
          this.getImageView().setRotate(0);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() + inc);
        }
        //INICIO ZONA CRITICA 2
        if(this.getImageView().getLayoutY() > 273 && this.getImageView().getLayoutY() <= 345){
          if(!entraRC2(solucao) && !critZone2){//testa a condicao do metodo selecionado e se o trem ja passou da zona critica
            break;//se entrar na condicao o movimento do trem eh bloqueado nao entrando na zona critica
          }        
          this.getImageView().setRotate(315);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() + inc);
          this.getImageView().setLayoutX(this.getImageView().getLayoutX() + inc);
        }
        if(this.getImageView().getLayoutY() > 345 && this.getImageView().getLayoutY() <= 400){
          this.getImageView().setRotate(0);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() + inc);
        }
        if(this.getImageView().getLayoutY() > 400 && this.getImageView().getLayoutY() <= 477){
          this.getImageView().setRotate(315);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() + inc);
          this.getImageView().setLayoutX(this.getImageView().getLayoutX() + inc);
        }
        //FIM ZONA CRITICA 2
        if(this.getImageView().getLayoutY() > 477 && this.getImageView().getLayoutY() <= 624){
          saiRC2(solucao);//executa os passos de saida da zona critica dos diferentes metodos 
          this.getImageView().setRotate(0);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() + inc);
        }
        if(this.getImageView().getLayoutY() > 624){
          this.getImageView().setLayoutX(449);
          this.getImageView().setLayoutY(-135);
        }
        break;
      }//fim case 2
      case 4:{//Descreve os movimentos do trem na posicao inferior direita
        if(this.getImageView().getLayoutY() >= 479){
          this.getImageView().setRotate(180);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() - inc);
        }
        //INICIO ZONA CRITICA 2
        if(this.getImageView().getLayoutY() < 479 && this.getImageView().getLayoutY() >= 403){
          if(!entraRC2(solucao) && !critZone2){//testa a condicao do metodo selecionado e se o trem ja passou da zona critica
            break;//se entrar na condicao o movimento do trem eh bloqueado nao entrando na zona critica
          }
          this.getImageView().setRotate(135);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() - inc);
          this.getImageView().setLayoutX(this.getImageView().getLayoutX() - inc);
        }
        if(this.getImageView().getLayoutY() < 403 && this.getImageView().getLayoutY() >= 342){
          this.getImageView().setRotate(180);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() - inc);
        }         
        if(this.getImageView().getLayoutY() < 342 && this.getImageView().getLayoutY() >= 269){
          this.getImageView().setRotate(135);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() - inc);
          this.getImageView().setLayoutX(this.getImageView().getLayoutX() - inc);
        }
        //FIM ZONA CRITICA 2
        if(this.getImageView().getLayoutY() < 269 && this.getImageView().getLayoutY() >= 191){
          saiRC2(solucao);//executa os passos de saida da zona critica dos diferentes metodos 
          this.getImageView().setRotate(180);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() - inc);
        }
        //INICIO ZONA CRITICA 1
        if(this.getImageView().getLayoutY() < 191 && this.getImageView().getLayoutY() >= 120){
          if(!entraRC1(solucao) && !critZone1){//testa a condicao do metodo selecionado e se o trem ja passou da zona critica
            break;//se entrar na condicao o movimento do trem eh bloqueado nao entrando na zona critica
          }
          this.getImageView().setRotate(225);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() - inc);
          this.getImageView().setLayoutX(this.getImageView().getLayoutX() + inc);
        }
        if(this.getImageView().getLayoutY() < 120 && this.getImageView().getLayoutY() >= 81){
          this.getImageView().setRotate(180);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() - inc);
        }
        if(this.getImageView().getLayoutY() < 81 && this.getImageView().getLayoutY() >= 6){
          this.getImageView().setRotate(225);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() - inc);
          this.getImageView().setLayoutX(this.getImageView().getLayoutX() + inc);
        }
        //FIM ZONA CRITICA 1 
        if(this.getImageView().getLayoutY() < 6 && this.getImageView().getLayoutY() >= -135){
          saiRC1(solucao);//executa os passos de saida da zona critica dos diferentes metodos 
          this.getImageView().setRotate(180);
          this.getImageView().setLayoutY(this.getImageView().getLayoutY() - inc);
        }
        if(this.getImageView().getLayoutY() < -135){
          this.getImageView().setLayoutX(451);
          this.getImageView().setLayoutY(624);
        }
        break;
      }//fim do case 4
    }//fim do switch
  }//fim do metodo moveTrain

  /*
   * ***************************************************************
   * Metodo: entraRC1
   * Funcao: Aplica o metodo selecionado para definir se a thread pode ou nao
   * acessar a zona critica 1
   * Parametros: solucao eh a solucao escolhida
   * Retorno: boolean caso false nao pode entrar na zona critica e fica em loop,
   * caso true pode acessar a zona critica
   */
  private boolean entraRC1(int solucao) {
    switch (solucao) {//solucao eh a definida pelo usuario
      case 0: {//variavel de travamento
        if (TrackScreenController.lockVar1 == 1) {//lockVar1 eh uma variavel estatica definida na classe de controle
          return false;
        } else {
          TrackScreenController.lockVar1 = 1;
          critZone1 = true;//flag setada como true para indicar que o trem ja acessou a regiao critica e pode continuar acessando
          return true;
        }
      }
      case 1:{//estrita alternancia
        if (TrackScreenController.turn1 == 0) {//turn1 eh uma variavel estatica definida na classe de controle
          return false;
        } else {
          critZone1 = true;//flag setada como true para indicar que o trem ja acessou a regiao critica e pode continuar acessando
          return true;
        }
      }
      case 2: {//solucao de peterson
        int other;
        other = 1 - processo;
        TrackScreenController.interesse1[processo] = true;
        TrackScreenController.turnP1 = processo;
        if (TrackScreenController.turnP1 == processo && TrackScreenController.interesse1[other] == true) {//turnP1 e interesse1 sao variaveis estaticas definidas na classe de controle
          return false;
        } else {
          critZone1 = true;//flag setada como true para indicar que o trem ja acessou a regiao critica e pode continuar acessando
          return true;
        }
      }
      default:
        return true;
    }
  }

  /*
   * ***************************************************************
   * Metodo: saiRC1
   * Funcao: Aplica o metodo selecionado para realizar os controles quando uma
   * thread sai da zona critica 1
   * Parametros: solucao eh a solucao escolhida
   * Retorno: void
   */
  private void saiRC1(int solucao) {
    switch (solucao) {
      case 0: {//variavel de travamento
        TrackScreenController.lockVar1 = 0;//lockVar1 eh uma variavel estatica definida na classe de controle
        critZone1 = false;// flag setada como false para indicar que o trem ja passou pela regiao critica 1
        break;
      }
      case 1: {//estrita alternancia
        TrackScreenController.turn1 = 0;//turn1 eh uma variavel estatica definida na classe de controle
        critZone1 = false;//flag setada como false para indicar que o trem ja passou pela regiao critica 1
        break;
      }
      case 2: {//solucao de peterson
        TrackScreenController.interesse1[processo] = false;//interesse1 eh uma variavel estatica definida na classe de controle
        critZone1 = false;//flag setada como false para indicar que o trem ja passou pela regiao critica 1
        break;
      }
    }
  }

  /*
   * ***************************************************************
   * Metodo: entraRC2
   * Funcao: Aplica o metodo selecionado para definir se a thread pode ou nao
   * acessar a zona critica 2
   * Parametros: solucao eh a solucao escolhida
   * Retorno: boolean caso false nao pode entrar na zona critica e fica em loop,
   * caso true pode acessar a zona critica
   */
  private boolean entraRC2(int solucao) {
    switch (solucao) {//variavel de travamento
      case 0: {
        if (TrackScreenController.lockVar2 == 1) {//lockVar2 eh uma variavel estatica definida na classe de controle
          return false;
        } else {
          TrackScreenController.lockVar2 = 1;
          critZone2 = true;//flag setada como true para indicar que o trem ja acessou a regiao critica e pode continuar acessando
          return true;
        }
      }
      case 1: {//estrita alternancia
        if (TrackScreenController.turn2 == 0) {//turn2 eh uma variavel estatica definida na classe de controle
          return false;
        } else {
          critZone2 = true;//flag setada como true para indicar que o trem ja acessou a regiao critica e pode continuar acessando
          return true;
        }
      }
      case 2: {//solucao de peterson
        int other;
        other = 1 - processo;
        TrackScreenController.interesse2[processo] = true;//interesse2 eh uma variavel estatica definida na classe de controle
        TrackScreenController.turnP2 = processo;//turnP2 eh uma variavel estatica definida na classe de controle
        if (TrackScreenController.turnP2 == processo && TrackScreenController.interesse2[other] == true) {
          return false;
        } else {
          critZone2 = true;// flag setada como true para indicar que o trem ja acessou a regiao critica e pode continuar acessando
          return true;
        }
      }
      default:
        return true;
    }
  }

  /*
   * ***************************************************************
   * Metodo: saiRC2
   * Funcao: Aplica o metodo selecionado para realizar os controles quando uma
   * thread sai da zona critica 2
   * Parametros: solucao eh a solucao escolhida
   * Retorno: void
   */
  private void saiRC2(int solucao) {
    switch (solucao) {
      case 0: {//variavel de travamento
        TrackScreenController.lockVar2 = 0;//lockVar2 eh uma variavel estatica definida na classe de controle
        critZone2 = false;//flag setada como false para indicar que o trem ja passou pela regiao critica 2
        break;
      }
      case 1: {//estrita alternancia
        TrackScreenController.turn2 = 0;//turn2 eh uma variavel estatica definida na classe de controle
        critZone2 = false;//flag setada como false para indicar que o trem ja passou pela regiao critica 2
        break;
      }
      case 2: {//solucao de peterson
        TrackScreenController.interesse2[processo] = false;//interesse2 eh uma variavel estatica definida na classe de controle
        critZone2 = false;//flag setada como false para indicar que o trem ja passou pela regiao critica 2
        break;
      }
    }
  }

  //Getters e Setters dos atributos
  public int getInitialPosition(){
    return this.initialPosition;
  }
  public double getSpeed(){
    return this.speed;
  }
  public void setSpeed(double speed){
    this.speed = speed;
  }
  public ImageView getImageView(){
    return this.imageView;
  }
  public void setImageView(ImageView imageView){
    this.imageView = imageView;
  }
}//fim classe ThreadTrain

