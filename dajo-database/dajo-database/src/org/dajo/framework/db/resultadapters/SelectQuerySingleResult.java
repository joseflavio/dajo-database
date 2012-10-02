package org.dajo.framework.db.resultadapters;

public final class SelectQuerySingleResult<T> {

    private final boolean available;

    private final T value;

    public SelectQuerySingleResult(final T value) {
        this.available = true;
        this.value = value;
    }

    public SelectQuerySingleResult() {
        this.available = false;
        this.value = null;
    }

    public boolean isAvailable() {
        return available;
    }

    public T getValue() {
        if( available == false ) {
            throw new RuntimeException("Value not available");
        }
        return value;
    }

    @Override
    public String toString() {
        return "SelectQuerySingleResult [available=" + available + ", value=" + value + "]";
    }


}// class
