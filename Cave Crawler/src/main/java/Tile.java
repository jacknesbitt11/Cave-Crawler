package com.nesbot.cavecrawler;

import javafx.scene.Group;
import javafx.scene.image.*;



public class Tile {
    protected static Group root;
    private int x;
    private int y;
    protected static int exit_x = 0;
    protected static int exit_y = 0;
    protected final ImageView tile_image = new ImageView(new Image("character-up.png"));
    protected boolean isTraversable;
    protected boolean isDamaging;
    public static final  Image rock1 = new Image("rock1.png");
    private static final  Image rock2 = new Image("rock2.png");
    private static final  Image flatground1 = new Image("flatground1.png");
    private static final  Image border = new Image("border.png");
    private static final  Image pickaxe = new Image("pickaxe.png");
    private static final  Image shovel = new Image("shovel.png");
    private static final  Image exit_open = new Image("exit-open.png");
    private static final  Image exit_closed = new Image("exit-closed.png");
    private static final  Image flatground2 = new Image("flatground2.png");
    private static final  Image flatground3 = new Image("flatground3.png");
    private static final  Image hole = new Image("hole.png");
    public TileType type;


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
            tiles[x][y] = new Tile(x * Constants.TILE_UNIT, y * Constants.TILE_UNIT);
            tiles[x][y].assignTileType(map.map[x][y]);
            tiles[x][y].generateTile();
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
            tiles[x][y].x = x * Constants.TILE_UNIT;
            tiles[x][y].y = y * Constants.TILE_UNIT;
            tiles[x][y].assignTileType(map.map[x][y]);
        x++;
        }


    }

    public void assignTileType(int id){
        type = TileType.fromId(id);



        double random;
        switch (type) {
            case ROCK:
               random = Math.random();
                if(random > 0.75){
                    tile_image.setImage(rock2);
                } else{
                    tile_image.setImage(rock1);

                }
                isTraversable = false;
                isDamaging = false;
                break;
            case FLAT_GROUND:
                random = Math.random();
                if(random > 0.95){
                    tile_image.setImage(flatground2);
                } else if( random < 0.05){
                    tile_image.setImage(flatground3);

                } else {
                    tile_image.setImage(flatground1);
                }
                isTraversable = true;
                isDamaging = false;

                break;
            case BORDER:
                tile_image.setImage(border);
                isTraversable = false;
                isDamaging = false;
                break;
            case PICKAXE:
                tile_image.setImage(pickaxe);
                isTraversable = true;
                isDamaging = false;
                break;
            case SHOVEL:
                tile_image.setImage(shovel);
                isTraversable = true;
                isDamaging = false;
                break;
            case EXIT_CLOSED:
                exit_x = x/Constants.TILE_UNIT;
                exit_y = y/Constants.TILE_UNIT;
                tile_image.setImage(exit_closed);
                isTraversable = false;
                isDamaging = false;
                System.out.println(exit_x);
                System.out.println(exit_y);

                break;
            case EXIT_OPEN:
                tile_image.setImage(exit_open);
                isTraversable = true;
                isDamaging = false;
                break;
            case HOLE:
                tile_image.setImage(hole);
                isTraversable = true;
                isDamaging = true;
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
