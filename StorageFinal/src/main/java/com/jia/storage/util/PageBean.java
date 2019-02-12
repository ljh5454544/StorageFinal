package com.jia.storage.util;

public class PageBean {

    private int pageNum;
    private int maxPage;
    private int rowsNum;
    private int rowsPerPage = 5;

    public int getLastPage(){
        if (pageNum > 1){
            return pageNum - 1;
        }
        return 1;
    }

    public int getNextPage(){
        if (pageNum < maxPage){
            return pageNum + 1;
        }
        return maxPage;
    }

    public int getPageNum(){
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getRowsNum() {
        return rowsNum;
    }

    public void setRowsNum(int rowsNum) {
        this.rowsNum = rowsNum;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }
}
