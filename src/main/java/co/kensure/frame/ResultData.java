package co.kensure.frame;

/**
 *  <p>消息集
 *  <p>row：             一条信息
 *  <p>rows：            多条信息
 *  <p>fields：          信息头字段
 *  <p>total：           信息总数
 * @author fankaidi
 */
public class ResultData {

    /**
     * 实体类
     */
    private Object row;

    /**
     * 实体集合
     */
    private Iterable<?> rows;

    /**
     * 列集合
     */
    private Iterable<String> fields;

    /**
     * 合计
     */
    private Long total;


    public ResultData(){}

    public ResultData(Object row, Iterable<?> rows, Iterable<String> fields, Long total) {
        this.row = row;
        this.rows = rows;
        this.fields = fields;
        this.total = total;
    }

    /**
     * 实体类
     * @return  class
     */
    public Object getRow() {
        return row;
    }

    public void setRow(Object row) {
        this.row = row;
    }
    /**
     * 类集合
     * @return list
     */
    public Iterable<?> getRows() {
        return rows;
    }

    public void setRows(Iterable<?> rows) {
        this.rows = rows;
    }

    /**
     * 列名称集合
     * @return  list
     */
    public Iterable<String> getFields() {
        return fields;
    }

    public void setFields(Iterable<String> fields) {
        this.fields = fields;
    }

    /**
     * 类集合长度
     * @return  int
     */
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
