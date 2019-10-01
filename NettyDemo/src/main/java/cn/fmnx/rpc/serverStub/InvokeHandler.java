package cn.fmnx.rpc.serverStub;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;


/**
 * @description:服务器端业务处理类
 * @author: gmf
 * @date: Created in 2019/10/1 21:52
 * @version:
 * @modified By:
 */
public class InvokeHandler extends ChannelInboundHandlerAdapter {
    //得到某接口下某个实现类的名字
    private String getImplClassName(ClassInfo classInfo)throws Exception{
        //服务方接口和实现类所在的包路径
        String interfacePath = "cn.fmnx.rpc.server";//cn.fmnx.rpc.server
        int lastDot = classInfo.getClassName().lastIndexOf(".");
        String interfaceName = classInfo.getClassName().substring(lastDot);
        Class superClass = Class.forName(interfacePath+interfaceName);
        Reflections reflections = new Reflections(interfacePath);
        //得到某接口下的所有实习类
        Set<Class> implClassSet = reflections.getSubTypesOf(superClass);
        if (implClassSet.size()==0){
            System.out.println("未找到实现类");
            return null;
        }else if(implClassSet.size()>1){
            System.out.println("找到多个实现类，未明确使用那个");
            return null;
        }else {
            //把集合转换成数组
            Class[] classes = implClassSet.toArray(new Class[0]);
            return classes[0].getName();//得到实现类的名字
        }
    }

    @Override//读取客户端发来的数据并通过反射调用实现类的方法
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClassInfo classInfo = (ClassInfo)msg;
        Object o = Class.forName(getImplClassName(classInfo)).newInstance();
        Method method = o.getClass().getMethod(classInfo.getMethodName(),classInfo.getTypes());
        //通过反射调用实现类的方法
        Object invoke = method.invoke(o, classInfo.getObjects());
        ctx.writeAndFlush(invoke);
    }
}
