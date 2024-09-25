package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.event.GraphicsObjectSpawnedEvent;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.stream.locatable.GraphicsObjectStream;
import org.powbot.api.rt4.stream.ops.ViewableOps;

import java.util.Arrays;
import java.util.Comparator;

public class SafeDistance extends Task {
    Tile[] safeTiles = {
            new Tile(1290, 10093, 0),
            new Tile(1289, 10094, 0),
            new Tile(1289, 10099, 0),
            new Tile(1291, 10100, 0),
            new Tile(1295, 10093, 0)
    };

    ishLizardmenShaman main;

    public SafeDistance(ishLizardmenShaman main) {
        super();
        this.name = "SafeDistance";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {
        return Npcs.stream().name("Lizardman shaman").within(2).interactingWithMe().isNotEmpty()
                && !new DodgingSpawns(main).shouldExecute();
    }

    @Override
    public void execute() {
        Tile currentTile = Players.local().tile();
        Npc shaman = Npcs.stream().name("Lizardman shaman").within(2).interactingWithMe().first();
        if (shaman.valid()) {
            System.out.println("We're in melee distance from the shaman");
            Tile[] sortedTiles = Arrays.stream(safeTiles)
                    .sorted(Comparator.comparingInt(tile -> (int) tile.distanceTo(currentTile)))
                    .toArray(Tile[]::new);
            Tile targetTile = Tile.getNil();
            for (Tile tile : sortedTiles) {
                System.out.println("Checking to see if " + tile + " is safe");
                if (Npcs.stream().filter(npc -> npc.name().equals("Lizardman shaman") && npc.tile().distanceTo(tile) < 3).interactingWithMe().isEmpty()) {
                    System.out.println("Checking which tile to attend " + tile);
                    targetTile = tile;
                    break;
                }
            }
                if (targetTile != Tile.getNil()) {
                    final Tile safeTile = targetTile;
                    System.out.println("Safe closes tile found - " + safeTile);
                    if (Movement.step(safeTile)) {
                        Condition.wait(() -> Players.local().tile().equals(safeTile) || !new DodgingSpawns(main).shouldExecute(), 300, 20);
                        System.out.println("We're on a safe tile away from shaman");
                }
            }

        }
    }
}



//animation jump -7152
// 7157 - spawns animation
// 6768 - spawn monster id
// 8 - distance
/* safe tiles -Tile[] path = {
    new Tile(1290, 10093, 0),
    new Tile(1289, 10094, 0),
    new Tile(1289, 10099, 0),
    new Tile(1291, 10100, 0),
    new Tile(1295, 10100, 0),
    new Tile(1296, 10099, 0),
    new Tile(1296, 10095, 0),
    new Tile(1295, 10093, 0)
};

 */
/*
        Tile currentTile = Players.local().tile();
        if (Npcs.stream().name("Lizardman shaman").within(2).interactingWithMe().isNotEmpty()) {
            System.out.println("We're in melee distance from the shaman");
            Tile[] sortedTiles = Arrays.stream(safeTiles)
                    .sorted(Comparator.comparingInt(tile -> (int) tile.distanceTo(currentTile)))
                    .toArray(Tile[]::new);
                Tile furthestTile = sortedTiles[sortedTiles.length - 1]; // The furthest tile
                if(Movement.step(furthestTile)) {
                    Condition.wait(() -> Players.local().tile().equals(furthestTile), 300, 20);
                    System.out.println("Walked to the furthest tile away from shaman");
                }
        }
    }
 */