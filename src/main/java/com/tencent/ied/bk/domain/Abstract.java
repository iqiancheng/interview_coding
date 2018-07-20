package com.tencent.ied.bk.domain;

import lombok.Data;

/**
 * 摘要实体类
 * @author qiancheng
 */
@Data
public class Abstract extends SearchEntry implements Comparable<Abstract> {

    /**
     * 摘要ID
     */
    private Integer id;

    /**
     * 摘要内容
     */
    private String content;

    /**
     * 摘要长度（单词数）
     */
    private int wordsTotalCount;


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Abstract o) {
        /* 比较全词匹配的关键字次数，按次数由大到小输出摘要 */
        int x = o.getSearchCount().get() - this.getSearchCount().get();
        /* 若全词匹配的关键字次数相同，则比较 包含匹配次数，次数多的优先输出 */
        int y = o.getContainsMatchCount().get() - this.getContainsMatchCount().get();
        /* 若全词匹配与包含匹配次数相同，则对摘要按由小到大进行排序输出 */
        int z = o.getWordsTotalCount() - this.getWordsTotalCount();
        if(x==0){
            if(y==0){
                return -z;// 由小到大排
            }
            return y;
        }
        return x;
    }
}
