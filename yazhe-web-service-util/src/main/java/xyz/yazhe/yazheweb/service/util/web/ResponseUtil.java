package xyz.yazhe.yazheweb.service.util.web;

import com.google.gson.Gson;
import xyz.yazhe.yazheweb.service.util.GsonUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 向客户端设置返回数据类型的工具
 * @author BeFondOfTaro
 * Created at 13:44 2018/3/16
 */
public class ResponseUtil {

	private static Gson gson = GsonUtil.getGson();

    /**
     * 返回json格式数据
     * @param httpServletResponse
     * @param data 数据对象
     * @throws IOException
     */
    public static void toJson(HttpServletResponse httpServletResponse, Object data) throws IOException{
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.getWriter().write(
                gson.toJson(data)
        );
    }
}
