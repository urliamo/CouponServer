package Coupons.api;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Coupons.JavaBeans.UserData;
import Coupons.Logic.ICacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/*")
public class LoginFilter implements Filter {

@Autowired
private ICacheManager cacheManager;

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
throws IOException, ServletException {

HttpServletRequest req = ((HttpServletRequest) request);
String path = req.getRequestURI();
if (path.startsWith("/users/login")) {

chain.doFilter(request, response); // Just continue chain.
return;

}

// Create
if (path.startsWith("/customers") && req.getMethod()
.equalsIgnoreCase("post"))
{

chain.doFilter(request, response); // Just continue chain.
return;

}

Integer token = Integer.parseInt(req.getParameter("token"));

UserData userData = (UserData) cacheManager.get(token);

if (userData != null) {

request.setAttribute("userData", userData);
chain.doFilter(request, response);
return;

}

HttpServletResponse res = (HttpServletResponse) response;
// 401 = Unauthorized http error code
res.setStatus(401);

}

}