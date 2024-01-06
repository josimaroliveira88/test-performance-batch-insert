package br.com.josimar.performance;

public class Conta {
    private int codigoInstituicao;
    private int codigoTipoPlano;
    private int numeroConta;
    private String nomeConta;

    public Conta() {

    }

    @Override
    public String toString() {
        return "Conta [codigoInstituicao=" + codigoInstituicao + ", codigoTipoPlano=" + codigoTipoPlano
                + ", numeroConta=" + numeroConta + ", nomeConta=" + nomeConta + "]";
    }

    public Conta(int codigoInstituicao, int codigoTipoPlano, int numeroConta, String nomeConta) {
        this.codigoInstituicao = codigoInstituicao;
        this.codigoTipoPlano = codigoTipoPlano;
        this.numeroConta = numeroConta;
        this.nomeConta = nomeConta;
    }
    public int getCodigoInstituicao() {
        return this.codigoInstituicao;
    }

    public void setCodigoInstituicao(int codigoInstituicao) {
        this.codigoInstituicao = codigoInstituicao;
    }

    public int getCodigoTipoPlano() {
        return this.codigoTipoPlano;
    }

    public void setCodigoTipoPlano(int codigoTipoPlano) {
        this.codigoTipoPlano = codigoTipoPlano;
    }

    public int getNumeroConta() {
        return this.numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNomeConta() {
        return this.nomeConta;
    }

    public void setNomeConta(String nomeConta) {
        this.nomeConta = nomeConta;
    }
}