package storage;

import com.google.inject.ImplementedBy;
import models.Client;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;


@ImplementedBy(JPAClientRepository.class)
public interface ClientRepository {
    CompletionStage<Client> add(Client client);

    CompletionStage<Stream<Client>> listAll();
}
