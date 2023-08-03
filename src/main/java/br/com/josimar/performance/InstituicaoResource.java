package br.com.josimar.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

@RequestScoped
@Path("/instituicao")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class InstituicaoResource {
    Logger LOG = Logger.getLogger(InstituicaoResource.class);

    @Inject
    InstituicaoDao instituicaoDao;

    @Path("/saveall")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response saveAll(List<Instituicao> instituicoes) {
        instituicaoDao.deleteAll();
        var listaTest = createInstituicoes();
        LOG.info("list size: " + listaTest.size());
        var initTime = System.currentTimeMillis();
        LOG.info("init time: " + initTime);
        instituicaoDao.saveAll(listaTest);
        var endTime = System.currentTimeMillis();
        LOG.info("end time: " + endTime);
        LOG.info("duration: " + (endTime - initTime));
        // return a list of instituicoes
        return Response.ok().entity(instituicaoDao.listInstituicoes()).build();
    }

    @Path("/saveall-with-native-query")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response saveAllWithNative(List<Instituicao> instituicoes) {
        instituicaoDao.deleteAll();
        var listaTest = createInstituicoes();
        LOG.info("list size: " + listaTest.size());
        var initTime = System.currentTimeMillis();
        LOG.info("init time: " + initTime);
        instituicaoDao.saveAllWithNativeQuery(listaTest);
        var endTime = System.currentTimeMillis();
        LOG.info("end time: " + endTime);
        LOG.info("duration: " + (endTime - initTime));
        // return a list of instituicoes
        return Response.ok().entity(instituicaoDao.listInstituicoes()).build();
    }

    @Path("saveall-one-by-one")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response saveAllOneByOne(List<Instituicao> instituicoes) {
        instituicaoDao.deleteAll();
        // save init time and end time and calculate duration
        var listaTest = createInstituicoes();
        LOG.info("list size: " + listaTest.size());
        var initTime = System.currentTimeMillis();
        LOG.info("init time: " + initTime);
        int i = 0;
        for (Instituicao instituicao : listaTest) {
            instituicaoDao.save(instituicao);
            // print the log each 1000 records
            if (i % 1000 == 0) {
                LOG.info("included " + i);
            }
            i++;
        }
        var endTime = System.currentTimeMillis();
        LOG.info("end time: " + endTime);
        LOG.info("duration: " + (endTime - initTime));
        // return a list of first 100 instituicoes
        return Response.ok().entity(instituicaoDao.listInstituicoes().subList(0, 100)).build();
    }

    @DELETE
    @Path("/deleteall")
    public Response deleteAll() {
        instituicaoDao.deleteAll();
        return Response.ok().build();
    }

    // private method that create a list of Instituicao for testing
    private List<Instituicao> createInstituicoes() {
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
