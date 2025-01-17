package org.ideacloud.utils;

import org.ideacloud.exceptions.PaginationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtil {

    public static Pageable makePageable(int page, int size) {

        if (page < 1 || size < 1) {
            throw new PaginationException();
        }

        return PageRequest.of(page-1, size);
    }

}
