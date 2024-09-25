package org.example.ishLizardmenShaman.Tasks;

import org.example.ishLizardmenShaman.Task;
import org.example.ishLizardmenShaman.ishLizardmenShaman;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import java.util.Collection;

public class Banking extends Task {
    ishLizardmenShaman main;

    public Banking(ishLizardmenShaman main) {
        super();
        this.name = "Banking";
        this.main = main;
    }

    @Override
    public boolean shouldExecute() {

        return !new RestoreStats().shouldExecute() && Bank.nearest().distance() < 10
                && (Inventory.stream().name("Cooked karambwan", "Chilli potato", "Swordfish", "Potato with butter"
                , "Potato with cheese", "Monkfish", "Shark", "Sea turtle", "Pineapple pizza",  "Manta ray", "Tuna potato", "Dark crab",
                "Anglerfish").isEmpty()
                || Inventory.stream().nameContains("Prayer potion").isEmpty()
                || Inventory.stream().nameContains("Superantipoison").isEmpty());
    }

    @Override
    public void execute() {
        System.out.println("Selected inventory: " + main.getInventoryList());
        System.out.println("Current inventory: " + Inventory.stream().list());
        if (Bank.open() && !new RestoreStats().shouldExecute()) {
            Condition.wait(() -> Bank.opened(), 150, 30);
            System.out.println("Opening bank");
            if (Bank.depositInventory()) {
                Condition.wait(() -> Inventory.stream().isEmpty(), 150, 10);
                System.out.println("Depositing all inventory");
            }
                if(Equipment.stream().nameContains("Ring of dueling").isEmpty()){
                    Bank.withdraw("Ring of dueling(8)", 1);
                    Condition.wait(()->Inventory.stream().nameContains("Ring of dueling").isNotEmpty(), 150, 33);
                    Inventory.stream().nameContains("Ring of dueling").first().interact("Equip");
                    Condition.wait(()->Equipment.stream().nameContains("Ring of dueling").isNotEmpty(), 150, 33);
                }
                main.withdrawInventory();
                Condition.wait(() -> Inventory.stream().contains((Collection<? extends Item>) main.getInventoryList()), 150, 20);
                if (Bank.opened() && !shouldExecute()) {
                    System.out.println("Bank.close()");
                    Bank.close();
            }
        } else {
            System.out.println("Inventory is all gucci!");
        }
    }
}