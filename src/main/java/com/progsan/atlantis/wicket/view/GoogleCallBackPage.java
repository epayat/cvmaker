package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.oauth.GoogleAuthHelper;
import com.progsan.atlantis.oauth.GoogleUserInfo;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import java.io.IOException;

/**
 * Created by Erdal on 14.02.2016.
 */
public class GoogleCallBackPage extends BaseWebPage {
    public GoogleCallBackPage(PageParameters parameters) throws IOException {
        super(parameters);
        StringValue authCode = parameters.get("code");
        if (!authCode.isEmpty()){
            GoogleAuthHelper authHelper = new GoogleAuthHelper();

            GoogleUserInfo userInfo = authHelper.getUserInfo(authCode.toString());
            System.out.println(userInfo);

            getUserSession().authenticateUser(userInfo);

            setResponsePage(CandidateEditorPage.class);
        }
    }
}
