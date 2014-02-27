package dmdfp;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by khk on 2/20/14.
 */
@WebFilter({"/login.jsf",
            "/logout.jsf",
            "/modifyItem.jsf",
            "/adjustItem.jsf",
            "/createItem.jsf"})
public class AuthorizationFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Authorization auth = (Authorization) req.getSession().getAttribute("auth");

        if (auth != null && auth.isLoggedIn())
        {
            /**
             * User is logged in, login page redirects to front page.
             * If user is logging out, redirect to front page.
             * Otherwise continue request as is.
             */
            switch (req.getServletPath())
            {
                case "/login.jsf":
                    resp.sendRedirect(req.getContextPath() + "/");
                    break;
                case "/logout.jsf":
                    req.getSession().invalidate();
                    auth.setLoggedIn(false);
                    resp.sendRedirect(req.getContextPath() + "/");
                    break;
                default:
                    filterChain.doFilter(servletRequest, servletResponse);
            }
        }
        else if (!req.getServletPath().equals("/login.jsf"))
        {
            /**
             * User is not logged in, redirect to login page.
             */
            resp.sendRedirect(req.getContextPath() + "/login.jsf");
        }
        else
        {
            /**
             * Going to login page, all good.
             */
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy()
    {
    }
}
