package com.nesbot.cavecrawler;
public enum TileType {
    ROCK(1),
    FLAT_GROUND(2),
    BORDER(3),
    PICKAXE(4),
    SHOVEL(5),
    EXIT_CLOSED(6),
    EXIT_OPEN(7),
    HOLE(8);
    private final int id;
    TileType(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public static TileType fromId(int id) {
        for (TileType type : TileType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }}
