import javafx.scene.Group;
import javafx.scene.image.*;



public class Tile {
    public final static int TILE_UNIT = 128;
    static Group root;
    private int x;
    private int y;
    static int exit_x = 0;
    static int exit_y = 0;
    ImageView tile_image = new ImageView(new Image("character-up.png"));
    boolean traversable;
    boolean damaging;
    int tile_type;
    static  Image rock1 = new Image("rock1.png");
    static  Image rock2 = new Image("rock2.png");
    static  Image flatground1 = new Image("flatground1.png");
    static  Image border = new Image("border.png");
    static  Image edge_bottom = new Image("edge-bottom.png");
    static  Image black = new Image("black.png");
    static  Image pickaxe = new Image("pickaxe.png");
    static  Image shovel = new Image("shovel.png");
    static  Image exit_open = new Image("exit-open.png");
    static  Image exit_closed = new Image("exit-closed.png");
    static  Image edge_right = new Image("edge-right.png");
    static  Image flatground2 = new Image("flatground2.png");
    static  Image flatground3 = new Image("flatground3.png");
    static  Image pickaxe_icon = new Image("pickaxe-icon.png");
    static  Image shovel_icon = new Image("shovel-icon.png");
    static  Image hole = new Image("hole.png");
    public tileType type;

























    public void generateTile() {
        //Used to generate a tile at the given X and Y coordinates
        tile_image.setX(x);
        tile_image.setY(y);
        root.getChildren().add(tile_image);
    }

    public static void mapAllTiles(ReadCSVFile map, Tile[][] tiles) {
        //Used when all the tiles are mapped the first time, creates each tile object in the tiles tile[][] object using generate tile and assign type
        int x = 0;
        int y = 0;
        for (int i = 0; i < map.width * map.height; i++) {
            if (x == map.width) {
                y++;
                x = 0;
            }
            tiles[x][y] = new Tile(x * TILE_UNIT, y * TILE_UNIT);
            tiles[x][y].assignTileType(map.map[x][y]);
            tiles[x][y].generateTile();
            tiles[x][y].tile_type = map.map[x][y];
            x++;
        }
    }

    public static void tileNextLevel(ReadCSVFile map, Tile[][] tiles){
        //Similar to map_all_tiles but only sets the images and tile properties, it doesn't create any new tiles
        int x = 0;
        int y = 0;
        for (int i = 0; i < map.width * map.height; i++) {
            if (x == map.width) {
                y++;
                x = 0;
            }
            tiles[x][y].x = x * TILE_UNIT;
            tiles[x][y].y = y * TILE_UNIT;
            tiles[x][y].assignTileType(map.map[x][y]);
        x++;
        }


    }

    public void assignTileType(int id){
        type = tileType.fromId(id);



        //Passed the number from the csv file and a specific tile, assigns all the properties according to the tile type from the csv file
        double random;
        switch (type) {
            case rock:
                //rock, randomly picks an image between rock1 and rock2
               random = Math.random();
                if(random > 0.75){
                    tile_image.setImage(rock2);
                } else{
                    tile_image.setImage(rock1);

                }
                traversable = false;
                damaging = false;
                break;
            case flatGround:
                //flatground, randomly picks an image between flatground1, flatground2 and flatground3
                random = Math.random();
                if(random > 0.95){
                    tile_image.setImage(flatground2);
                } else if( random < 0.05){
                    tile_image.setImage(flatground3);

                } else {
                    tile_image.setImage(flatground1);
                }
                traversable = true;
                damaging = false;

                break;
            case border:
                //border
                tile_image.setImage(border);
                traversable = false;
                damaging = false;
                break;
            case pickaxe:
                //pickaxe
                tile_image.setImage(pickaxe);
                traversable = true;
                damaging = false;
                break;
            case shovel:
                //shovel
                tile_image.setImage(shovel);
                traversable = true;
                damaging = false;
                break;
            case exitClosed:
                //exit closed, the location is saved so that the tile can be updated easily later
                exit_x = x/TILE_UNIT;
                exit_y = y/TILE_UNIT;
                tile_image.setImage(exit_closed);
                traversable = false;
                damaging = false;
                System.out.println(exit_x);
                System.out.println(exit_y);

                break;
            case exitOpen:
                //exit open
                tile_image.setImage(exit_open);
                traversable = true;
                damaging = false;
                break;
            case hole:
                //hole
                tile_image.setImage(hole);
                traversable = true;
                damaging = true;
                break;
        }
    }

    public void move(int x, int y) {
        //Move tile by x and y units
        this.x += x;
        this.y += y;

        tile_image.setX(this.x);
        tile_image.setY(this.y);
    }





    Tile(int x, int y){
        this.x = x;
        this.y = y;
    }
}
