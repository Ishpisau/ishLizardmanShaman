package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class EnterCave extends Task {
    ishLizardmenShaman main;

    public EnterCave(ishLizardmenShaman main) {
        super();
        this.name = "EnterCave";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {
        return main.caveTile.distance() < 15;
    }

    @Override
    public void execute() {
        GameObject cave = Objects.stream().within(15).name("Lizard dwelling").first();
        if (cave.distance() <= 15 && cave.inViewport()) {
            System.out.println("Testing Cave");
            cave.interact("Enter");
            System.out.println("Entered the cave, waiting....");
            Condition.wait(() -> main.lightTile.distance() < 9, 100, 40);
            System.out.println("Looks like we're inside the cave. Nice");
        }
    }
}



