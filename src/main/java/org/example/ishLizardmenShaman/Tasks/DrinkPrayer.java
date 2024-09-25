package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class DrinkPrayer extends Task {
    ishLizardmenShaman main;
    public DrinkPrayer(ishLizardmenShaman main) {
        super();
        this.name = "DrinkPrayer";
        this.main = main;
    }
    @Override
    public boolean shouldExecute() {
        return Prayer.prayerPoints() < 40 && Players.local().inCombat()
                && !new DodgingSpawns(main).shouldExecute() && !new EatFood(main).shouldExecute();
    }

    @Override
    public void execute() {
        Item ppot = Inventory.stream().name("Prayer potion(4)", "Prayer potion(3)", "Prayer potion(2)", "Prayer potion(1)").first();
        if (Prayer.prayerPoints() < 40 && Players.local().inCombat()){
            System.out.println("looks like we're running out of prayer - Our current prayer points are: " + Prayer.prayerPoints());
            if(ppot.interact("Drink")){
                Condition.wait(()->!ppot.valid(), 900, 5);
                System.out.println("Drank a potion, we've got " + Prayer.prayerPoints() + " Points");
            }
        }
    }
}

