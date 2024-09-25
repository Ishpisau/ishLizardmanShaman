package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Combat;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;


public class EatFood extends Task {
    ishLizardmenShaman main;
    public EatFood(ishLizardmenShaman main) {
        super();
        this.name = "EatFood";
        this.main = main;
    }
    @Override
    public boolean shouldExecute() {
        return Combat.healthPercent() < 60 && Players.local().inCombat();
    }

    @Override
    public void execute() {
        Item food = Inventory.stream().name("Cooked karambwan", "Chilli potato", "Swordfish", "Potato with butter"
        , "Potato with cheese", "Monkfish", "Shark", "Sea turtle", "Pineapple pizza",  "Manta ray", "Tuna potato", "Dark crab",
                "Anglerfish").first();
        if (Combat.healthPercent() < 70 && Players.local().inCombat()){
            System.out.println("looks like we've lost some health - eating food" + " Our current health is: " + Combat.health());
            if(food.interact("Eat")){
                Condition.wait(()->!food.valid() || !new DodgingSpawns(main).shouldExecute(), 900, 5);
                System.out.println("Ate food, we've got " + Combat.health() + " Health");
            }
        }
    }
}

