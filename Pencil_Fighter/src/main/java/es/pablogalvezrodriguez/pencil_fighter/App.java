package es.pablogalvezrodriguez.pencil_fighter;

import java.net.URISyntaxException;
import java.net.URL;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;



/**
 * JavaFX App
 */
public class App extends Application {
    
    
    int personajeVelAct = 0;
    int personajeVelActY = 0;
    int luchadorPosX = 100;
    int luchadorPosY = 340;
    int rotacionLuchador = 0;
    int j2PosX = 550;
    int j2PosY = 340;
    int velJ2 = 0;
    boolean pulsIzq = false;
    boolean pulsDer = false;
    boolean pulsArr = false;
    boolean pulsAbj = false;
    boolean pulsW = false;
    boolean pulsA = false;
    boolean pulsD = false;
    boolean salto = false;
    boolean teclaK = false; 
    boolean disparando = false;
    boolean disparandoj2 = false;
    
    boolean empate = false;
    
    int posDisparo1X = -30;
    int posDisparo1Y = -60;
    int velDisparo1 = 0;
    
    int posDisparo2X = -690;
    int posDisparo2Y = -60;
    int velDisparo2 = 0;
    
    int vidaj1 = 1;
    int vidaj2 = 1;
    
    int animacionMuerteJ1 = -30;
    
    int hboxPosY = 100;
    int animacionTexto1vel = 0;

    int transicionVel = 0;
    int transicionPosY = 0;
    boolean comienzo = true;
    

