/*
 * Copyright 2015 Henry Addo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
