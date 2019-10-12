package com.lnu.auth.web;

import com.lnu.auth.model.Channel;
import com.lnu.auth.model.Item;
import com.lnu.auth.model.youtube.Videos;
import com.lnu.auth.service.SecurityService;
import com.lnu.auth.service.SocialMediaConnectionService;
import com.lnu.auth.service.UserService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    private static String YOUTUBE_PARKINSONS_VIDEO = "https://www.googleapis.com/youtube/v3/search?part=id%2Csnippet&maxResults=25&order=date&q=parkinson%20diseasee%20exercises&prettyPrint=true&key=AIzaSyCFw-l8-axsDFYTzAq5tIc2RixHW29vq18";

    private static final String FEEDS_API = "https://www.news-medical.net/tag/feed/Parkinsons-Disease.aspx";

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    SocialMediaConnectionService socialMediaConnectionService;

    @Autowired
    AuthenticationSuccessHandler successHandler;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationFailureHandler failureHandler;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/login")
    public String login(Model model, String logout, HttpServletRequest req) {
        model.addAttribute("facebookUrl", socialMediaConnectionService.getFBAuthUrl());
        model.addAttribute("spotifyUrl", socialMediaConnectionService.getSpotifyAuthUrl());
        model.addAttribute("githubUrl", socialMediaConnectionService.getGithubUrl());
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
            securityService.logout();
            return "login";
        }
        return "login";
    }

    @GetMapping("/facebooklogin")
    @ResponseStatus(value = HttpStatus.OK)
    public void facebookLogin(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        String code = req.getParameter("code");
        if (code == null || code.equals("")) {
            throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
        }
        String accessToken = socialMediaConnectionService.getAccessToken(code);
        if (accessToken != null) {
            autoLogin(req, response, "doc");
        }
    }

    @GetMapping("/githublogin")
    @ResponseStatus(value = HttpStatus.OK)
    public void githubLogin(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        String code = req.getParameter("code");
        if (code != null || !code.equals("")) {
            autoLogin(req, response, "researcher");
        }
    }

    private void autoLogin(HttpServletRequest req, HttpServletResponse response, String username) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken token = securityService.getAuthentication(username);
        token.setDetails(new WebAuthenticationDetails(req));//if request is needed during authentication
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(token);
        } catch (AuthenticationException e) {
            //if failureHandler exists
            try {
                failureHandler.onAuthenticationFailure(req, response, e);
            } catch (IOException | ServletException se) {
                //ignore
            }
            throw e;
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        successHandler.onAuthenticationSuccess(req, response, auth);//if successHandler exists
        //if user has a http session you need to save context in session for subsequent requests
        HttpSession session = req.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    @GetMapping("/spotifylogin")
    @ResponseStatus(value = HttpStatus.OK)
    public void spotifyLogin(@RequestParam("code") String code, HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        if (code != null) {
            autoLogin(req, response, "patient1");
        }
    }

    @GetMapping("/patientHome")
    public String patientHome(ModelMap model) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<Videos> response = restTemplate.exchange(URLDecoder.decode(YOUTUBE_PARKINSONS_VIDEO), HttpMethod.GET, entity, new ParameterizedTypeReference<Videos>() {
        });
        Videos videos = response.getBody();
        model.addAttribute("videos", videos.getItems());
        return "patientHome";
    }

    @GetMapping("/researcherHome")
    public String researcherHome(ModelMap model) {
        Channel channels = new Channel();
        try {
            try (XmlReader reader = new XmlReader(new URL(FEEDS_API))) {
                SyndFeed feed = new SyndFeedInput().build(reader);
                List<Item> items = new ArrayList<>();
                for (SyndEntry entry : feed.getEntries()) {
                    items.add(new Item(entry.getTitle(), entry.getDescription().getValue(), entry.getLink(), entry.getComments(), entry.getUri(), entry.getPublishedDate().toString()));
                }
                channels = new Channel(feed.getTitle(), feed.getDescription(), feed.getLink(), feed.getLanguage(), feed.getAuthor(), items);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("patientsData", userService.getPatientData(authUser.getUsername()));
        model.addAttribute("channels", channels);
        return "researcherHome";
    }


    @GetMapping("/doctorHome")
    public String doctorHome(ModelMap model, HttpServletRequest req) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userService.getPatientData(authUser.getUsername()));

        model.addAttribute("username", authUser.getUsername());
        model.addAttribute("patientsData", userService.getPatientData(authUser.getUsername()));
        return "doctorHome";
    }
}
