/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Simon Dao
 */
public class TicTacToe extends Application {
    
    int width = 600;
    int height = 600;
    int scale = 200;
    int count = 0;
    
    boolean running = true;
    boolean showWinScreen = false;
    
    GraphicsContext gc;
    Scene scene;
    String title = "Tic Tac Toe";
    String winner;
    
    int mouseX;
    int mouseY;
    
    boolean[][] Xmap = new boolean[3][3];
    boolean[][] Omap = new boolean[3][3];
    
    @Override
    public void start(Stage primaryStage) {
            
        StackPane layout = new StackPane();
        Canvas c = new Canvas(width,height);
        gc = c.getGraphicsContext2D();
        layout.getChildren().add(c);
        scene = new Scene(layout, width, height);
        
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keys ->{
            if(keys.getCode() == KeyCode.ESCAPE){
            primaryStage.close();
        }   
        });
        
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keys -> {
            if(keys.getCode() == KeyCode.SPACE) {
                showWinScreen = false;
                clearBoard(gc);
                System.out.println(showWinScreen);
                for(int i = 0;i<3;i++) {
                    for(int j = 0;j<3;j++) {
                        Omap[i][j] = false;
                        Xmap[i][j] = false;
                    }
                }
            }
        });
        
        scene.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, mouse ->
        {
            mouseX = (int)mouse.getX()/scale;
            mouseY = (int)mouse.getY()/scale;
            
            count++;
            if(count > 2) {
                count = 1;
            }
            
            if(count % 2 == 0 && Omap[mouseX][mouseY] == false) {
                Xmap[mouseX][mouseY] = true;
            } else {
                Omap[mouseX][mouseY] = true;
            }
        });
        
        setMap();
        
        new AnimationTimer(){
                long lastTick = 0;
                
           //TIMER     
                public void handle(long now){
                    
                if(running == true ){ 
                    
                    primaryStage.setScene(scene);
                    
                    if(lastTick == 0 ){
                        lastTick = now;
                        tick(gc);
                        
                        return;
                    }
                    if ( now - lastTick > 1000000000/60){
                        lastTick = now;
                        tick(gc);
                      
                    }
                }
                }
            }.start(); 
        
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void tick(GraphicsContext gc){
        gc.setFill(Color.GREY);
        gc.fillRoundRect(195, 0, 10, 600, 25, 25);
        gc.fillRoundRect(395, 0, 10, 600, 25, 25);
        gc.fillRoundRect(695, 0, 10, 600, 25, 25);
        
        gc.fillRoundRect(0, 195, 600, 10, 25, 25);
        gc.fillRoundRect(0, 395, 600, 10, 25, 25);
        gc.fillRoundRect(0, 695, 600, 10, 25, 25);
        
        drawMap(gc);
        checkIfWin();
        setWinScreen(gc);
    }

    void setMap() {
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
               Xmap[i][j] = false;
               Omap[i][j] = false;
            }
        }
    }
    
    void drawMap(GraphicsContext gc) {
        
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                if(Xmap[i][j] == true && Omap[mouseX][mouseY] == false) {
                    gc.setFill(Color.BLUE);
                    gc.setFont(new Font("",scale));
                    gc.fillText("X", mouseX*scale+40, mouseY*scale+170);
                }
                else if(Omap[i][j] == true && Xmap[mouseX][mouseY] == false) {
                    gc.setFill(Color.RED);
                    gc.setFont(new Font("",scale));
                    gc.fillText("O", mouseX*scale+30, mouseY*scale+170);
                }
            }
        }
    }
    
    void checkIfWin() {
        //check X map
        //check vertical
        for(int i=0;i<3;i++) {
            int Xstreak = 0;
            for(int j=0;j<3;j++) {
                if(Xmap[i][j]) {
                    Xstreak++;
                }
                
                if(Xstreak == 3) {
                    winner = "X";
                    showWinScreen = true;
                }
            }
        }
        //check horizontal
        for(int j=0;j<3;j++) {
            int Xstreak = 0;
            for(int i=0;i<3;i++) {
                if(Xmap[i][j]) {
                    Xstreak++;
                }
                if(Xstreak == 3) {
                    winner = "X";
                    showWinScreen = true;
                }
            }
        }
        //check left digonal
        int Xleft = 0;
        for(int i=0;i<3;i++) {
            if(Xmap[i][i]) {
                Xleft++;
            }
            if(Xleft == 3) {
                    winner = "X"; 
                    showWinScreen = true;
            }
        }
        //check right digonal
        int Xright = 0;
        int Xy = -1;
        for(int x=2;x>=0;x--) {
            Xy++;
            if(Xmap[x][Xy]) {
                Xright++;
            }
            if(Xright == 3) {
               System.out.println(winner);
                    winner = "X"; 
                    showWinScreen = true;
            }
        }
        
        //check O map
        // check vertical
        for(int i=0;i<3;i++) {
            int Ostreak = 0;
            for(int j=0;j<3;j++) {
                if(Omap[i][j]) {
                    Ostreak++;
                }
                if(Ostreak == 3) {
                    winner = "O";
                    showWinScreen = true;
                }
            }
        }
        //check horizontal
        for(int j=0;j<3;j++) {
            int Ostreak = 0;
            for(int i=0;i<3;i++) {
                if(Omap[i][j]) {
                    Ostreak++;
                }
                if(Ostreak == 3) {
                    winner = "O";
                    showWinScreen = true;
                }
            }
        }
        //check left digonal
        int left = 0;
        for(int i=0;i<3;i++) {
            if(Omap[i][i]) {
                left++;
            }
            if(left == 3) {
                    winner = "O"; 
                    showWinScreen = true;
            }
        }
        //check right digonal
        int Yright = 0;
        int Yy = -1;
        for(int x=2;x>=0;x--) {
            Yy++;
            if(Omap[x][Yy]) {
                Yright++;
            }
            if(Yright == 3) {
                    winner = "O"; 
                    showWinScreen = true;
            }
        }
    }
    
    void setWinScreen(GraphicsContext gc) {
        
        if(showWinScreen == true) {
            System.out.println(showWinScreen);
            gc.setFill(Color.CHARTREUSE);
            gc.fillRoundRect(100, 200, 400, 200, 40, 40);
        
            gc.setFill(Color.BLUE);
            gc.setFont(new Font("",100));
            gc.fillText(winner+" wins!", 130, 330);
        
            gc.setFont(new Font("",30));
            gc.fillText("press space to replay!", 140, 370);
        }
    }
    
    void clearBoard(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 600, 600);
        
        gc.setFill(Color.GREY);
        gc.fillRoundRect(195, 0, 10, 600, 25, 25);
        gc.fillRoundRect(395, 0, 10, 600, 25, 25);
        gc.fillRoundRect(695, 0, 10, 600, 25, 25);
        
        gc.fillRoundRect(0, 195, 600, 10, 25, 25);
        gc.fillRoundRect(0, 395, 600, 10, 25, 25);
        gc.fillRoundRect(0, 695, 600, 10, 25, 25);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
