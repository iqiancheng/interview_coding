package com.tencent.ied.bk;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.tencent.ied.bk.domain.Abstract;
import com.tencent.ied.bk.tree.TernarySearchTree;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 摘要搜索类
 * 请在JDK8 环境运行
 * @author qiancheng
 */
@Slf4j
public class CSearchAbstract {

	private TernarySearchTree<Abstract> tree = new TernarySearchTree<Abstract>();

	private static final int MAX_CAPACITY = 200;

	private volatile AtomicInteger idGenerator = new AtomicInteger(0);

	/**
	 * 输入摘要 （最多支持200个摘要）
	 * @param strAbstract
	 *            标识输入的摘要。
	 * @param iCount
	 *            标识该摘要搜索的次数。
	 * @return 成功返回true,异常返回FALSE。
	 */
	public boolean addAbstract(String strAbstract, int iCount) {
		if(StringUtils.isBlank(strAbstract) || idGenerator.get()>MAX_CAPACITY){
			/* 最多支持200个摘要 */
			return false;
		}

		synchronized (idGenerator){
			if(!(idGenerator.get()>MAX_CAPACITY)){
				Abstract anAbstract = new Abstract();
				anAbstract.setId(idGenerator.incrementAndGet());// id
				anAbstract.setContent(strAbstract);// 摘要内容
				anAbstract.setSearchCount(new AtomicInteger(iCount));// 搜索次数
				List<String> wordList = splittWords(strAbstract);
				int wordsTotalCount = wordList.size();
				/* 1<=摘要所含单词个数<=50 */
				if(wordsTotalCount<1 || wordsTotalCount >50){
					return false;
				}
				anAbstract.setWordsTotalCount(wordsTotalCount);// 摘要总单词数
				try {
					buildTree(wordList,anAbstract);
				} catch (IllegalArgumentException e) {
					log.error("输入摘要遇到异常：摘要的格式不合法（单词数超长或单词超长等）",e.getMessage());
					return false;
				} catch (Exception e) {
					log.error("输入摘要遇到异常",e);
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 从摘要拆分单词
	 * @param strAbstract
	 * @return
	 */
	private List<String> splittWords(String strAbstract) {
		return Splitter.on(CharMatcher.anyOf(".;, :_-'\"?!"))/* 去除标点符号 */
										.trimResults() /* 去除空格 */
										.omitEmptyStrings() /* 忽略空字符串*/
										.splitToList(strAbstract);

	}

	/**
	 * 把搜索的摘要句子拆分成单词，以单词为key构建三叉树结构
	 * @param  wordList
	 * @return
	 */
	private void buildTree(List<String> wordList, Abstract ab) {
		if(CollectionUtils.isEmpty(wordList)){
			return;
		}

		HashSet<String> wordSet = new HashSet<String>();
		for (String s : wordList) {
			String lowerStr = s.toLowerCase();
			if(!wordSet.contains(lowerStr)){
				wordSet.add(lowerStr);
			}
		}
		/* 以单词为key 加入到树结构中 */
		wordSet.stream().forEach(word->tree.put(word,ab));
	}

	/**
	 * 根据关键词搜索
	 * @param strKeyWord
	 *            搜索关键词。
	 * @return 返回搜索到的摘要。
	 */
	public String searchAbstract(String strKeyWord) {
		String val = StringUtils.EMPTY;
		if(StringUtils.isBlank(strKeyWord) || tree.size()==0){
			return val;
		}

		/* 根据关键词在TST树中查找结果集合 */
		Set<Abstract> resultSet = tree.valuesWithPrefix(strKeyWord.toLowerCase());
		val = resultSet.stream()
				.map(ab-> ab.getContent())
				.collect(Collectors.joining("# "));/* 拼接成以#分隔的字符串结果 */

		return  val;
	}
}
