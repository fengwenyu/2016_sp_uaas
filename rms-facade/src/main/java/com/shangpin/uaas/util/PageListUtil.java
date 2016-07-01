package com.shangpin.uaas.util;

import java.util.ArrayList;
import java.util.List;

import com.shangpin.uaas.api.common.PagedList;
import com.shangpin.uaas.api.common.Paginator;

/**
 * 假分页的封装工具
 */
public class PageListUtil {

    public static <T> PagedList<T> convert(Paginator paginator, List<T> collection) {
        if (paginator.getPageSize() > 1000) {
            paginator.setPageSize(1000);
        }
        if (null == collection) {
            collection = new ArrayList<T>();
        }

        int start = (int) (paginator.getPage() - 1) * paginator.getPageSize();
        if (start > collection.size()) {
            start = collection.size();
        }

        int end = start + paginator.getPageSize();
        if (end > collection.size()) {
            end = collection.size();
        }

        List<T> subCollection = collection.subList(start, end);
        PagedList<T> pagedList = new PagedList<T>(collection.size(), paginator, subCollection);

        return pagedList;
    }
}
