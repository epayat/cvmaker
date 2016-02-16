package com.progsan.atlantis.wicket.view;

import com.progsan.atlantis.IUserInfo;
import com.progsan.atlantis.oauth.FacebookAuthHelper;;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

/**
 * Created by Erdal on 16.02.2016.
 */
public class CallBackPageForFacebook extends BaseWebPage {
    public CallBackPageForFacebook(PageParameters parameters) {
        super(parameters);

        StringValue authCode = parameters.get("code");
        if (!authCode.isEmpty()){
            FacebookAuthHelper authHelper = new FacebookAuthHelper();

            IUserInfo userInfo = authHelper.getUserInfo(authCode.toString());

            System.out.println(userInfo);

            getUserSession().authenticateUser(userInfo);

            setResponsePage(CandidateEditorPage.class);
        }
    }
}
