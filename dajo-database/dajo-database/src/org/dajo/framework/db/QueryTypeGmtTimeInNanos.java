package org.dajo.framework.db;

public final class QueryTypeGmtTimeInNanos {

    private final long gmtTimeInNanos;

    public QueryTypeGmtTimeInNanos(final long gmtTimeInNanos) {
        this.gmtTimeInNanos = gmtTimeInNanos;
    }

    public long getNanos() {
        return gmtTimeInNanos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (gmtTimeInNanos ^ (gmtTimeInNanos >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        QueryTypeGmtTimeInNanos other = (QueryTypeGmtTimeInNanos) obj;
        if (gmtTimeInNanos != other.gmtTimeInNanos) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GmtTimeInNanos [gmtTimeInNanos=" + gmtTimeInNanos + "]";
    }

}// class
