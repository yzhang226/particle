package org.lightning.particle.core.test;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.lightning.particle.core.test.model.User;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STGroupString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cook at 2018/7/8
 */
public class StringTemplateTest {

    @Test
    public void test1() {
        ST hello = new ST("Hello, <name>");
//        ST hello = new ST("Hello, $name$");
        hello.add("name", "World");
        System.out.println(hello.render());
    }

    @Test
    public void test2() {
        // 第二个和第三个参数用于定义表达式的头尾字符
        ST hello = new ST("Hello, $if(name)$$name$$endif$", '$', '$');
        hello.add("name", "risk");
        System.out.println(hello.render());
    }

    @Test
    public void test3() {
        ST hello = new ST("Hello, <if(name)><name><endif>");
        hello.add("name", "risk");
        System.out.println(hello.render());
    }

    @Test
    public void test4() {
        STGroup stg = new STGroupString("sqlTemplate(columns,condition) ::= \"select <columns> from table where 1=1 <if(condition)>and <condition><endif> \"");
        ST sqlST = stg.getInstanceOf("sqlTemplate");
        sqlST.add("columns","order_id");
        sqlST.add("condition", "dt='2017-04-04'");
        System.out.print(sqlST.render());
    }

    @Test
    public void test5() {
        STGroup stg = new STGroupString("sqlTemplate(columns,condition) ::= <% select <columns> \n" +
                "from table \n" +
                "where 1=1 <if(condition)>and <condition><endif> %>");
        ST sqlST = stg.getInstanceOf("sqlTemplate");
        sqlST.add("columns","order_id");
        sqlST.add("condition", "dt='2017-04-04'");
        System.out.print(sqlST.render());
    }

    @Test
    public void test6() {
        STGroup stg = new STGroupFile("dataExtractSql.stg");
        ST sqlST = stg.getInstanceOf("sqlTemplate");

        List<String> columnList = new ArrayList<>();
        columnList.add("order_id");
        columnList.add("price");
        columnList.add("phone");
        sqlST.add("columns", columnList);
        sqlST.add("condition", "dt='2017-04-04'");
        System.out.print(sqlST.render());
    }

    @Test
    public void test7() {
        STGroup stg = new STGroupFile("dataExtractSql2.stg");
        ST sqlST = stg.getInstanceOf("sqlTemplate");

        sqlST.add("columns", Lists.newArrayList("order_id", "price", "phone", "user"));
        sqlST.add("condition", "dt='2017-04-04'");
        sqlST.add("joinKey", "user");
        sqlST.add("tableName", "orderTable");

        sqlST.add("childColumns", Lists.newArrayList("user", "userLeave", "userLocation"));
        sqlST.add("childJoinKey", "user");
        sqlST.add("childTableName", "userTable");

        String result = sqlST.render();

        System.out.print(result);
    }

    @Test
    public void test8() {
        STGroup stg = new STGroupFile("demo2.stg");
        ST say = stg.getInstanceOf("say");
        say.add("name", "mushan");
        say.add("name","willing");
        System.out.println(say.render());
        System.out.println(say.render(30));
    }

    @Test
    public void test9() {
//        ST st = new ST("<b>$u.id$</b>: $u.name$", '$', '$');
        ST st = new ST(" <u.id> : <u.name>");
        st.add("u", new User(999, "part"));
        String result = st.render();
        System.out.println(result);
    }

    @Test
    public void test10() {
        STGroup stg = new STGroupFile("demo3.stg");
        ST st = stg.getInstanceOf("introduction");
        st.addAggr("person.{name,age}","mushan", 18);
        System.out.println(st.render());
    }

}
