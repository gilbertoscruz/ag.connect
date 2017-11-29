/**
 * 
 */
package agrisolus.com.br.agconnect.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author EGILCRU
 *
 */
public class RetornoWebWs {

	@Expose
	@SerializedName("code")
	private int code;

	@Expose
	@SerializedName("mensagem")
	private String mensagem;

	@Expose
	@SerializedName("retorno_web")
	private String retorno;

	/**
	 * 
	 */
	public RetornoWebWs() {
		super();
	}

	/**
	 * @param code
	 * @param mensagem
	 * @param retorno
	 */
	public RetornoWebWs(int code, String mensagem, String retorno) {
		super();
		this.code = code;
		this.mensagem = mensagem;
		this.retorno = retorno;
	}

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
	public String getRetorno() {
		return retorno;
	}

	/**
	 * @param retorno
	 *            the retorno to set
	 */
	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

}
