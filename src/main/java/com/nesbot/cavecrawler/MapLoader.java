package com.nesbot.cavecrawler;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapLoader {
    private int width = 0;
    private int height = 0;
    private int[][] map;

    public MapLoader(String fileName){
        loadArray(fileName);
    }
    //Used to read in the map csv files, the csv is read in column by column so that x and y can be used uniformly


    public void loadArray(String fileName) {
        List<List<Integer>> columns = new ArrayList<>();
        int expectedColumnCount = 0;
        int rowIndex = 0;
        InputStream input = getClass().getClassLoader().getResourceAsStream(fileName);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                int[] rowValues = parse(line);

                if (rowIndex == 0) {
                    expectedColumnCount = rowValues.length;
                    this.width = expectedColumnCount;
                    for (int i = 0; i < expectedColumnCount; i++) {
                        columns.add(new ArrayList<>());
                    }
                }
                for (int colIndex = 0; colIndex < rowValues.length; colIndex++) {
                    if (colIndex < expectedColumnCount) {
                        columns.get(colIndex).add(rowValues[colIndex]);
                    }
                }
                rowIndex++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.height = rowIndex;
        map = new int[columns.size()][];
        for (int i = 0; i < columns.size(); i++) {
            List<Integer> columnList = columns.get(i);
            map[i] = columnList.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();
        }
    }

    private static int[] parse(String line){
        String[] stringValues = line.split(",");
        try {
            return Arrays.stream(stringValues)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (NumberFormatException e) {
            return new int[0];
        }
    }

    public Tile[][] createTiles() {
        //Used when all the tiles are mapped the first time, creates each tile object in the tiles tile[][] object using generate tile and assign type
        Tile[][] tiles = new Tile[getWidth()][getHeight()];

        int x = 0;
        int y = 0;
        for (int i = 0; i < getWidth() * getHeight(); i++) {
            if (x == getWidth()) {
                y++;
                x = 0;
            }
            tiles[x][y] = new Tile(x * Constants.TILE_UNIT, y * Constants.TILE_UNIT);
            tiles[x][y].assignTileType(map[x][y]);
            tiles[x][y].generateTile();
            x++;
        }
        return tiles;
    }

    public void resetTiles(Tile[][] tiles){
        //Similar to map_all_tiles but only sets the images and tile properties, it doesn't create any new tiles
        int x = 0;
        int y = 0;
        for (int i = 0; i < getWidth() * getHeight(); i++) {
            if (x == getWidth()) {
                y++;
                x = 0;
            }
            tiles[x][y].setX( x * Constants.TILE_UNIT);
            tiles[x][y].setY( y * Constants.TILE_UNIT);
            tiles[x][y].assignTileType(map[x][y]);
            x++;
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
