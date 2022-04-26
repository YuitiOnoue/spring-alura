package br.com.alura.gerenciador.modelo;

public class ActionResponse {
	
	private String type;
	private String destination;
	private Boolean valid = false;
	
	public ActionResponse() {
		
	}
	
	public ActionResponse(String response) {
		
		if (response == null) {
			return;
		}
		
		if (!response.contains(":")) {
			return;
		}
		
		this.type = response.split(":")[0];
		this.destination = response.split(":")[1];
		this.valid = true;
	}
	
	public String getType() {
		return type;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public Boolean isValid() {
		return valid;
	}

}
