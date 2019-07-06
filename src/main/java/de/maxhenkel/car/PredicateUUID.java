package de.maxhenkel.car;

import java.util.UUID;

import com.google.common.base.Predicate;
import de.maxhenkel.car.entity.car.base.EntityCarBase;

public class PredicateUUID implements Predicate<EntityCarBase> {

    private UUID uuid;

    public PredicateUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean apply(EntityCarBase input) {
        if (input.getUniqueID().equals(uuid)) {
            return true;
        }

        return false;
    }

}
