/**
 * 
 */
package agrisolus.com.br.agconnect.bean;


/**
 * @author EGILCRU
 *
 */
public class JSonUsuarioRetorno{

	private int code;
	private String mensagem;
	private JSonUsuario retorno;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * @param mensagem
	 *            the mensagem to set
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * @return the retorno
	 */
	public JSonUsuario getRetorno() {
		return retorno;
	}

	/**
	 * @param retorno
	 *            the retorno to set
	 */
	public void setRetorno(JSonUsuario retorno) {
		this.retorno = retorno;
	}

}
