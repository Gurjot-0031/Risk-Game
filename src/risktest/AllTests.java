package risktest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        MapValidation.class,
        TestPlayer.class,
        CheckStartupPhase.class,
        CheckReinforcementArmies.class,
        CheckAttackPhase.class,
        CheckFortifyPhase.class
})
public class AllTests {

}

