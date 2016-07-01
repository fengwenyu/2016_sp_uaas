package com.shangpin.uaas.api.common;

import java.util.ArrayList;
import java.util.Collection;

public class PagedList<T>
{
  private long totalPage;
  private long totalCount;
  private long currentPage;
  private int pageSize;
  private ArrayList<T> list;

  public PagedList()
  {
    this.list = new ArrayList<T>();
  }

  public PagedList(long totalCount, Paginator paginator) {
    this();
    this.totalCount = totalCount;
    this.currentPage = paginator.getPage();
    this.pageSize = paginator.getPageSize();
  }

  public PagedList(long totalCount, Paginator paginator, Collection<T> list) {
    this.list = new ArrayList<T>(list);
    this.totalCount = totalCount;
    this.currentPage = paginator.getPage();
    this.pageSize = paginator.getPageSize();
  }

  public long getTotalPage() {
    if (this.totalPage == 0L) {
      if (this.totalCount <= 0L)
        this.totalPage = 1L;
      else {
        this.totalPage = (this.totalCount % this.pageSize > 0L ? this.totalCount / this.pageSize + 1L : this.totalCount / this.pageSize);
      }
    }
    return this.totalPage;
  }

  public void setTotalPage(long totalPage) {
    this.totalPage = totalPage;
  }

  public long getTotalCount() {
    return this.totalCount;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
  }

  public long getCurrentPage() {
    return this.currentPage;
  }

  public void setCurrentPage(long currentPage) {
    this.currentPage = currentPage;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public ArrayList<T> getList() {
    return this.list;
  }

  public void setList(ArrayList<T> list) {
    this.list = list;
  }

  public static <E> PagedList<E> wrap(Collection<E> collection)
  {
    return new PagedList<E>(collection.size(), Paginator.page(1L, collection.size()), collection);
  }
}