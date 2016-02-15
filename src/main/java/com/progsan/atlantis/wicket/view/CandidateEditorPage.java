package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.jpa.model.CandidateEntity;
import com.progsan.atlantis.jpa.service.CandidateService;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;


/**
 * Created by Erdal on 08.02.2016.
 */
public class CandidateEditorPage extends BaseWebPage {
    private final Logger LOGGER = LoggerFactory.getLogger(CandidateEditorPage.class);

    public CandidateEditorPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize(){
        super.onInitialize();
        Form<CandidateEntity> inputForm = new Form<CandidateEntity>("inputForm", new CompoundPropertyModel<>(new IModel<CandidateEntity>() {
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
        })){

            protected void onSubmit(){
                CandidateService candidateService = new CandidateService(getEntityManagerFactory());
                candidateService.save(getSessionData().getCandidateEntity());
            }
            protected void onError(){
                LOGGER.warn("error on form submit!");
            }
        };
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

        this.add(new FeedbackPanel("feedback"));
    }

}
