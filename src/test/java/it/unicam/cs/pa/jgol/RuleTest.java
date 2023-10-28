package it.unicam.cs.pa.jgol;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    @Test
    public void shouldDieIfAlone() {
        testRule(Rule::isolationRule,
                getCell(DefaultConwayStatus.ALIVE),
                getCells(DefaultConwayStatus.DEATH, DefaultConwayStatus.DEATH, DefaultConwayStatus.DEATH),
                DefaultConwayStatus.DEATH);
    }

    @Test
    public void shouldDieIfNearOnlyOneAlive() {
        testRule(Rule::isolationRule,
                getCell(DefaultConwayStatus.ALIVE),
                getCells(DefaultConwayStatus.ALIVE, DefaultConwayStatus.DEATH, DefaultConwayStatus.DEATH),
                DefaultConwayStatus.DEATH);

    }

    @Test
    public void isolationRuleShouldNotBeAppliedWithTwoAliveAround() {
        testRule(Rule::isolationRule,
                getCell(DefaultConwayStatus.ALIVE),
                getCells(DefaultConwayStatus.ALIVE, DefaultConwayStatus.ALIVE, DefaultConwayStatus.DEATH));

    }

    @Test
    public void shouldSurviveIfSomeoneIsNear() {
        testRule(Rule::surviveRule,
                getCell(DefaultConwayStatus.ALIVE),
                getCells(DefaultConwayStatus.ALIVE,DefaultConwayStatus.ALIVE),
                DefaultConwayStatus.ALIVE);
    }

    private <S,L> void testRuleOptinal( Rule<S,L> rule, Cell<S,L> current, Set<Cell<S,L>> near, Optional<S> expected) {
        assertEquals(expected,rule.apply(current,near));
    }

    private <S,L> void testRule( Rule<S,L> rule, Cell<S,L> current, Set<Cell<S,L>> near, S expected) {
        testRuleOptinal(rule,current,near,Optional.of(expected));
    }

    private <S,L> void testRule( Rule<S,L> rule, Cell<S,L> current, Set<Cell<S,L>> near) {
        testRuleOptinal(rule,current,near,Optional.empty());
    }

    private Cell<DefaultConwayStatus,DummyLocation> getCell(DefaultConwayStatus status) {
        return new SimpleCell<>(status,null,null);
    }

    private Set<Cell<DefaultConwayStatus,DummyLocation>> getCells(DefaultConwayStatus ... status) {
        return Stream.of(status).map(this::getCell).collect(Collectors.toSet());
    }


    private class DummyLocation {

    }

}