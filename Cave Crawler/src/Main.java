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
        Scene scene = new Scene(root, 1920, 1080, Color.BLACK);
        stage.setTitle("Cave Crawler");
        Image icon = new Image("rock.png"); //Sets icon
        tile.root = root;


        stage.getIcons().add(icon);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        game(scene);
    }
    public void game(Scene scene) {
        ReadCSVFile mapLoader = new ReadCSVFile("C:/Users/jack/Dropbox/Java Project/Cave Crawler/src/map.csv");
        String file_name = "test_image128.png";
        tile[][] tiles = new tile[mapLoader.width][mapLoader.height];
        player player = new player("lantern-up.png");
        tile.map_all_tiles(mapLoader, tiles);

        player.generate_tile();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        if (player.collision(0, -1, tiles) || player.y == 1) {
                            player.update_image(1);
                            break;
                        }
                        player.player_move(0, TILE_UNIT, tiles);
                        player.update_image(1);
                        break;
                    case A:
                        if (player.collision(-1, 0, tiles) || player.x == 1) {
                            player.update_image(4);
                            break;
                        }
                        player.player_move(TILE_UNIT, 0, tiles);
                        break;
                    case S:
                        if (player.collision(0, 1, tiles) || player.y == mapLoader.height - 2) {
                            player.update_image(3);
                            break;
                        }
                        player.player_move(0, -TILE_UNIT, tiles);
                        break;
                    case D:
                        if (player.collision(1, 0, tiles) || player.x == mapLoader.width - 2) {
                            player.update_image(2);
                            break;
                        }
                        player.player_move(-TILE_UNIT, 0, tiles);
                        break;
                     }
                }
            });




    }


}