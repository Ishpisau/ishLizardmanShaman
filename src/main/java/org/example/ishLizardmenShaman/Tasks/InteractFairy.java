package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class InteractFairy extends Task {
    ishLizardmenShaman main;

    public InteractFairy(ishLizardmenShaman main) {
        super();
        this.name = "InteractFairy";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {
        return Objects.stream().name("Portal").within(10).isNotEmpty() &&
                Objects.stream().within(18).name("Fairy ring").within(15).isNotEmpty();
    }

    @Override
    public void execute() {
        GameObject fairyRing = Objects.stream().within(18).name("Fairy ring").first();
        Item dramen = Inventory.stream().name("Dramen staff").first();
        System.out.println("Interacting with fairy");
                if(dramen.valid() && dramen.interact("Wield")) {
                    System.out.println("Equipping dramen staff");
                    Condition.wait(() -> Equipment.stream().name("Dramen staff").isNotEmpty(), 150, 10);
                    System.out.println("Dramen staff equipped");
                }
                if (fairyRing.valid() && fairyRing.inViewport()) {
                    System.out.println("I can see fairy ring, using it to teleport");
                    fairyRing.interact("Last-destination (CIR)");
                    Condition.wait(() -> main.fairyNearCave.distance() < 10, 600, 10);
        }
    }
}