package br.com.josimar.performance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "instituicao")
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "instituicao.insert",
        query = "INSERT INTO instituicao (id, nome, indicador_instituicao_financeira, codigo_instituicao_organizacional) " +
            "VALUES ( :id, :nome, :indicador_instituicao_financeira, :codigo_instituicao_organizacional)")
})
public class Instituicao {
    @Id
    private Integer id;
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
    @Column(name = "indicador_instituicao_financeira", length = 1, nullable = false)
    private String indicadorInstituicaoFinanceira;
    @Column(name = "codigo_instituicao_organizacional", nullable = false)
    private Short codigoInstituicaoOrganizacional;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getIndicadorInstituicaoFinanceira() {
        return indicadorInstituicaoFinanceira;
    }
    public void setIndicadorInstituicaoFinanceira(String indicadorInstituicaoFinanceira) {
        this.indicadorInstituicaoFinanceira = indicadorInstituicaoFinanceira;
    }
    public Short getCodigoInstituicaoOrganizacional() {
        return codigoInstituicaoOrganizacional;
    }
    public void setCodigoInstituicaoOrganizacional(Short codigoInstituicaoOrganizacional) {
        this.codigoInstituicaoOrganizacional = codigoInstituicaoOrganizacional;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result
                + ((indicadorInstituicaoFinanceira == null) ? 0 : indicadorInstituicaoFinanceira.hashCode());
        result = prime * result
                + ((codigoInstituicaoOrganizacional == null) ? 0 : codigoInstituicaoOrganizacional.hashCode());
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
        Instituicao other = (Instituicao) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (indicadorInstituicaoFinanceira == null) {
            if (other.indicadorInstituicaoFinanceira != null)
                return false;
        } else if (!indicadorInstituicaoFinanceira.equals(other.indicadorInstituicaoFinanceira))
            return false;
        if (codigoInstituicaoOrganizacional == null) {
            if (other.codigoInstituicaoOrganizacional != null)
                return false;
        } else if (!codigoInstituicaoOrganizacional.equals(other.codigoInstituicaoOrganizacional))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Instituicao [id=" + id + ", nome=" + nome + ", indicadorInstituicaoFinanceira="
                + indicadorInstituicaoFinanceira + ", codigoInstituicaoOrganizacional="
                + codigoInstituicaoOrganizacional + "]";
    }
}
