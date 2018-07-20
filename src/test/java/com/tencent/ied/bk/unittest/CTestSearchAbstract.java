package com.tencent.ied.bk.unittest;

import com.tencent.ied.bk.CSearchAbstract;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CTestSearchAbstract {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindFlushPattern() {
        CSearchAbstract objSA = new CSearchAbstract();
        objSA.addAbstract("These are good books .You can choose one book from them.", 9);
        objSA.addAbstract("You can search books from Google.", 8);
        objSA.addAbstract("Search and preview millions of books from libraries and publishers worldwide using Google Book Search.", 8);
        objSA.addAbstract("Go to Google Books Home. Advanced Book Search, About Google ... All books.", 6);
        objSA.addAbstract("Bookshelf provides free access to books and documents in life science and healthcare.", 7);

        String strRst = objSA.searchAbstract("BOOK");

        String rstExpt = "These are good books .You can choose one book from them.# Search and preview millions " +
        "of books from libraries and publishers worldwide using Google Book Search.# You can search books from Google.# Bookshelf provides" +
        " free access to books and documents in life science and healthcare.# Go to Google Books Home. Advanced Book Search, About " +
        "Google ... All books.";
        assertEquals(rstExpt,strRst);
        strRst = objSA.searchAbstract("google");
        rstExpt = "You can search books from Google.# Search and preview millions of books from libraries and publishers worldwide using "
       + "Google Book Search.# Go to Google Books Home. Advanced Book Search, About Google ... All books.";

       assertEquals(rstExpt,strRst);
	}

    /**
     * 边界：添加空白字符串摘要
     */
	@Test
    public void testAddBlank(){
        CSearchAbstract objSA = new CSearchAbstract();
        assertEquals(false,objSA.addAbstract("", 9));
    }

    /**
     * 边界：添加超过200个摘要
     */
    @Test
    public void testOver200Add(){
        CSearchAbstract objSA = new CSearchAbstract();
        for (int i = 0; i <= 200; i++) {
            objSA.addAbstract("You can search books from Google.", 2);
        }
        assertEquals(false,objSA.addAbstract("You can search books from Google.", 9));
    }

    /**
     * 边界：单词总数超过50
     */
	@Test
    public void testWordsCountOutofRangeAdd(){
        CSearchAbstract objSA = new CSearchAbstract();
        assertEquals(false,objSA.addAbstract(
                "You can search books from Google " +
                            "You can search books from Google " +
                            "You can search books from Google " +
                            "You can search books from Google " +
                            "You can search books from Google " +
                            "You can search books from Google " +
                            "You can search books from Google " +
                            "You can search books from Google " +
                            "You can search .", 2));
     }

    /**
     * 边界：单词字符超长 51
     */
    @Test //(expected=java.lang.IllegalArgumentException.class)
    public void testWordLenghtOutofRangeAdd(){
        CSearchAbstract objSA = new CSearchAbstract();
        assertEquals(false,objSA.addAbstract(
                "YoucansearchbooksfromGoogleYoucansearchbooksfromGoog You can search .", 2));

    }

    /**
     * 边界：搜索关键词为空
     */
    @Test
    public void testSearchBlankKeyword(){
        CSearchAbstract objSA = new CSearchAbstract();
        objSA.addAbstract("You can search .", 2);
        assertEquals("",objSA.searchAbstract(" "));
    }

}
