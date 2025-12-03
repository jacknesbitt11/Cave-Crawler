import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.Paint;
import javafx.scene.image.*;



public class tile{
    static Group root;
    int x;
    int y;
    String file_name;
    ImageView tile_image;
    Image tile_texture;
    boolean traversable;


    public void generate_tile() {
        tile_texture = new Image(file_name);
        tile_image = new ImageView(tile_texture);
        tile_image.setX(x);
        tile_image.setY(y);
        root.getChildren().add(tile_image);
    }


    public void move(int x, int y) {
        this.x += x;
        this.y += y;

        tile_image.setX(this.x);
        tile_image.setY(this.y);
    }

    tile(int x, int y, int tile_type){
        this.x = x;
        this.y = y;
        switch (tile_type){
            case 1:
                traversable = false;
                file_name = "rock.png";
                break;
            case 2:
                traversable = true;
                file_name = "flatground.png";
                break;
            case 3:
                traversable = true;
                file_name = "test_image128.png";
                break;
            case 4:
                traversable = true;
                file_name = "player.png";
                break;

        }
    }
}