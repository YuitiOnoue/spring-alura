package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.alura.gerenciador.acao.Acao;
import br.com.alura.gerenciador.acao.AlteraEmpresa;
import br.com.alura.gerenciador.acao.ListaEmpresas;
import br.com.alura.gerenciador.acao.MostraEmpresa;
import br.com.alura.gerenciador.acao.NovaEmpresa;
import br.com.alura.gerenciador.acao.NovaEmpresaForm;
import br.com.alura.gerenciador.acao.RemoveEmpresa;
import br.com.alura.gerenciador.modelo.ActionResponse;

/**
 * Servlet implementation class UnicaEntradaServlet
 */
@WebServlet("/entrada")
public class UnicaEntradaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String CLASS_PATH = "br.com.alura.gerenciador.acao.";
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramAcao = request.getParameter("acao");
		
		System.out.println("Ação " + paramAcao);
		
		HttpSession sessao = request.getSession();
		boolean usuarioNaoEstaLogado = sessao.getAttribute("usuarioLogado") == null;
		boolean ehUmaAcaoProtegida = !(paramAcao.equals("Login") || paramAcao.equals("LoginForm"));
		
		if (ehUmaAcaoProtegida && usuarioNaoEstaLogado) {
			response.sendRedirect("entrada?acao=LoginForm");
			return;
		}
		
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
