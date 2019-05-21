package com.pad.xmen.ale.sessions.models;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 2019-05-21
 */
public class Action {

    private ActionKey key;

    private String parameters;

    public Action() {
    }

    public Action(ActionKey key, String parameters) {
        this.key = key;
        this.parameters = parameters;
    }

    public ActionKey getKey() {
        return key;
    }

    public void setKey(ActionKey key) {
        this.key = key;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        if(parameters != null) {
            return key + " with arguments '" + parameters + "'";
        }
        else {
            return key.toString();
        }
    }
}
