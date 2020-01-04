package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid查询总记录数
     */
    @Override
    public int findTotalCount(int cid,String rname) {
        //String sql  ="select count(*) from tab_route where cid = ?";
        //1.定义sql模板
        String sql  ="select count(*) from tab_route where 1=1  ";
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        List params = new ArrayList();    //条件们
        //拼接字符串 进行查询
        //判断参数是否有值
        if (cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);   //添加 ？ 对应的值
        }
        if (rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");    //模糊查询
            params.add("%"+rname+"%");
        }
        sql = sb.toString();

        return template.queryForObject(sql,Integer.class,params.toArray());   //把集合转换为数组
    }

    /**
     * 根据cid，start，pageSize查询当前页的数据集合
     */
    @Override
    public List<Route> findByPage(int cid,  int start, int pageSize,String rname) {
        //String sql = "select * from tab_route where cid = ? limit ? , ?";

        //1.定义sql模板
        String sql  ="select * from tab_route where 1=1  ";
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        List params = new ArrayList();    //条件们
        //拼接字符串 进行查询
        //判断参数是否有值
        if (cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);   //添加 ？ 对应的值
        }
        if (rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");    //模糊查询
            params.add("%"+rname+"%");
        }
        params.add(start);
        params.add(pageSize);
        sb.append(" limit ? , ? ");  //分页条件
        sql = sb.toString();

        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }
}
