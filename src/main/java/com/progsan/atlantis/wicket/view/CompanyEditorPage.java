package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.jpa.model.CompanyEntity;
import com.progsan.atlantis.jpa.model.IndustryEntity;
import com.progsan.atlantis.jpa.service.CompanyService;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Erdal on 24.04.2016.
 */
public class CompanyEditorPage extends BaseWebPage {
    private final Logger LOGGER = LoggerFactory.getLogger(CompanyEditorPage.class);
    private final FileUploadField logoUploadField;

    public CompanyEditorPage(PageParameters parameters) {
        super(parameters);
        Form<CompanyEntity> companyForm = new Form<CompanyEntity>("companyForm", new CompoundPropertyModel<CompanyEntity>(new IModel<CompanyEntity>() {
            @Override
            public CompanyEntity getObject() {
                CompanyEntity companyEntity = getSessionData().getCompanyEntity();

                return companyEntity;
            }

            @Override
            public void setObject(CompanyEntity object) {
                getSessionData().setCompanyEntity(object);
            }

            @Override
            public void detach() {

            }
        })){
            protected void onSubmit(){
                CompanyService companyService= new CompanyService(getEntityManagerFactory());
                getSessionData().setCompanyEntity(companyService.save(getSessionData().getCompanyEntity()));
                getPage().info("changes committed into database.");
            }
            protected void onError(){
                LOGGER.warn("error on address form submit!");
            }
        };
        companyForm.add(new TextField<String>("name"));
        companyForm.add(new TextField<String>("description"));
        companyForm.add(new TextField<String>("companySize"));
        companyForm.add(new DropDownChoice<IndustryEntity>("industry", getIndustryList(), new IChoiceRenderer<IndustryEntity>() {
            @Override
            public Object getDisplayValue(IndustryEntity object) {
                return object.getName();
            }

            @Override
            public String getIdValue(IndustryEntity object, int index) {
                return object.getIndustryId().toString();
            }

            @Override
            public IndustryEntity getObject(String id, IModel<? extends List<? extends IndustryEntity>> choices) {
                for (IndustryEntity industryEntity: choices.getObject()){
                    if (id.equals(industryEntity.getIndustryId().toString()))
                        return industryEntity;
                }
                return null;
            }
        }));
        IResource logoResource = new DynamicImageResource() {
            @Override
            protected byte[] getImageData(IResource.Attributes attributes) {
                CompanyEntity companyEntity = getSessionData().getCompanyEntity();
                if (companyEntity != null && companyEntity.getLogo() != null)
                    return companyEntity.getLogo().getData();
                else
                    return null;
            }
        };

        companyForm.add(new Image("logoImg", logoResource));
        companyForm.setMultiPart(true);
        companyForm.setMaxSize(Bytes.megabytes(16));

        logoUploadField = new FileUploadField("logo");
        companyForm.add(logoUploadField);
        this.add(companyForm);
    }

    private List<? extends IndustryEntity> getIndustryList() {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        try{
            TypedQuery<IndustryEntity> q = entityManager.createNamedQuery("industry.list", IndustryEntity.class);
            return q.getResultList();
        }finally {
            entityManager.close();
        }
    }
}
