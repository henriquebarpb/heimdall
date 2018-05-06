
package br.com.conductor.heimdall.api.security;

/*-
 * =========================LICENSE_START==================================
 * heimdall-api
 * ========================================================================
 * Copyright (C) 2018 Conductor Tecnologia SA
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ==========================LICENSE_END===================================
 */

/**
 * <h1>CredentialSecurity</h1><br/>
 * 
 * Provides methods to retrive the username and password of a Credential.
 *
 * @author Marcos Filho
 *
 */
public interface CredentialSecurity {

	 /**
	  * Returns the username.
	  * 
	  * @return String - username
	  */
     String getUserName();
     
     /**
	  * Returns the password.
	  * 
	  * @return String - password
	  */
     String getPassword();
}
