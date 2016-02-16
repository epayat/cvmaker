package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.jpa.model.AddressEntity;
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

        Form<CandidateEntity> candidateForm = new Form<CandidateEntity>("candidateForm", new CompoundPropertyModel<>(new IModel<CandidateEntity>() {
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
                getPage().info("changes committed into database.");
            }
            protected void onError(){
                LOGGER.warn("error on candidate form submit!");
            }
        };
        this.add(candidateForm);

        candidateForm.add(new TextField<String>("careerDesc"));
        candidateForm.add(new TextField<String>("firstName"));
        candidateForm.add(new TextField<String>("lastName"));
        candidateForm.add(new EmailTextField("email"));
        candidateForm.add(new TextField<String>("phone"));
        candidateForm.add(new TextField<String>("maritalStatus"));
        candidateForm.add(new TextField<Integer>("birthYear"));
        candidateForm.add(new DateTextField("earlyStartDate"));
        candidateForm.add(new TextField<Double>("salaryExpectation"));

        Form<AddressEntity> addressForm = new Form<AddressEntity>("addressForm", new CompoundPropertyModel<AddressEntity>(new IModel<AddressEntity>() {
            @Override
            public AddressEntity getObject() {
                CandidateEntity candidateEntity = getSessionData().getCandidateEntity();
                if (candidateEntity.getAddress() == null){
                    candidateEntity.setAddress(new AddressEntity());
                }
                return candidateEntity.getAddress();
            }

            @Override
            public void setObject(AddressEntity object) {
                CandidateEntity candidateEntity = getSessionData().getCandidateEntity();
                candidateEntity.setAddress(object);
            }

            @Override
            public void detach() {

            }
        })) {
            protected void onSubmit(){
                CandidateService candidateService = new CandidateService(getEntityManagerFactory());
                getSessionData().setCandidateEntity(candidateService.save(getSessionData().getCandidateEntity()));
                getPage().info("changes committed into database.");
            }
            protected void onError(){
                LOGGER.warn("error on address form submit!");
            }
        };

        addressForm.add(new TextField<String>("street1"));
        addressForm.add(new TextField<String>("street2"));
        addressForm.add(new TextField<String>("houseNr"));
        addressForm.add(new TextField<String>("postalCode"));
        addressForm.add(new TextField<String>("city"));
        addressForm.add(new TextField<String>("country"));
        this.add(addressForm);
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
                        entityManager.close();
                    }
                }
            }
        }
    }
}
