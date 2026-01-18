package com.nesbot.cavecrawler;

import javafx.scene.Group;
import javafx.scene.image.*;

public class Tile {
    private static Group root;
    private int x;
    private int y;
    protected static int exitX = 0;
    protected static int exitY = 0;
    private final ImageView tileImage = new ImageView(new Image("character-up.png"));
    protected boolean isTraversable;
    protected boolean isDamaging;
    private static final  Image rock1 = new Image("rock1.png");
    private static final  Image rock2 = new Image("rock2.png");
    private static final  Image flatGround1 = new Image("flatground1.png");
    private static final  Image border = new Image("border.png");
    private static final  Image pickaxe = new Image("pickaxe.png");
    private static final  Image shovel = new Image("shovel.png");
    private static final  Image exitOpen = new Image("exit-open.png");
    private static final  Image exitClosed = new Image("exit-closed.png");
    private static final  Image flatGround2 = new Image("flatground2.png");
    private static final  Image flatGround3 = new Image("flatground3.png");
    private static final  Image hole = new Image("hole.png");
    public TileType type;

    public Tile(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static void setRoot(Group root) {
        Tile.root = root;
    }

    public static Group getRoot() {
        return Tile.root;
    }

    public void setImage(Image image){
        tileImage.setImage(image);
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void show(){
        show(true);
    }

    public void show(boolean reset){
        if(reset) {
            tileImage.setX(0);
            tileImage.setY(0);
        }
        tileImage.toFront();
    }

    public void hide(){
        tileImage.toBack();
    }

    public void generateTile() {
        //Used to generate a tile at the given X and Y coordinates
        tileImage.setX(x);
        tileImage.setY(y);
        root.getChildren().add(tileImage);
    }

    public void assignTileType(int id){
        type = TileType.fromId(id);
        double random;

        switch (type) {
            case ROCK:
               random = Math.random();
                if(random > 0.75){
                    tileImage.setImage(rock2);
                } else{
                    tileImage.setImage(rock1);
                }
                isTraversable = false;
                isDamaging = false;
                break;
            case FLAT_GROUND:
                random = Math.random();
                if(random > 0.95){
                    tileImage.setImage(flatGround2);
                } else if( random < 0.05){
                    tileImage.setImage(flatGround3);
                } else {
                    tileImage.setImage(flatGround1);
                }
                isTraversable = true;
                isDamaging = false;
                break;
            case BORDER:
                tileImage.setImage(border);
                isTraversable = false;
                isDamaging = false;
                break;
            case PICKAXE:
                tileImage.setImage(pickaxe);
                isTraversable = true;
                isDamaging = false;
                break;
            case SHOVEL:
                tileImage.setImage(shovel);
                isTraversable = true;
                isDamaging = false;
                break;
            case EXIT_CLOSED:
                exitX = x/Constants.TILE_UNIT;
                exitY = y/Constants.TILE_UNIT;
                tileImage.setImage(exitClosed);
                isTraversable = false;
                isDamaging = false;
                break;
            case EXIT_OPEN:
                tileImage.setImage(exitOpen);
                isTraversable = true;
                isDamaging = false;
                break;
            case HOLE:
                tileImage.setImage(hole);
                isTraversable = true;
                isDamaging = true;
                break;
        }
    }

    public void move(int x, int y) {
        //Move tile by x and y units
        this.x += x;
        this.y += y;

        tileImage.setX(this.x);
        tileImage.setY(this.y);
    }
}