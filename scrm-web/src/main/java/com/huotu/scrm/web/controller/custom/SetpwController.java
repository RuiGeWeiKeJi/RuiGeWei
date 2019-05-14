package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.service.service.user.UseService;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequestMapping()
@Controller
public class SetpwController {

    @Autowired
    private UseService useService;

    @RequestMapping(value = "/setpw")
    @ResponseBody
    public Object findSetpw(HttpServletRequest request){

        String username = GetUserLoginInfo.getUserInfo(request).getUSE002();
        List<String> getAllUser=new ArrayList<>();
        if(username.equals("系统管理员")){
            getAllUser=useService.getAllBy();
        }else {
            getAllUser.add(username);
        }

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("SetPw");
        modelAndView.addObject("userpw",getAllUser);
        return modelAndView;
    }

    @RequestMapping(value = "/editpw")
    @ResponseBody
    public Object editPw(
            @RequestParam("user") String user,
            @RequestParam("pw1") String password
    ) {

        int result = useService.updateUserPass(user, password);
        if (result > 0)
            return "true";
        else
            return "false";
    }

}
