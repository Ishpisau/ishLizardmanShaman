package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;
import org.powbot.dax.api.models.RunescapeBank;

public class WalkToBank extends Task {
    Tile[] path = {
            new Tile(3145, 3629, 0),
            new Tile(3137, 3628, 0),
            new Tile(3131, 3635, 0)
    };
    public WalkToBank() {
        super();
        this.name = "WalkToBank";
    }

    @Override
    public boolean shouldExecute() {
        return Objects.stream().within(15).name("Pool of Refreshment").isEmpty()
                && (Inventory.stream().name("Cooked karambwan", "Chilli potato", "Swordfish", "Potato with butter"
                , "Potato with cheese", "Monkfish", "Shark", "Sea turtle", "Pineapple pizza",  "Manta ray", "Tuna potato", "Dark crab",
                "Anglerfish").isEmpty() && Combat.healthPercent() < 50) ||
        (Inventory.stream().nameContains("Superantipoison").isEmpty() && Combat.isPoisoned())
        || (Inventory.stream().nameContains("Prayer potion").isEmpty() && Prayer.prayerPoints() < 20);
    }

    @Override
    public void execute() {
        TilePath tilepath = new TilePath(path);
        Tile targetLocation = path[path.length-1];
        Item food = Inventory.stream().name("Cooked karambwan").first();
        Item ppot = Inventory.stream().nameContains("Prayer potion").first();
        Item antiPoison = Inventory.stream().nameContains("Superantipoison").first();
        Item ring = Equipment.stream().nameContains("Ring of dueling").first();
        GameObject pool = Objects.stream().within(15).name("Pool of Refreshment").first();
        System.out.println("Checking the state of inventory ");

        if ((!food.valid() && Combat.healthPercent() < 50)
                || (!ppot.valid() && Prayer.prayerPoints() < 20)
                || (!antiPoison.valid() && Combat.isPoisoned())){
            System.out.println("Either we ran out of PPOTS, antipoisons or food");
        if(ring.interact("Ferox Enclave")) {
            Condition.wait(() -> Players.local().animation() == -1, 600, 20);
            if(pool.distance() >= 8){
                System.out.println("lets go to restore pool");
                for(int i = 0; i<path.length;i++){
                    if(targetLocation.distance() < 5){
                        break;
                    } else {
                        System.out.println("Walking on path tile: " + i);
                        tilepath.traverse();
                        Condition.wait(()->pool.distance() <= 8, 185, 10);
                    }
                }
            }
        }
            if (pool.distance() < 10) {
                System.out.println("Attended pool");
                new RestoreStats().shouldExecute();
            }
        }
    }
}
