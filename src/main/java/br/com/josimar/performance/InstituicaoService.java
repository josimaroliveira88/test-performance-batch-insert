package br.com.josimar.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@RequestScoped
public class InstituicaoService {

    Logger LOG = Logger.getLogger(InstituicaoService.class);
    @Inject
    InstituicaoDao instituicaoDao;

    public  void saveInst() {
        instituicaoDao.deleteAll();
        var listaTest = createInstituicoes();
        LOG.info("list size: " + listaTest.size());
        var initTime = System.currentTimeMillis();
        LOG.info("init time: " + initTime);
        instituicaoDao.saveAll(listaTest);
        var endTime = System.currentTimeMillis();
        LOG.info("end time: " + endTime);
        LOG.info("duration: " + (endTime - initTime));
        listaTest = null;
    }

    public List<Instituicao> createInstituicoes() {
        List<Instituicao> instituicoes = new ArrayList<>();
        for (int i = 1; i < 10001; i++) {
            var instituicao = new Instituicao();
            instituicao.setId(i);
            instituicao.setNome("nome" + i);
            // if i is even, set indicadorInstituicaoFinanceira to "S", otherwise to "N"
            // codigoInstituicaoOrganizacional is a random number between 0 and 999
            // generate a random number between 0 and 999
            Random random = new Random();
            short numeroAleatorio = (short) random.nextInt(1000);
            instituicao.setIndicadorInstituicaoFinanceira(numeroAleatorio % 2 == 0 ? "S" : "N");
            instituicao.setCodigoInstituicaoOrganizacional(numeroAleatorio);
            instituicoes.add(instituicao);
        }
        return instituicoes;
    }
}
