package dmdfp;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by khk on 2/20/14.
 */
@WebFilter({"/login.jsf", "/logout.jsf", "/modifyitem.jsf", "/adjustitem.jsf", "/createitem.jsf"})
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
            if (req.getServletPath().equals("/logout.jsf"))
            {
                req.getSession().invalidate();
                auth.setLoggedIn(false);
                resp.sendRedirect(req.getContextPath() + "/login.jsf");
            }
            // Forward user to itemlist if going to login page
            else if (req.getServletPath().equals("/login.jsf"))
            {
                resp.sendRedirect(req.getContextPath() + "/itemlist.jsf");
            }
            // Otherwise, continue request
            else
            {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
        else if (!req.getServletPath().equals("/login.jsf"))
        {
            // User is not logged in, redirect to login page
            resp.sendRedirect(req.getContextPath() + "/login.jsf");
        }
        else
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy()
    {
    }
}
