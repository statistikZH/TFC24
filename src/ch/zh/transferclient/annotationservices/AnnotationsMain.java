/*
 * Copyright 2018-2020 Statistisches Amt des Kantons Zürich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.zh.transferclient.annotationservices;

/**
 * 
 * This class is used to modify the javadoc-author and javadoc-version of all source files of the Transfer-Client.
 * 
 * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)
 * @version 2.4
 *
 */
public class AnnotationsMain
    {
    
    /**
     * Constructs an AnnotationsMain object.
     */
    private AnnotationsMain()
        {
        //see also https://stackoverflow.com/questions/31409982/java-best-practice-class-with-only-static-methods
        }
    
    
    
    /**
     * Licence information to be used.
     */
    protected static final String LICENCEINFO_1FSTLINE   = " * Copyright 2018-2020 Statistisches Amt des Kantons Zürich";
    
    /**
     * Author to be used for source code files.
     */
    protected static final String AUTHOR                 = " * @author  Daniel Bierer (Statistisches Amt des Kantons Zürich)";
    
    /**
     * Version to be used for source code files.
     */
    protected static final String VERSION                = " * @version 2.4";
    
    /**
     * Author to be used for package html files.
     */
    protected static final String PACKAGES_HTML_AUTHOR   = "@author  Daniel Bierer (Statistisches Amt des Kantons Zürich)";
    
    
    /**
     * Version to be used for package html files.
     */
    protected static final String PACKAGES_HTML_VERSION  = "@version 2.4";
    
    
    /**
     * Starts the application.
     * 
     * 
     * @param  args      Command-line arguments.
     * @throws Exception An exception can be thrown.
     */
    public static void main(String[] args) throws Exception
        {
        
        // Writing Backups
        //AnnotationsBackupService.backup_to_src_old();
        
        AnnotationsUpdate.update("src/ch/zh/transferclient/controller");
        AnnotationsUpdate.update("src/ch/zh/transferclient/gui");
        AnnotationsUpdate.update("src/ch/zh/transferclient/main");
        AnnotationsUpdate.update("src/ch/zh/transferclient/processing");
        AnnotationsUpdate.update("src/ch/zh/transferclient/properties");
        AnnotationsUpdate.update("src/ch/zh/transferclient/util");
        
        }
        
    }
