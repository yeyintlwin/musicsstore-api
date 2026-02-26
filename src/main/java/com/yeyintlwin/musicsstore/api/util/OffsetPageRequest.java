package com.yeyintlwin.musicsstore.api.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

public class OffsetPageRequest implements Pageable {

    private final int offset;
    private final int limit;
    @NonNull
    private final Sort sort;

    public OffsetPageRequest(int offset, int limit, @NonNull Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public OffsetPageRequest(int offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    @NonNull
    public Sort getSort() {
        return sort;
    }

    @Override
    @NonNull
    public Pageable next() {
        return new OffsetPageRequest((int) (getOffset() + getPageSize()), getPageSize(), getSort());
    }

    @NonNull
    public OffsetPageRequest previous() {
        return hasPrevious() ? new OffsetPageRequest((int) (getOffset() - getPageSize()), getPageSize(), getSort())
                : this;
    }

    @Override
    @NonNull
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    @NonNull
    public Pageable first() {
        return new OffsetPageRequest(0, getPageSize(), getSort());
    }

    @Override
    @NonNull
    public Pageable withPage(int pageNumber) {
        return new OffsetPageRequest(pageNumber * getPageSize(), getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
