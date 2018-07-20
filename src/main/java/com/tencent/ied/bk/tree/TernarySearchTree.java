package com.tencent.ied.bk.tree;

import com.tencent.ied.bk.domain.Abstract;

import java.util.Set;
import java.util.TreeSet;

/**
 * Ternary search Tree 三叉树
 * @author qiancheng
 */
public class TernarySearchTree<E extends Abstract> {
    private int size;           // 树大小
    private Node<Abstract> root;   // 根结点
    private static final int MAX_DEPTH = 50;

    /**
     * 节点类
     * @param <V>
     */
    class Node<V> {
        private char c;  // 字符
        private Node<V> left, mid, right;  // 每个节点下有三个子节点
        private Set<V> valSet = new TreeSet<V>(); // 存放当前节点绑定的内容
    }

    /**
     * 获取树的大小
     * @return size
     */
    public int size() { return size; }

    /**
     * 判断是否存在此字符key
     * @return true if key exists
     */
    public boolean contains(String key) { return get(key) != null; }

    /**
     * 根据指定的key返回绑定的内容集合
     * @param key 关键词
     * @return key返回绑定的内容集合
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null} or empty
     */
    public Set<Abstract> get(String key) {
        if (key == null){
            throw new IllegalArgumentException("calls get() with null argument");
        }
        else if (key.length() == 0){
            throw new IllegalArgumentException("key must have length >= 1");
        }

        Node<Abstract> x = get(root, key, 0);
        return x == null ? null : x.valSet;
    }

    /**
     * 根据key关键词返回节点
      * @param node 节点
     * @param key 关键词
     * @param depth 深度
     * @return
     */
    private Node<Abstract> get(Node<Abstract> node, String key, int depth) {
        if (node == null){
            return null;
        }

        if (key.length() == 0){
            throw new IllegalArgumentException("key must have length >= 1");
        }

        char c = key.charAt(depth);
        if      (c < node.c)              {return get(node.left,  key, depth);}
        else if (c > node.c)              {return get(node.right, key, depth);}
        else if (depth < key.length() - 1) {return get(node.mid,   key, depth+1);}
        else                           {return node;}
    }

    /**
     * 根据关键词插入 k-v 结构数据
     * @param key 关键词
     * @param val 绑定数据
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public synchronized void put(String key, Abstract val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }

        if (!contains(key)){
            size++;
        }

        root = put(root, key, val, 0);
    }

    /**
     * 构造三叉树节点
     * @param node
     * @param key
     * @param val
     * @param depth
     * @return
     */
    private synchronized Node<Abstract> put(Node<Abstract> node, String key, Abstract val, int depth) {
        /* 1<=单词所含字母数<=50 */
        if(depth>MAX_DEPTH){
            throw new IllegalArgumentException("word is too long over 50 character!");
        }

        char c = key.charAt(depth);
        if (node == null) {
            /* 如果节点不存在则创建 */
            node = new Node<Abstract>();
            node.c = c;
        }


        if      (c < node.c)               {node.left  = put(node.left,  key, val, depth);}
        else if (c > node.c)               {node.right = put(node.right, key, val, depth);}
        else if (depth < key.length() - 1)  {node.mid   = put(node.mid,   key, val, depth + 1);}
        else                            {node.valSet.add(val);}
        return node;
    }

    /**
     * 返回所有以prefix开头绑定的数据（摘要）对象集合
     * @param prefix 关键词
     * @return all of the values in the set that start with {@code prefix},
     *     as a list
     * @throws IllegalArgumentException if {@code prefix} is {@code null}
     */
    public Set<Abstract> valuesWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }

        Set<Abstract> values = new TreeSet<Abstract>();
        Node<Abstract> x = get(root, prefix, 0);
        if (x == null){
            return values;
        }
        else if (!x.valSet.isEmpty()){
            values = x.valSet;
        }

        values.stream().forEach(anAbstract -> anAbstract.getSearchCount().incrementAndGet());
        collect(x.mid, new StringBuilder(prefix), values);
        return values;
    }

    /**
     * 归拢所有以关键词prefix开头的key绑定的数据集合
     * @param node
     * @param prefix
     * @param values
     */
    private void collect(Node<Abstract> node, StringBuilder prefix, Set<Abstract> values) {
        if (node == null) {return;}
        collect(node.left,  prefix, values);
        if (!node.valSet.isEmpty()) {
            node.valSet.removeAll(values);
            /* 包含匹配 */
            node.valSet.stream()
                    .filter(anAbstract -> !values.contains(anAbstract))/* 去掉已经全词搜索出来的 */
                    .forEach(anAbstract -> anAbstract.getContainsMatchCount().incrementAndGet());/* 给摘要的包含匹配计数加一 */
            values.addAll(node.valSet);/* 把包含匹配的结果加入到总结果集合 */
        }
        collect(node.mid,   prefix.append(node.c), values);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(node.right, prefix, values);
    }

}