package com.nesbot.cavecrawler;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class Main extends Application{
    int currentLevel = 1;
    public static void main(String[] args){
        launch(args);





    }
    @Override
    public void start(Stage stage) throws Exception {
        Sounds.loadSounds();
        Group level = new Group();
        Scene scene = new Scene(level, 1920, 1080, Color.BLACK);
        stage.setTitle("Cave Crawler");
        Image icon = Tile.rock1; //Sets icon
        Tile.root = level;
        stage.getIcons().add(icon);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        game(scene);
    }
    public void game(Scene scene) {
        //Main game loop
        ReadCSVFile mapLoader = new ReadCSVFile("map.csv");
        Tile[][] tiles = new Tile[mapLoader.width][mapLoader.height];
        Player player = new Player();
        Tile.mapAllTiles(mapLoader, tiles);

        player.generatePlayer();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //Used to get all the keyboard inputs from the user, W through D are movement controls, so they check collision and update the direction the player is facing
                switch (event.getCode()) {
                    case W:
                        if (player.collision(0, -1, tiles) || player.y == 1) {
                            player.updateImage(Direction.UP);
                            break;
                        }
                        player.playerMove(0, Constants.TILE_UNIT, tiles);
                        break;
                    case A:
                        if (player.collision(-1, 0, tiles) || player.x == 1) {
                            player.updateImage(Direction.LEFT);
                            break;
                        }
                        player.playerMove(Constants.TILE_UNIT, 0, tiles);
                        break;
                    case S:
                        if (player.collision(0, 1, tiles) || player.y == mapLoader.height - 2) {
                            player.updateImage(Direction.DOWN);
                            break;
                        }
                        player.playerMove(0, -Constants.TILE_UNIT, tiles);
                        break;
                    case D:
                        if (player.collision(1, 0, tiles) || player.x == mapLoader.width - 2) {
                            player.updateImage(Direction.RIGHT);
                            break;
                        }
                        player.playerMove(-Constants.TILE_UNIT, 0, tiles);
                        break;
                    //N and R are used to advance to the next level or restart the current level
                    case N:
                        if(player.win) {
                            if(currentLevel < Constants.NUMBER_OF_LEVELS){
                                currentLevel++;
                            }
                            mapLoader.map = mapLoader.load_array("map" + currentLevel + ".csv");
                            Tile.tileNextLevel(mapLoader, tiles);
                            player.reset(tiles);

                            break;
                        }
                    case R:
                        if(player.win || player.lose) {
                            Tile.tileNextLevel(mapLoader, tiles);
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