package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.TilePath;


public class WalkToShaman extends Task {
    ishLizardmenShaman main;
    Tile[] path = {
            new Tile(1307, 10077, 0),
            new Tile(1298, 10077, 0),
            new Tile(1292, 10083, 0),
            new Tile(1293, 10090, 0)
    };

    public WalkToShaman(ishLizardmenShaman main) {
        super();
        this.name = "WalkToShaman";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {
        return main.lightTile.distance() <= 5 && main.shamanTile.distance() >= 3;
    }

    @Override
    public void execute() {
        TilePath tilepath = new TilePath(path);
        Tile targetLocation = path[path.length-1];
        if(main.shamanTile.distance() >= 5){
            Condition.wait(()->Objects.stream().within(10).name("Lizard dwelling").isNotEmpty(), 150, 10);
            System.out.println("lets go to the cave");
            for(int i = 0; i<path.length;i++){
                if(targetLocation.distance() < 1){
                break;
                } else {
                    System.out.println("Walking - on path tile: " + i);
                    tilepath.traverse();
                    Condition.wait(()->main.shamanTile.distance() <= 5, 350, 7);
                    System.out.println("Arrived at the barrier");
                }
            }
        }
    }
}

