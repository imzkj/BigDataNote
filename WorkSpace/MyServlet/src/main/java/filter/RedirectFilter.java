package filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ZKJ on 2017/9/11.
 */
public class RedirectFilter implements Filter {

    private static boolean isStart = false;

    public void init(FilterConfig filterConfig) throws ServletException {
        if (isStart == false) {
//            FalconReportService falconReportService = new FalconReportService();
//            falconReportService.configByXmlAndStart();
            System.out.println("开启");
            isStart = true;
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURL().toString();
        if (url.endsWith(".do")) {
            url = url.substring(0, url.length() - 3);
        }
        if (url.endsWith(".action")) {
            url = url.substring(0, url.length() - 7);
        }
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        Pattern urlPattern = Pattern.compile("http://.*?/(.*)");
        Matcher urlMatcher = urlPattern.matcher(url);
        String requestURL;
        String metricName = "";
        if (urlMatcher.find()) {
            requestURL = urlMatcher.group(1).replace("/", ".").trim();
            metricName = "url." + requestURL;
        }
        System.out.println(metricName);
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("过滤");
    }

    public void destroy() {
        System.out.println("结束");
    }
}