    @Override
    public void start(Stage stage) {
        
        //audio
        URL urlAudio = getClass().getResource("/sound/battle loop.mp3");
        if(urlAudio != null) {
            try {
                AudioClip battleLoop = new AudioClip(urlAudio.toURI().toString());
                battleLoop.play();
            } catch (URISyntaxException ex) {
                System.out.println("Error en el formato de ruta de archivo de audio");
            }            
        } else {
            System.out.println("No se ha encontrado el archivo de audio");
        }
        
        //escena
        Pane root = new Pane();
        Scene scene = new Scene(root, 720, 480);
        stage.setTitle("Pencil Fighter");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
        
        //iniciar el fondo
        Image fondo = new Image(getClass().getResourceAsStream("/images/fondo.png"));
        ImageView verFondo = new ImageView(fondo);
        root.getChildren().add(verFondo);
        
        //iniciar imagenes
        Image j2Idle = new Image(getClass().getResourceAsStream("/images/j2_idle.png"));
        Image j2Walk1 = new Image(getClass().getResourceAsStream("/images/j2_walk1.png"));
        Image j2Walk2 = new Image(getClass().getResourceAsStream("/images/j2_walk2.png"));
        Image j2Shoot = new Image(getClass().getResourceAsStream("/images/j2_shoot.png"));
        Image j2Dead = new Image(getClass().getResourceAsStream("/images/j2_dead.png"));
        Image end = new Image(getClass().getResourceAsStream("/images/end.png"));
        
        ImageView animacion = new ImageView(j2Idle);
        root.getChildren().add(animacion);
        
        ImageView endanim = new ImageView(end);
        root.getChildren().add(endanim);
        
        
        
        //iniciar partes del personaje
        Circle cabeza = new Circle(7, 2, 15); //cabeza
        Rectangle torso = new Rectangle(0, 15, 5, 30); //torso
        Rectangle antebrazoI = new Rectangle(1, 15, 4, 15); //ANTEBRAZO IZQUIERDO
        Rectangle brazoI = new Rectangle(-12, 23, 4, 15); //BRAZO IZQUIERDO
        Rectangle antebrazoD = new Rectangle(2, 20, 4, 15); //ANTEBRAZO DERECHO
        Rectangle brazoD = new Rectangle(17, 22, 4, 14); //BRAZO DERECHO
        Rectangle piernaI1 = new Rectangle(0, 42, 4, 14); //piernaizq1
        Rectangle piernaI2 = new Rectangle(-14, 44, 4, 16); //piernaizq2
        Rectangle piernaD1 = new Rectangle(4, 45, 4, 14); //piernader1
        Rectangle piernaD2 = new Rectangle(15, 44, 4, 16); //piernader2
        
        
        
        //ROTACION ANTEBRAZO IZQUIERDO
        Rotate antebrazoIrot = new Rotate();
        antebrazoIrot.setPivotX(1);
        antebrazoIrot.setPivotY(15);
        antebrazoIrot.setAngle(70);
        antebrazoI.getTransforms().add(antebrazoIrot);
        
        //ROTACION BRAZO IZQUIERDO
        //Rotate brazoIrot = new Rotate();
        Rotate brazoIrot = new Rotate();
        brazoIrot.setPivotX(-12);
        brazoIrot.setPivotY(23);
        brazoIrot.setAngle(-30);
        brazoI.getTransforms().add(brazoIrot);
        
        //ROTACION ANTEBRAZO DERECHO
        Rotate antebrazoDrot = new Rotate();
        antebrazoDrot.setPivotX(2);
        antebrazoDrot.setPivotY(20);
        antebrazoDrot.setAngle(-30);
        antebrazoD.getTransforms().add(antebrazoDrot);

        //ROTACION BRAZO DERECHO
        Rotate brazoDrot = new Rotate();
        brazoDrot.setPivotX(17);
        brazoDrot.setPivotY(22);
        brazoDrot.setAngle(30);
        brazoD.getTransforms().add(brazoDrot);
        
        //ROTACION PIERNA IZQ 1
        Rotate piernaI1rot = new Rotate();
        piernaI1rot.setPivotX(0);
        piernaI1rot.setPivotY(42);
        piernaI1rot.setAngle(80);
        piernaI1.getTransforms().add(piernaI1rot);
        
        //ROTACION PIERNA IZQ 1
        Rotate piernaD1rot = new Rotate();
        piernaD1rot.setPivotX(4);
        piernaD1rot.setPivotY(45);
        piernaD1rot.setAngle(-80);
        piernaD1.getTransforms().add(piernaD1rot);
        
        
        // "FXMan" (nombre del j1) grupo
        Group FXMan = new Group();
        FXMan.getChildren().add(cabeza);
        FXMan.getChildren().add(torso);
        FXMan.getChildren().add(antebrazoI);
        FXMan.getChildren().add(brazoI);
        FXMan.getChildren().add(antebrazoD);
        FXMan.getChildren().add(brazoD);
        FXMan.getChildren().add(piernaI1);
        FXMan.getChildren().add(piernaI2);
        FXMan.getChildren().add(piernaD1);
        FXMan.getChildren().add(piernaD2);
        
        //POSICION GRUPO
        FXMan.setLayoutX(luchadorPosX);
        FXMan.setLayoutY(luchadorPosY);
        
        //ROTACION GRUPO
        Rotate FXManRota = new Rotate();
        
        FXManRota.setAngle(0);
        FXMan.getTransforms().add(FXManRota);
        
        
        //proyectil J1 1
        Circle disparo1J1 = new Circle(0, 0, 7); 
        //grupo proyectil j1 1
        Group disparo1J1grupo = new Group();
        disparo1J1grupo.getChildren().add(disparo1J1);
        disparo1J1grupo.setLayoutX(posDisparo1X);
        disparo1J1grupo.setLayoutY(posDisparo1Y);
        
        root.getChildren().add(FXMan);
        
        
        //j2
        
        Rectangle J2 = new Rectangle(0, 0, 60, 60);
        J2.setFill(Color.TRANSPARENT);
        
        Group jugador2 = new Group();
        jugador2.getChildren().add(J2);
        jugador2.getChildren().add(animacion);
        jugador2.setLayoutX(j2PosX);
        jugador2.setLayoutY(j2PosY);
        root.getChildren().add(jugador2);
        
        //proyectil J2 1
        Circle disparo1J2 = new Circle(0, 0, 7); 
        //grupo proyectil j2 1
        Group disparo1J2grupo = new Group();
        disparo1J2grupo.getChildren().add(disparo1J2);
        disparo1J2grupo.setLayoutX(posDisparo2X);
        disparo1J2grupo.setLayoutY(posDisparo2Y);
        
        
        //suelos
        Rectangle suelo = new Rectangle(0, 400, 720, 5);
        Rectangle suelo2 = new Rectangle(0, 300, 720, 5);
        Rectangle suelo3 = new Rectangle(0, 200, 720, 5);
        
        //texto de quien gana
        HBox quienGana = new HBox();
        quienGana.setLayoutY(hboxPosY);
        quienGana.setMinWidth(720);
        quienGana.setSpacing(100);
        quienGana.setAlignment(Pos.CENTER);
        
        Text textQuienGana = new Text();
        textQuienGana.setText("nadie está ganando");
        textQuienGana.setFont (new Font ("Impact", 40));
        textQuienGana.setFill(Color.BLACK);
        
        quienGana.getChildren().add(textQuienGana);
        
        
        
        //texto y pantalla de reinicio
        
        Text reintentar = new Text();
        reintentar.setLayoutY(270);
        reintentar.setLayoutX(200);
        reintentar.setText("¿Volver a jugar?");
        reintentar.setFont (new Font ("Impact", 50));
        reintentar.setFill(Color.BLACK);
        
        Group animacionTransicion = new Group();
        animacionTransicion.getChildren().add(endanim);
        animacionTransicion.getChildren().add(reintentar);
        
        
        //get children
        root.getChildren().add(suelo);
        root.getChildren().add(suelo2);
        root.getChildren().add(suelo3);
        root.getChildren().add(disparo1J1grupo);
        root.getChildren().add(disparo1J2grupo);
        root.getChildren().add(quienGana);
        root.getChildren().add(animacionTransicion);
        
        
        Timeline animacionLucha = new Timeline(
            new KeyFrame(Duration.seconds(0.017), (var ae) -> {
                
                //animacion personajes y disparos j1 y j2
                luchadorPosX += personajeVelAct;
                posDisparo1X += velDisparo1; 
                
                j2PosX += velJ2;
                posDisparo2X += velDisparo2;
                
                FXMan.setLayoutX(luchadorPosX);
                FXMan.setLayoutY(luchadorPosY);
                
                disparo1J1grupo.setLayoutX(posDisparo1X);
                disparo1J1grupo.setLayoutY(posDisparo1Y);
                
                jugador2.setLayoutX(j2PosX);
                jugador2.setLayoutY(j2PosY);
                
                disparo1J2grupo.setLayoutX(posDisparo2X);
                disparo1J2grupo.setLayoutY(posDisparo2Y);
                
                //colisiones
                
                Shape colisionJ1 = Shape.intersect(cabeza, disparo1J2);
                boolean j1NoDisparado = colisionJ1.getBoundsInLocal().isEmpty();
                
                Shape colisionJ2 = Shape.intersect(J2, disparo1J1);
                boolean j2NoDisparado = colisionJ2.getBoundsInLocal().isEmpty();
                
                
                //transicion reinicio
                hboxPosY += animacionTexto1vel;
                
                transicionPosY += transicionVel;
                
                animacionTransicion.setLayoutY(transicionPosY);
                
                
                System.out.println(vidaj1);
                System.out.println(vidaj2);
                //System.out.println(posDisparo1X);
                //System.out.println(posDisparo1Y);
                //System.out.println(luchadorPosX);
                //System.out.println(luchadorPosY);
                //System.out.println(posDisparo2X);
                //System.out.println(posDisparo2Y);
                //System.out.println(j2PosX);
                //System.out.println(j2PosY);
                //System.out.println(hboxPosY);
                //System.out.println(transicionPosY);
                
                
                //transicion de reinicio sube para arriba
                if (comienzo == true) {
                    transicionVel = -30;
                    reintentar.setFill(Color.BLACK);
                    if (transicionPosY < -750) {
                        transicionVel = 0;
                    }
                    
                }
                
                
                //animaciones j1
                if (pulsDer == true) {
                    
                    if (luchadorPosX/20 % 2 == 0) {
                        FXManRota.setAngle(10);
                        //System.out.println("pulsando derecha");
                    } else {
                        FXManRota.setAngle(-10);
                        //System.out.println("pulsando derecha2");
                    }
                    
                } else {
                    
                    //FXManRota.setAngle(0);
                    //System.out.println("pulsando derecha es falso");
                }
                
                if (pulsIzq == true) {
                    
                    if (luchadorPosX/20 % 2 == 0) {
                        FXManRota.setAngle(10);
                        //System.out.println("pulsando izquierda");
                    } else {
                        FXManRota.setAngle(-10);
                        //System.out.println("pulsando izquierda2");
                    }
                } else {
                       
                }
                
                //animaciones j2
                if (pulsA == true) {
                    if (j2PosX/20 % 2 == 0) {
                        animacion.setImage(j2Walk1);
                    } else {
                        animacion.setImage(j2Walk2);
                    }
                }
                
                if (pulsD == true) {
                    if (j2PosX/20 % 2 == 0) {
                        animacion.setImage(j2Walk1);
                    } else {
                        animacion.setImage(j2Walk2);
                    }
                }
                
                //reinicio y bloqueo de los disparos
                if (posDisparo1X >= 720) {
                        posDisparo1X = -30;
                        posDisparo1Y = -60;
                        velDisparo1 = 0;
                        disparando = false;
                    }
                if (posDisparo2X <= 0) {
                        posDisparo2X = -690;
                        posDisparo2Y = -60;
                        velDisparo2 = 0;
                        disparandoj2 = false;
                    }
                
                //si j2 es disparado
                if (j2NoDisparado == false) {
                    vidaj2 = 0;
                    velJ2 = 0;
                }
                
                //si j1 es disparado
                if (j1NoDisparado == false) {
                    vidaj1 = 0;
                    personajeVelAct = 0;
                    FXManRota.setAngle(-30);
                } 
                
                //test de si el cambio de texto funcionaba, ignorar
                if (vidaj1 == 1 && vidaj2 == 1) {
                    empate = false;
                    textQuienGana.setText("nadie GANA");
                    hboxPosY = -50;
                    quienGana.setLayoutY(hboxPosY);
                }
                
                //texto si el j1 gana
                if (vidaj2 == 0 && empate == false) {
                    textQuienGana.setText("J1 GANA");
                    animacion.setImage(j2Dead);
                    quienGana.setLayoutY(hboxPosY);
                    animacionTexto1vel = 10;
                    
                }
                
                //texto si el j2 gana
                if (vidaj1 == 0 && empate == false) {
                    textQuienGana.setText("J2 GANA");
                    quienGana.setLayoutY(hboxPosY);
                    animacionTexto1vel = 10;
                    
                }
                
                //texto si hay empate (los dos pierden su vida)
                if (vidaj1 == 0 && vidaj2 == 0) {
                    empate = true;
                    if (empate == true) {
                        textQuienGana.setText("¡¡EMPATE!!");
                        quienGana.setLayoutY(hboxPosY);
                        animacionTexto1vel = 10;
                        
                    }
                }
                
                
                //la transicion se para
                if (hboxPosY > 70) {
                    animacionTexto1vel = 0;
                }
                
                
                if (comienzo == false) {
                    transicionVel = 30;
                    if (transicionPosY >= 0) {
                        transicionVel = 0;
                    }
                }
                
                    
            })
        );
        
        
        
        
        
        
        // pulsar botones
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){
                case LEFT:
                    if (vidaj1 == 1) {
                        personajeVelAct = -2;
                        pulsIzq = true;
                    }
                    
                    
                    
                    break;
                case RIGHT:
                    
                    if (vidaj1 == 1) {
                        personajeVelAct = 2;
                        pulsDer = true;
                    }
                    
                    break;
                    
                case UP:
                    if (vidaj1 == 1) {
                        pulsArr = true;
                        luchadorPosY = luchadorPosY - 100;
                        if (pulsArr = true && luchadorPosY < 140) {
                        luchadorPosY = 140;
                        }
                    }
                    break;
                        
                case DOWN:
                    if (vidaj1 == 1) {
                        pulsAbj = true;
                        luchadorPosY = luchadorPosY + 100;
                        if (pulsAbj = true && luchadorPosY > 340) {
                        luchadorPosY = 340;
                        }
                    }
                    break;
                    
                case K:
                    if (vidaj1 == 1) {
                        FXManRota.setAngle(50);
                        if (disparando == false) {
                            
                            velDisparo1 = 16;
                            posDisparo1X = luchadorPosX;
                            posDisparo1Y = luchadorPosY;
                            disparando = true;
                        } 
                    }
                    
                    break;
                    
                case W:
                    if (vidaj2 == 1) {
                        j2PosY = j2PosY - 100;
                        if (j2PosY < 140) {
                        j2PosY = 140;
                        } 
                    }
                    break;
                    
                case S:
                    if (vidaj2 == 1) {
                        j2PosY = j2PosY + 100;
                        if (j2PosY > 340) {
                        j2PosY = 340;
                        }
                    }
                    break;
                
                case A:
                    if (vidaj2 == 1){
                        pulsA = true;
                        velJ2 = -2;
                        break;
                    }
                    
                case D:
                    if (vidaj2 == 1) {
                        pulsD = true;
                        velJ2 = 2;
                        break;
                    }
                    
                case G:
                    if (vidaj2 == 1) {
                        if (disparandoj2 == false) {
                        
                            velDisparo2 = -16;
                            posDisparo2X = j2PosX;
                            posDisparo2Y = j2PosY;
                            animacion.setImage(j2Shoot);
                            disparandoj2 = true;
                        }
                    }
                break;
                
                case ENTER:
                    if (vidaj1 == 0 || vidaj2 == 0) {
                        reintentar.setFill(Color.WHITE);
                        comienzo = false;
                        if (transicionPosY >= 0) {
                            resetGame();
                        }
                    }
            }
            
            
        });
        
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch(event.getCode()){
            
            case LEFT:
                if (vidaj1 == 1) {
                    personajeVelAct = 0;
                    FXManRota.setAngle(0);
                    
                    pulsIzq = false;
                }
                break;
                
            case RIGHT:
                if (vidaj1 == 1) {
                    personajeVelAct = 0;
                    FXManRota.setAngle(0);
                    
                    pulsDer = false;
                }
                break;
                
            case UP:
                
                break;
                
            case DOWN:
                
                break;
            
            case A:
                if (vidaj2 == 1) {
                    velJ2 = 0;
                    pulsA = false;
                    animacion.setImage(j2Idle);
                }
                break;
            
            case D:
                if (vidaj2 == 1) {
                    velJ2 = 0;
                    pulsD = false;
                    animacion.setImage(j2Idle);
                }
                break;
            
            case G:
                if (vidaj2 == 1) {
                    animacion.setImage(j2Idle);
                }
                break;
                
            case K:
                if (vidaj1 == 1) {
                    FXManRota.setAngle(0);
                }
                break;
            
            case ENTER:
                animacion.setImage(j2Idle);
                FXManRota.setAngle(0);
            
            }
        });
        
        
        
        animacionLucha.setCycleCount(Timeline.INDEFINITE);
        animacionLucha.play();
        
    }

    
    private void resetGame() {
        luchadorPosX = 100;
        luchadorPosY = 340;
        j2PosX = 550;
        j2PosY = 340;
        posDisparo1X = -30;
        posDisparo1Y = -60;
        posDisparo2X = -690;
        posDisparo2Y = -60;
        vidaj1 = 1;
        vidaj2 = 1;
        animacionMuerteJ1 = -30;
        
        animacionTexto1vel = 0;
        comienzo = true;
        transicionVel = 30;
        
    }
    
    
    
    public static void main(String[] args) {
        launch();
    }

}