package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.entity.Power.Authority;
import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.service.cutom.CustemInfoService;
import com.huotu.scrm.service.service.user.LoginService;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 访问默认打开页面
     *
     * @return
     */
    @RequestMapping(value = "")
    public Object findFirst(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        //获取cookie中的用户名和密码进行登录
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                    Constant.userName=username;
                }
                if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }
        ModelAndView model = new ModelAndView();
        model.addObject("username", username);
        model.addObject("password", password);
        if (password != null)
            model.addObject("check", true);
        else
            model.addObject("check", false);
        model.setViewName("Login");
        return model;
        //return "Login";
    }

    @RequestMapping(value = "/cusop")
    public String findWel() {
        return "CusOp";
    }

    /**
     * 用户登录
     *
     * @param uaserName
     * @param password
     * @return
     */
    @RequestMapping("/loginuser")
    @ResponseBody
    public Object findUser(
            @RequestParam("username") String uaserName,
            @RequestParam("password") String password,
            @RequestParam("remeber") String remeber,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                //单表条件对象构建(定区编码和地区关键字在分区表中)
                if (!StringUtils.isEmpty(uaserName)) {
                    Predicate p1 = cb.equal(root.get("USE002"), uaserName);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(password)) {
                    Predicate p1 = cb.equal(root.get("USE006"), password);
                    plist.add(p1);
                }

                //pList转换为具体类型的数组
                Predicate[] predicate = plist.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);

            }
        };

        User user = loginService.findLogin(specification);
        Map<String, Object> map = new LinkedHashMap<>();
        if (user == null) {
            map.put("fail", "fail");
        } else {
            map.put("user", user);
            request.getSession().setAttribute("user", user);

            Constant.userName=user.getUSE002();
            System.out.println(Constant.userName);

            //记住密码
            if (!StringUtils.isEmpty(remeber)) {
                Cookie cookie_username = new Cookie("username", user.getUSE002());
                Cookie cookie_password = new Cookie("password", user.getUSE006());
                //设置持久化时间
                cookie_username.setMaxAge(60 * 60 * 24);
                cookie_password.setMaxAge(60 * 60 * 24);
                //设置携带路径  访问全部资源都携带
                cookie_username.setPath(request.getContextPath());
                cookie_password.setPath(request.getContextPath());
                //发送
                response.addCookie(cookie_username);
                response.addCookie(cookie_password);
            } else {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("username".equals(cookie.getName())) {
                            Cookie cookie1 = new Cookie("username", null);
                            cookie1.setMaxAge(0);
                            cookie1.setPath(request.getContextPath());
                        }
                        if ("password".equals(cookie.getName())) {
                            Cookie cookie1 = new Cookie("password", null);
                            cookie1.setMaxAge(0);
                            cookie1.setPath(request.getContextPath());
                        }
                    }
                }
            }

            List<Authority> authorities = loginService.getPower(uaserName);
            map.put("authorities", authorities);
            request.getSession().setAttribute("authorities", authorities);
            Integer count = loginService.findBy(uaserName);
            map.put("count", count);
            request.getSession().setAttribute("count", count);
        }
        return map;

    }

}
