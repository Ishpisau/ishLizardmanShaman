package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class Fighting extends Task {
    ishLizardmenShaman main;
    public Fighting(ishLizardmenShaman main) {
        super();
        this.name = "Fighting";
        this.main = main;
    }
    @Override
    public boolean shouldExecute() {
        return Npcs.stream().name("Lizardman shaman").within(8).interactingWithMe().isEmpty()
                && !new WalkToBank().shouldExecute() && !new EatFood(main).shouldExecute()
                && !new DrinkPrayer(main).shouldExecute() && !new DrinkAntipoison(main).shouldExecute()
                && !Players.local().interacting().valid()
                && !Players.local().inCombat()
                && Npcs.stream().name("Lizardman shaman").within(8).animation(7152).isEmpty()
                && !new DodgingSpawns(main).shouldExecute() && !new SafeDistance(main).shouldExecute()
                && (Players.local().animation() == -1 || Players.local().movementAnimation() == -1)
                && !new TeleportToHouse(main).shouldExecute()
                && !new InteractFairy(main).shouldExecute();
    }

    @Override
    public void execute() {
        Npc shaman = Npcs.stream().name("Lizardman shaman").within(8).filtered(npc ->!npc.healthBarVisible()).nearest().reachable().first();
        if (shaman.valid()){
            System.out.println("Found shaman to fight "+shaman.name());
            if (shaman.inViewport() && !Players.local().inCombat()){
                System.out.println("Shaman visible, attacking");
                if (shaman.interact("Attack")){
                    Condition.wait(()->Players.local().interacting().valid(), 150, 30);
                    System.out.println("Fighting shaman");
                }
            }
            if(Npcs.lastAttackedNpcHealth() == 0){
                System.out.println("Killed a shaman");
                main.kills++;
            }
        }
    }
}
//animation jump -7152
// 7157 - spawns animation
// 6768 - spawn monster id
// 8 - distance
/* safe tiles -Tile[] path = {
    new Tile(1290, 10093, 0),
    new Tile(1289, 10094, 0),
    new Tile(1289, 10099, 0),
    new Tile(1291, 10100, 0),
    new Tile(1295, 10100, 0),
    new Tile(1296, 10099, 0),
    new Tile(1296, 10095, 0),
    new Tile(1295, 10093, 0)
};

 */
