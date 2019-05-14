package storage;

import models.Client;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPAClientRepository implements ClientRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPAClientRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }


    @Override
    public CompletionStage<Client> add(Client client) {
        return supplyAsync(()->wrap(em->insert(em,client)),executionContext);
    }

    @Override
    public CompletionStage<Stream<Client>> listAll() {
        return supplyAsync(()->wrap(em->listAll(em)),executionContext);
    }


    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }
    private Client insert(EntityManager em, Client client) {
        em.persist(client);
        return client;
    }

    private Stream<Client> listAll (EntityManager em){
        return em.createQuery("select c from Client",Client.class)
                .getResultList().stream();

    }
}
