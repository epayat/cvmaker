package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.jpa.model.CandidateEntity;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * Created by Erdal on 08.02.2016.
 */
public class CandidateEditorPage extends BaseWebPage {
    public CandidateEditorPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize(){
        super.onInitialize();
        Form<CandidateEntity> inputForm = new Form<>("inputForm", new CompoundPropertyModel<>(new IModel<CandidateEntity>() {
            @Override
            public CandidateEntity getObject() {
                return getSessionData().getCandidateEntity();
            }

            @Override
            public void setObject(CandidateEntity object) {
                getSessionData().setCandidateEntity(object);
            }

            @Override
            public void detach() {

            }
        }));
        this.add(inputForm);

        inputForm.add(new TextField<String>("careerDesc"));
        inputForm.add(new TextField<String>("firstName"));
        inputForm.add(new TextField<String>("lastName"));
        inputForm.add(new EmailTextField("email"));
        inputForm.add(new TextField<String>("phone"));
        inputForm.add(new TextField<String>("maritalStatus"));
        inputForm.add(new TextField<Integer>("birthYear"));
        inputForm.add(new DateTextField("earlyStartDate"));
        inputForm.add(new TextField<Double>("salaryExpectation"));
    }
}
