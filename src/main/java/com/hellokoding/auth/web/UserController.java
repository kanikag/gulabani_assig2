package com.hellokoding.auth.web;

import com.hellokoding.auth.service.SecurityService;
import com.hellokoding.auth.service.UserService;
import com.hellokoding.auth.social.FacebookConnectionService;
import com.hellokoding.auth.validator.UserValidator;
import com.hellokoding.auth.social.FBGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    FacebookConnectionService fbConnection;

    @Autowired
    FBGraph fbGraph;


    @GetMapping("/login")
    public String login(Model model, String logout, HttpServletRequest req) {
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
            return "login";
        }

        return "login";
    }

    @GetMapping("/facebooklogin")
    public ModelAndView facebookLogin(HttpServletRequest req, ModelMap model) {
        String code = req.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR: Didn't get code parameter in callback.");
        }
        String accessToken = fbConnection.getAccessToken(code);
        if (accessToken != null) {
            securityService.autoLogin("doc");
            return new ModelAndView("forward:/doctorHome", model);
        }
        model.addAttribute("error", "Could not login with facebook");
        return new ModelAndView("forward:/login", model);
    }

    @GetMapping("/spotifylogin")
    public ModelAndView spotifyLogin(@RequestParam("code") String code, HttpServletRequest req, ModelMap model) {
        securityService.autoLogin("patient1");
        return new ModelAndView("forward:/patientHome", model);
    }

    @GetMapping("/patientHome")
    public String patientHome(Model model) {
        return "patientHome";
    }

    @GetMapping("/doctorHome")
    public String doctorHome(ModelMap model) {
        return "doctorHome";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model, HttpServletRequest req, HttpServletResponse response) {
        String code = req.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR: Didn't get code parameter in callback.");
        }

        String accessToken = fbConnection.getAccessToken(code);

        String graph = fbGraph.getFBGraph(accessToken);
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
        System.out.println(fbProfileData);
        securityService.autoLogin("doc");
        return "welcome";
    }
}
