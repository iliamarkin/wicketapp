package ru.markin.frontend;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.markin.backend.dto.User;
import ru.markin.backend.service.UserService;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class HomePage extends WebPage {

    private final List<User> users;
    private final User newUser;
    private final DateFormat format;

    @SpringBean
    private UserService userService;

    private static final long serialVersionUID = 1L;

    public HomePage() {
        super();
        Injector.get().inject(this);
        this.users = this.userService.getUsers();
        this.newUser = new User();
        this.format = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final WebMarkupContainer container = initializeUserContainer();
        container.setOutputMarkupId(true);
        add(container);
        final RefreshingView<User> refreshingView = initializeRefreshingView();
        container.add(refreshingView);
        final Form<User> newUserForm = initializeForm(container);
        container.add(newUserForm);
    }

    @Nonnull
    private Form<User> initializeForm(final WebMarkupContainer container) {
        final Form<User> newUserForm = new Form<>("userForm", Model.of(newUser));
        final TextField<String> userName = initializeUserNameInput();
        final TextField<String> userSurname = initializeSurnameInput();
        final TextField<String> userBirthdayDate = initializeBirthdayDateInput();
        final AjaxSubmitLink buttonAdd = initializeButtonAdd(container, newUserForm);
        newUserForm.add(userName);
        newUserForm.add(userSurname);
        newUserForm.add(userBirthdayDate);
        newUserForm.add(buttonAdd);
        return newUserForm;
    }

    @Nonnull
    private AjaxSubmitLink initializeButtonAdd(final WebMarkupContainer container, final Form<User> form) {
        return new AjaxSubmitLink("addButton", form) {

            private static final long serialVersionUID = 8568570034047848894L;

            @Override
            public void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
                final User user = new User();
                user.setName(newUser.getName());
                user.setSurname(newUser.getSurname());
                user.setBirthdayDate(newUser.getBirthdayDate());
                users.add(userService.addUser(user));
                clearUser();
                target.add(container);
            }

            @SuppressWarnings("ConstantConditions")
            private void clearUser() {
                newUser.setName(null);
                newUser.setSurname(null);
                newUser.setBirthdayDate(null);
            }
        };
    }

    @Nonnull
    private TextField<String> initializeBirthdayDateInput() {
        return new TextField<>("userBirthdayDateInput", new Model<String>() {
            private static final long serialVersionUID = 1243456146611413161L;

            @Override
            public String getObject() {
                return Optional.ofNullable(newUser.getBirthdayDate())
                        .map(format::format)
                        .orElse("");
            }

            @Override
            public void setObject(final String object) {
                try {
                    if (object != null) {
                        newUser.setBirthdayDate(format.parse(object));
                    }
                } catch (ParseException ignored) {
                }
            }
        });
    }

    @Nonnull
    private TextField<String> initializeSurnameInput() {
        return new TextField<>("userSurnameInput",
                new PropertyModel<>(Model.of(newUser), "surname"));
    }

    @Nonnull
    private TextField<String> initializeUserNameInput() {
        return new TextField<>("userNameInput",
                new PropertyModel<>(Model.of(newUser), "name"));
    }

    @Nonnull
    private RefreshingView<User> initializeRefreshingView() {
        return new RefreshingView<User>("users",
                new ListModel<>(this.users)) {

            private static final long serialVersionUID = -701590160841372468L;

            @Override
            protected Iterator<IModel<User>> getItemModels() {
                return new ModelIteratorAdapter<User>(users.iterator()) {
                    @Override
                    protected IModel<User> model(final User user) {
                        return Model.of(user);
                    }
                };
            }

            @Override
            protected void populateItem(final Item<User> item) {
                item.add(new Label("userId", new PropertyModel<>(item.getModel(), "id")));
                item.add(new Label("userName", new PropertyModel<>(item.getModel(), "name")));
                item.add(new Label("userSurname", new PropertyModel<>(item.getModel(), "surname")));
                item.add(new Label("userBirthdayDate", new PropertyModel<>(item.getModel(), "birthdayDate")));
            }
        };
    }

    @Nonnull
    private WebMarkupContainer initializeUserContainer() {
        final WebMarkupContainer container = new WebMarkupContainer("container");
        add(container);
        return container;
    }
}
