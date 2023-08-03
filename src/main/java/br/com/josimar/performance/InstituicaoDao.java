package br.com.josimar.performance;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@RequestScoped
public class InstituicaoDao {
    Logger LOG = Logger.getLogger(InstituicaoDao.class);

    @Inject
    EntityManager em;

    @ConfigProperty(name = "BATCH_SIZE")
    int BATCH_SIZE;

    @Transactional
    public void save(Instituicao instituicao) {
        em.persist(instituicao);
    }

    //persist a list of Instituicao
    @Transactional
    public void saveAll(List<Instituicao> instituicoes) {
        var indice = 0;
        LOG.info("batch size: " + BATCH_SIZE);
        for (Instituicao instituicao : instituicoes) {
            em.persist(instituicao);
            indice++;
            if ((indice % BATCH_SIZE) == 0) {
                em.flush();
                em.clear();
            }
        }
        em.flush();
    }

    @Transactional
    public void saveAllWithNativeQuery(List<Instituicao> instituicoes) {
        var indice = 0;
        LOG.info("batch size: " + BATCH_SIZE);
        for (Instituicao instituicao : instituicoes) {
            em.createNamedQuery("instituicao.insert")
                .setParameter("id", instituicao.getId())
                .setParameter("nome", instituicao.getNome())
                .setParameter("indicador_instituicao_financeira", instituicao.getIndicadorInstituicaoFinanceira())
                .setParameter("codigo_instituicao_organizacional", instituicao.getCodigoInstituicaoOrganizacional())
                .executeUpdate();
            indice++;
            if ((indice % BATCH_SIZE) == 0) {
                em.flush();
                em.clear();
            }
        }
        em.flush();
    }

    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM Instituicao").executeUpdate();
    }

    // List all instituicoes
    // list first 100 instituicoes and return
    // must have an option to limit the number of instituicoes and pagination on each call
    public List<Instituicao> listInstituicoes() {
        int limit = 100;
        int offset = 0;
        LOG.info("limit: " + limit);
        LOG.info("offset: " + offset);

        var query = em.createQuery("SELECT i FROM Instituicao i", Instituicao.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }
}
