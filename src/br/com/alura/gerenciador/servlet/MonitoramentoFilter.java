package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class MonitoramentoFilter
 */
@WebFilter("/entrada")
public class MonitoramentoFilter extends HttpFilter implements Filter {
       
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		long inicio = System.currentTimeMillis();
		
		System.out.println("MonitoramentoFilter");
		
		String acao = request.getParameter("acao");
		
		chain.doFilter(request, response);
		
		long fim = System.currentTimeMillis();
		System.out.println("Tempo de monitoramento da ação " + acao + ": " + (fim - inicio));
	}

}
