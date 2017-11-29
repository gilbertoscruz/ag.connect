package agrisolus.com.br.agconnect.consts;

/**
 * Created by Gilberto on 21/10/2016.
 */

public class Constantes {

    public static final String TAG = "AG.Connect";
    public static final String CONST_DIRETORIO_ARQUIVO = "AgriSolusConnect";

    private static final String BASE_URL = "https://www.agrisolus.com.br/s1aa/";
    //private static final String BASE_URL = "https://192.168.0.26/Nc.web/";


    public static final String CONST_URL_AUTENTICACAO = BASE_URL + "AgricultecApi/Login";
    public static final String CONST_URL_AVICULTURA_AVIARIOS = BASE_URL + "AgricultecApi/GetAviariosComPermissao";
    public static final String CONST_URL_RELATORIO_ERROS = "http://www.agricultec.com.br/aglogerro/api/LogErroController";
    public static final String CONST_MEDIDOR_AGUA_SALVAR = BASE_URL + "/AgricultecApi/SalvarControleAgua";
    public static final String CONST_MEDIDOR_AGUA_LISTA_SALVAR = BASE_URL + "/AgricultecApi/SalvarListaControleAgua";
    public static final String CONST_MEDIDOR_AGUA_LISTA_MEDIDORES = BASE_URL + "/AgricultecApi/GetListaMedidoresControleAgua";

    public static final String KEY_CONTA_USUARIO = "br.com.agrisolus.conta.usuario.bean";
    public static final String KEY_CONTA_USUARIO_NOME = "br.com.agrisolus.conta.nome.usuario";
    public static final String KEY_CONTA_USUARIO_SENHA = "br.com.agrisolus.conta.senha.usuario";
    public static final String KEY_CONTA_ERRO = "br.com.agrisolus.conta.usuario.erro";

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1101;

    public static final int SENSOR_AGUA_PULSOS_MEIA_POLEGADA = 545;
    public static final int SENSOR_AGUA_PULSOS_UMA_POLEGADA = 450;
    //public static final int SENSOR_AGUA_PULSOS_UMA_MEIA_POLEGADA = 1635;
}
