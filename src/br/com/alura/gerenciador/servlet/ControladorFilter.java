package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.acao.Acao;
import br.com.alura.gerenciador.modelo.ActionResponse;

/**
 * Servlet Filter implementation class ControladorFilter
 */
@WebFilter("/entrada")
public class ControladorFilter extends HttpFilter implements Filter {
	
	private static final String CLASS_PATH = "br.com.alura.gerenciador.acao.";
       
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String paramAcao = request.getParameter("acao");
		
		String actionResponse;
		try {
			Class<?> classe = Class.forName(CLASS_PATH + paramAcao);
			Acao acao = (Acao) classe.newInstance();
			actionResponse = acao.executa(request, response);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ServletException
				| IOException e) {
			throw new ServletException(e);
		}
		
		ActionResponse retorno = new ActionResponse(actionResponse);
		
		if (retorno.isValid()) {

			switch (retorno.getType()) {
				case "redirect": {
					response.sendRedirect(retorno.getDestination());
					break;
				}
				case "forward": {
					RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/" +  retorno.getDestination());
					rd.forward(request, response);
					break;
				}
				default:
					System.out.println("ActionResponse get type " + retorno.getType());
			}
		}
		
	}

}
