package org.hisoka.discovery;

import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/3/23
 * @copyright: 2016 All rights reserved.
 */
@JsonRootName("details")
public class InstanceDetails {

    private String description;

    public InstanceDetails() {
        this("");
    }

    public InstanceDetails(String description) {
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
