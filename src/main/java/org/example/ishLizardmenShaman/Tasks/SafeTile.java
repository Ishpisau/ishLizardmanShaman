package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;
import org.powbot.mobile.Con;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class SafeTile extends Task {
    Tile[] safeTiles = {
            new Tile(1290, 10093, 0),
            new Tile(1289, 10094, 0),
            new Tile(1289, 10099, 0),
            new Tile(1291, 10100, 0),
            new Tile(1295, 10093, 0)
    };

    ishLizardmenShaman main;

    public SafeTile(ishLizardmenShaman main) {
        super();
        this.name = "SafeTile";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {
        return !new DodgingSpawns(main).shouldExecute() && Npcs.stream().name("Lizardman shaman").within(8).reachable().isNotEmpty()
                && Players.local().movementAnimation() == 808 && !new SafeDistance(main).shouldExecute();
    }

    @Override
    public void execute() {
        Tile currentTile = Players.local().tile();
        boolean isSafe = Arrays.asList(safeTiles).contains(currentTile);
        if (!isSafe || Npcs.stream().name("Lizardman shaman").animation(7152).interactingWithMe().isNotEmpty()) {
            System.out.println("We're not on a safe tile || Shaman is jumping");
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
                    System.out.println("We're on a safe tile away from shamans");
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
else if (Npcs.stream().name("Lizardman shaman").within(1).interactingWithMe().isNotEmpty()){
                System.out.println("Shaman is witihn 1 tile, moving away");
            System.out.println("Spawns have been spawned ^^");
            Tile[] sortedTiles = Arrays.stream(safeTiles)
                    .sorted(Comparator.comparingInt(tile -> (int) tile.distanceTo(currentTile)))
                    .toArray(Tile[]::new);
                Random random = new Random();
                Tile randomTile = sortedTiles[random.nextInt(sortedTiles.length)];
                Movement.step(randomTile);
                System.out.println("Walked to random tile away from shaman");
                if (isSafe){
                boolean isSafe = Arrays.asList(safeTiles).contains(currentTile);
                    System.out.println("We're on a safe tile");
                } else{
                    System.out.println("We're not on safe tile, should prolly move");
                    Movement.step(randomTile);
                    System.out.println("Standing in a safe tile now " + randomTile);
                }
        }
 */