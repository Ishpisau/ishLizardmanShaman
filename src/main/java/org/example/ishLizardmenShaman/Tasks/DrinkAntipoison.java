package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class DrinkAntipoison extends Task {
    ishLizardmenShaman main;
    public DrinkAntipoison(ishLizardmenShaman main) {
        super();
        this.name = "DrinkAntipoison";
        this.main = main;
    }
    @Override
    public boolean shouldExecute() {
        return Combat.isPoisoned() && Players.local().inCombat()
                && !new DodgingSpawns(main).shouldExecute();
    }

    @Override
    public void execute() {
        Item antiPoison = Inventory.stream().nameContains("Superantipoison").first();
        if (Players.local().inCombat() && Combat.isPoisoned()){
            System.out.println("looks like we've been poisoned");
            if(antiPoison.interact("Drink")){
                System.out.println("Drinking anti poison potion");
                Condition.wait(()->!Combat.isPoisoned() || !antiPoison.valid(), 900, 5);
                System.out.println("No longer poisoned");
            }
        }
    }
}
