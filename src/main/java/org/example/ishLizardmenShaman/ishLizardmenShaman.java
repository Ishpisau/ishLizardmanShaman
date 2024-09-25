package org.example.ishLizardmenShaman;

import org.example.ishLizardmenShaman.Tasks.*;
import org.powbot.api.Events;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.dax.api.DaxWalker;
import org.powbot.dax.teleports.Teleport;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;


@ScriptManifest(name = "ishLizardmenShaman", description = "Uses POH fairy ring to travel. Start with ranged weapon equipped and dueling ring equipped. ", author = "Ish", category = ScriptCategory.Combat, version = "0.0.1")

@ScriptConfiguration(name = "inventory", description = "Select your Inventory", optionType = OptionType.INVENTORY)


public class ishLizardmenShaman extends AbstractScript {


    public static void main(String[] args){
        new ScriptUploader().uploadAndStart("ishLizardmenShaman", "",
                "emulator-5564", true, false);
    }                                                                  //emulator-5558  //127.0.0.1:5585

    ArrayList<Task> taskList = new ArrayList<>();
    public int kills = 0;
    private int lastNpcHealth = -1;
    public Tile fairyNearCave = new Tile(1304, 3761, 0);
    public Tile caveTile = new Tile(1312, 3685, 0);
    public Tile shamanTile = new Tile (1292, 10091, 0);
    public Tile lightTile = new Tile (1312, 10084, 0);
    private Map<Integer, Integer> inventoryList;
    public Item weapon = Equipment.itemAt(Equipment.Slot.MAIN_HAND);
    public String itemName = weapon.name();


    @Override
    public void onStart(){
        System.out.println(itemName + " Is the users weapon");

        Paint paint = PaintBuilder.newBuilder()
                .x(40)
                .y(45)
                .trackSkill(Skill.Ranged)
                .trackSkill(Skill.Slayer)
                .trackInventoryItem(LootItems.dragonWarhammerID)
                .trackInventoryItems(LootItems.grimyCadantineID)
                .trackInventoryItems(LootItems.grimyDwarfWeedID)
                .trackInventoryItems(LootItems.grimyKwuarmID)
                .trackInventoryItems(LootItems.grimyLantadymeID)
                .trackInventoryItems(LootItems.magicSeedID)
                .trackInventoryItems(LootItems.ranarrSeedID)
                .trackInventoryItems(LootItems.runiteOreID)
                .trackInventoryItems(LootItems.snapdragonSeedID)
                .trackInventoryItems(LootItems.spiritSeedID)
                .trackInventoryItems(LootItems.torstolSeedID)
                .trackInventoryItems(LootItems.yewSeedID)
                .addString("Kills: ", () -> String.valueOf(kills))
                .build();
        addPaint(paint);

        inventoryList = (Map<Integer, Integer>) getOption("inventory");

        if (inventoryList.isEmpty()) {
            System.out.println("No equipment selected");
        } else {
            System.out.println("Selected inventory: " + inventoryList);
        }

        taskList.add(new DodgingSpawns(this));
        taskList.add(new SafeDistance(this));
        taskList.add(new SafeTile(this));
        taskList.add(new EatFood(this));
        taskList.add(new DrinkAntipoison(this));
        taskList.add(new DrinkPrayer(this));
        taskList.add(new Looting(this));
        taskList.add(new DrinkRangePot(this));
        taskList.add(new WalkToCave(this));
        taskList.add(new EnterCave(this));
        taskList.add(new WalkToShaman(this));
        taskList.add(new EnterCave(this));
        taskList.add(new EnterFight(this));
        taskList.add(new WalkToBank());
        taskList.add(new RestoreStats());
        taskList.add(new Banking(this));
        taskList.add(new TeleportToHouse(this));
        taskList.add(new InteractFairy(this));
        taskList.add(new Fighting(this));
        Events.register(new DodgingSpawns(this));

        DaxWalker.blacklistTeleports(Teleport.values());
    }

    @Override
    public void poll() {
    for(Task task : taskList) {
        checkForKills();
            //System.out.println(LocalTime.now() + " Checking task: "+task.name);
            if(task.shouldExecute()){
                System.out.println(LocalTime.now() + " Running task: "+task.name);
                task.execute();
            }
            if (ScriptManager.INSTANCE.isStopping() || ScriptManager.INSTANCE.isPaused()){
                break;
            }

        }
    }
    public void checkForKills() {
        Npc currentNpc = Npcs.stream().name("Lizardman shaman").interactingWithMe().first();
        if (currentNpc.valid()) {
            int currentHealth = currentNpc.health();
            if (lastNpcHealth > 0 && currentHealth == 0) {
                System.out.println("Killed a shaman");
                kills++;
            }
            lastNpcHealth = currentHealth; // Update lastNpcHealth here
        } else {
            lastNpcHealth = -1; // Reset if no valid NPC is found
        }
    }



    public void withdrawInventory() {
        for (Map.Entry<Integer, Integer> entry : inventoryList.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            Bank.withdraw(itemId, quantity);
            System.out.println("Withdrawing item ID: " + itemId + ", Quantity: " + quantity);
        }
    }

    public Map<Integer, Integer> getInventoryList() {
        return inventoryList;
    }
}