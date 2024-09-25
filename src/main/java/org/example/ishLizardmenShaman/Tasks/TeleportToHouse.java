package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class TeleportToHouse extends Task {
    ishLizardmenShaman main;

    public TeleportToHouse(ishLizardmenShaman main) {
        super();
        this.name = "TeleportToHouse";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {
        return Bank.nearest().distance() < 9
        && !new RestoreStats().shouldExecute()
                && !new Banking(main).shouldExecute() && Inventory.isFull();
    }

    @Override
    public void execute() {
        GameObject portal = Objects.stream().id(4525).within(10).first();
        GameObject fairyRing = Objects.stream().within(18).id(29392).within(15).first();
        if(!portal.valid()) {
            System.out.println("Well it looks like we're ready for some smoke, teleporting to house");
            Magic.Spell.TELEPORT_TO_HOUSE.cast();
            Condition.wait(() ->portal.valid() && fairyRing.valid(), 600, 10);
            System.out.println("hopefully we're in the house");
            new InteractFairy(main).shouldExecute();
        }
    }
}