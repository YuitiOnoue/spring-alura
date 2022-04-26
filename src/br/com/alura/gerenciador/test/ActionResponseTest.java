package br.com.alura.gerenciador.test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.alura.gerenciador.modelo.ActionResponse;

public class ActionResponseTest {
	
	@Test
	public void ConstrutorVazio() {
		ActionResponse actionResponse = new ActionResponse();
		
		assertFalse(actionResponse.isValid());
		assertNull(actionResponse.getType());
		assertNull(actionResponse.getDestination());
	}
	
	@Test
	public void ConstrutorValorNulo() {
		ActionResponse actionResponse = new ActionResponse(null);
		
		assertFalse(actionResponse.isValid());
		assertNull(actionResponse.getType());
		assertNull(actionResponse.getDestination());	
	}
	
	@Test
	public void ConstrutorValorVazio() {
		ActionResponse actionResponse = new ActionResponse("");
		
		assertFalse(actionResponse.isValid());
		assertNull(actionResponse.getType());
		assertNull(actionResponse.getDestination());	
	}
	
	@Test
	public void ConstrutorValorSemDoisPontos() {
		ActionResponse actionResponse = new ActionResponse("rasengan");
		
		assertFalse(actionResponse.isValid());
		assertNull(actionResponse.getType());
		assertNull(actionResponse.getDestination());
	}
	
	@Test
	public void ConstrutorValorRedirectCerto() {
		ActionResponse actionResponse = new ActionResponse("redirect:konoha");
		
		assertTrue(actionResponse.isValid());
		assertEquals(actionResponse.getType(), "redirect");
		assertEquals(actionResponse.getDestination(), "konoha");
	}
	
	@Test
	public void ConstrutorValorMultiplosDoisPontos() {
		ActionResponse actionResponse = new ActionResponse("foward:soul:society");
				
		assertTrue(actionResponse.isValid());
		assertEquals(actionResponse.getType(), "foward");
		assertEquals(actionResponse.getDestination(), "soul");
	}	

}
