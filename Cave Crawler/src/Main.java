import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Arrays;


public class Main extends Application{
    static int TILE_UNIT = 128;

    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        //create groups, setup frame, in future I can create multiple scenes for the main menu etc.
        Group root = new Group();
        Scene scene = new Scene(root, 514, 512, Color.BLACK);
        stage.setTitle("Cave Crawler");
        Image icon = new Image("test_image128.png"); //Sets icon
        tile.root = root;


        stage.getIcons().add(icon);
        stage.setFullScreen(true);

        stage.setScene(scene);
        stage.show();
        game(scene);
    }
    public void game(Scene scene) {
        ReadCSVFile mapLoader = new ReadCSVFile("C:/Users/jack/Dropbox/Java Project/Cave Crawler/src/map.csv");
        String file_name = "test_image128.png";
        int x = 0;
        int y = 0;
        tile[][] tiles = new tile[mapLoader.width][mapLoader.height];
        player player = new player("player.png");
        for (int i = 0; i < mapLoader.width * mapLoader.height; i++) {
            if (x == mapLoader.width) {
                y++;
                x = 0;
            }


            switch (mapLoader.map[x][y]) {
                case 1:
                    tiles[x][y] = new tile(x * TILE_UNIT, y * TILE_UNIT, 1);
                    tiles[x][y].generate_tile();
                    x++;
                    break;
                case 2:
                    tiles[x][y] = new tile(x * TILE_UNIT, y * TILE_UNIT, 2);
                    tiles[x][y].generate_tile();
                    x++;
                    break;
                case 3:
                case 4:
                case 5:
                    tiles[x][y] = new tile(x * TILE_UNIT, y * TILE_UNIT, 3);
                    tiles[x][y].generate_tile();
                    x++;
                    break;
            }
        }

        player.generate_tile();


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                boolean t = true;
                switch (event.getCode()) {
                    case W:
                        if(player.collision(0, -1, tiles) ||player.y == 1){
                            player.update_image(1);
                            break;
                        }
                        player.y--;
                        for (tile[] tile_row : tiles) {
                            for (tile individual_tile : tile_row) {
                                individual_tile.move(0, TILE_UNIT);
                            }
                        };
                        player.update_image(1);
                        break;
                        case A:
                            if(player.collision(-1, 0, tiles) ||player.x == 1){
                                player.update_image(4);
                                break;
                            }
                            player.x--;
                            for (tile[] tile_row : tiles) {
                                for (tile individual_tile : tile_row) {
                                    individual_tile.move(TILE_UNIT, 0);
                                }
                            };
                            player.update_image(4);
                            break;
                        case S:
                            if(player.collision(0, 1, tiles) ||player.y == mapLoader.height-2){
                                player.update_image(3);
                                break;
                            }
                            player.y++;
                            for (tile[] tile_row : tiles) {
                                for (tile individual_tile : tile_row) {
                                    individual_tile.move(0, -TILE_UNIT);
                                }
                            };
                            player.update_image(3);
                            break;
                        case D:
                            if(player.collision(1, 0, tiles) ||player.x == mapLoader.width-2){
                                player.update_image(2);
                                break;
                            } else{
                                player.x++;
                                for (tile[] tile_row : tiles) {
                                    for (tile individual_tile : tile_row) {
                                        individual_tile.move(-TILE_UNIT, 0);
                                    }
                                }
                            };
                            player.update_image(2);
                            break;
                        case F:
                            player.lantern = !player.lantern;
                            player.update_image(1);


                            break;
                    }
                }
            });



    }



}