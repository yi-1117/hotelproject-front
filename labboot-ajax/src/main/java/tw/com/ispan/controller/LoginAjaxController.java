package tw.com.ispan.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import tw.com.ispan.domain.CustomerBean;
import tw.com.ispan.jwt.JsonWebTokenUtility;
import tw.com.ispan.service.CustomerService;

@RestController
@RequestMapping("/ajax/secure")
@CrossOrigin
public class LoginAjaxController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private JsonWebTokenUtility jsonWebTokenUtility;

    @PostMapping("/login")
    public String login(HttpSession session, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();
        JSONObject obj = new JSONObject(json);
        String username = obj.isNull("username") ? null : obj.getString("username");
        String password = obj.isNull("password") ? null : obj.getString("password");

        if (username == null || username.length() == 0 || password == null || password.length() == 0) {
            responseJson.put("message", "請輸入帳號密碼");
            responseJson.put("success", false);
            return responseJson.toString();
        }
        CustomerBean bean = customerService.login(username, password);

        if (bean == null) {
            responseJson.put("message", "登入失敗");
            responseJson.put("success", false);
        } else {
            session.setAttribute("user", bean);
            responseJson.put("message", "登入成功");
            responseJson.put("success", true);

            JSONObject user = new JSONObject()
                    .put("custid", bean.getCustid())
                    .put("email", bean.getEmail());
            String token = jsonWebTokenUtility.createToken(user.toString());
            responseJson.put("token", token);
            responseJson.put("user", bean.getEmail());

        }

        return responseJson.toString();
    }

}
