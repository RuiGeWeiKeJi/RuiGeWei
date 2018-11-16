package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.service.user.UseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UseService useService;

    /**
     * 获取员工编号
     * @return
     */
    @RequestMapping("/userId")
    public ModelAndView getUseId() {
        ModelAndView model = new ModelAndView();
        String uid = useService.findByCode();
        model.addObject("uid", uid);
        model.setViewName("UserAE");
        return model;
    }

    /**
     * 保存员工数据
     * @param use
     * @return
     */
    @RequestMapping(value = "/userSave")
    @ResponseBody
    public Object insertUse(User use) {
        if(use!=null){
            if(use.getUSE005().equals("1"))
                use.setUSE005("男");
            else
                use.setUSE005("女");
        }
        useService.insertUse(use);
        List<User> userList=useService.findAllBy();

        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", userList.size());
        datasource.put("data", userList);
        return datasource;
    }


    /**
     * 删除联系人
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/useDelete")
    @ResponseBody //必须要有这个,如果没有,ajax异步请求不能就收到数据
    public Object deleteByUserId(
            @RequestParam(value = "userId") String userId
    ) {
        User user=useService.findOneByUserId(userId);
        useService.deleteByUserId(user);
        List<User> userList=useService.findAllBy();

        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", userList.size());
        datasource.put("data", userList);
        return datasource;

    }

    /**
     * 编辑联系人
     * @return
     */
    @RequestMapping(value = "/findUserAE")
    public String gotoUserAE(){
        return "UserAE";
    }


}
