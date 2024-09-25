package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;


public class DrinkRangePot extends Task {
    ishLizardmenShaman main;
    public DrinkRangePot(ishLizardmenShaman main) {
        super();
        this.name = "DrinkRangePot";
        this.main = main;
    }
    @Override
    public boolean shouldExecute() {
        return Players.local().inCombat() && Skills.realLevel(Skill.Ranged) +7 >= Skills.level(Skill.Ranged)
                && !new SafeTile(main).shouldExecute() && !new DodgingSpawns(main).shouldExecute();
    }

    @Override
    public void execute() {
        Item pot = Inventory.stream().nameContains("Ranging potion").first();
        if (Skills.realLevel(Skill.Ranged) +7 >= Skills.level(Skill.Ranged)){
            System.out.println("Drinking range potion as your current range with boost is: " + Skills.level(Skill.Ranged));
            if(pot.interact("Drink")){
                Condition.wait(()->!pot.valid() && Skills.realLevel(Skill.Ranged) +7 < Skills.level(Skill.Ranged), 600, 10);
                System.out.println("Drank ranged potion, our new range level is " + Skills.level(Skill.Ranged));
            }
        }
    }
}

