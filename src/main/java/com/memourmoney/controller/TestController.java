//package com.memourmoney.controller;
//
//import io.micrometer.common.util.StringUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.ObjectUtils;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.xml.transform.Result;
//
//@RestController
//public class TestController {
//
//    @PostMapping("/test")
//    public Result service(@Validated @RequestBody UserRequestBo requestBo) throws Exception {
//        Result result = new Result();
//        // 参数校验
//        if (StringUtils.isNotEmpty(requestBo.getId())
//                || StringUtils.isNotEmpty(requestBo.getType())
//                || StringUtils.isNotEmpty(requestBo.getName())
//                || StringUtils.isNotEmpty(requestBo.getAge())) {
//            throw new Exception("必输项校验失败");
//        } else {
//            // 调用service更新user，更新可能抛出异常，要捕获
//            try {
//                int count = 0;
//                User user = userService.queryUser(requestBo.getId());
//                if (ObjectUtils.isEmpty(user)) {
//                    result.setCode("11111111111");
//                    result.setMessage("请求失败");
//                    return result;
//                }
//                // 转换业务对象
//                UserDTO userDTO = new UserDTO();
//                BeanUtils.copyProperties(requestBo, userDTO);
//                if ("02".equals(user.getType())) {// 退回修改的更新
//                    count = userService.updateUser(userDTO);
//                }else if ("03".equals(user.getType())) {// 已生效状态，新增一条待复核
//                    count = userService.addUser(userDTO);
//                }
//                // 组装返回对象
//                result.setData(count);
//                result.setCode("00000000");
//                result.setMessage("请求成功");
//            } catch (Exception ex) {
//                // 异常处理
//                result.setCode("111111111111");
//                result.setMessage("请求失败");
//            }
//        }
//        return result;
//    }
//}
