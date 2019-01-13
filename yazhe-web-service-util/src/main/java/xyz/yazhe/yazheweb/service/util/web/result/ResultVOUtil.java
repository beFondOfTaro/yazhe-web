package xyz.yazhe.yazheweb.service.util.web.result;


import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResultEnum;

/**
 * http响应的工具类
 * @author BeFondOfTaro
 * Created in 12:24 2018/1/18
 */
public class ResultVOUtil{

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultEnum.SUCCESS.getMessage());
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO error(ResultEnum resultEnum){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }

	/**
	 * 返回系统异常
	 * @return
	 */
	public static ResultVO retSysError(){
    	ResultVO resultVO = new ResultVO();
    	resultVO.setCode(ResultEnum.UNKNOWN_ERROR.getCode());
    	resultVO.setMsg(ResultEnum.UNKNOWN_ERROR.getMessage());
    	return resultVO;
	}
}
