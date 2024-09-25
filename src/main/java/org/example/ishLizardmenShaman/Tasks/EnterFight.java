package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.mobile.Con;


public class EnterFight extends Task {
    ishLizardmenShaman main;
    public EnterFight(ishLizardmenShaman main) {
        super();
        this.name = "EnterFight";
        this.main = main;
    }
    @Override
    public boolean shouldExecute() {
        return main.shamanTile.distance() < 10 && Npcs.stream().name("Lizardman shaman").reachable().isEmpty();
    }

    @Override
    public void execute() {
        GameObject barrier = Objects.stream().name("Mystical barrier").within(10).first();
        Npc shaman = Npcs.stream().name("Lizardman shaman").first();
        Item weapon = Inventory.stream().name(main.itemName).first();

        if (weapon.interact("Wield")) {
            Condition.wait(() -> Equipment.stream().name(main.itemName).isNotEmpty(), 200, 15);
        }
        if(Combat.autoRetaliate(true)){
            System.out.println("Setting auto retaliate to true");
            Condition.wait(()->Combat.autoRetaliate(true), 200, 20);
        }
        System.out.println("Looks like we're going to fight");
        if (!shaman.reachable() && barrier.distance() <10){
            System.out.println("Activating prayer");
            Prayer.prayer(Prayer.Effect.PROTECT_FROM_MISSILES, true);
            Condition.wait(()-> Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MISSILES), 300, 10);
            Prayer.prayer(Prayer.Effect.EAGLE_EYE, true);
            Condition.wait(()->Prayer.prayerActive(Prayer.Effect.EAGLE_EYE), 300, 10);
            System.out.println("Entering the shaman room");
            barrier.interact("Pass");
            Condition.wait(shaman::reachable, 150, 10);
            System.out.println("We're in the room");
        }
    }
}

