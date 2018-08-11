package com.leo.infoContainer;

/**
 * Created by DELL on 2018/8/2.
 */
public class IpContainer {

    //192.168.2.110   ;  192.168.2.110  ; 192.168.2.110


    //请求的Servlet（ip必须是静态ip，不能是localhost、1127.0.0.1）
    public final static String LoginServlet = "http://192.168.2.110:8080/AndroidWebTest/LoginServlet";
    //查看空座位
    public final static String QuerySpareSeatServlet = "http://192.168.2.110:8080/AndroidWebTest/QuerySpareSeatServlet";
    //查看被使用座位
    public final static String QueryOccupiedSeatServlet = "http://192.168.2.110:8080/AndroidWebTest/QueryOccupiedSeatServlet";
    //查看座位的选餐信息
    public final static String QuerySeatOrderServlet = "http://192.168.2.110:8080/AndroidWebTest/QuerySeatOrderServlet";
    //查看消费额度
    public final static String QueryTotalConsumeServlet = "http://192.168.2.110:8080/AndroidWebTest/QueryTotalConsumeServlet";
    //查看 并台 消费额度
   // public final static String QueryMergerConsumeServlet = "http://192.168.2.110:8080/AndroidWebTest/QueryMergerConsumeServlet";
    //改变座位状态
    public final static String ChangeSeatStateServlet = "http://192.168.2.110:8080/AndroidWebTest/ChangeSeatStateServlet";
    //改变座位状态
    public final static String ChangeSeatServlet = "http://192.168.2.110:8080/AndroidWebTest/ChangeSeatServlet";
    //获取菜单
    public final static String QueryMenuServlet = "http://192.168.2.110:8080/AndroidWebTest/QueryMenuServlet";
    //下单
    public final static String MakeOrderServlet = "http://192.168.2.110:8080/AndroidWebTest/MakeOrderServlet";
    //单个座位结账
    public final static String CheckoutServlet = "http://192.168.2.110:8080/AndroidWebTest/CheckoutServlet";


}
