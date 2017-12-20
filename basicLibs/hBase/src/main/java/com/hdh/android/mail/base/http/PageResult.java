package com.hdh.android.mail.base.http;

/**
 *
 * @author albert  <a href="mailto:zhengting.zhou@gmail.com">Contact me.</a>
 * @since 2017/11/23 18:24
 * @param <T>
 */
public class PageResult<T> {
    public int pageCount;
    public int start;
    public int startOfPage;
    public int resultCount;
    public int pageSize;
    public T data;
    public int currentPage;

}
