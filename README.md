# 搜索算法练习

## 名词说明

**单词**
由大小写英文字母组成，不含其它字符。

**摘要**
由多个单词和语句结束符组成。
一条语句内的单词间用一个空格分隔；
摘要中若语句结束，以英文逗号或句号结尾。

**搜索次数**
标识该摘要被搜索次数。
搜索次数大于等于0。

**搜索关键词**
由一个关键单词组成，不包含2个及其以上的单词.

**全词匹配**
搜索的关键词与摘要中的单词完全一致，不存在被包含关系。

**包含匹配**
关键词与摘要中的单词为包含关系，如关键单词operation，被cooperation包含。

### 搜索规则
1、搜索出所有包含关键单词的摘要。
2、每次搜索时，若该摘要被搜索到关键词，则其搜索次数自动加1。
3、输出的摘要间以#符号间隔。
4、收索次数最高的摘要排在最前面。
5、若几份摘要的搜索次数相同，则输出规则按以下方式进行排序:
	 A、比较全词匹配的关键字次数，按次数由大到小输出摘要。
	 B、若全词匹配的关键字次数相同，则比较 包含匹配次数，次数多的优先输出。
	 C、若全词匹配与包含匹配次数相同，则对摘要按由小到大进行排序输出。
6、进行搜索时，不区分大小写。

**待实现接口**
```java
boolean addAbstract(String strAbstract,integer iCount)
````
	strAbstract: 摘要输入入参，同一份摘要不会重复输入。
	 iCount:摘要的搜索次数。
	若入参为摘要空，直接返回false。


```java
String searchAbstract(String strKeyWord)
```
	 strKeyWord：搜索的关键单词。
	返回值为搜索符合关键字的摘要，每份摘要间以”#”号间隔。

### 举例
**输入内容:**
```java
addAbstract(“These are good books .You can choose one book from them.”,9);
addAbstarct(“You can search books from Google.”,8);
addAbstarct(“Search and preview millions of books from libraries and publishers worldwide using Google Book Search.”,8);
addAbstarct(“Go to Google Books Home. Advanced Book Search, About Google ... All books.”,6)
addAbstarct(“Bookshelf provides free access to books and documents in life science and healthcare.”,7)
```

第一次搜索 关键词为BOOK

**输出:**
```java
These are good books .You can choose one book from them.# Search and preview millions of books from libraries and publishers worldwide using Google Book Search.# You can search books from Google.# Bookshelf provides free access to books and documents in life science and healthcare.# Go to Google Books Home. Advanced Book Search, About Google ... All books.
```

第二次搜索 关键词为Google 

**输出:**
You can search books from Google .# Search and preview millions of books from libraries and publishers worldwide using Google Book Search .#Go to Google Books Home. Advanced Book Search, About Google ... All books.


### 规格
0<=摘要个数范围<=200
1<=摘要所含单词个数<=50
1<=单词所含字母数<=50
超出如上约束的输入认为是错误的，对应接口返回失败


### 其他要求：

- 已经提供初步的代码框架及测试用例，请在此框架上继续完成代码，并保证基本用例通过。

- 可以根据实际需要自由增加类等数据结构定义

- 要求考虑并发场景保证数据安全。

- 不得修改原有的接口定义。

- 不限制其他类库的使用。

- 注意遵从编程规范，圈复杂度不超过10。

- 尽善尽美地发挥，将工程师的思想发挥出来
