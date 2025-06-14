package com.oasisnourish.models;

import java.time.LocalDateTime;

public abstract class EntityBase {
    protected long id;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
