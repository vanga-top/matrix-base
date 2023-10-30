package org.abc.matrix.commons.lang.result;

/**
 * 带分页类型的返回
 * Created by chenhui on 16/9/27.
 */
public class BaseListResult<T> extends BaseResult<T> {

    /**
     * 总记录数
     */
    private int total;
    /**
     * 当前查询的页
     */
    private int currentPage;

    /**
     * 每一页的大小
     */
    private int pageSize;


    public BaseListResult(T resultData, boolean success, String code, String resultMessage) {
        super(resultData, success, code, resultMessage);
    }

    public BaseListResult() {
    }

    public int getTotal() {
        return total;
    }

    public BaseListResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public BaseListResult setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public BaseListResult setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 设置分页基本信息
     *
     * @param currentPage
     * @param pageSize
     * @param total
     * @return
     */
    public BaseListResult setPageInfo(int currentPage, int pageSize, int total) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        return this;
    }
}
