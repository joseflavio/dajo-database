package org.dajo.framework.db;

public class QueryTypeGmtTimeInMillis {

    private final long gmtTimeInMillis;

    public QueryTypeGmtTimeInMillis(final long gmtTimeInMillis) {
        this.gmtTimeInMillis = gmtTimeInMillis;
    }

    public long getMillis() {
        return gmtTimeInMillis;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (gmtTimeInMillis ^ (gmtTimeInMillis >>> 32));
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
        QueryTypeGmtTimeInMillis other = (QueryTypeGmtTimeInMillis) obj;
        if (gmtTimeInMillis != other.gmtTimeInMillis) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "QueryTypeGmtTimeInMillis [gmtTimeInMillis=" + gmtTimeInMillis + "]";
    }

}// class
