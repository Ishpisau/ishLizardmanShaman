package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;


public class WalkToCave extends Task {
    ishLizardmenShaman main;
    Tile[] path = {
            new Tile(1297, 3752, 0),
            new Tile(1293, 3746, 0),
            new Tile(1293, 3738, 0),
            new Tile(1292, 3730, 0),
            new Tile(1289, 3722, 0),
            new Tile(1289, 3715, 0),
            new Tile(1287, 3706, 0),
            new Tile(1290, 3701, 0),
            new Tile(1292, 3694, 0),
            new Tile(1292, 3686, 0),
            new Tile(1293, 3680, 0),
            new Tile(1299, 3678, 0),
            new Tile(1307, 3677, 0),
            new Tile(1312, 3683, 0)
    };

    public WalkToCave(ishLizardmenShaman main) {
        super();
        this.name = "WalkToCave";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {
        return main.fairyNearCave.distance() <= 5 && main.caveTile.distance() > 5;
    }

    @Override
    public void execute() {
        TilePath tilepath = new TilePath(path);
        Tile targetLocation = path[path.length-1];

        if(main.caveTile.distance() >= 14){
            System.out.println("lets go to the cave");
            for(int i = 0; i<path.length;i++){
                if(targetLocation.distance() < 5){
                break;
                } else {
                    System.out.println("Walking - on path tile: " + i);
                    tilepath.traverse();
                    Condition.wait(()->main.caveTile.distance() <= 14, 230, 10);
                    }
                }
            }
    }
}

