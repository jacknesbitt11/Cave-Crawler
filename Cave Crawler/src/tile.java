import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.Paint;
import javafx.scene.image.*;



public class tile{
    static int TILE_UNIT = 128;
    static Group root;
    int x;
    int y;
    static int exit_x = 0;
    static int exit_y = 0;
    ImageView tile_image = new ImageView(new Image("lantern-up.png"));
    boolean traversable;
    int tile_type;
    static  Image rock = new Image("rock.png");
    static  Image flatground = new Image("flatground.png");
    static  Image border = new Image("border.png");
    static  Image edge_bottom = new Image("edge-bottom.png");
    static  Image black = new Image("black.png");
    static  Image lantern_up = new Image("lantern-up.png");
    static  Image pickaxe = new Image("pickaxe.png");
    static  Image shovel = new Image("shovel.png");
    static  Image exit_open = new Image("exit-open.png");
    static  Image exit_closed = new Image("exit-closed.png");





    public void generate_tile() {
        tile_image.setX(x);
        tile_image.setY(y);
        root.getChildren().add(tile_image);
    }

    public static void map_all_tiles(ReadCSVFile map, tile[][] tiles) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < map.width * map.height; i++) {
            if (x == map.width) {
                y++;
                x = 0;
            }
            tiles[x][y] = new tile(x * TILE_UNIT, y * TILE_UNIT);
            assign_tile_type(map.map[x][y], tiles[x][y]);
            tiles[x][y].generate_tile();
            tiles[x][y].tile_type = map.map[x][y];
            x++;
        }
    }

    public static void assign_tile_type(int type, tile tiles){
        switch (type) {
            case 1:
                tiles.tile_image.setImage(rock);
                tiles.traversable = false;
                break;
            case 2:
                tiles.tile_image.setImage(flatground);
                tiles.traversable = true;
                break;
            case 3:
                tiles.tile_image.setImage(border);
                tiles.traversable = false;
                break;
            case 4:
                tiles.tile_image.setImage(edge_bottom);
                tiles.traversable = false;
                break;
            case 5:
                tiles.tile_image.setImage(black);
                tiles.traversable = true;
                break;
            case 6:
                tiles.tile_image.setImage(lantern_up);
                tiles.traversable = true;
                break;
            case 7:
                tiles.tile_image.setImage(pickaxe);
                tiles.traversable = true;
                break;
            case 8:
                tiles.tile_image.setImage(shovel);
                tiles.traversable = true;
                break;
            case 9:
                exit_x = tiles.x/TILE_UNIT;
                exit_y = tiles.y/TILE_UNIT;
                tiles.tile_image.setImage(exit_closed);
                tiles.traversable = false;
                break;
            case 10:
                tiles.tile_image.setImage(exit_open);
                tiles.traversable = true;
                break;
        }
        tiles.tile_type = type;

    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;

        tile_image.setX(this.x);
        tile_image.setY(this.y);
    }





    tile(int x, int y){
        this.x = x;
        this.y = y;
    }
}
