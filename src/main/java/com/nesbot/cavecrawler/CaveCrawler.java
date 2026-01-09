package com.nesbot.cavecrawler;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class CaveCrawler extends Application{
    private int currentMap = 1;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Sounds.loadSounds();
        Group map = new Group();
        Scene scene = new Scene(map, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT, Color.BLACK);
        Image icon =  new Image("rock1.png");
        Tile.setRoot(map);
        stage.setTitle("Cave Crawler");
        stage.getIcons().add(icon);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        game(scene);
    }

    public void game(Scene scene) {
        //Main game loop
        MapLoader mapLoader = new MapLoader("map.csv");
        Tile[][] tiles = mapLoader.createTiles();
        Player player = new Player();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //Used to get all the keyboard inputs from the user, W through D are movement controls, so they check collision and update the direction the player is facing
                switch (event.getCode()) {
                    case W:
                        if (player.isCollision(0, -1, tiles) || player.getY() == 1) {
                            player.updateImage(Direction.UP);
                            break;
                        }
                        player.playerMove(0, Constants.TILE_UNIT, tiles);
                        break;
                    case A:
                        if (player.isCollision(-1, 0, tiles) || player.getX() == 1) {
                            player.updateImage(Direction.LEFT);
                            break;
                        }
                        player.playerMove(Constants.TILE_UNIT, 0, tiles);
                        break;
                    case S:
                        if (player.isCollision(0, 1, tiles) || player.getY() == mapLoader.getHeight() - 2) {
                            player.updateImage(Direction.DOWN);
                            break;
                        }
                        player.playerMove(0, -Constants.TILE_UNIT, tiles);
                        break;
                    case D:
                        if (player.isCollision(1, 0, tiles) || player.getX() == mapLoader.getWidth() - 2) {
                            player.updateImage(Direction.RIGHT);
                            break;
                        }
                        player.playerMove(-Constants.TILE_UNIT, 0, tiles);
                        break;
                    //N and R are used to advance to the next level or restart the current level
                    case N:
                        if(player.isWin()) {
                            if(currentMap < Constants.NUMBER_OF_LEVELS){
                                currentMap++;
                            }
                            mapLoader.loadMap("map" + currentMap + ".csv");
                            mapLoader.resetTiles(tiles);
                            player.reset(tiles);
                            break;
                        }
                    case R:
                        if(player.isWin() || player.isLose()) {
                            mapLoader.resetTiles(tiles);
                            player.reset(tiles);
                        }
                        break;
                    // F is used to break a boulder when the player is holding a pickaxe
                    case F:
                        player.breakBoulderOrFillHole(tiles);
                        break;
                }
            }
        });
    }
}