package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.jpa.model.CandidateEntity;
import com.progsan.atlantis.jpa.model.ImageEntity;
import com.progsan.atlantis.jpa.service.CandidateService;
import org.apache.commons.io.IOUtils;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


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
        IResource imageResource = new DynamicImageResource() {
            @Override
            protected byte[] getImageData(IResource.Attributes attributes) {
                CandidateEntity candidateEntity = getSessionData().getCandidateEntity();
                if (candidateEntity.getPhoto() != null)
                    return candidateEntity.getPhoto().getData();
                else
                    return null;
            }
        };
        Image image = new Image("photo", imageResource);
        this.add(image);
        this.add(new UpdatePhotoForm("updatePhotoForm"));

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


    public class UpdatePhotoForm extends Form<Void> {
        private final FileUploadField fileUploadField;

        public UpdatePhotoForm(String id) {
            super(id);
            setMultiPart(true);
            setMaxSize(Bytes.megabytes(16));

            fileUploadField = new FileUploadField("updatePhoto");
            add(fileUploadField);
        }
        @Override
        protected void onSubmit()
        {
            final List<FileUpload> uploads = fileUploadField.getFileUploads();
            if (uploads != null){
                for (FileUpload upload : uploads){
                    EntityManager entityManager = getEntityManagerFactory().createEntityManager();

                    entityManager.getTransaction().begin();
                    try {
                        CandidateEntity candidateEntity = getSessionData().getCandidateEntity();
                        if (candidateEntity.getPhoto() == null){
                            candidateEntity.setPhoto(new ImageEntity());
                            entityManager.persist(candidateEntity.getPhoto());
                        }

                        // set client id from a drop down choice "brands"

                        candidateEntity.getPhoto().setModifiedOn(new Timestamp((new Date()).getTime()));
                        candidateEntity.getPhoto().setFileName(upload.getClientFileName());
                        candidateEntity.getPhoto().setData(IOUtils.toByteArray(upload.getInputStream()));
                        candidateEntity.setPhoto(entityManager.merge(candidateEntity.getPhoto()));
                        candidateEntity = entityManager.merge(candidateEntity);
                        getSessionData().setCandidateEntity(candidateEntity);
                        entityManager.getTransaction().commit();;
                        getPage().info("saved file: " + upload.getClientFileName());
                    } catch (IOException e) {
                        if (entityManager.getTransaction().isActive())
                            entityManager.getTransaction().rollback();
                        throw new IllegalStateException("Unable to write file", e);
                    }finally {
                        if (entityManager.getTransaction().isActive())
                            entityManager.getTransaction().rollback();
                    }
                }
            }
        }
    }
}
