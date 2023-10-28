package it.unicam.cs.pa.jgol;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class DefaultCrontroller<S, L> implements Controller<S, L> {

    private final Rule<S, L> rules;
    private Field<S, L> current;
    private final Queue<Field<S, L>> previous;
    private Queue<Field<S, L>> next;
    private final ExecutorService executor;
    private final List<Predicate<Field<S,L>>> predicates;
    private final List<Runnable> activities;

    public DefaultCrontroller(Rule<S, L> rules, Field<S, L> init) {
        this(Executors.newCachedThreadPool(),rules,init);
    }

    public DefaultCrontroller(ExecutorService executor, Rule<S, L> rules, Field<S, L> init) {
        this.executor = executor;
        this.rules = rules;
        this.current = init;
        this.previous = new LinkedList<>();
        this.next = new LinkedList<>();
        this.predicates = new ArrayList<>();
        this.activities = new ArrayList<>();
    }

    @Override
    public synchronized Field<S, L> getField() {
        return current;
    }

    @Override
    public synchronized boolean hasPrevious() {
        return !previous.isEmpty();
    }

    @Override
    public synchronized boolean hasNext() {
        return !next.isEmpty();
    }

    @Override
    public synchronized Field<S, L> previous() {
        if (previous.isEmpty()) {
            return null;
        } else {
            next.add(current);
            current = previous.poll();
            return current;
        }
    }

    @Override
    public synchronized Field<S, L> next() {
        if (next.isEmpty()) {
            return null;
        } else {
            previous.add(current);
            current = next.poll();
            return current;
        }
    }

    @Override
    public synchronized Field<S, L> apply() {
        Field<S, L> generated = current.apply(rules);
        previous.add(current);
        next.clear();
        current = generated;
        startActivities();
        return current;
    }

    private void startActivities() {
        IntStream.
            range(0, predicates.size()).
            filter(i -> predicates.get(i).test(current)).
            forEach(i -> executor.execute(activities.get(i)));
    }

    @Override
    public void recordHandler(Predicate<Field<S, L>> p, Runnable activity) {

    }


}
