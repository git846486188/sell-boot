package com.neuedu.sell.utill;

import com.neuedu.sell.VO.ResultVO;

public class ResultVOUtill {
    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(data);
        return resultVO;
    }
    public static ResultVO success(){
        return success(null);
    }
    public static ResultVO reeor(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
