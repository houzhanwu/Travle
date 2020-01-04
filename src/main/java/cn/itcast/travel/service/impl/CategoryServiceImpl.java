package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();

    /**
     * 查询所有 nav （导航栏）信息
     * @return
     */
    @Override
    public List<Category> findAll() {

        //1.1从jedis查询，获取Jdeis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可使用sortSet排序查找
      //  Set<String> categorys = jedis.zrange("category", 0, -1);
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        // System.out.println("categorys" +categorys);
        //2判断查询的集合是否为空
        List<Category> cs =null;
        if(categorys == null || categorys.size() == 0){

            //System.out.println("从数据库查询。。。");
            //3.如果为空，需要从数据库查询，将数据存入redis
            //3.1从数据库查询
             cs = dao.findAll();
            //3.2将集合数据存入到redis中的category 的key
            for (int i = 0; i < cs.size() ; i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
           // System.out.println("从redis查询。。。");
            //4.如果不为空，将set数据存入list
            //目的为了将set数据与list数据相统一为list
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }

}
