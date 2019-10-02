package hello;

import org.apache.thrift.TException;

/**
 * @description:
 * @author: gmf
 * @date: Created in 2019/10/2 10:24
 * @version:
 * @modified By:
 */
public class HelloServiceImpl implements HelloService.Iface{
    @Override
    public String helloString(String para) throws TException {
        return "hi:"+para+"你好呀";
    }
}
