package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.LootItems;
import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;



public class Looting extends Task {
    ishLizardmenShaman main;

    public Looting(ishLizardmenShaman main) {
        super();
        this.name = "Looting";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {
        return GroundItems.stream().isNotEmpty() && !Inventory.isFull()
                && !new DodgingSpawns(main).shouldExecute() && Npcs.stream().name("Lizardman shaman").animation(7157).interactingWithMe().isEmpty();
    }

    @Override
    public void execute() {
        GroundItem item = GroundItems.stream().within(8).filter(i -> isLootItem(i.id())).reachable().first();
               if (item.valid() && item.interact("Take")) {
            System.out.println("Looks like we've got a drop: " + item + " Trying to pick it up");
            Condition.wait(()->!item.valid() || !new DodgingSpawns(main).shouldExecute(), 1000, 33);
            System.out.println("Picked it up");
        }
    }
    private boolean isLootItem (int itemId){
        return LootItems.LOOT_ITEMS.contains(itemId);
    }
}