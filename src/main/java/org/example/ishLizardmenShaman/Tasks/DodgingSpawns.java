package org.example.ishLizardmenShaman.Tasks;

import com.google.common.eventbus.Subscribe;
import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.Events;
import org.powbot.api.Tile;
import org.powbot.api.event.TickEvent;
import org.powbot.api.rt4.*;
import org.powbot.api.waiter.TickWaiter;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public class DodgingSpawns extends Task {
    Tile[] safeTiles = {
            new Tile(1290, 10093, 0),
            new Tile(1289, 10094, 0),
            new Tile(1289, 10099, 0),
            new Tile(1291, 10100, 0),
            new Tile(1295, 10093, 0)
    };

    ishLizardmenShaman main;
    private boolean spawnDetected = false;
    private Tile targetTile = Tile.getNil();

    private TickWaiter tickWaiter;
    private boolean waitingForTicks = false;

    public DodgingSpawns(ishLizardmenShaman main) {
        super();
        this.name = "DodgingSpawns";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {
        return Npcs.stream().name("Spawn").within(4).isNotEmpty();
    }

    @Override
    public void execute() {
        if (!spawnDetected && Npcs.stream().name("Spawn").within(4).isNotEmpty()) {
            searchForSafeTile();
            System.out.println("executed searchForSafeTile()");
            spawnDetected = true;
            System.out.println("spawnDetected = true");
            tickWaiter = new TickWaiter(5);
            Events.register(tickWaiter);
            waitingForTicks = true; // Set waiting state
            System.out.println(LocalTime.now() + " Registered TickWaiter, waiting for 5 ticks...");
        }
        if (spawnDetected && tickWaiter.succeeded()) {
                System.out.println("executing moveToSafeTile()");
                moveToSafeTile();
                Condition.wait(() -> Npcs.stream().name("Spawn").within(2).isEmpty()
                        || Npcs.stream().name("Spawn").within(8).count() >4, 300, 13);
                spawnDetected = false;
                System.out.println("setting spawnDetected to false");
                Events.unregister(tickWaiter);
                tickWaiter = null;
                waitingForTicks = false; // Reset waiting state
        }
        if (Npcs.stream().name("Spawn").within(8).count() >4){
            emergencyMove();
            System.out.println("executed emergencyMove");
        }
    }
    @Subscribe
    public void onTick(TickEvent evt) {
    }

    private void emergencyMove(){
            System.out.println("Looks like 2 spawn events happened - doing something about it");
            searchForSafeTile();
            System.out.println("searchForSafeTile()");
            moveToSafeTile();
            System.out.println("moveToSafeTile");
    }
    private void searchForSafeTile() {
        Tile currentTile = Players.local().tile();
        System.out.println("Spawns have been spawned ^^");
        Tile[] sortedTiles = Arrays.stream(safeTiles)
                .sorted(Comparator.comparingInt(tile -> (int) tile.distanceTo(currentTile)))
                .toArray(Tile[]::new);
        for (int i = sortedTiles.length - 1; i >= 0; i--) {
            Tile tile = sortedTiles[i];
            System.out.println("Checking tile? " + tile);
            System.out.println("Doesn't look like " + tile + " Is safe, continue searching");
            if (Npcs.stream().filter(npc -> npc.name().equals("Lizardman shaman") && npc.tile().distanceTo(tile) < 3).interactingWithMe().isEmpty()) {
                System.out.println("Checking which tile to attend " + tile);
                targetTile = tile;
                break;
            }else {
                if (targetTile == Tile.getNil()) {
                    System.out.println("No safe tiles available.");
                }
            }
        }
    }

    private void moveToSafeTile() {
        if (targetTile != Tile.getNil() && Npcs.stream().name("Spawn").within(2).isNotEmpty()) {
            final Tile finalTargetTile = targetTile;
            System.out.println("Going to move to the safest " + finalTargetTile);
            Movement.step(finalTargetTile);
            Condition.wait(() -> Players.local().tile().equals(targetTile), 200, 33);
            System.out.println("Hopefully we're on a safe tile");
        }
    }
}

/*
//animation jump -7152
// 7157 - spawns animation
// 6768 - spawn monster id
// 8 - distance
*/