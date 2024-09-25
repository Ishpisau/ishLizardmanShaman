package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;

public class RestoreStats extends Task {
    public RestoreStats() {
        super();
        this.name = "RestoreStats";
    }

    @Override
    public boolean shouldExecute() {
        return Objects.stream().within(15).name("Pool of Refreshment").isNotEmpty()
                && (Prayer.prayerPoints() < Skills.level(Skill.Prayer)
                || Movement.energyLevel() < 90
        || Combat.healthPercent() < 99);
    }

    @Override
    public void execute() {
        GameObject pool = Objects.stream().within(15).name("Pool of Refreshment").first();
        if (pool.inViewport()) {
            System.out.println("Restoring energy as run energy is " + Movement.energyLevel());
            pool.interact("Drink");
            System.out.println("Interacting with the pool");
            if (Condition.wait(() -> Players.local().inMotion(), 150, 100)) {
                Condition.wait(() -> Players.local().animation() == 7305, 150, 100);
                System.out.println("Energy restored " + Movement.energyLevel());
                Movement.running(true);
                System.out.println("Ensuring that the run is on");
            } else {
                System.out.println("Unable to restore energy, pool not in sight");
            }
        }
    }
}
