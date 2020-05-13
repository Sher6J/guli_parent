package cn.sher6j.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sher6j
 * @create 2020-05-13-21:57
 */
@Data  //生成get/set方法
@AllArgsConstructor //生成有参构造方法
@NoArgsConstructor //生成无参构造方法
public class GuliException extends RuntimeException {

    private Integer code; //异常的状态码

    private String msg; //异常信息


}
