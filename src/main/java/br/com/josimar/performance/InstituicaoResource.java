package br.com.josimar.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    private static final int QUANTIDADE_CONTAS = 10000000;

    Logger LOG = Logger.getLogger(InstituicaoResource.class);

    @Inject
    InstituicaoDao instituicaoDao;

    @Inject
    InstituicaoBean bean;

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
        var lista = createContas();

        var initTime = System.currentTimeMillis();
        LOG.info("init time before find: " + initTime);
        var conta = findContaById(codigoInstituicao, codigoTipoPlano, numeroConta, lista);
        var endTime = System.currentTimeMillis();
        LOG.info("end time after find: " + endTime);
        LOG.info("duration of find: " + (endTime - initTime));
        return conta;
    }
    @GET
    @Path("/contas-bs")
    @Produces(MediaType.APPLICATION_JSON)
    public Conta findContaWithBinarySearch(@QueryParam("codigoInstituicao") int codigoInstituicao,
            @QueryParam("codigoTipoPlano") int codigoTipoPlano, @QueryParam("numeroConta") int numeroConta) {
        var lista = createContas();

        var initTime = System.currentTimeMillis();
        LOG.info("init time before find: " + initTime);
        var conta = findContaByIdWithBinarySearch(codigoInstituicao, codigoTipoPlano, numeroConta, lista);
        var endTime = System.currentTimeMillis();
        LOG.info("end time after find: " + endTime);
        LOG.info("duration of find: " + (endTime - initTime));
        return conta;
    }

    private List<Conta> createContas() {
        List<Conta> contas = new ArrayList<>();
        // create a random number between 1 and 10000
        var random = new Random();
        int numeroAleatorio = random.nextInt(QUANTIDADE_CONTAS) + 1;
        for (int i = 1; i < QUANTIDADE_CONTAS + 1; i++) {
            var conta = new Conta();
            conta.setCodigoInstituicao(i);
            conta.setCodigoTipoPlano(i);
            if (i == numeroAleatorio) {
                conta.setNumeroConta(42);
                conta.setCodigoInstituicao(42+QUANTIDADE_CONTAS);
                conta.setCodigoTipoPlano(42+QUANTIDADE_CONTAS);
                conta.setNomeConta("Conta foi encontrada");
            } else {
                var numeroConta = random.nextInt(QUANTIDADE_CONTAS) + 1;
                if (numeroConta == 42) {
                    conta.setNumeroConta(numeroConta+1);
                    conta.setNomeConta("nome" + conta.getNumeroConta());
                } else {
                    conta.setNumeroConta(numeroConta);
                    conta.setNomeConta("nome" + conta.getNumeroConta());
                }
            }
            contas.add(conta);
            if (i == numeroAleatorio) {
                LOG.info("Conta a ser encontrada no index " + i + ": " + conta);
            }

            if (i == 0) {
                LOG.info("First conta created: " + conta);
            }
        }

        LOG.info("First conta before sort: " + contas.get(0));

        // log the initial timestamp
        var initTime = System.currentTimeMillis();
        LOG.info("init time: " + initTime);
        // order by codigoInstituicao, codigoTipoPlano, numeroConta
        contas.sort(Comparator.comparing(Conta::getCodigoInstituicao)
                .thenComparing(Conta::getCodigoTipoPlano)
                .thenComparing(Conta::getNumeroConta));
        // log the end timestamp
        var endTime = System.currentTimeMillis();
        LOG.info("end time: " + endTime);
        // log the duration
        LOG.info("duration: " + (endTime - initTime));
        LOG.info("First conta after sort: " + contas.get(0));
        LOG.info("Last conta after sort: " + contas.get(contas.size() - 1));


        return contas;
    }

    private Conta findContaById(int codigoInstituicao, int codigoTipoPlano, int numeroConta, List<Conta> contas) {
        Conta contaBuscada = new Conta(codigoInstituicao, codigoTipoPlano, numeroConta, "");
        LOG.info("Conta busca " + contaBuscada);

        var indice=0;
        for (Conta conta : contas) {
            if (conta.getCodigoInstituicao() == contaBuscada.getCodigoInstituicao()
                    && conta.getCodigoTipoPlano() == contaBuscada.getCodigoTipoPlano()
                    && conta.getNumeroConta() == contaBuscada.getNumeroConta()) {
                LOG.info("Conta foi encontrada no index: " + indice);
                return conta;
            }

            indice++;
        }
        return null;
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
