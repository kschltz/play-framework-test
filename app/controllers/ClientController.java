package controllers;

import models.Client;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import storage.ClientRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

public class ClientController extends Controller {
    private final FormFactory formFactory;
    private final ClientRepository personRepository;
    private final HttpExecutionContext ec;

    @Inject
    public ClientController(FormFactory formFactory,
                            ClientRepository personRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.personRepository = personRepository;
        this.ec = ec;
    }

    public Result page(final Http.Request request){
        return ok(views.html.client.render(request));
    }

    public CompletionStage<Result> addClient(final Http.Request request){
        Client client = formFactory.form(Client.class).bindFromRequest(request).get();
        return personRepository
                .add(client)
                .thenApplyAsync(cl->redirect(routes.ClientController.page()),ec.current());
    }

    public CompletionStage<Result> getPersons() {
        return personRepository
                .listAll()
                .thenApplyAsync(personStream ->
                        ok(toJson(personStream.collect(Collectors.toList()))), ec.current());
    }

}
