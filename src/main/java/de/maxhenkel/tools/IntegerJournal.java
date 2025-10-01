package de.maxhenkel.tools;

import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntegerJournal extends SnapshotJournal<Integer> {

    private final Consumer<Integer> onIntegerChanged;
    private final Supplier<Integer> integerGetter;

    public IntegerJournal(Consumer<Integer> onIntegerChanged, Supplier<Integer> integerGetter) {
        this.onIntegerChanged = onIntegerChanged;
        this.integerGetter = integerGetter;
    }

    @Override
    protected Integer createSnapshot() {
        return integerGetter.get();
    }

    @Override
    protected void revertToSnapshot(Integer snapshot) {
        onIntegerChanged.accept(snapshot);
    }

}
