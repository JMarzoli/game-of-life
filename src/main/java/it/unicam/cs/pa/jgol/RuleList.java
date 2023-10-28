package it.unicam.cs.pa.jgol;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RuleList<S,L> implements Rule<S,L> {

    private final List<Rule<S,L>> rules;

    public RuleList(List<Rule<S, L>> rules) {
        this.rules = rules;
    }

    @Override
    public Optional<S> apply(Cell<S, L> current, Set<Cell<S, L>> near) {
        return rules.stream()
                .sequential()
                .map(r -> r.apply(current,near))
                .filter(Optional::isPresent)
                .findFirst().orElse(Optional.empty());
//        return result.orElse(Optional.empty());
//        for(Rule<S,L> r: rules) {
//            Optional<S> v = r.apply(current,near);
//            if (v.isPresent()) {
//                return v;
//            }
//        }
//        return Optional.empty();
    }
}
