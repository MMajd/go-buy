package com.mmajd.gobuy.common;

public class PagesUtil {
    public static long getStartCount(final long pageNo, final long recordsPerPage) {
        return ((pageNo - 1) * recordsPerPage) + 1;
    }

    public static long getEndCount(final long pageNo, final long recordsPerPage, final long totalRecords) {
        long endCount = getStartCount(pageNo, recordsPerPage) + recordsPerPage - 1;
        return Math.min(endCount, totalRecords);
    }
}
