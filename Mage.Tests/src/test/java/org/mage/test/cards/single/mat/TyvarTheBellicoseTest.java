package org.mage.test.cards.single.mat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TyvarTheBellicoseTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar the Bellicose");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Llanowar Elves", 2, 2);
    }

    @Test
    public void testGrantedAbility() {
        // Tyvar starts as a 5/4
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar the Bellicose");

        // Enduring Vitality grants "{T}: Add one mana of any color." to all creatures you control
        addCard(Zone.BATTLEFIELD, playerA, "Enduring Vitality");

        // The ability prompts the user to pick a color of mana to add.
        setChoice(playerA, "Green");

        // The test framework defaults to the first available creature with the ability when there are duplicates.
        // Since Tyvar was added first, he will be the one to tap.
        // This perfectly tests the fix, as Tyvar has no innate mana ability!
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add one mana of any color");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Tyvar should receive a +1/+1 counter because he successfully produced 1 mana.
        assertPowerToughness(playerA, "Tyvar the Bellicose", 6, 5);
    }
}