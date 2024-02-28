package br.com.josimar.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import io.vertx.core.json.Json;

@RequestScoped
@Path("/instituicao")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class InstituicaoResource {
    Logger LOG = Logger.getLogger(InstituicaoResource.class);

    @Inject
    InstituicaoDao instituicaoDao;

    @Inject
    InstituicaoBean bean;

    @Inject
    ContaUtil contaUtil;

    @Inject
    InstituicaoService instituicaoService;

    @Path("/saveall")
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response saveAll(List<Instituicao> instituicoes) {
        instituicaoService.saveInst();
        // return a list of instituicoes
        return Response.ok().entity(instituicaoDao.listInstituicoes()).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String counter() {
        return "count: " + bean.getCounter();
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

    @GET
    @Path("/contas")
    @Produces(MediaType.APPLICATION_JSON)
    public Conta findConta(@QueryParam("codigoInstituicao") int codigoInstituicao,
            @QueryParam("codigoTipoPlano") int codigoTipoPlano, @QueryParam("numeroConta") int numeroConta) {
        var lista = contaUtil.getContas();

        var initTime = System.currentTimeMillis();
        LOG.info("init time before find: " + initTime);
        var conta = findContaById(codigoInstituicao, codigoTipoPlano, numeroConta, lista);
        var endTime = System.currentTimeMillis();
        LOG.info("end time after find: " + endTime);
        LOG.info("duration of find: " + (endTime - initTime));

        // clear the object to avoid memory leaks and decrease memory usage
        lista = null;

        return conta;
    }

    private Conta findContaById(int codigoInstituicao, int codigoTipoPlano, int numeroConta, Map<ContaId, Conta> lista) {
        ContaId contaBuscada = new ContaId(codigoInstituicao, codigoTipoPlano, numeroConta);
        LOG.info("Conta busca " + contaBuscada);

        // var indice=0;

        if (lista.containsKey(contaBuscada)) {
            LOG.info("Conta foi encontrada pela chave: " + contaBuscada);
            return lista.get(contaBuscada);
        } else {
            LOG.info("Conta nao foi encontrada");
            return null;
        }
    }

    private Conta findContaByIdWithBinarySearch(int codigoInstituicao, int codigoTipoPlano, int numeroConta, List<Conta> contas) {
        Conta contaBuscada = new Conta(codigoInstituicao, codigoTipoPlano, numeroConta, "");
        LOG.info("Conta busca " + contaBuscada);
        int index = Collections.binarySearch(contas, contaBuscada, (c1, c2) -> {
            if (c1.getCodigoInstituicao() != c2.getCodigoInstituicao()) {
                return c1.getCodigoInstituicao() - c2.getCodigoInstituicao();
            } else if (c1.getCodigoTipoPlano() != c2.getCodigoTipoPlano()) {
                return c1.getCodigoTipoPlano() - c2.getCodigoTipoPlano();
            } else {
                return c1.getNumeroConta() - c2.getNumeroConta();
            }
        });

        if (index >= 0) {
            LOG.info("Conta encontrada na posição " + index);
            return contas.get(index);
        } else {
            LOG.info("Conta não encontrada");
            return null;
        }
    }
}
