package cn.itcast.travel.dao;

import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Route;
import org.junit.Test;

import java.util.List;

public class RouteDaoTest {
    RouteDao dao = new RouteDaoImpl();

    @Test
    public void test1(){
        int totalCount = dao.findTotalCount(1);
        System.out.println(totalCount);
        List<Route> list = dao.findByPage(1, 1, 5);
        for (Route route : list) {
            System.out.println(route.getRname());
        }
        System.out.println(list);
    }

}
