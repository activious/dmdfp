package dmdfp;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by khk on 2/20/14.
 */
@WebFilter("/admin/*")
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

        String path = req.getServletPath();

        if (auth != null && auth.isLoggedIn())
        {
            /**
             * User is logged in, login page redirects to front page.
             * If user is logging out, redirect to front page.
             * Otherwise continue request as is.
             */
            switch (path)
            {
                case "/admin/login.jsf":
                    redir(req, resp, "/admin/itemList.jsf");
                    return;
                case "/admin/logout.jsf":
                    req.getSession().invalidate();
                    auth.setLoggedIn(false);
                    redir(req, resp, "/admin/itemList.jsf");
                    return;
                default:
                    filterChain.doFilter(servletRequest, servletResponse);
            }
        }
        else if (!path.equals("/admin/login.jsf"))
        {
            /**
             * User is not logged in, redirect to login page.
             */
            redir(req, resp, "/admin/login.jsf");
            return;
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

    private void redir(HttpServletRequest req,
                       HttpServletResponse resp,
                       String path)
            throws IOException
    {
        resp.sendRedirect(req.getContextPath() + path);
    }
}
