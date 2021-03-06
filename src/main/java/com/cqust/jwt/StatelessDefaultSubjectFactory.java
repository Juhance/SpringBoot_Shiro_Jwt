package com.cqust.jwt;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        // ²»´´½¨session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
