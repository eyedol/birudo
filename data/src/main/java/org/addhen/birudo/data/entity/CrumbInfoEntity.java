package org.addhen.birudo.data.entity;


public class CrumbInfoEntity {

    private String crumb;

    private String crumbRequestField;

    public String getCrumb() {
        return crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    public boolean isCsrfEnabled() {
        return (crumbRequestField !=null && !crumbRequestField.isEmpty()) &&(crumb !=null && !crumb.isEmpty());
    }

    @Override
    public String toString() {
        return crumbRequestField + ":" + crumb;
    }
}
