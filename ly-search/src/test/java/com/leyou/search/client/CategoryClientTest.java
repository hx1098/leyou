package com.leyou.search.client;

import com.leyou.LySearchApplication;
import com.leyouo.item.pojo.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IDEA
 * author:hanxiao
 * Date:2020/2/9
 * Time:15:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LySearchApplication.class})
public class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void queryCategoryByIds() {
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(1L, 2L, 3L));
        Assert.assertEquals(3, categories.size());
        for (Category category : categories) {
            System.err.println("ategory = " + category );
        }
        while (true) {

        }
    }
}