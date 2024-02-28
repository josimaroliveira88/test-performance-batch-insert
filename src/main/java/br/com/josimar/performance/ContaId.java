package br.com.josimar.performance;

public class ContaId {
    private int codigoInstituicao;
    private int codigoTipoPlano;
    private int numeroConta;

    public ContaId() {

    }

    public ContaId(int codigoInstituicao, int codigoTipoPlano, int numeroConta) {
        this.codigoInstituicao = codigoInstituicao;
        this.codigoTipoPlano = codigoTipoPlano;
        this.numeroConta = numeroConta;
    }

    public int getCodigoInstituicao() {
        return this.codigoInstituicao;
    }

    public void setCodigoInstituicao(int codigoInstituicao) {
        this.codigoInstituicao = codigoInstituicao;
    }

    public int getCodigoTipoPlano() {
        return codigoTipoPlano;
    }

    public void setCodigoTipoPlano(int codigoTipoPlano) {
        this.codigoTipoPlano = codigoTipoPlano;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigoInstituicao;
        result = prime * result + codigoTipoPlano;
        result = prime * result + numeroConta;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContaId other = (ContaId) obj;
        if (codigoInstituicao != other.codigoInstituicao)
            return false;
        if (codigoTipoPlano != other.codigoTipoPlano)
            return false;
        if (numeroConta != other.numeroConta)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ContaId [codigoInstituicao=" + codigoInstituicao + ", codigoTipoPlano=" + codigoTipoPlano
                + ", numeroConta=" + numeroConta + "]";
    }
}
