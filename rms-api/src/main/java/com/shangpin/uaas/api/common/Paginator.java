package com.shangpin.uaas.api.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paginator {
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final int DEFAULT_FIRST_PAGE = 1;
	private int pageSize = 10;

	private long page = 1L;

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = (pageSize < 1 ? 1 : pageSize);
	}

	public long getPage() {
		return this.page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getOffset() {
		return (this.page - 1L) * this.pageSize;
	}

	public static Paginator page(long page) {
		Paginator paginator = new Paginator();
		paginator.setPage(page);
		return paginator;
	}

	public static Paginator page(long page, int pageSize) {
		Paginator paginator = new Paginator();
		paginator.setPage(page);
		paginator.setPageSize(pageSize);
		return paginator;
	}

	public static Paginator pageContains(long index, int pageSize) {
		return page(index / pageSize + 1L, pageSize);
	}

	public void turnToPageContains(long index) {
		this.page = pageContains(index, this.pageSize).page;
	}

	public Map<String, Object> getGormParams() {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", Long.valueOf(getOffset()));
		map.put("max", Integer.valueOf(getPageSize()));
		return map;
	}

	public <T> List<T> filter(List<T> list) {
		return list.subList((int) getOffset(),
				Math.max((int) getOffset(), Math.min((int) getOffset() + getPageSize(), list.size())));
	}
}